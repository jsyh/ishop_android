package com.jsyh.xjd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.SearchActivity;
import com.jsyh.xjd.adapter.category.CategoryTopAdapter;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.CategoryInfo;
import com.jsyh.xjd.model.CategoryInfoModel;
import com.jsyh.xjd.presenter.CategoryPresenter;
import com.jsyh.xjd.qrzxing.CaptureActivity;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.CategoryView;

import java.util.ArrayList;
import java.util.List;


/**
 * MD 历史的 鬼东西，cccccc
 * 分类
 */
public class CategoryFragment extends Fragment implements
        CategoryView,
        AdapterView.OnItemClickListener,
        View.OnClickListener {

    private CategoryPresenter mPresenter;


    private LinearLayout mMainContent;

    private ViewStub mNetworStub;
    private View mNetworkErrorLayout;
    private Button mReloadRequest;            //网络错误k

    private EditText mSearch;    //搜索框
    private ImageView mQR;        // 二维码扫描,jnlkjkl

    //--------------- 一级列表 ----------------------
    private ListView mOneListView;
    private CategoryTopAdapter mOneAdapter;
    private List<CategoryInfo> mOneData;


    //--------------- 二级列表 ----------------------
    private FragmentManager mFragmentManager;
    private FragmentTransaction mft;
    private String categoryTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getChildFragmentManager();
        mPresenter = new CategoryPresenter(this);

    }

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
//			rootView = inflater.inflate(layoutId, null);
            rootView = inflater.inflate(R.layout.category_fragment, container, false);
