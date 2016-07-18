package com.jsyh.xjd.activity.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.utils.Utils;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

/**
 * Created by sks on 2015/9/24.
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    TextView title, submit;
    ImageView back;
    private EditText editIdea;
    private String content;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_feedback);
        editIdea = (EditText) findViewById(R.id.editContent);
    }

    @Override
    public void initTitle() {
        title = (TextView) findViewById(R.id.title);
        title.setText("意见反馈");
        submit = (TextView) findViewById(R.id.ensure);
        submit.setText("提交");
        findViewById(R.id.fl_Left).setOnClickListener(this);
        findViewById(R.id.fl_right).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.fl_right:
                content = editIdea.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast(this, "请输入意见后提交！");
                    return;
                }
                ToastUtil.showToast(this, "意见反馈接口？");
                //反馈成功关闭此页面
                Utils.finishActivityWithAnimation(this);
                break;
        }
    }
}
