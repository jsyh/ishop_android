package com.jsyh.xjd.adapter.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.GoodsListActivity;
import com.jsyh.xjd.model.CategoryInfo;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.shopping.uilibrary.adapter.recyclerview.BaseAdapterHelper;
import com.jsyh.shopping.uilibrary.adapter.recyclerview.MultiItemTypeSupport;
import com.jsyh.shopping.uilibrary.adapter.recyclerview.QuickAdapter;

import java.util.List;

/**
 * Created by momo on 2015/10/21.
 */
public class CategorySubAdapter extends QuickAdapter<CategoryInfo> {

    public CategorySubAdapter(Context context, @LayoutRes int layoutResId) {
        super(context, layoutResId);
    }

    public CategorySubAdapter(Context context, @LayoutRes int layoutResId, List<CategoryInfo> data) {
        super(context, layoutResId, data);
    }

    protected CategorySubAdapter(Context context, MultiItemTypeSupport<CategoryInfo> multiItemTypeSupport) {
        super(context, multiItemTypeSupport);
    }

    protected CategorySubAdapter(Context context, MultiItemTypeSupport<CategoryInfo> multiItemTypeSupport, List<CategoryInfo> data) {
        super(context, multiItemTypeSupport, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, final CategoryInfo item) {
        helper.setText(R.id.tvTwoLevelItem, item.getClassify_name());
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatch(item.getClassify_name(),item.getClassify_id()+"");
            }
        });
    }

    private void dispatch(String keyword,String id){
        Intent intent = new Intent(context, GoodsListActivity.class);

        Bundle extras = new Bundle();
//        extras.putString("keyword", keyword);
        extras.putString("classify_id",id);
        intent.putExtras(extras);
        Utils.startActivityWithAnimation(context,intent);
    }
}
