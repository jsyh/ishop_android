package com.jsyh.xjd.wxapi;

/**
 * Created by Su on 2016/1/19.
 */
public class WXPayResult {
    public WXPayResult(String resCode) {
        this.resCode = resCode;
    }

    private String resCode;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }
}
