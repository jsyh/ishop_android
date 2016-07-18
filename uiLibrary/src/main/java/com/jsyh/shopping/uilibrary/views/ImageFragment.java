package com.jsyh.shopping.uilibrary.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsyh.shopping.uilibrary.R;

public class ImageFragment extends Fragment {

    private static String ARG_IMAGE = "image";
    private int flag = 0;
    private static Context mContext;
    private static IntentBack intentBack;
    //public static int image[] = {R.mipmap.guide1,R.mipmap.guide2,R.mipmap.guide3,R.mipmap.guide4,R.mipmap.guide5};

    public static int image[];

    public static ImageFragment newInstance(int param1, int[] idRes, Context context) {
        mContext = context;
        image = idRes;
        intentBack = (IntentBack) mContext;
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flag = getArguments().getInt(ARG_IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.color_fragment, container, false);
        v.setBackgroundResource(image[flag]);
        TextView tv = (TextView) v.findViewById(R.id.start_inport);
        if (flag == 4) {
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intentBack.getIntentCallBack();
            }
        });
        return v;
    }

    public interface IntentBack {
        void getIntentCallBack();
    }

}
