package com.jsyh.xjd.views;

import com.jsyh.xjd.model.PersonalCommentsModel;

/**
 * Created by Su on 2016/5/4.
 */
public interface CommentsView {
    void onResponse(PersonalCommentsModel model);

    void onError(String s);
}
