package com.jsyh.xjd.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.model.Area;
import com.jsyh.xjd.model.AreaModel;
import com.jsyh.xjd.presenter.SelectAreaPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.SelectAreaView;
import com.jsyh.shopping.uilibrary.adapter.listview.BaseAdapterHelper;
import com.jsyh.shopping.uilibrary.adapter.listview.QuickAdapter;

import java.util.ArrayList;

/**
 * Created by sks on 2015/10/9.
 * 选择地址
 */
public class SelectAreaActivity extends BaseActivity implements View.OnClickListener,
        SelectAreaView, AdapterView.OnItemClickListener {
    TextView title;
    ImageView back;
    private Context context;
    private ListView listView;
    private QuickAdapter<Area> quickAdapter;
    private ArrayList<Area> list = new ArrayList<>();
    private ArrayList<Area> intentL = new ArrayList<>();
    private SelectAreaPresenter saPresenter;
    private String id = "1";
    private int count = 0;
    private String action = "province";

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_selectarea);
        context = this;
        saPresenter = new SelectAreaPresenter(this);
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        title.setText("选择地址");
        back.setBackgroundResource(R.mipmap.ic_back);
        findViewById(R.id.fl_Left).setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView);
        quickAdapter = new QuickAdapter<Area>(context, R.layout.text_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, Area item) {
                helper.setText(R.id.txtName, item.getRegion_name());
            }
        };
        listView.setAdapter(quickAdapter);
        listView.setOnItemClickListener(this);
        saPresenter.loadArea(context, id, action);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        id = list.get(i).getRegion_id();
        switch (count) {
            case 0:
                action = "province";
                break;
            case 1:
                action = "city";
                break;
            case 2:
                action = "area";
                break;
        }
        intentL.add(list.get(i));
        if (count > 2) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("arealist", intentL);
            intent.putExtras(bundle);
            setResult(NewAddressActivity.AREA, intent);
            finish();
        } else
            saPresenter.loadArea(context, id, action);
    }

    @Override
    public void getArea(AreaModel model) {
        if (model.getCode().equals("1")) {
            if (model.getData().size() > 0) {
                list.clear();
                quickAdapter.clear();
                list.addAll(model.getData());
                quickAdapter.addAll(list);
//                quickAdapter.notifyDataSetChanged();
                count++;
            } else
                Utils.showToast(context, model.getMsg());
        } else
            Utils.showToast(context, model.getMsg());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_Left:
                finish();
                break;
        }
    }
}
