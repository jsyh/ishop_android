package com.jsyh.xjd.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.adapter.category.CategoryLogoAdapter;
import com.jsyh.xjd.adapter.category.CategorySubAdapter;
import com.jsyh.xjd.model.CategoryInfo;
import com.jsyh.xjd.model.CategoryInfo2;
import com.jsyh.xjd.model.CategoryInfoModel;

import java.util.List;


public class CategorySubFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CategoryInfoModel mDatas;         //数据


    private View mRootView;
    private RecyclerView mRecycler;      //商品列表
    private CategorySubAdapter mCategoryAdapter;
    private List<CategoryInfo> mCategoryInfos;
    private List<CategoryInfo2> mCategoryLogo;
    private String categoryTitle;


    public static CategorySubFragment newInstance(CategoryInfoModel data, String categoryTitle) {
        CategorySubFragment fragment = new CategorySubFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, data);
        args.putString(ARG_PARAM2, categoryTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public CategorySubFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDatas = getArguments().getParcelable(ARG_PARAM1);
            categoryTitle = getArguments().getString(ARG_PARAM2);
            // mUrls = mDatas.getData().getProduct();
            mCategoryInfos = mDatas.getData().getClassify();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.category_sub_fragment, container, false);
        setupViews(mRootView);
        //mUrls 可能有空指针
        /*if (mUrls != null && mUrls.size() > 0) {

            for (CategoryAdvInfo url : mUrls) {
                if (!TextUtils.isEmpty(url.getGoods_thumb())) {

                    DefaultSliderView textSliderView = new DefaultSliderView(getContext());
                    textSliderView
                            .image(ConfigValue.IMG_IP + url.getGoods_thumb())
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(this);
                    textSliderView.getBundle()
                            .putString("extra", url.getGoods_name());

                    mAdvlayout.addSlider(textSliderView);
                }
            }

            mAdvlayout.setDirection(5000);
            mAdvlayout.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Right_Bottom);
        }*/


        return mRootView;
    }

    private void setupViews(View view) {
        LinearLayout mLlTopLayout = (LinearLayout) view.findViewById(R.id.mLlTopLayout);
        if (mDatas.getData().getProduct().size() > 0) {
            mLlTopLayout.setVisibility(View.VISIBLE);
            /*mRecyclerHeader = RecyclerViewHeader.fromXml(getActivity(), R.layout.category_sub_header_layout);
            mRecyclerHeader.attachTo(mRecycler);*/
            RecyclerView mRvTopLogo = (RecyclerView) view.findViewById(R.id.rvCategoryLogo);
            mRvTopLogo.setLayoutManager(new GridLayoutManager(getActivity(), 3));

            CategoryLogoAdapter categoryLogoAdapter = new CategoryLogoAdapter(getContext(),
                    R.layout.category_two_level_item_logo, mDatas.getData().getProduct());

            mRvTopLogo.setAdapter(categoryLogoAdapter);

        } else mLlTopLayout.setVisibility(View.GONE);
        mRecycler = (RecyclerView) view.findViewById(R.id.rvSubCategory);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mRecycler.setLayoutManager(layoutManager);
        mCategoryAdapter = new CategorySubAdapter(getContext(),
                R.layout.category_two_level_item_temporary, mCategoryInfos);

        mRecycler.setAdapter(mCategoryAdapter);
        ((TextView) mRootView.findViewById(R.id.mTvCategoryTitle)).setText(categoryTitle);


    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
