package com.jsyh.xjd.model;

import java.io.Serializable;

/**
 * Created by sks on 2015/9/21.
 */
public class BaseModel implements Serializable {
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
