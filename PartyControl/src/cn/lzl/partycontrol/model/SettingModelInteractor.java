package cn.lzl.partycontrol.model;

import cn.lzl.partycontrol.presenter.OnChangeSettingModelListener;

public interface SettingModelInteractor {
    public  void onChangeSettingMode(int action,OnChangeSettingModelListener callback);
    public void setIndex(int index);
}
