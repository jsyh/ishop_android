package com.jsyh.xjd.model;

/**
 * Created by Su on 2016/5/18.
 */
public class FinishRegisterEvent {
    private String userName;
    private String passWord;

    public FinishRegisterEvent(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
