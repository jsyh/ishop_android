package com.jsyh.xjd.activity.me;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.model.Message;
import com.jsyh.shopping.uilibrary.adapter.listview.BaseAdapterHelper;
import com.jsyh.shopping.uilibrary.adapter.listview.QuickAdapter;

import java.util.ArrayList;

/**
 * Created by sks on 2015/9/23.
 * 我的消息
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener{
    private Context context;
    TextView title;
    ImageView back;
    private ListView listView;
    private QuickAdapter<Message> quickAdapter;
    private ArrayList<Message> list = new ArrayList<>();
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_message);
        context = this;
        title=(TextView)findViewById(R.id.title);
        back = (ImageView)findViewById(R.id.back);
        back.setBackgroundResource(R.mipmap.ic_back);
        title.setText("消息中心");
        findViewById(R.id.fl_Left).setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView);
        quickAdapter = new QuickAdapter<Message>(context,R.layout.message_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, Message item) {
                helper.setText(R.id.txtTitle,item.getTitle());
                helper.setText(R.id.txtContent,item.getMessage());
            }
        };
        quickAdapter.addAll(list);
        listView.setAdapter(quickAdapter);
        listView.setOnItemClickListener(this);
        getData();
    }
    private void getData(){
//        Map<String,String> map = new HashMap<>();
//        map.put("api_token", Utils.AppMD5String(context));
//        map.put("key", ConfigValue.DATA_KEY);
//        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/message",
//                map,new BaseDelegate.ResultCallback<MessageModel>() {
//                    @Override
//                    public void onError(Request request, Object tag, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onApplyResponse(MessageModel response, Object tag) {
//                        if(!response.getCode().equals(0)){
//                            if(response.getData().size() != 0) {
//                                list.addAll(response.getData());
//                                quickAdapter.notifyDataSetChanged();
//                            }else
//                                Utils.showToast(context,"暂无消息！");
//                        }else
//                            Utils.showToast(context,response.getMsg());
//                    }
//                },true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fl_Left:
                finish();
                break;
        }
    }
}
