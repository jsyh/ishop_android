package com.jsyh.xjd.adapter.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.GoodsListActivity;
import com.jsyh.xjd.model.CategoryInfo2;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.shopping.uilibrary.adapter.recyclerview.BaseAdapterHelper;
import com.jsyh.shopping.uilibrary.adapter.recyclerview.MultiItemTypeSupport;
import com.jsyh.shopping.uilibrary.adapter.recyclerview.QuickAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Su on 2016/4/8.
 */
public class CategoryLogoAdapter extends QuickAdapter<CategoryInfo2> {
    public CategoryLogoAdapter(Context context, @LayoutRes int layoutResId) {
        super(context, layoutResId);
    }

    public CategoryLogoAdapter(Context context, @LayoutRes int layoutResId, List<CategoryInfo2> data) {
        super(context, layoutResId, data);
    }

    protected CategoryLogoAdapter(Context context, MultiItemTypeSupport<CategoryInfo2> multiItemTypeSupport) {
        super(context, multiItemTypeSupport);
    }

    protected CategoryLogoAdapter(Context context, MultiItemTypeSupport<CategoryInfo2> multiItemTypeSupport, List<CategoryInfo2> data) {
        super(context, multiItemTypeSupport, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, final CategoryInfo2 item) {
        ImageView mImageView = helper.getImageView(R.id.ivTwoLevelItem);
        if (item != null) {

            String logo = item.getBrand_logo();
            Picasso.with(context).load(logo).error(R.mipmap.goods_detail_shop_photo).into(mImageView);
           /* mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatch(null, item.getBrand_id() + "");
                }
            });*/
            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatch(null, item.getBrand_id() + "");
                }
            });

        }
    }

    private void dispatch(String keyword, String id) {
        Intent intent = new Intent(context, GoodsListActivity.class);

        Bundle extras = new Bundle();
//        extras.putString("keyword", keyword);
        extras.putString("brand_id", id);
        intent.putExtras(extras);
        //context.startActivity(intent);
        Utils.startActivityWithAnimation(context,intent);
    }
}
