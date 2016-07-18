package com.jsyh.xjd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Su on 2016/4/5.
 */
public class CommentsModel2 implements Parcelable {
    public String content;
    public String content_name;
    public String gmjl;                             //购买记录
    public String gmsl;
    public String order_id;
    public String rank;
    public String time;
    public String user_id;
    public String user_rank;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.content_name);
        dest.writeString(this.gmjl);
        dest.writeString(this.gmsl);
        dest.writeString(this.order_id);
        dest.writeString(this.rank);
        dest.writeString(this.time);
        dest.writeString(this.user_id);
        dest.writeString(this.user_rank);
    }

    public CommentsModel2() {
    }

    protected CommentsModel2(Parcel in) {
        this.content = in.readString();
        this.content_name = in.readString();
        this.gmjl = in.readString();
        this.gmsl = in.readString();
        this.order_id = in.readString();
        this.rank = in.readString();
        this.time = in.readString();
        this.user_id = in.readString();
        this.user_rank = in.readString();
    }

    public static final Parcelable.Creator<CommentsModel2> CREATOR = new Parcelable.Creator<CommentsModel2>() {
        @Override
        public CommentsModel2 createFromParcel(Parcel source) {
            return new CommentsModel2(source);
        }

        @Override
        public CommentsModel2[] newArray(int size) {
            return new CommentsModel2[size];
        }
    };
}
