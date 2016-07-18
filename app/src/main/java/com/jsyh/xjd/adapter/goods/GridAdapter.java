package com.jsyh.xjd.adapter.goods;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.GoodsInfoActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.model.GoodsInfo;
import com.jsyh.shopping.uilibrary.adapter.recyclerview.BaseAdapterHelper;
import com.jsyh.shopping.uilibrary.adapter.recyclerview.MultiItemTypeSupport;
import com.jsyh.shopping.uilibrary.adapter.recyclerview.QuickAdapter;
import com.liang.library.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by momo on 2015/10/21.
 */
public class GridAdapter extends QuickAdapter<GoodsInfo> {

    private Transformation mTransformation;

    public GridAdapter(Context context, @LayoutRes int layoutResId) {
        super(context, layoutResId);
        initTransformation();
    }

    public GridAdapter(Context context, @LayoutRes int layoutResId, List<GoodsInfo> data) {
        super(context, layoutResId, data);
        initTransformation();
    }

    protected GridAdapter(Context context, MultiItemTypeSupport<GoodsInfo> multiItemTypeSupport) {
        super(context, multiItemTypeSupport);
    }

    protected GridAdapter(Context context, MultiItemTypeSupport<GoodsInfo> multiItemTypeSupport, List<GoodsInfo> data) {
        super(context, multiItemTypeSupport, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, final GoodsInfo item) {
        if (!TextUtils.isEmpty(item.getImage())) {
            Picasso.with(context)
                    .load(ConfigValue.IMG_IP+item.getImage())
                    .fit()
                    .error(R.mipmap.empty)
                    .transform(mTransformation)
                    .into((ImageView) helper.getView(R.id.ivGoodsIconMode2));
        }

        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, GoodsInfoActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("goodsId", item.getGoods_id());
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

        helper.setText(R.id.tvGoodsPrice2, "¥ " + item.getPrice());
        helper.setText(R.id.tvGoodsNameMode2, item.getTitle());
    }

    private void initTransformation() {
        mTransformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(3)
                .borderColor(Color.TRANSPARENT)
                .borderWidthDp(1)
                .oval(false)
                .build();


    }
}
