package com.softwinner.un.tool.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.softwinner.un.tool.utilex.UNLog;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 文件名:UNVideoViewHelper.java V2.0 Feb 2,2015<br>
 * 描　述:视频显示画面工具类<br>
 * 版　权:AW-BU3-PD2<br>
 *
 * @author PD2-Roy
 */
public class UNVideoViewHelper {

    public static final String TAG = "UNVideoViewHelper";

    // main Object
    private Context mContext;
    private GlViewDec mRenderView; // GL View
    private RelativeLayout mParentView;
    private SurfaceHolder mHolder;
    private Renderer mRenderer;
    private UNVideoViewListener videoViewListener;
    private boolean isVideoViewShow = false; // for the first frame appears

    public UNVideoViewHelper(Context context, RelativeLayout parentView) {
        this.mContext = context;
        this.mParentView = parentView;
        this.init();
    }

    // From JNI
    private static native void init(int ptr, int width, int height);

    // From JNI
    private static native void render(int ptr);

    // Listener for call back
    public void setVideoViewListener(UNVideoViewListener videoViewListener) {
        this.videoViewListener = videoViewListener;
    }

    public boolean isVideoViewShow() {
        return isVideoViewShow;
    }

    public void setVideoViewShow(boolean value) {
        isVideoViewShow = value;
    }

    private void init() {
        // create renderView
        mRenderView = new GlViewDec(mContext);
        mParentView.addView(mRenderView); // 把此view添加到容器中

        // init renderView layout
        LayoutParams params = mRenderView.getLayoutParams();
        params.height = LayoutParams.MATCH_PARENT;
        params.width = LayoutParams.MATCH_PARENT;
        mRenderView.setLayoutParams(params);
        mRenderView.setZOrderOnTop(false);

        // init callback
        mRenderView.getHolder().addCallback(new Callback() {
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                UNLog.debug_print(UNLog.LV_DEBUG, TAG, " Video display surface is being changed. "
                        + "width " + width + "; height " + height);
            }

            public void surfaceCreated(SurfaceHolder holder) {
                UNLog.debug_print(UNLog.LV_DEBUG, TAG, "Video display surface created");
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                UNLog.debug_print(UNLog.LV_DEBUG, TAG, "Video display surface destroyed");
            }
        });
        mRenderer = new Renderer();
        ((GLSurfaceView) mRenderView).setRenderer(mRenderer);
        // ((GLSurfaceView)
        // mRenderView).setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);//render
        // by jni call back
        ((GLSurfaceView) mRenderView).setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    /**
     * setOpenGLESDisplay
     *
     * @param ptr
     */
    private void setOpenGLESDisplay(int ptr) {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "setOpenGLESDisplay");
        mRenderer.setOpenGLESDisplay(ptr);
    }

    /**
     * iFrame return， callback here
     */
    private void showFrameCallBack() {
        if (videoViewListener == null) {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "showFrameCallBack  mListener = null");
            return;
        }
        if (!isVideoViewShow) {
            videoViewListener.videoViewShow();
            isVideoViewShow = true;
        } else {
            videoViewListener.videoViewShowing();
        }
    }

    /**
     * when uninit frame ,callback here
     */
    private void stopFrameCallBack() {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "stopFrameCallBack");
        isVideoViewShow = false;
        if (videoViewListener == null) {
            UNLog.debug_print(UNLog.LV_DEBUG, TAG, "stopFrameCallBack  mListener = null");
            return;
        }
        videoViewListener.videoViewEnd();
    }

    /**
     * Video View Listener
     *
     * @author AW-Roy
     */
    public interface UNVideoViewListener {

        /**
         * The First IFrame arrived, then call back here
         */
        void videoViewShow();

        /**
         * Every IFrame arrived, then call back here
         */
        void videoViewShowing();

        /**
         * Video end, then call back here
         */
        void videoViewEnd();

    }

    private class Renderer implements GLSurfaceView.Renderer {
        int ptr;
        boolean initPending;
        int width, height;

        public Renderer() {
            UNLog.debug_print(UNLog.LV_DEBUG, TAG, "Renderer Create");
            ptr = 0;
            initPending = false;
        }

        public void setOpenGLESDisplay(int ptr) {
            synchronized (this) {
                if (this.ptr != 0 && ptr != this.ptr) {
                    initPending = true;
                }
                this.ptr = ptr;
            }
        }

        /**
         * 刷新视频界面（现在是自动模式）
         */

        public void onDrawFrame(GL10 gl) {
            // UNLog.debug_print(UNLog.LV_ERROR, TAG, "onDrawFrame");
            synchronized (this) {
                if (ptr == 0) {
                    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // 设置清除颜色设为黑
                    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
                    return;
                }

                if (initPending) {
                    init(ptr, width, height);
                    initPending = false;
                }

                render(ptr);// 更新画面

            }
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            UNLog.debug_print(UNLog.LV_ERROR,TAG, "onSurfaceChanged width " + width + "; height " + height);
            this.width = width;
            this.height = height;
            initPending = true;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "onSurfaceCreated");
        }
    }
}
