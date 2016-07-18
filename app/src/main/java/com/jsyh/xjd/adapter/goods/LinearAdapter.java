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
import com.jsyh.xjd.views.GoodsFilterView;
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
public class LinearAdapter extends QuickAdapter<GoodsInfo> {


    private  Transformation mTransformation;

    private GoodsFilterView mView;

    public void setView(GoodsFilterView mView) {
        this.mView = mView;
    }

    public LinearAdapter(Context context, @LayoutRes int layoutResId) {
        super(context, layoutResId);
        initTransformation();
    }

    public LinearAdapter(Context context, @LayoutRes int layoutResId, List<GoodsInfo> data) {
        super(context, layoutResId, data);
        initTransformation();
    }

    protected LinearAdapter(Context context, MultiItemTypeSupport<GoodsInfo> multiItemTypeSupport) {
        super(context, multiItemTypeSupport);
    }

    protected LinearAdapter(Context context, MultiItemTypeSupport<GoodsInfo> multiItemTypeSupport, List<GoodsInfo> data) {
        super(context, multiItemTypeSupport, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, final GoodsInfo item) {
        if (!TextUtils.isEmpty(item.getImage())) {

            Picasso.with(context)
                    .load(ConfigValue.IMG_IP+item.getImage())
                    .fit()
                    .transform(mTransformation)
                    .error(R.mipmap.empty)
                    .into((ImageView) helper.getView(R.id.ivGoodsIconMode1));
        }

        helper.setText(R.id.tvGoodsNameMode1, item.getTitle());
        helper.setText(R.id.tvGoodsPrice1, "¥ " + item.getPrice());
        // 1: 促销  0： 非
        if ("1".equals(item.getIs_promotion())) {
            helper.getView(R.id.tvSaleFlag).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tvSaleFlag).setVisibility(View.GONE);
        }
        //好评
        if (!TextUtils.isEmpty(item.getGood())) {

            helper.setText(R.id.tvCommentPercentile, "好评" + item.getGood() + "%");
        }
        // 销量
        if (!TextUtils.isEmpty(item.getSell_num())) {

            helper.setText(R.id.tvSaleNums, item.getSell_num() + "人");
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

        helper.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mView != null) {
                }
                return true;
            }
        });
    }


    private void initTransformation(){
        mTransformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(3)
                .borderColor(Color.TRANSPARENT)
                .borderWidthDp(1)
                .oval(false)
                .build();


    }
}