//			initView(rootView);// 控件初始化
            initToolbar(rootView);

            mNetworStub = (ViewStub) rootView.findViewById(R.id.vsNetworkError);
            mMainContent = (LinearLayout) rootView.findViewById(R.id.mainContents);

            initOneCategory(rootView);
            mPresenter.loadCategoryByType(getActivity(), CategoryPresenter.one_level, null);
        }


        return rootView;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.etSearch:
                if (Utils.NO_NETWORK_STATE == Utils.isNetworkAvailable(getContext())) {
                    return;
                }

                /*mSearchDialog = (FullScreanDialogFragment) getFragmentManager().findFragmentByTag("searchDialog");
                if (mSearchDialog == null) {
                    mSearchDialog = new FullScreanDialogFragment();
                } else {
                    getFragmentManager().beginTransaction().remove(mSearchDialog).commit();
                }

                mSearchDialog.setDialogListener(new FullScreanDialogFragment.DialogListener() {
                    @Override
                    public void onDialogKeyword(String keyword) {
                        Intent intent = new Intent(getActivity(), GoodsFilterActivity1.class);

                        Bundle extras = new Bundle();
                        extras.putString("keyword", keyword);
                        intent.putExtras(extras);

                        startActivity(intent);

                    }
                });

                mSearchDialog.show(getFragmentManager().beginTransaction(), "searchDialog");*/
                Intent itSear = new Intent(getActivity(), SearchActivity.class);
                //startActivity(itSear);
                Utils.startActivityWithAnimation(getActivity(),itSear);

                break;
            case R.id.rlQR:
            case R.id.ivQR:            //二维码扫描

                if (Utils.NO_NETWORK_STATE == Utils.isNetworkAvailable(getContext())) {
                    return;
                }

                Intent itCapture = new Intent(getActivity(), CaptureActivity.class);
                startActivity(itCapture);
                break;

            case R.id.btnReloadNetwork:        //重新加载网络

                mPresenter.loadCategoryByType(getContext(), CategoryPresenter.one_level, null);

                break;
        }
    }


    private void initToolbar(View v) {
        mSearch = (EditText) v.findViewById(R.id.etSearch);
        mSearch.setOnClickListener(this);

        mQR = (ImageView) v.findViewById(R.id.ivQR);
        mQR.setOnClickListener(this);
        v.findViewById(R.id.rlQR).setOnClickListener(this);
        v.findViewById(R.id.custom_title_bar).setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.nav_color));
    }


    /**
     * 初始化一级列表
     *
     * @param v
     */
    private void initOneCategory(View v) {
        mOneListView = (ListView) v.findViewById(R.id.lvOneLevel);
        mOneListView.setOnItemClickListener(this);


        mOneAdapter = new CategoryTopAdapter(getActivity(), R.layout.category_one_level_item);
        mOneListView.setAdapter(mOneAdapter);

    }


    /**
     * 一级 分类回调
     *
     * @param data
     */
    @Override
    public void onOneLevelData(CategoryInfoModel data, @Nullable Exception e) {

        if (e != null) {

            switchLayout();
            return;
        }

        if (data == null) return;

        if (mNetworkErrorLayout != null) {

            mNetworkErrorLayout.setVisibility(View.GONE);
        }

        mMainContent.setVisibility(View.VISIBLE);

        if (mOneData == null) {
            mOneData = new ArrayList<>();
        }
        mOneData.clear();
        mOneAdapter.clear();

        data.getData().getClassify().get(currPosition).setChecked(true); //第一个默认选中
        categoryTitle = data.getData().getClassify().get(currPosition).getClassify_name();

        mOneData.addAll(data.getData().getClassify());

        mOneAdapter.addAll(this.mOneData);

        checkCacheWithLoad(currPosition, data.getData().getClassify().get(currPosition).getClassify_id());

//		mPresenter.loadCategoryByType(getContext(),CategoryPresenter.two_level,);

    }

    /**
     * 二级分类回调
     *
     * @param data
     */
    @Override
    public void onTwoLevelData(CategoryInfoModel data, @Nullable Exception e) {

        if (e != null) {

            switchLayout();
            return;
        }

        if (mNetworkErrorLayout != null) {

            mNetworkErrorLayout.setVisibility(View.GONE);
        }

        mMainContent.setVisibility(View.VISIBLE);

        if (data.getData() != null && data.getData().getClassify().size() > 0) {

            /**
             * java.lang.IllegalArgumentException: No view found for id 0x7f0f010f (com.jsyh.onlineshopping:id/contents) for fragment CategorySubFragment{429ad160 #2 id=0x7f0f010f}
             * getFragmentManager到的是activity对所包含fragment的Manager，而如果是fragment嵌套fragment，那么就需要利用getChildFragmentManager()了。
             */

            mFragmentManager.beginTransaction()
                    .replace(R.id.contents, CategorySubFragment.newInstance(data,categoryTitle))
                    .commitAllowingStateLoss();
            /**
             * FATAL EXCEPTION: main
             java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState

             Like commit() but allows the commit to be executed after an activity's state is saved. This is dangerous because the commit can
             be lost if the activity needs to later be restored from its state, so this should only be used for cases where it is
             okay for the UI state to change unexpectedly on the user.
             */
        }


    }


    public void switchLayout() {

        if (mNetworkErrorLayout == null) {

            mNetworkErrorLayout = mNetworStub.inflate();
            mReloadRequest = (Button) mNetworkErrorLayout.findViewById(R.id.btnReloadNetwork);

            mReloadRequest.setOnClickListener(this);

        } else {
            mNetworkErrorLayout.setVisibility(View.VISIBLE);

        }

        mMainContent.setVisibility(View.GONE);


    }


    private int currPosition;
    private TextView currItemView;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkHttpClientManager.cancelTag(getContext(), "home_category" + CategoryPresenter.one_level);
        OkHttpClientManager.cancelTag(getContext(), mPresenter.getTag());
    }

    /**
     * 一级item点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (currPosition == position) {
            return;
        }
        mOneData.get(currPosition).setChecked(false);
        mOneData.get(position).setChecked(true);

        currPosition = position;

        mOneAdapter.notifyDataSetChanged();

        if (android.os.Build.VERSION.SDK_INT >= 11) {

            mOneListView.smoothScrollToPositionFromTop(position, 0);
        } else {
            mOneListView.setSelectionFromTop(position, 0);
        }
        categoryTitle = mOneData.get(currPosition).getClassify_name();
//		checkCacheWithLoad(position,mOneData.get(currPosition).getClassify_id());
        mPresenter.loadCategoryByType(getContext(), CategoryPresenter.two_level, mOneData.get(currPosition).getClassify_id());

    }

    /**
     * 判断缓存中是否已经存在
     */
    private void checkCacheWithLoad(int key, int id) {


        mPresenter.loadCategoryByType(getActivity(), CategoryPresenter.two_level, id);

    }


}
