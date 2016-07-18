package com.jsyh.xjd.adapter.goods;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsyh.xjd.R;
import com.orhanobut.logger.Logger;

/**
 * Created by momo on 2015/10/20.
 *
 */
public class EmptyContentAdapter extends RecyclerView.Adapter<EmptyContentAdapter.EmptyHolder> {

    private Context context;

    public EmptyContentAdapter(@NonNull Context context) {
        this.context = context;
    }



    @Override
    public EmptyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView =  LayoutInflater.from(context).inflate(R.layout.goods_empty, null, false);

        Logger.d("width:"+parent.getWidth()+",height:"+parent.getHeight()+"----");
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight()/2);

        rootView.setLayoutParams(lp);


        return new EmptyHolder(rootView);
    }

    @Override
    public void onBindViewHolder(EmptyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class EmptyHolder extends RecyclerView.ViewHolder{

        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
