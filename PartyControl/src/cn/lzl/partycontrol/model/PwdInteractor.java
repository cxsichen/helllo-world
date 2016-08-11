package cn.lzl.partycontrol.model;

import cn.lzl.partycontrol.presenter.OnPwdCheckListener;

public interface PwdInteractor {
    public boolean onCheckPwd(String pwd,OnPwdCheckListener callback);
}
