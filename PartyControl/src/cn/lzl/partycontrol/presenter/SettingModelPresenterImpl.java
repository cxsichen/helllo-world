package cn.lzl.partycontrol.presenter;

import cn.lzl.partycontrol.model.SettingModelInteractor;
import cn.lzl.partycontrol.model.SettingModelInteractorImpl;
import cn.lzl.partycontrol.view.SettingModelView;

public class SettingModelPresenterImpl implements SettingModelPresenter,OnChangeSettingModelListener{
    
    private SettingModelView mSettingModelView;
    private SettingModelInteractor mSettingModelInteractor;
    
    public SettingModelPresenterImpl(SettingModelView settingModelView) {
        this.mSettingModelView = settingModelView;
        this.mSettingModelInteractor = new SettingModelInteractorImpl();
    }
    
    @Override
    public void onChangeSettingModel(int action) {
        mSettingModelInteractor.onChangeSettingMode(action, this);
    }

    @Override
    public void sendMsg(String msg) {
        mSettingModelView.onChangeSettingModel(msg);
    }
    
    public void setIndex(int index){
        mSettingModelInteractor.setIndex(index);
    }

}
