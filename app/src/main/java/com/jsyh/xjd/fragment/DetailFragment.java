package com.jsyh.xjd.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.model.GoodsInfoModel2;
import com.jsyh.xjd.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

	private String encoding = "utf-8";	//编码


	public static final String PARAMS1 = "html";
	public static final String PARAMS2 = "base_params";

	private String mHtmlString;				//webview 加载的文本数据

	private List<GoodsInfoModel2.Params> mBaseParamsDatas;		//基本数据


	private WebView mWebView;
	private String url = "file:///android_asset/jingdong/index/index.html";

	private RadioGroup mSwitchLayoutRadio;

	private RadioButton mIntroductionRadioButton;
	private RadioButton mBaseParamsRadioButton;


	private ViewStub mIntroductionStub;        //商品简介
	private View mIntroductionView;


	private ViewStub mBaseParamsStub;        //基本参数
	private View mBaseParamsView;

	private TableLayout mParamsTableLayout;


	public DetailFragment() {
	}


	public static DetailFragment newInstance(String html, ArrayList<GoodsInfoModel2.Params> param) {

		DetailFragment detatileFragment = new DetailFragment();

		Bundle bundle = new Bundle();

		bundle.putString(PARAMS1, html);
		bundle.putSerializable(PARAMS1, param);

		detatileFragment.setArguments(bundle);
		return detatileFragment;
	}

	public static DetailFragment newInstance(Bundle extral) {

		DetailFragment detatileFragment = new DetailFragment();

		detatileFragment.setArguments(extral);
		return detatileFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		if (bundle != null) {
			mHtmlString = bundle.getString(PARAMS1);
			mBaseParamsDatas = (ArrayList<GoodsInfoModel2.Params>) bundle.getSerializable(PARAMS2);


		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.details_webview, null);


		return view;
	}

	//@Override
	protected void initView() {
//		mWebView = (WebView) getView().findViewById(R.id.webview);
//		mWebView.getSettings().setBuiltInZoomControls(false);
//		mWebView.getSettings().setDomStorageEnabled(true);
//		mWebView.getSettings().setJavaScriptEnabled(true);
//		mWebView.loadUrl(url);

		mSwitchLayoutRadio = (RadioGroup) getView().findViewById(R.id.rg_control);
		mSwitchLayoutRadio.setOnCheckedChangeListener(this);

		mIntroductionRadioButton = (RadioButton) getView().findViewById(R.id.rbIntroduction);
		mBaseParamsRadioButton = (RadioButton) getView().findViewById(R.id.rbBaseParams);

		mIntroductionStub = (ViewStub) getView().findViewById(R.id.vsIntroduction);

		mBaseParamsStub = (ViewStub) getView().findViewById(R.id.vsBaseParams);


		mSwitchLayoutRadio.check(R.id.rbIntroduction);        //默认商品图文选中


	}


	@Override
	protected void initTitle() {
		//super.initTitle();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
			case R.id.rbIntroduction:

				mIntroductionRadioButton.setTextSize(16);
				mBaseParamsRadioButton.setTextSize(12);

				if (mIntroductionView == null) {
					mIntroductionView = mIntroductionStub.inflate();



					mWebView = (WebView) mIntroductionView.findViewById(R.id.indroductionWebView);
					mWebView.getSettings().setBuiltInZoomControls(false);
					mWebView.getSettings().setDomStorageEnabled(true);
					mWebView.getSettings().setDefaultTextEncodingName(encoding);


					//mWebView.getSettings().setJavaScriptEnabled(true);
					/*mWebView.getSettings().setUseWideViewPort(true);
					mWebView.getSettings().setLoadWithOverviewMode(true);*/

					mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

					if (!TextUtils.isEmpty(mHtmlString)) {

						loadDataForWebView(mHtmlString);
					}

//					mWebView.loadUrl(url);


				} else {

					mIntroductionView.setVisibility(View.VISIBLE);

				}

				if (mBaseParamsView != null) {
					mBaseParamsView.setVisibility(View.GONE);
				}

				break;

			case R.id.rbBaseParams:

				mIntroductionRadioButton.setTextSize(12);
				mBaseParamsRadioButton.setTextSize(15);


				if (mBaseParamsView == null) {
					mBaseParamsView = mBaseParamsStub.inflate();


					mParamsTableLayout = (TableLayout) mBaseParamsView.findViewById(R.id.tlParaLayout);

					initParamLayout(mBaseParamsDatas);

				} else {
					mBaseParamsView.setVisibility(View.VISIBLE);

				}

				if (mIntroductionView != null) {
					mIntroductionView.setVisibility(View.GONE);
				}


				break;

		}


	}


	public void setDataToFragment(String mHtmlString,List<GoodsInfoModel2.Params> datas){

		this.mHtmlString = mHtmlString;
		this.mBaseParamsDatas = datas;

		loadDataForWebView(this.mHtmlString);


	}

	/**
	 * 加载数据到webview
	 * @param html
	 */
	public void loadDataForWebView(String html) {

		String newHtml = html.replaceAll("<img", "<img width=100%");
		mWebView.loadDataWithBaseURL(null,newHtml, "text/html", encoding,null);
	}


	/**
	 * 实例化 参数布局
	 * @param datas
	 */
	public void initParamLayout(List<GoodsInfoModel2.Params> datas) {

		if (datas==null)
			return;
		for (GoodsInfoModel2.Params params : datas) {

			TableRow tableRow = new TableRow(getActivity());
			tableRow.setBackgroundColor(Color.WHITE);

			//标题
			TextView title = new TextView(getContext());
			TableRow.LayoutParams lp = new TableRow.LayoutParams(0, DensityUtils.dp2px(getActivity(),30), 2.0f);
			title.setLayoutParams(lp);
			title.setGravity(Gravity.CENTER | Gravity.RIGHT);
			title.setText(params.attr_name);
			title.setPadding(title.getPaddingLeft(), title.getPaddingTop(), DensityUtils.dp2px(getActivity(), 10.0f), title.getPaddingRight());
			title.setBackgroundResource(R.drawable.cell_shape);

			//内容
			TextView content = new TextView(getContext());
			TableRow.LayoutParams lp2 = new TableRow.LayoutParams(0, DensityUtils.dp2px(getActivity(),30), 4.0f);
			content.setGravity(Gravity.CENTER|Gravity.LEFT);
			content.setLayoutParams(lp2);
			content.setPadding(DensityUtils.dp2px(getActivity(), 10.0f), content.getPaddingTop(), content.getPaddingTop(), content.getPaddingRight());
			content.setText(params.attr_value);
			content.setBackgroundResource(R.drawable.cell_shape);


			tableRow.addView(title);
			tableRow.addView(content);


			mParamsTableLayout.addView(tableRow);

		}



	}



}
