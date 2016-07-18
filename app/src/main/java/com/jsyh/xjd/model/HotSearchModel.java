package com.jsyh.xjd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by momo on 2015/10/11.
 *
 * 热搜 model
 */
public class HotSearchModel extends BaseModel implements Parcelable {

    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.data);
    }

    public HotSearchModel() {
    }

    protected HotSearchModel(Parcel in) {
        this.data = in.createStringArrayList();
    }

    public static final Parcelable.Creator<HotSearchModel> CREATOR = new Parcelable.Creator<HotSearchModel>() {
        public HotSearchModel createFromParcel(Parcel source) {
            return new HotSearchModel(source);
        }

        public HotSearchModel[] newArray(int size) {
            return new HotSearchModel[size];
        }
    };
}
