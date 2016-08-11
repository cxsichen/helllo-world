package cn.lzl.partycontrol.presenter;

import cn.lzl.partycontrol.model.PwdInteractor;
import cn.lzl.partycontrol.model.PwdInteractorImpl;
import cn.lzl.partycontrol.view.PwdCheckView;

public class PwdCheckPresenterImpl implements PwdCheckPresenter,OnPwdCheckListener{
    
    private PwdCheckView mPwdCheckView;
    private PwdInteractor mPwdInteractor;
    
    public PwdCheckPresenterImpl(PwdCheckView pwdCheckView) {
        this.mPwdCheckView = pwdCheckView;
        mPwdInteractor = new PwdInteractorImpl(mPwdCheckView.initPwd());
    }

    @Override
    public void onCheckPwd(String pwd) {
        mPwdInteractor.onCheckPwd(pwd, this);
    }

    @Override
    public void onSuccess() {
        mPwdCheckView.onResult(true);
    }

    @Override
    public void onError() {
        mPwdCheckView.onResult(false);
    }
}
