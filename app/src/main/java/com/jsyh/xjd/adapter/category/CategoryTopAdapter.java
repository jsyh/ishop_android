package com.jsyh.xjd.adapter.category;

import android.content.Context;

import com.jsyh.xjd.R;
import com.jsyh.xjd.model.CategoryInfo;
import com.jsyh.shopping.uilibrary.adapter.listview.BaseAdapterHelper;
import com.jsyh.shopping.uilibrary.adapter.listview.MultiItemTypeSupport;
import com.jsyh.shopping.uilibrary.adapter.listview.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2015/10/21.
 *
 * 一级分类  adapter
 */
public class CategoryTopAdapter extends QuickAdapter<CategoryInfo> {

    public CategoryTopAdapter(Context context, ArrayList<CategoryInfo> data, MultiItemTypeSupport<CategoryInfo> multiItemSupport) {
        super(context, data, multiItemSupport);
    }

    public CategoryTopAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public CategoryTopAdapter(Context context, int layoutResId, List<CategoryInfo> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, CategoryInfo item) {

        helper.getView(R.id.tvOneLevelName).setSelected(item.isChecked());

        helper.setText(R.id.tvOneLevelName, item.getClassify_name());
    }
}
