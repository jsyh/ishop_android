package com.jsyh.xjd.adapter.goods;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jsyh.xjd.R;
import com.jsyh.xjd.model.GoodsListFiltrateModel;

import java.util.List;

/**
 * 筛选类别适配器
 * Created by Su on 2016/4/21.
 */
public class GoodsListOptionsCategoryAdapter extends RecyclerView.Adapter<GoodsListOptionsCategoryAdapter.GoodsListOptionsViewHolder> {


    private Context mContext;
    private List<GoodsListFiltrateModel.DataBean.ClassifyBean> mClassifyBeanList;
    private RecyclerView mRecyclerView;
    private OnItemClickCallBack mOnItemClickCallBack;
    public static int LAST_CHECK_POSITION = -1;
    public static int ITEM_COUNT = 0;

    public GoodsListOptionsCategoryAdapter(Context context, List<GoodsListFiltrateModel.DataBean.ClassifyBean> classifyBeanList, RecyclerView mRecyclerView) {
        this.mContext = context;
        this.mClassifyBeanList = classifyBeanList;
        this.mRecyclerView = mRecyclerView;
        //GoodslistActivitybi必须实现接口
        mOnItemClickCallBack = (OnItemClickCallBack) mContext;
        if (mClassifyBeanList.size() >= 3)
            ITEM_COUNT = 3;
        else ITEM_COUNT = mClassifyBeanList.size();
    }


    @Override
    public GoodsListOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new GoodsListOptionsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.goods_list_options_recycler_item, null));
    }

    @Override
    public void onBindViewHolder(final GoodsListOptionsViewHolder holder, final int position) {
        // Utils.showToast(mContext, mClassifyBeanList.get(position).is_checked()+"");

        if (LAST_CHECK_POSITION == position) {
            holder.mCheckBox.setChecked(true);
        } else holder.mCheckBox.setChecked(false);

        holder.mCheckBox.setText(mClassifyBeanList.get(position).getCat_name());
        final GoodsListFiltrateModel.DataBean.ClassifyBean classifyBean = mClassifyBeanList.get(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (LAST_CHECK_POSITION != -1 && LAST_CHECK_POSITION < getItemCount()) {
                        Log.d("last", LAST_CHECK_POSITION + "11111");
                        View lastView = mRecyclerView.getChildAt(LAST_CHECK_POSITION);
                        CheckBox lastCheckBox = (CheckBox) lastView.findViewById(R.id.mCheckBoxCategory);
                        //Utils.showToast(mContext, lastCheckBox.getText().toString());
                        if (LAST_CHECK_POSITION != position)
                            lastCheckBox.setChecked(false);
                    }
                    mOnItemClickCallBack.onItemClick(classifyBean.getCat_id());
                } else {
                    mOnItemClickCallBack.onItemClick(null);
                }
                LAST_CHECK_POSITION = position;
                Log.d("last", LAST_CHECK_POSITION + "22222");
            }
        });

    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    public class GoodsListOptionsViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBox;

        public GoodsListOptionsViewHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.mCheckBoxCategory);
        }
    }

    public interface OnItemClickCallBack {
        void onItemClick(String itemId);
    }
}
