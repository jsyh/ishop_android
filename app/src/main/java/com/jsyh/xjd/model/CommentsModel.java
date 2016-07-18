package com.jsyh.xjd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Su on 2016/4/5.
 */
public class CommentsModel implements Parcelable {
    /**
     * 评论
     */
    public String bad;
    public String good;
    public String medium;
    public List<CommentsModel2> comment;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bad);
        dest.writeString(this.good);
        dest.writeString(this.medium);
        dest.writeTypedList(comment);
    }

    public CommentsModel() {
    }

    protected CommentsModel(Parcel in) {
        this.bad = in.readString();
        this.good = in.readString();
        this.medium = in.readString();
        this.comment = in.createTypedArrayList(CommentsModel2.CREATOR);
    }

    public static final Parcelable.Creator<CommentsModel> CREATOR = new Parcelable.Creator<CommentsModel>() {
        @Override
        public CommentsModel createFromParcel(Parcel source) {
            return new CommentsModel(source);
        }

        @Override
        public CommentsModel[] newArray(int size) {
            return new CommentsModel[size];
        }
    };
}
