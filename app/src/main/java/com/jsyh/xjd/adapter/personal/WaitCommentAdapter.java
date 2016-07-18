package com.jsyh.xjd.adapter.personal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jsyh.xjd.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Su on 2016/5/5.
 */
public class WaitCommentAdapter extends RecyclerView.Adapter<WaitCommentAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mGoodsIdList;

    private Map<String, String> mMap = new HashMap<>();

    public Map<String, String> getMap() {
        return mMap;
    }

    public WaitCommentAdapter(Context context, List<String> goodsIdList) {
        mContext = context;
        mGoodsIdList = goodsIdList;
        initCommentMap();
    }

    public void initCommentMap() {
        for (int i = 0; i < mGoodsIdList.size(); i++) {
            mMap.put(i + "", mGoodsIdList.get(i).split("@@")[0] + "@@" + "5" + "@@" + "");
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_wait_comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        String url = mGoodsIdList.get(position).split("@@")[1];
        Picasso.with(mContext).load(url).error(R.mipmap.goods_detail_shop_photo).into(holder.mImageViewGoodsPic);
        final String goodsId = mGoodsIdList.get(position).split("@@")[0];

        final String[] commentContent = new String[1];
        final String[] score = new String[1];

        //判断map里是否已经存在，存在值直接赋值
        if (mMap.containsKey(position + "")) {
            String[] temp = mMap.get(position + "").split("@@", 3);
            if (temp[1].equals("1")) {
                holder.mRadioButton1.setChecked(true);
                score[0] = "1";
            }
            if (temp[1].equals("3")) {
                holder.mRadioButton2.setChecked(true);
                score[0] = "3";
            }
            if (temp[1].equals("5")) {
                holder.mRadioButton3.setChecked(true);
                score[0] = "5";
            }
            commentContent[0] = temp[2];
            holder.mEditTextComment.setText(commentContent[0]);
        }
        holder.mRadioGroupComment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mRadioButton1:
                        score[0] = "1";
                        break;
                    case R.id.mRadioButton2:
                        score[0] = "3";
                        break;
                    case R.id.mRadioButton3:
                        score[0] = "5";
                        break;
                }
                mMap.put(position + "", goodsId + "@@" + score[0] + "@@" + commentContent[0]);
            }
        });


        holder.mEditTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    commentContent[0] = s.toString();
                } else commentContent[0] = "";
                mMap.put(position + "", goodsId + "@@" + score[0] + "@@" + commentContent[0]);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGoodsIdList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageViewGoodsPic;
        EditText mEditTextComment;
        RadioGroup mRadioGroupComment;
        RadioButton mRadioButton1;
        RadioButton mRadioButton2;
        RadioButton mRadioButton3;

        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageViewGoodsPic = (ImageView) itemView.findViewById(R.id.mImageViewGoodsPic);
            mEditTextComment = (EditText) itemView.findViewById(R.id.mEditTextComment);
            mRadioGroupComment = (RadioGroup) itemView.findViewById(R.id.mRadioGroupComment);
            mRadioButton1 = (RadioButton) itemView.findViewById(R.id.mRadioButton1);
            mRadioButton2 = (RadioButton) itemView.findViewById(R.id.mRadioButton2);
            mRadioButton3 = (RadioButton) itemView.findViewById(R.id.mRadioButton3);

            mView = itemView;
        }
    }
}
