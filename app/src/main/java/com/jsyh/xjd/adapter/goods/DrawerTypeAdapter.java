package com.jsyh.xjd.adapter.goods;

import android.content.Context;
import android.view.View;

import com.jsyh.xjd.R;
import com.jsyh.xjd.model.FilterInfo;
import com.jsyh.shopping.uilibrary.adapter.listview.BaseAdapterHelper;
import com.jsyh.shopping.uilibrary.adapter.listview.MultiItemTypeSupport;
import com.jsyh.shopping.uilibrary.adapter.listview.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2015/10/21.
 */
public class DrawerTypeAdapter extends QuickAdapter<FilterInfo.TypeInfo> {
    public DrawerTypeAdapter(Context context, ArrayList<FilterInfo.TypeInfo> data, MultiItemTypeSupport<FilterInfo.TypeInfo> multiItemSupport) {
        super(context, data, multiItemSupport);
    }

    public DrawerTypeAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public DrawerTypeAdapter(Context context, int layoutResId, List<FilterInfo.TypeInfo> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, FilterInfo.TypeInfo item) {
        if (item.isCheck()) {
            helper.getView(R.id.ivChecked).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.ivChecked).setVisibility(View.GONE);

        }

        helper.setText(R.id.tvSubFilterName, item.getCat_name());
    }
}
