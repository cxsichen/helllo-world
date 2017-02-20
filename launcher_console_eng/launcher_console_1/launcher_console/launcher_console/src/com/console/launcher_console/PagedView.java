package com.console.launcher_console;

import java.util.ArrayList;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class PagedView extends ViewGroup {  
    private static final int SCREEN_SCROLLED_MIN_VERSION = 11;  
    private static float TRANSITION_SCALE_FACTOR = 0.74f;  
    private AccelerateInterpolator mAlphaInterpolator = new AccelerateInterpolator(  
            0.9f);  
    private DecelerateInterpolator mLeftScreenAlphaInterpolator = new DecelerateInterpolator(  
            4);  
    private static final boolean PERFORM_OVERSCROLL_ROTATION = true;  
    private static float CAMERA_DISTANCE = 6500;  
    private static float TRANSITION_PIVOT = 0f;  
    private static float TRANSITION_MAX_ROTATION = 22;  
  
    protected static final int INVALID_PAGE = -1;  
  
    // the min drag distance for a fling to register, to prevent random page  
    // shifts  
    private static final int MIN_LENGTH_FOR_FLING = 25;  
  
    protected static final int PAGE_SNAP_ANIMATION_DURATION = 550;  
    protected static final int SLOW_PAGE_SNAP_ANIMATION_DURATION = 950;  
    protected static final float NANOTIME_DIV = 1000000000.0f;  
  
    private static final float OVERSCROLL_ACCELERATE_FACTOR = 2;  
  
    private static final float RETURN_TO_ORIGINAL_PAGE_THRESHOLD = 0.33f;  
    // The page is moved more than halfway, automatically move to the next page  
    // on touch up.  
    private static final float SIGNIFICANT_MOVE_THRESHOLD = 0.4f;  
  
    // The following constants need to be scaled based on density. The scaled  
    // versions will be  
    // assigned to the corresponding member variables below.  
    private static final int FLING_THRESHOLD_VELOCITY = 500;  
    private static final int MIN_SNAP_VELOCITY = 1500;  
    private static final int MIN_FLING_VELOCITY = 250;  
  
    static final int AUTOMATIC_PAGE_SPACING = -1;  
  
    protected int mFlingThresholdVelocity;  
    protected int mMinFlingVelocity;  
    protected int mMinSnapVelocity;  
  
    protected float mDensity;  
    protected float mSmoothingTime;  
    protected float mTouchX;  
  
    protected boolean mFirstLayout = true;  
  
    protected int mCurrentPage;  
    protected int mNextPage = INVALID_PAGE;  
    protected int mMaxScrollX;  
    protected Scroller mScroller;  
    private VelocityTracker mVelocityTracker;  
  
    private float mDownMotionX;  
    protected float mLastMotionX;  
    protected float mLastMotionXRemainder;  
    protected float mLastMotionY;  
    protected float mTotalMotionX;  
    private int mLastScreenCenter = -1;  
    private int[] mChildOffsets;  
    private int[] mChildRelativeOffsets;  
    private int[] mChildOffsetsWithLayoutScale;  
  
    protected final static int TOUCH_STATE_REST = 0;  
    protected final static int TOUCH_STATE_SCROLLING = 1;  
    protected final static int TOUCH_STATE_PREV_PAGE = 2;  
    protected final static int TOUCH_STATE_NEXT_PAGE = 3;  
    protected final static float ALPHA_QUANTIZE_LEVEL = 0.0001f;  
  
    protected int mTouchState = TOUCH_STATE_REST;  
    protected boolean mForceScreenScrolled = false;  
  
    protected int mTouchSlop;  
    private int mPagingTouchSlop;  
    private int mMaximumVelocity;  
    protected int mPageSpacing;  
    protected int mPageLayoutPaddingTop;  
    protected int mPageLayoutPaddingBottom;  
    protected int mPageLayoutPaddingLeft;  
    protected int mPageLayoutPaddingRight;  
    protected int mPageLayoutWidthGap;  
    protected int mPageLayoutHeightGap;  
    protected int mCellCountX = 0;  
    protected int mCellCountY = 0;  
    protected boolean mCenterPagesVertically;  
    protected boolean mAllowOverScroll = true;  
    protected int mUnboundedScrollX;  
    protected int[] mTempVisiblePagesRange = new int[2];  
    protected boolean mForceDrawAllChildrenNextFrame;  
  
    // mOverScrollX is equal to getScrollX() when we're within the normal scroll  
    // range. Otherwise  
    // it is equal to the scaled overscroll position. We use a separate value so  
    // as to prevent  
    // the screens from continuing to translate beyond the normal bounds.  
    protected int mOverScrollX;  
  
    // parameter that adjusts the layout to be optimized for pages with that  
    // scale factor  
    protected float mLayoutScale = 1.0f;  
  
    protected static final int INVALID_POINTER = -1;  
  
    protected int mActivePointerId = INVALID_POINTER;  
  
    protected ArrayList<Boolean> mDirtyPageContent;  
  
    // If true, syncPages and syncPageItems will be called to refresh pages  
    protected boolean mContentIsRefreshable = true;  
  
    // If true, modify alpha of neighboring pages as user scrolls left/right  
    protected boolean mFadeInAdjacentScreens = true;  
  
    // It true, use a different slop parameter (pagingTouchSlop = 2 * touchSlop)  
    // for deciding  
    // to switch to a new page  
    protected boolean mUsePagingTouchSlop = true;  
  
    // If true, the subclass should directly update scrollX itself in its  
    // computeScroll method  
    // (SmoothPagedView does this)  
    protected boolean mDeferScrollUpdate = false;  
  
    protected boolean mIsPageMoving = false;  
  
    // All syncs and layout passes are deferred until data is ready.  
    protected boolean mIsDataReady = false;  
  
    // If set, will defer loading associated pages until the scrolling settles  
  
    // Begin Immersion changes  
    protected boolean mHapticFlingStarted = false;  
    protected boolean mHapticCaptureFling = true;  
    protected int mLastHapticScreen = 0;  
    ZInterpolator mZInterpolator = new ZInterpolator(0.5f);  
  
    // End Immersion changes  
  
    public PagedView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        init();  
    }  
  
    public PagedView(Context context, AttributeSet attrs) {  
        this(context, attrs, 0);  
    }  
  
    public PagedView(Context context) {  
        this(context, null);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
  
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);  
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);  
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);  
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);  
        if (widthMode != MeasureSpec.EXACTLY) {  
            throw new IllegalStateException(  
                    "Workspace can only be used in EXACTLY mode.");  
        }  
  
        // Return early if we aren't given a proper dimension  
        if (widthSize <= 0 || heightSize <= 0) {  
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
            return;  
        }  
  
        /* 
         * Allow the height to be set as WRAP_CONTENT. This allows the 
         * particular case of the All apps view on XLarge displays to not take 
         * up more space then it needs. Width is still not allowed to be set as 
         * WRAP_CONTENT since many parts of the code expect each page to have 
         * the same width. 
         */  
        int maxChildHeight = 0;  
  
        final int verticalPadding = getPaddingTop() + getPaddingBottom();  
        final int horizontalPadding = getPaddingLeft() + getPaddingRight();  
  
        // The children are given the same width and height as the workspace  
        // unless they were set to WRAP_CONTENT  
        final int childCount = getChildCount();  
        for (int i = 0; i < childCount; i++) {  
            // disallowing padding in paged view (just pass 0)  
            final View child = getPageAt(i);  
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();  
  
            int childWidthMode;  
            if (lp.width == LayoutParams.WRAP_CONTENT) {  
                childWidthMode = MeasureSpec.AT_MOST;  
            } else {  
                childWidthMode = MeasureSpec.EXACTLY;  
            }  
  
            int childHeightMode;  
            if (lp.height == LayoutParams.WRAP_CONTENT) {  
                childHeightMode = MeasureSpec.AT_MOST;  
            } else {  
                childHeightMode = MeasureSpec.EXACTLY;  
            }  
  
            final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(  
                    widthSize - horizontalPadding, childWidthMode);  
            final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(  
                    heightSize - verticalPadding, childHeightMode);  
  
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);  
            maxChildHeight = Math  
                    .max(maxChildHeight, child.getMeasuredHeight());  
        }  
  
        if (heightMode == MeasureSpec.AT_MOST) {  
            heightSize = maxChildHeight + verticalPadding;  
        }  
  
        setMeasuredDimension(widthSize, heightSize);  
  
        // We can't call getChildOffset/getRelativeChildOffset until we set the  
        // measured dimensions.  
        // We also wait until we set the measured dimensions before flushing the  
        // cache as well, to  
        // ensure that the cache is filled with good values.  
        invalidateCachedOffsets();  
  
        if (childCount > 0) {  
  
            // Calculate the variable page spacing if necessary  
            if (mPageSpacing == AUTOMATIC_PAGE_SPACING) {  
                // The gap between pages in the PagedView should be equal to the  
                // gap from the page  
                // to the edge of the screen (so it is not visible in the  
                // current screen). To  
                // account for unequal padding on each side of the paged view,  
                // we take the maximum  
                // of the left/right gap and use that as the gap between each  
                // page.  
                int offset = getRelativeChildOffset(0);  
                int spacing = Math.max(offset,  
                        widthSize - offset - getChildAt(0).getMeasuredWidth());  
                setPageSpacing(spacing);  
            }  
        }  
  
        if (childCount > 0) {  
            mMaxScrollX = getChildOffset(childCount - 1)  
                    - getRelativeChildOffset(childCount - 1);  
        } else {  
            mMaxScrollX = 0;  
        }  
    }  
  
    @Override  
    protected void onLayout(boolean changed, int l, int t, int r, int b) {  
  
        final int verticalPadding = getPaddingTop() + getPaddingBottom();  
        final int childCount = getChildCount();  
        int childLeft = getRelativeChildOffset(0);  
  
        for (int i = 0; i < childCount; i++) {  
            final View child = getPageAt(i);  
            if (child.getVisibility() != View.GONE) {  
                final int childWidth = getScaledMeasuredWidth(child);  
                final int childHeight = child.getMeasuredHeight();  
                int childTop = getPaddingTop();  
                if (mCenterPagesVertically) {  
                    childTop += ((getMeasuredHeight() - verticalPadding) - childHeight) / 2;  
                }  
                 
                child.layout(childLeft, childTop,  
                        childLeft + child.getMeasuredWidth(), childTop  
                                + childHeight);  
                childLeft += childWidth + mPageSpacing;  
            }  
        }  
  
        if (mFirstLayout && mCurrentPage >= 0 && mCurrentPage < getChildCount()) {  
            updateCurrentPageScroll();  
            mFirstLayout = false;  
        }  
    }  
  
    View getPageAt(int index) {  
        return getChildAt(index);  
    }  
      
    public int getmCurrentPage() {  
        return mCurrentPage;  
    }  
  
    protected int getChildWidth(int index) {  
        final int measuredWidth = getPageAt(index).getMeasuredWidth();  
        return measuredWidth;  
    }  
  
    protected int getScaledMeasuredWidth(View child) {  
        final int measuredWidth = child.getMeasuredWidth();  
        final int minWidth = 0;  
        final int maxWidth = (minWidth > measuredWidth) ? minWidth  
                : measuredWidth;  
        return (int) (maxWidth * mLayoutScale + 0.5f);  
    }  
  
    protected void updateCurrentPageScroll() {  
        int offset = getChildOffset(mCurrentPage);  
        int relOffset = getRelativeChildOffset(mCurrentPage);  
        int newX = offset - relOffset;  
        scrollTo(newX, 0);  
        mScroller.setFinalX(newX);  
        mScroller.forceFinished(true);  
    }  
  
    protected int getChildOffset(int index) {  
        int[] childOffsets = Float.compare(mLayoutScale, 1f) == 0 ? mChildOffsets  
                : mChildOffsetsWithLayoutScale;  
  
        if (childOffsets != null && childOffsets[index] != -1) {  
            return childOffsets[index];  
        } else {  
            if (getChildCount() == 0)  
                return 0;  
  
            int offset = getRelativeChildOffset(0);  
            for (int i = 0; i < index; ++i) {  
                offset += getScaledMeasuredWidth(getPageAt(i)) + mPageSpacing;  
            }  
            if (childOffsets != null) {  
                childOffsets[index] = offset;  
            }  
            return offset;  
        }  
    }  
  
    protected int getRelativeChildOffset(int index) {  
        if (mChildRelativeOffsets != null && mChildRelativeOffsets[index] != -1) {  
            return mChildRelativeOffsets[index];  
        } else {  
            final int padding = getPaddingLeft() + getPaddingRight();  
            final int offset = getPaddingLeft()  
                    + (getMeasuredWidth() - padding - getChildWidth(index)) / 2;  
            if (mChildRelativeOffsets != null) {  
                mChildRelativeOffsets[index] = offset;  
            }  
            return offset;  
        }  
    }  
  
    public void setPageSpacing(int pageSpacing) {  
        mPageSpacing = pageSpacing;  
        invalidateCachedOffsets();  
    }  
  
    protected void invalidateCachedOffsets() {  
        int count = getChildCount();  
        if (count == 0) {  
            mChildOffsets = null;  
            mChildRelativeOffsets = null;  
            mChildOffsetsWithLayoutScale = null;  
            return;  
        }  
  
        mChildOffsets = new int[count];  
        mChildRelativeOffsets = new int[count];  
        mChildOffsetsWithLayoutScale = new int[count];  
        for (int i = 0; i < count; i++) {  
            mChildOffsets[i] = -1;  
            mChildRelativeOffsets[i] = -1;  
            mChildOffsetsWithLayoutScale[i] = -1;  
        }  
    }  
  
    protected void init() {  
        mDirtyPageContent = new ArrayList<Boolean>();  
        mDirtyPageContent.ensureCapacity(32);  
        mScroller = new Scroller(getContext(), new ScrollInterpolator());  
        mCurrentPage = 1;     //modify by cxs
        mCenterPagesVertically = true;  
  
        final ViewConfiguration configuration = ViewConfiguration  
                .get(getContext());  
        mTouchSlop = configuration.getScaledTouchSlop();  
        mPagingTouchSlop = configuration.getScaledPagingTouchSlop();  
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();  
        mDensity = getResources().getDisplayMetrics().density;  
  
        mFlingThresholdVelocity = (int) (FLING_THRESHOLD_VELOCITY * mDensity);  
        mMinFlingVelocity = (int) (MIN_FLING_VELOCITY * mDensity);  
        mMinSnapVelocity = (int) (MIN_SNAP_VELOCITY * mDensity);  
    }  
  
    private static class ScrollInterpolator implements Interpolator {  
        public ScrollInterpolator() {  
        }  
  
        public float getInterpolation(float t) {  
            t -= 1.0f;  
            return t * t * t * t * t + 1;  
        }  
    }  
  
    @Override  
    public boolean onInterceptTouchEvent(MotionEvent ev) {  
        /* 
         * This method JUST determines whether we want to intercept the motion. 
         * If we return true, onTouchEvent will be called and we do the actual 
         * scrolling there. 
         */  
        acquireVelocityTrackerAndAddMovement(ev);  
  
        // Skip touch handling if there are no pages to swipe  
        if (getChildCount() <= 0)  
            return super.onInterceptTouchEvent(ev);  
  
        /* 
         * Shortcut the most recurring case: the user is in the dragging state 
         * and he is moving his finger. We want to intercept this motion. 
         */  
        final int action = ev.getAction();  
        if ((action == MotionEvent.ACTION_MOVE)  
                && (mTouchState == TOUCH_STATE_SCROLLING)) {  
            return true;  
        }  
  
        switch (action & MotionEvent.ACTION_MASK) {  
        case MotionEvent.ACTION_MOVE: {  
            /* 
             * mIsBeingDragged == false, otherwise the shortcut would have 
             * caught it. Check whether the user has moved far enough from his 
             * original down touch. 
             */  
            if (mActivePointerId != INVALID_POINTER) {  
                determineScrollingStart(ev);  
                break;  
            }  
            // if mActivePointerId is INVALID_POINTER, then we must have missed  
            // an ACTION_DOWN  
            // event. in that case, treat the first occurence of a move event as  
            // a ACTION_DOWN  
            // i.e. fall through to the next case (don't break)  
            // (We sometimes miss ACTION_DOWN events in Workspace because it  
            // ignores all events  
            // while it's small- this was causing a crash before we checked for  
            // INVALID_POINTER)  
        }  
  
        case MotionEvent.ACTION_DOWN: {  
            final float x = ev.getX();  
            final float y = ev.getY();  
            // Remember location of down touch  
            mDownMotionX = x;  
            mLastMotionX = x;  
            mLastMotionY = y;  
            mLastMotionXRemainder = 0;  
            mTotalMotionX = 0;  
            mActivePointerId = ev.getPointerId(0);  
  
            /* 
             * If being flinged and user touches the screen, initiate drag; 
             * otherwise don't. mScroller.isFinished should be false when being 
             * flinged. 
             */  
            final int xDist = Math.abs(mScroller.getFinalX()  
                    - mScroller.getCurrX());  
            final boolean finishedScrolling = (mScroller.isFinished() || xDist < mTouchSlop);  
            if (finishedScrolling) {  
                mTouchState = TOUCH_STATE_REST;  
                mScroller.abortAnimation();  
            } else {  
                mTouchState = TOUCH_STATE_SCROLLING;  
            }  
  
            // check if this can be the beginning of a tap on the side of the  
            // pages  
            // to scroll the current page  
            if (mTouchState != TOUCH_STATE_PREV_PAGE  
                    && mTouchState != TOUCH_STATE_NEXT_PAGE) {  
                if (getChildCount() > 0) {  
                    if (hitsPreviousPage(x, y)) {  
                        mTouchState = TOUCH_STATE_PREV_PAGE;  
                    } else if (hitsNextPage(x, y)) {  
                        mTouchState = TOUCH_STATE_NEXT_PAGE;  
                    }  
                }  
            }  
            break;  
        }  
  
        case MotionEvent.ACTION_UP:  
        case MotionEvent.ACTION_CANCEL:  
            mTouchState = TOUCH_STATE_REST;  
            mActivePointerId = INVALID_POINTER;  
            releaseVelocityTracker();  
            break;  
  
        case MotionEvent.ACTION_POINTER_UP:  
            onSecondaryPointerUp(ev);  
            releaseVelocityTracker();  
            break;  
        }  
  
        /* 
         * The only time we want to intercept motion events is if we are in the 
         * drag mode. 
         */  
        return mTouchState != TOUCH_STATE_REST;  
    }  
  
    protected boolean hitsPreviousPage(float x, float y) {  
        return (x < getRelativeChildOffset(mCurrentPage) - mPageSpacing);  
    }  
  
    protected boolean hitsNextPage(float x, float y) {  
        return (x > (getMeasuredWidth() - getRelativeChildOffset(mCurrentPage) + mPageSpacing));  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent ev) {  
  
        if (getChildCount() <= 0)  
            return super.onTouchEvent(ev);  
  
        acquireVelocityTrackerAndAddMovement(ev);  
  
        final int action = ev.getAction();  
  
        switch (action & MotionEvent.ACTION_MASK) {  
        case MotionEvent.ACTION_DOWN:  
            /* 
             * If being flinged and user touches, stop the fling. isFinished 
             * will be false if being flinged. 
             */  
            if (!mScroller.isFinished()) {  
                mScroller.abortAnimation();  
            }  
  
            // Remember where the motion event started  
            mDownMotionX = mLastMotionX = ev.getX();  
            mLastMotionXRemainder = 0;  
            mTotalMotionX = 0;  
            mActivePointerId = ev.getPointerId(0);  
            if (mTouchState == TOUCH_STATE_SCROLLING) {  
                pageBeginMoving();  
            }  
            break;  
  
        case MotionEvent.ACTION_MOVE:  
            if (mTouchState == TOUCH_STATE_SCROLLING) {  
                // Scroll to follow the motion event  
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);  
                final float x = ev.getX(pointerIndex);  
                final float deltaX = mLastMotionX + mLastMotionXRemainder - x;  
  
                mTotalMotionX += Math.abs(deltaX);  
  
                // Only scroll and update mLastMotionX if we have moved some  
                // discrete amount. We  
                // keep the remainder because we are actually testing if we've  
                // moved from the last  
                // scrolled position (which is discrete).  
                if (Math.abs(deltaX) >= 1.0f) {  
                    mTouchX += deltaX;  
                    mSmoothingTime = System.nanoTime() / NANOTIME_DIV;  
                    if (!mDeferScrollUpdate) {  
                        scrollBy((int) deltaX, 0);  
                    } else {  
                        invalidate();  
                    }  
                    mLastMotionX = x;  
                    mLastMotionXRemainder = deltaX - (int) deltaX;  
                } else {  
                    awakenScrollBars();  
                }  
            } else {  
                determineScrollingStart(ev);  
            }  
            break;  
  
        case MotionEvent.ACTION_UP:  
            if (mTouchState == TOUCH_STATE_SCROLLING) {  
                final int activePointerId = mActivePointerId;  
                final int pointerIndex = ev.findPointerIndex(activePointerId);  
                final float x = ev.getX(pointerIndex);  
                final VelocityTracker velocityTracker = mVelocityTracker;  
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);  
                int velocityX = (int) velocityTracker  
                        .getXVelocity(activePointerId);  
                final int deltaX = (int) (x - mDownMotionX);  
                final int pageWidth = getScaledMeasuredWidth(getPageAt(mCurrentPage));  
                boolean isSignificantMove = Math.abs(deltaX) > pageWidth  
                        * SIGNIFICANT_MOVE_THRESHOLD;  
  
                mTotalMotionX += Math.abs(mLastMotionX + mLastMotionXRemainder  
                        - x);  
  
                boolean isFling = mTotalMotionX > MIN_LENGTH_FOR_FLING  
                        && Math.abs(velocityX) > mFlingThresholdVelocity;  
  
                // In the case that the page is moved far to one direction and  
                // then is flung  
                // in the opposite direction, we use a threshold to determine  
                // whether we should  
                // just return to the starting page, or if we should skip one  
                // further.  
                boolean returnToOriginalPage = false;  
                if (Math.abs(deltaX) > pageWidth  
                        * RETURN_TO_ORIGINAL_PAGE_THRESHOLD  
                        && Math.signum(velocityX) != Math.signum(deltaX)  
                        && isFling) {  
                    returnToOriginalPage = true;  
                }  
  
                int finalPage = 0;  
                // We give flings precedence over large moves, which is why we  
                // short-circuit our  
                // test for a large move if a fling has been registered. That  
                // is, a large  
                // move to the left and fling to the right will register as a  
                // fling to the right.  
                if (((isSignificantMove && deltaX > 0 && !isFling) || (isFling && velocityX > 0))  
                        && mCurrentPage > 0) {  
                    finalPage = returnToOriginalPage ? mCurrentPage  
                            : mCurrentPage - 1;  
                    snapToPageWithVelocity(finalPage, velocityX);  
                } else if (((isSignificantMove && deltaX < 0 && !isFling) || (isFling && velocityX < 0))  
                        && mCurrentPage < getChildCount() - 1) {  
                    finalPage = returnToOriginalPage ? mCurrentPage  
                            : mCurrentPage + 1;  
                    snapToPageWithVelocity(finalPage, velocityX);  
                } else {  
                    snapToDestination();  
                }  
  
            } else if (mTouchState == TOUCH_STATE_PREV_PAGE) {  
                // at this point we have not moved beyond the touch slop  
                // (otherwise mTouchState would be TOUCH_STATE_SCROLLING), so  
                // we can just page  
                int nextPage = Math.max(0, mCurrentPage - 1);  
                if (nextPage != mCurrentPage) {  
                   
                    snapToPage(nextPage);  
                } else {  
                    snapToDestination();  
                }  
            } else if (mTouchState == TOUCH_STATE_NEXT_PAGE) {  
                // at this point we have not moved beyond the touch slop  
                // (otherwise mTouchState would be TOUCH_STATE_SCROLLING), so  
                // we can just page  
                int nextPage = Math.min(getChildCount() - 1, mCurrentPage + 1);  
                if (nextPage != mCurrentPage) {  
                 
                    snapToPage(nextPage);  
                } else {  
                    snapToDestination();  
                }  
            }  
            mTouchState = TOUCH_STATE_REST;  
            mActivePointerId = INVALID_POINTER;  
            releaseVelocityTracker();  
            break;  
  
        case MotionEvent.ACTION_CANCEL:  
            if (mTouchState == TOUCH_STATE_SCROLLING) {  
                snapToDestination();  
            }  
            mTouchState = TOUCH_STATE_REST;  
            mActivePointerId = INVALID_POINTER;  
            releaseVelocityTracker();  
            break;  
  
        case MotionEvent.ACTION_POINTER_UP:  
            onSecondaryPointerUp(ev);  
            break;  
        }  
  
        return true;  
    }  
  
    @Override  
    public boolean onGenericMotionEvent(MotionEvent event) {  
        // TODO   
        if (event.getSource()!= 0) {  
            switch (event.getAction()) {  
            case MotionEvent.ACTION_SCROLL: {  
                // Handle mouse (or ext. device) by shifting the page depending  
                // on the scroll  
                final float vscroll;  
                final float hscroll;  
                if ((event.getMetaState() & KeyEvent.META_SHIFT_ON) != 0) {  
                    vscroll = 0;  
                    hscroll = event.getAxisValue(MotionEvent.AXIS_VSCROLL);  
                } else {  
                    vscroll = -event.getAxisValue(MotionEvent.AXIS_VSCROLL);  
                    hscroll = event.getAxisValue(MotionEvent.AXIS_HSCROLL);  
                }  
                if (hscroll != 0 || vscroll != 0) {  
                    if (hscroll > 0 || vscroll > 0) {  
                        scrollRight();  
                    } else {  
                        scrollLeft();  
                    }  
                    return true;  
                }  
            }  
            }  
        }  
        return super.onGenericMotionEvent(event);  
    }  
  
    private void acquireVelocityTrackerAndAddMovement(MotionEvent ev) {  
        if (mVelocityTracker == null) {  
            mVelocityTracker = VelocityTracker.obtain();  
        }  
        mVelocityTracker.addMovement(ev);  
    }  
  
    private void releaseVelocityTracker() {  
        if (mVelocityTracker != null) {  
            mVelocityTracker.recycle();  
            mVelocityTracker = null;  
        }  
    }  
  
    protected void pageBeginMoving() {  
        if (!mIsPageMoving) {  
            mIsPageMoving = true;  
            onPageBeginMoving();  
            if(onScreenChangeListenerDataLoad!=null)
            	onScreenChangeListenerDataLoad.onScreenChange(mIsPageMoving);
        }  
    }  
  
    protected void pageEndMoving() { 
        if (mIsPageMoving) {  
            mIsPageMoving = false;  
            onPageEndMoving();  
            if(onScreenChangeListenerDataLoad!=null)
            	onScreenChangeListenerDataLoad.onScreenChange(mIsPageMoving);
        }  
    }  
  
    protected boolean isPageMoving() {  
        return mIsPageMoving;  
    }  
  
    // a method that subclasses can override to add behavior  
    protected void onPageBeginMoving() {  
  
        // Begin Immersion changes  
        if (mHapticCaptureFling && mNextPage != mCurrentPage) {  
            if (!mHapticFlingStarted) {  
                mHapticFlingStarted = true;  
                mLastHapticScreen = mCurrentPage;  
            } else {  
                if ((mNextPage != INVALID_PAGE)  
                        && (mLastHapticScreen != mNextPage)) {  
                }  
                mHapticFlingStarted = false;  
            }  
        }  
        // End Immersion changes  
    }  
  
    // a method that subclasses can override to add behavior  
    protected void onPageEndMoving() {  
        // Reset the Flags  
        mHapticCaptureFling = true;  
        mHapticFlingStarted = false;  
        // End Immersion changes  
        mForceDrawAllChildrenNextFrame = true;  
  
    }  
  
    protected void determineScrollingStart(MotionEvent ev) {  
        determineScrollingStart(ev, 1.0f);  
    }  
  
    /* 
     * Determines if we should change the touch state to start scrolling after 
     * the user moves their touch point too far. 
     */  
    protected void determineScrollingStart(MotionEvent ev, float touchSlopScale) {  
        /* 
         * Locally do absolute value. mLastMotionX is set to the y value of the 
         * down event. 
         */  
        final int pointerIndex = ev.findPointerIndex(mActivePointerId);  
        if (pointerIndex == -1) {  
            return;  
        }  
        final float x = ev.getX(pointerIndex);  
        final float y = ev.getY(pointerIndex);  
        final int xDiff = (int) Math.abs(x - mLastMotionX);  
        final int yDiff = (int) Math.abs(y - mLastMotionY);  
  
        final int touchSlop = Math.round(touchSlopScale * mTouchSlop);  
        boolean xPaged = xDiff > mPagingTouchSlop;  
        boolean xMoved = xDiff > touchSlop;  
        boolean yMoved = yDiff > touchSlop;  
  
        if (xMoved || xPaged || yMoved) {  
            if (mUsePagingTouchSlop ? xPaged : xMoved) {  
                // Scroll if the user moved far enough along the X axis  
                mTouchState = TOUCH_STATE_SCROLLING;  
                mTotalMotionX += Math.abs(mLastMotionX - x);  
                mLastMotionX = x;  
                mLastMotionXRemainder = 0;  
                mTouchX = getScrollX();  
                mSmoothingTime = System.nanoTime() / NANOTIME_DIV;  
                pageBeginMoving();  
            }  
        }  
  
    }  
  
    protected void snapToPageWithVelocity(int whichPage, int velocity) {  
        whichPage = Math.max(0, Math.min(whichPage, getChildCount() - 1));  
        int halfScreenSize = getMeasuredWidth() / 2;  
  
        final int newX = getChildOffset(whichPage)  
                - getRelativeChildOffset(whichPage);  
        int delta = newX - mUnboundedScrollX;  
        int duration = 0;  
  
        if (Math.abs(velocity) < mMinFlingVelocity) {  
            // If the velocity is low enough, then treat this more as an  
            // automatic page advance  
            // as opposed to an apparent physical response to flinging  
            snapToPage(whichPage, PAGE_SNAP_ANIMATION_DURATION);  
            return;  
        }  
  
        // Here we compute a "distance" that will be used in the computation of  
        // the overall  
        // snap duration. This is a function of the actual distance that needs  
        // to be traveled;  
        // we keep this value close to half screen size in order to reduce the  
        // variance in snap  
        // duration as a function of the distance the page needs to travel.  
        float distanceRatio = Math.min(1f, 1.0f * Math.abs(delta)  
                / (2 * halfScreenSize));  
        float distance = halfScreenSize + halfScreenSize  
                * distanceInfluenceForSnapDuration(distanceRatio);  
  
        velocity = Math.abs(velocity);  
        velocity = Math.max(mMinSnapVelocity, velocity);  
  
        // we want the page's snap velocity to approximately match the velocity  
        // at which the  
        // user flings, so we scale the duration by a value near to the  
        // derivative of the scroll  
        // interpolator at zero, ie. 5. We use 4 to make it a little slower.  
        duration = 4 * Math.round(1000 * Math.abs(distance / velocity));  
  
        snapToPage(whichPage, delta, duration);  
    }  
  
    public void snapToPage(int whichPage) {  
        snapToPage(whichPage, PAGE_SNAP_ANIMATION_DURATION);  
    }  
  
    protected void snapToPage(int whichPage, int duration) {  
        whichPage = Math.max(0, Math.min(whichPage, getPageCount() - 1));  
        int newX = getChildOffset(whichPage)  
                - getRelativeChildOffset(whichPage);  
        final int sX = mUnboundedScrollX;  
        final int delta = newX - sX;  
        snapToPage(whichPage, delta, duration);  
    }  
  
    protected void snapToPage(int whichPage, int delta, int duration) {  
        mNextPage = whichPage;  
        //wufen  
        if(onScreenChangeListener!=null)
        onScreenChangeListener.onScreenChange(mNextPage);  
          
        View focusedChild = getFocusedChild();  
        if (focusedChild != null && whichPage != mCurrentPage  
                && focusedChild == getPageAt(mCurrentPage)) {  
            focusedChild.clearFocus();  
        }  
  
        pageBeginMoving();  
        awakenScrollBars(duration);  
        if (duration == 0) {  
            duration = Math.abs(delta);  
        }  
  
        if (!mScroller.isFinished())  
            mScroller.abortAnimation();  
        mScroller.startScroll(mUnboundedScrollX, 0, delta, 0, duration);  
        // Load associated pages immediately if someone else is handling the  
        // scroll, otherwise defer  
        // loading associated pages until the scroll settles  
        invalidate();  
    }  
  
    public void scrollLeft() {  
        if (mScroller.isFinished()) {  
            if (mCurrentPage > 0)  
                snapToPage(mCurrentPage - 1);  
        } else {  
            if (mNextPage > 0)  
                snapToPage(mNextPage - 1);  
        }  
          
    }  
  
    public void scrollRight() {  
        if (mScroller.isFinished()) {  
            if (mCurrentPage < getChildCount() - 1)  
                snapToPage(mCurrentPage + 1);  
        } else {  
            if (mNextPage < getChildCount() - 1)  
                snapToPage(mNextPage + 1);  
        }  
    }  
  
    protected void snapToDestination() {  
        snapToPage(getPageNearestToCenterOfScreen(),  
                PAGE_SNAP_ANIMATION_DURATION);  
    }  
  
    private void onSecondaryPointerUp(MotionEvent ev) {  
        final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;  
        final int pointerId = ev.getPointerId(pointerIndex);  
        if (pointerId == mActivePointerId) {  
            // This was our active pointer going up. Choose a new  
            // active pointer and adjust accordingly.  
            // TODO: Make this decision more intelligent.  
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;  
            mLastMotionX = mDownMotionX = ev.getX(newPointerIndex);  
            mLastMotionY = ev.getY(newPointerIndex);  
            mLastMotionXRemainder = 0;  
            mActivePointerId = ev.getPointerId(newPointerIndex);  
            if (mVelocityTracker != null) {  
                mVelocityTracker.clear();  
            }  
        }  
    }  
  
    float distanceInfluenceForSnapDuration(float f) {  
        f -= 0.5f; // center the values about 0.  
        f *= 0.3f * Math.PI / 2.0f;  
        return (float) Math.sin(f);  
    }  
  
    protected int getPageCount() {  
        return getChildCount();  
    }  
  
    protected int getPageNearestToCenterOfScreen() {  
        int minDistanceFromScreenCenter = Integer.MAX_VALUE;  
        int minDistanceFromScreenCenterIndex = -1;  
        int screenCenter = getScrollX() + (getMeasuredWidth() / 2);  
        final int childCount = getChildCount();  
        for (int i = 0; i < childCount; ++i) {  
            View layout = (View) getPageAt(i);  
            int childWidth = getScaledMeasuredWidth(layout);  
            int halfChildWidth = (childWidth / 2);  
            int childCenter = getChildOffset(i) + halfChildWidth;  
            int distanceFromScreenCenter = Math.abs(childCenter - screenCenter);  
            if (distanceFromScreenCenter < minDistanceFromScreenCenter) {  
                minDistanceFromScreenCenter = distanceFromScreenCenter;  
                minDistanceFromScreenCenterIndex = i;  
            }  
        }  
        return minDistanceFromScreenCenterIndex;  
    }  
  
    protected void dispatchDraw(Canvas canvas) {  
        int halfScreenSize = getMeasuredWidth() / 2;  
        // mOverScrollX is equal to getScrollX() when we're within the normal  
        // scroll range.  
        // Otherwise it is equal to the scaled overscroll position.  
        int screenCenter = mOverScrollX + halfScreenSize;  
  
        if (screenCenter != mLastScreenCenter || mForceScreenScrolled) {  
            // set mForceScreenScrolled before calling screenScrolled so that  
            // screenScrolled can  
            // set it for the next frame  
            mForceScreenScrolled = false;  
            screenScrolled(screenCenter);  
            mLastScreenCenter = screenCenter;  
        }  
  
        // Find out which screens are visible; as an optimization we only call  
        // draw on them  
        final int pageCount = getChildCount();  
        if (pageCount > 0) {  
            getVisiblePages(mTempVisiblePagesRange);  
            final int leftScreen = mTempVisiblePagesRange[0];  
            final int rightScreen = mTempVisiblePagesRange[1];  
            if (leftScreen != -1 && rightScreen != -1) {  
                final long drawingTime = getDrawingTime();  
                // Clip to the bounds  
                canvas.save();  
                canvas.clipRect(getScrollX(), getScrollY(), getScrollX()  
                        + getRight() - getLeft(), getScrollY() + getBottom()  
                        - getTop());  
  
                // On certain graphics drivers, if you draw to a off-screen  
                // buffer that's not  
                // used, it can lead to poor performance. We were running into  
                // this when  
                // setChildrenLayersEnabled was called on a CellLayout; that  
                // triggered a re-draw  
                // of that CellLayout's hardware layer, even if that CellLayout  
                // wasn't visible.  
                // As a fix, below we set pages that aren't going to be rendered  
                // are to be  
                // View.INVISIBLE, preventing re-drawing of their hardware layer  
                for (int i = getChildCount() - 1; i >= 0; i--) {  
                    final View v = getPageAt(i);  
                    if ((leftScreen <= i && i <= rightScreen && shouldDrawChild(v))) {  
                        v.setVisibility(VISIBLE);  
                        drawChild(canvas, v, drawingTime);  
                    } else {  
                        v.setVisibility(INVISIBLE);  
                    }  
                }  
                mForceDrawAllChildrenNextFrame = false;  
                canvas.restore();  
            }  
        }  
    }  
  
    protected void screenScrolled(int screenCenter) {  
  
        for (int i = 0; i < getChildCount(); i++) {  
            View v = getPageAt(i);  
            if (v != null) {  
                float scrollProgress = getScrollProgress(screenCenter, v, i);  
  
                float interpolatedProgress = mZInterpolator  
                        .getInterpolation(Math.abs(Math.min(scrollProgress, 0)));  
                float scale = (1 - interpolatedProgress) + interpolatedProgress  
                        * TRANSITION_SCALE_FACTOR;  
                float translationX = Math.min(0, scrollProgress)  
                        * v.getMeasuredWidth();  
  
                float alpha;  
  
                if (scrollProgress < 0) {  
                    alpha = scrollProgress < 0 ? mAlphaInterpolator  
                            .getInterpolation(1 - Math.abs(scrollProgress))  
                            : 1.0f;  
                            if (i == 0) {  
                                setOverScrollAmount(v, Math.abs(scrollProgress));  
                            }  
                } else {  
                    // On large screens we need to fade the page as it nears its  
                    // leftmost position  
                    alpha = mLeftScreenAlphaInterpolator  
                            .getInterpolation(1 - scrollProgress);  
                    if (i == getChildCount() - 1) {  
                        setOverScrollAmount(v, Math.abs(scrollProgress));  
                    }  
                }  
                int pageWidth = v.getMeasuredWidth();  
                int pageHeight = v.getMeasuredHeight(); 
                int temp=0;
                if (getVersion() >= SCREEN_SCROLLED_MIN_VERSION) {  
                    v.setCameraDistance(mDensity * CAMERA_DISTANCE);  
                    if (PERFORM_OVERSCROLL_ROTATION) {  
                        if (i == 0 && scrollProgress < 0) {  
                            // Overscroll to the left  
                            v.setPivotX(TRANSITION_PIVOT * pageWidth);  
                            v.setRotationY(-TRANSITION_MAX_ROTATION  
                                    * scrollProgress);  
                            scale = 1.0f;  
                            alpha = 1.0f;  
                            // On the first page, we don't want the page to have  
                            // any  
                            // lateral motion  
                            translationX = 0;  
                        } else if (i == getChildCount() - 1  
                                && scrollProgress > 0) {  
                            // Overscroll to the right  
                            v.setPivotX((1 - TRANSITION_PIVOT) * pageWidth);  
                            v.setRotationY(-TRANSITION_MAX_ROTATION  
                                    * scrollProgress);  
                            scale = 1.0f;  
                            alpha = 1.0f;  
                            // On the last page, we don't want the page to have  
                            // any  
                            // lateral motion.  
                            translationX = 0;  
                        } else {  
                            v.setPivotY(pageHeight / 2.0f);  
                            v.setPivotX(pageWidth / 2.0f);  
                            v.setRotationY(0f);  
                        }  
                    }  
  
                    v.setTranslationX(translationX);  
                    v.setScaleX(scale);  
                    v.setScaleY(scale);  
//                    v.setAlpha(alpha * 0.5f);  
                    v.setAlpha(alpha);   // if * 0.5f  it will to dark  
                }  
  
                // If the view has 0 alpha, we set it to be invisible so as to  
                // prevent  
                // it from accepting touches
                if (alpha < 0.3) {  
                    v.setVisibility(GONE);  
                } else if (v.getVisibility() != VISIBLE) {  
                    v.setVisibility(VISIBLE);  
                }  
                Log.d("Nevin","VERSION:"+getVersion());  
            }  
        }  
          
       /* for (int i = 0; i < getChildCount(); i++) { 
            View child = getChildAt(i); 
            if (child != null) { 
                float scrollProgress = getScrollProgress(screenCenter, child, i); 
                float alpha = 1 - Math.abs(scrollProgress); 
                child.setAlpha(alpha); 
            } 
        } 
        invalidate();*/  
  
    }  
      
    public int getVersion() {  
        try {  
            int currentapiVersion=android.os.Build.VERSION.SDK_INT;  
            return  currentapiVersion;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
  
    protected void setOverScrollAmount(View v, float r) {  
        int alpha = (int) Math.round((r * 255));  
        if (alpha != 0) {  
         //   v.setBackgroundColor((int) Color.BLACK * ( - alpha));    //delete by cxs
        }  
        v.invalidate();  
    }  
  
    protected void getVisiblePages(int[] range) {  
        final int pageCount = getChildCount();  
  
        if (pageCount > 0) {  
            final int screenWidth = getMeasuredWidth();  
            int leftScreen = 0;  
            int rightScreen = 0;  
            View currPage = getPageAt(leftScreen);  
            while (leftScreen < pageCount - 1  
                    && currPage.getLeft() + currPage.getWidth()  
                            - currPage.getPaddingRight() < getScrollX()) {  
                leftScreen++;  
                currPage = getPageAt(leftScreen);  
            }  
            rightScreen = leftScreen;  
            currPage = getPageAt(rightScreen + 1);  
            while (rightScreen < pageCount - 1  
                    && currPage.getLeft() - currPage.getPaddingLeft() < getScrollX()  
                            + screenWidth) {  
                rightScreen++;  
                currPage = getPageAt(rightScreen + 1);  
            }  
            range[0] = leftScreen;  
            range[1] = rightScreen;  
        } else {  
            range[0] = -1;  
            range[1] = -1;  
        }  
    }  
  
    protected boolean shouldDrawChild(View child) {  
        if (getVersion() >= SCREEN_SCROLLED_MIN_VERSION) {  
            return child.getAlpha() > 0.15;  
        }  
        return true;  
    }  
  
    protected float getScrollProgress(int screenCenter, View v, int page) {  
        final int halfScreenSize = getMeasuredWidth() / 2;  
  
        int totalDistance = getScaledMeasuredWidth(v) + mPageSpacing;  
        int delta = screenCenter  
                - (getChildOffset(page) - getRelativeChildOffset(page) + halfScreenSize);  
  
        float scrollProgress = delta / (totalDistance * 1.0f);  
        scrollProgress = Math.min(scrollProgress, 1.0f);  
        scrollProgress = Math.max(scrollProgress, -1.0f);  
        return scrollProgress;  
    }  
  
    static class ZInterpolator implements TimeInterpolator {  
        private float focalLength;  
  
        public ZInterpolator(float foc) {  
            focalLength = foc;  
        }  
  
        public float getInterpolation(float input) {  
            return (1.0f - focalLength / (focalLength + input))  
                    / (1.0f - focalLength / (focalLength + 1.0f));  
        }  
    }  
      
    public interface TimeInterpolator {  
        float getInterpolation(float input);  
    }  
  
    @Override  
    public void scrollBy(int x, int y) {  
        scrollTo(mUnboundedScrollX + x, getScrollY() + y);  
    }  
  
    @Override  
    public void scrollTo(int x, int y) {  
        mUnboundedScrollX = x;  
  
        if (x < 0) {  
            super.scrollTo(0, y);  
            if (mAllowOverScroll) {  
                overScroll(x);  
            }  
        } else if (x > mMaxScrollX) {  
            super.scrollTo(mMaxScrollX, y);  
            if (mAllowOverScroll) {  
                overScroll(x - mMaxScrollX);  
            }  
        } else {  
            mOverScrollX = x;  
            super.scrollTo(x, y);  
        }  
  
        mTouchX = x;  
        mSmoothingTime = System.nanoTime() / NANOTIME_DIV;  
    }  
  
    protected void overScroll(float amount) {  
        acceleratedOverScroll(amount);  
    }  
  
    protected void acceleratedOverScroll(float amount) {  
        int screenSize = getMeasuredWidth();  
  
        // We want to reach the max over scroll effect when the user has  
        // over scrolled half the size of the screen  
        float f = OVERSCROLL_ACCELERATE_FACTOR * (amount / screenSize);  
  
        if (f == 0)  
            return;  
  
        // Clamp this factor, f, to -1 < f < 1  
        if (Math.abs(f) >= 1) {  
            f /= Math.abs(f);  
        }  
  
        int overScrollAmount = (int) Math.round(f * screenSize);  
        if (amount < 0) {  
            mOverScrollX = overScrollAmount;  
            super.scrollTo(0, getScrollY());  
        } else {  
            mOverScrollX = mMaxScrollX + overScrollAmount;  
            super.scrollTo(mMaxScrollX, getScrollY());  
        }  
        invalidate();  
  
    }  
  
    protected boolean computeScrollHelper() {  
        if (mScroller.computeScrollOffset()) {  
            // Don't bother scrolling if the page does not need to be moved  
            if (getScrollX() != mScroller.getCurrX()  
                    || getScrollY() != mScroller.getCurrY()  
                    || mOverScrollX != mScroller.getCurrX()) {  
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());  
            }  
            invalidate();  
            return true;  
        } else if (mNextPage != INVALID_PAGE) {  
            mCurrentPage = Math.max(0, Math.min(mNextPage, getPageCount() - 1));  
            mNextPage = INVALID_PAGE;  
  
            // Load the associated pages if necessary  
  
            // We don't want to trigger a page end moving unless the page has  
            // settled  
            // and the user has stopped scrolling  
            if (mTouchState == TOUCH_STATE_REST) {  
                pageEndMoving();  
            }  
  
            // Notify the user when the page changes  
            AccessibilityManager accessibilityManager = (AccessibilityManager) getContext()  
                    .getSystemService(Context.ACCESSIBILITY_SERVICE);  
            if (accessibilityManager.isEnabled()) {  
                AccessibilityEvent ev = AccessibilityEvent  
                        .obtain(AccessibilityEvent.TYPE_VIEW_SCROLLED);  
                sendAccessibilityEventUnchecked(ev);  
            }  
            return true;  
        }  
        return false;  
    }  
      
  
    @Override  
    public void computeScroll() {  
        computeScrollHelper();  
    }  
      
      
    //add wufen-------  
    //分页监听  
    public interface OnScreenChangeListener {  
        void onScreenChange(int currentIndex);  
    }  
  
    private OnScreenChangeListener onScreenChangeListener;  
  
    public void setOnScreenChangeListener(  
            OnScreenChangeListener onScreenChangeListener) {  
        this.onScreenChangeListener = onScreenChangeListener;  
    }  
  
    // 动态数据监听  
    public interface OnScreenChangeListenerDataLoad {  
        void onScreenChange(Boolean pageMoving);  
    }  
  
    private OnScreenChangeListenerDataLoad onScreenChangeListenerDataLoad;  
  
    public void setOnScreenChangeListenerDataLoad(  
            OnScreenChangeListenerDataLoad onScreenChangeListenerDataLoad) {  
        this.onScreenChangeListenerDataLoad = onScreenChangeListenerDataLoad;  
    }  
    //-------  
}  