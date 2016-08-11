package cn.lzl.partycontrol.model;

import android.text.TextUtils;

import cn.lzl.partycontrol.presenter.OnPwdCheckListener;

public class PwdInteractorImpl implements PwdInteractor {
    
    private final String mPwd;
    
    public PwdInteractorImpl(String pwd) {
        this.mPwd = pwd;
    }

    @Override
    public boolean onCheckPwd(String pwd, OnPwdCheckListener callback) {
        boolean result = false;
        result = TextUtils.equals(mPwd, pwd);
        if(result){
            callback.onSuccess();
        }else{
            callback.onError();
        }
        return result;
    }
}
