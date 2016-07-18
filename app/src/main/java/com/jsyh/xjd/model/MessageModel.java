package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by sks on 2015/9/23.
 */
public class MessageModel extends BaseModel{
    private List<Message> data;

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }
}
