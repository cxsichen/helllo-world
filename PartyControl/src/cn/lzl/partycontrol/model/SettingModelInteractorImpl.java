package cn.lzl.partycontrol.model;

import cn.lzl.partycontrol.presenter.OnChangeSettingModelListener;
import cn.lzl.partycontrol.utils.Contacts;

public class SettingModelInteractorImpl implements SettingModelInteractor{
    
    public final String[] MODEL_MSG = {
            Contacts.HEX_MODEL_USER,
            Contacts.HEX_MODEL_BYD,
            Contacts.HEX_MODEL_VIOS,
            Contacts.HEX_MODEL_FORD,
            Contacts.HEX_MODEL_CAMRY,
            Contacts.HEX_MODEL_ELANTR,
            Contacts.HEX_MODEL_CRV};
    private int mCurrentIndex;

    @Override
    public void onChangeSettingMode(int action, OnChangeSettingModelListener callback) {
        if(action == Contacts.Action.RIGHT.value()){
            if(++mCurrentIndex == MODEL_MSG.length){
                mCurrentIndex = 0;
            }
        }else if(action == Contacts.Action.LEFT.value()){
            if(--mCurrentIndex < 0){
                mCurrentIndex = MODEL_MSG.length - 1;
            }
        }
        callback.sendMsg(MODEL_MSG[mCurrentIndex]);
    }

    @Override
    public void setIndex(int index) {
        mCurrentIndex = index;
    }

}
