package com.jsyh.shopping.uilibrary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jsyh.shopping.uilibrary.adapter.listview.BaseAdapterHelper;
import com.jsyh.shopping.uilibrary.adapter.listview.QuickAdapter;
import com.jsyh.shopping.uilibrary.popuwindow.FilterMultiplePopuwindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品条件筛选TabView
 */
public class GoodsFilterTabView extends FrameLayout implements View.OnClickListener,
        AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {

    private Context context;
    private View v;


    //综合
    private LinearLayout                mTabOneLayout;
    private ImageView                   mTabOneIndication;
    private TextView                    mTabOneTitle;

    private FilterMultiplePopuwindow                                    mMultiplePopuwindow;
    private QuickAdapter<FilterMultiplePopuwindow.MultipleFilterModel>   mAdapter;
    private List<FilterMultiplePopuwindow.MultipleFilterModel>           datas;

    private int checkedPositionOfMulti = -1;        //当前 综合 选中的



    //销量
    private TextView        mSaleVolumeView;

    //价格
    private LinearLayout    mTabThreeLayout;
    private TextView        mPrictTitle;
    private ImageView       mPriceIndicator;


    private final int       PRICE_NORMAL  = 0;
    private final int       PRICE_UP      = 1;
    private final int       PRICE_DOWN    = 2;
    private  int            order         = PRICE_NORMAL;


    //筛选
    private LinearLayout    mTabFourLayout;
    private TextView        mFilterView;
    private ImageView       mFilterIndicator;


    private final int       MULTIP            = 0;        //综合
    private final int       SALES_VOLUME      = 1;        //销量
    private final int       PRICE             = 3;        //价格
    private int             currChecked           = SALES_VOLUME;

    /**
     * 排序的类型
     0综合排序
     1销量排序
     2价格由低到高
     3价格又高到低
     4人气，
     （默认为0）
     */
    private final String MULTIP_ORDER = "0";             //综合排序
    private final String SALES_VOLUME_ORDER = "1";       //销量
    private final String PRICE_ASCENT_ORDER = "2";       //升序
    private final String PRICE_DESCENT_ORDER = "3";      //降序
    private final String POPULER = "4";                  //人气

    private String currentFilterStr = SALES_VOLUME_ORDER;             //当前的筛选条件


    public String getCurrentFilterStr() {
        return currentFilterStr;
    }

    private FilterTabListener tabListener;

    public void setOnTabListener(FilterTabListener tabListener) {
        this.tabListener = tabListener;
    }

    public GoodsFilterTabView(Context context) {
        super(context);
    }

    public GoodsFilterTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);

    }

    public GoodsFilterTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public GoodsFilterTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }


    public void initView(Context context,AttributeSet attrs){
        this.context = context;

        v = LayoutInflater.from(context).inflate(R.layout.goods_filter_tab_layout, this);

        initTabOne();


        //销量
        mSaleVolumeView = (TextView) v.findViewById(R.id.tvTabTwo);
        mSaleVolumeView.setOnClickListener(this);

        //价格
        mTabThreeLayout = (LinearLayout) v.findViewById(R.id.llTabThree);
        mTabThreeLayout.setOnClickListener(this);

        mPrictTitle = (TextView) v.findViewById(R.id.tvPriceTitle);
        mPriceIndicator = (ImageView) v.findViewById(R.id.ivPriceIndicator);

        //筛选
        mTabFourLayout = (LinearLayout) v.findViewById(R.id.llTabFour);
        mTabFourLayout.setOnClickListener(this);
        mFilterView = (TextView) v.findViewById(R.id.tvFilterView);
        mFilterIndicator = (ImageView) v.findViewById(R.id.ivFilterIndicator);

        setTabSaleChecked();

    }

    /**
     * 初始化 tab one
     */
    private void initTabOne() {
        mTabOneLayout = (LinearLayout) v.findViewById(R.id.llTabOne);
        mTabOneLayout.setOnClickListener(this);

        mTabOneTitle = (TextView) v.findViewById(R.id.tvTabOneTitle);
        mTabOneIndication = (ImageView) v.findViewById(R.id.ivTabIndication);


        initTabOneDatas();


    }


    private void initTabOneDatas() {

        datas = new ArrayList<>();
        String[] fitlers = getResources().getStringArray(R.array.arryfilter);
        for (String fitler : fitlers) {

            datas.add(new FilterMultiplePopuwindow.MultipleFilterModel(fitler, false));
        }

        mTabOneTitle.setText(fitlers[0]);

        this.mAdapter = new QuickAdapter<FilterMultiplePopuwindow.MultipleFilterModel>(context, R.layout.goods_muliple_filter_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, FilterMultiplePopuwindow.MultipleFilterModel item) {
                helper.setText(R.id.tvSubFilterName, item.name);

                if (item.checked) {
                    helper.getView(R.id.ivChecked).setVisibility(VISIBLE);
                } else {
                    helper.getView(R.id.ivChecked).setVisibility(GONE);
                }
            }
        };
        mAdapter.addAll(datas);


        mMultiplePopuwindow = new FilterMultiplePopuwindow(context, datas.size());
        mMultiplePopuwindow.setShowCount(datas.size());
        mMultiplePopuwindow.setOnItemClickListener(this);
        mMultiplePopuwindow.setAdapter(this.mAdapter);
        mMultiplePopuwindow.setOnDismissListener(this);

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.llTabOne) {           //综合
            if (mAdapter != null) {

                multipleIndicationRotation(mTabOneIndication, 180.0f);

                if (mMultiplePopuwindow.isShowing()) {
                    mMultiplePopuwindow.dismiss();

                } else {
                    mMultiplePopuwindow.showAsDropDown(mTabOneLayout);

                }
            }

        }else if (i == R.id.tvTabTwo) {     //销量

            if (currChecked != SALES_VOLUME) {
                //判断一下，不要重复加载

                currChecked = SALES_VOLUME;

                currentFilterStr = SALES_VOLUME_ORDER;      //排序的标志

                resetTabMulit();
                resetTabPrice();

                setTabSaleChecked();

            }



        }else if (i == R.id.llTabThree) {   //价格
            tabPriceClick();

        }else if (i == R.id.llTabFour) {        //筛选


            if (tabListener != null) {

                tabListener.onFilterListener();

            }
        }


    }

    private void tabPriceClick() {
        if (currChecked != PRICE) {
            currChecked = PRICE;

            setTextViewCheck(mPrictTitle,true);
            //还原其他tab
            resetTabMulit();
            resetTabSale();

        }

        switch (order) {
            // up
            case PRICE_NORMAL:
            case PRICE_DOWN:

                order = PRICE_UP;
                currentFilterStr = PRICE_ASCENT_ORDER;

                mPriceIndicator.setImageResource(R.mipmap.ic_price_order_up);

                break;

            case PRICE_UP:
                order = PRICE_DOWN;
                currentFilterStr = PRICE_DESCENT_ORDER;
                mPriceIndicator.setImageResource(R.mipmap.ic_price_order_down);

                break;

        }

        if (tabListener != null) {
            tabListener.onFilterCondition(currentFilterStr);
        }
    }

    /**
     * tab one indication roration
     * @param v
     * @param rotation
     */
    private  void multipleIndicationRotation(ImageView v, float rotation) {

        Matrix matrix = new Matrix();

        matrix.postRotate(rotation, v.getWidth() / 2, v.getHeight() / 2);

        v.setImageMatrix(matrix);


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (currChecked == MULTIP && checkedPositionOfMulti == position)return; //不重复加载，既单选。

        if (currChecked != MULTIP) {
            //保证只修改一次其他tab
            currChecked = MULTIP;
            resetTabSale();
            resetTabPrice();

        }



        if (checkedPositionOfMulti >= 0) {
            //这个表示，表示已经有选中的了。
            datas.get(checkedPositionOfMulti).checked = false;
        }

        //当前的为选中状态
        datas.get(position).checked = true;
        checkedPositionOfMulti = position;
        mAdapter.notifyDataSetChanged();            //刷新ListView

        //更改 tab one
        mTabOneTitle.setText(datas.get(position).name);     //设置标题文字
        setTextViewCheck(mTabOneTitle, true);
        mTabOneIndication.setImageResource(R.mipmap.ic_tab_one_checked);
        mMultiplePopuwindow.dismiss();

        if (position == 0) {
            currentFilterStr = MULTIP_ORDER;
        }else {
            currentFilterStr = POPULER;
        }

        if (tabListener != null) {
            tabListener.onFilterCondition(currentFilterStr);
        }

    }

    /**
     * 清空TabOne 的选择
     */
    public void resetTabMulit() {
        if (checkedPositionOfMulti >= 0) {
            //大于0 表示有选中

            datas.get(checkedPositionOfMulti).checked = false;
            mAdapter.notifyDataSetChanged();

            checkedPositionOfMulti = -1;

            setTextViewCheck(mTabOneTitle,false);
            mTabOneIndication.setImageResource(R.mipmap.ic_tab_one_normal);
        }

    }


    private void resetTabSale(){
        setTextViewCheck(mSaleVolumeView,false);

    }

    /**
     *  设置销量tab 是选中的  ， 同时回调
     */
    public void setTabSaleChecked(){
       setTextViewCheck(mSaleVolumeView,true);


        if (tabListener != null) {
            tabListener.onFilterCondition(currentFilterStr);
        }
    }

    private void resetTabPrice(){
        setTextViewCheck(mPrictTitle,false);
        order = PRICE_NORMAL;
        mPriceIndicator.setImageResource(R.mipmap.ic_price_normal);

    }

    /**
     * 设置 筛选的状态 是否选中的
     * @param checked
     */
    public void setFilterChecked(boolean checked) {

        setTextViewCheck(mFilterView, checked);
        if (checked){
            mFilterIndicator.setImageResource(R.mipmap.ic_filter_tab4_checked);
        }else{
            mFilterIndicator.setImageResource(R.mipmap.ic_filter_tab4);

        }

    }

    @Override
    public void onDismiss() {
        multipleIndicationRotation(mTabOneIndication, 360.0f);
    }




    /**
     * 设置文本的 colorSelector
     * @param v
     * @param checked
     */
    public void setTextViewCheck(TextView v, boolean checked) {
        if (checked) {

            v.setTextColor(generatorColorState());
        }else {

            v.setTextColor(getResources().getColor(R.color.text_gary_color));

        }
    }

    /**
     * 生成ColorStateList
     * @return
     */
    public ColorStateList generatorColorState() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{-android.R.attr.state_pressed},
                new int[]{}
        };

        int[] colors = new int[]{
                getResources().getColor(R.color.text_gary_color),
                getResources().getColor(android.R.color.holo_red_dark),

                getResources().getColor(R.color.text_gary_color)

        };


        return new ColorStateList(states, colors);

    }


    /**
     * 回调监听
     */
    public interface FilterTabListener {

        void onFilterListener();

        void onFilterCondition(String condition);

    }





}
