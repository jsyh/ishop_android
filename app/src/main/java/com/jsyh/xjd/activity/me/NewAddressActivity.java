package com.jsyh.xjd.activity.me;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.model.AddressInforModel;
import com.jsyh.xjd.model.AddressModel;
import com.jsyh.xjd.model.Area;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.presenter.AddressInforPresenter;
import com.jsyh.xjd.presenter.NewAddressPresenter;
import com.jsyh.xjd.utils.KeyBoardUtils;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.AddressView;
import com.jsyh.xjd.views.NewAddressView;

import java.util.ArrayList;

/**
 * Created by sks on 2015/9/24.
 * 编辑/修改 收货地址
 */
public class NewAddressActivity extends BaseActivity implements View.OnClickListener,
        NewAddressView, AddressView {

    TextView title;
    ImageView back;
    private Context context;
    private String add_id = "";
    private EditText editName, editPhone, editStreet;
    private TextView txtArea;
    private TextView btnAdd;
    private String consignee, number, area, street;
    public static final int REQUEST_CONTACT = 1;
    public static final int AREA = 2;
    private AddressInforPresenter aiPresenter;
    private NewAddressPresenter naPresenter;
    private ArrayList<Area> list;
    private String province;
    private String city;
    private String district;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_newaddress);
        context = this;
        aiPresenter = new AddressInforPresenter(this);
        naPresenter = new NewAddressPresenter(this);
        add_id = getIntent().getStringExtra("address");
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        back.setBackgroundResource(R.mipmap.ic_back);
        findViewById(R.id.fl_Left).setOnClickListener(this);
        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        txtArea = (TextView) findViewById(R.id.txtArea);
        editStreet = (EditText) findViewById(R.id.editStreet);
        editName.addTextChangedListener(textWatcher);
        editPhone.addTextChangedListener(textWatcher);
        editStreet.addTextChangedListener(textWatcher);
        txtArea.addTextChangedListener(textWatcher);
        btnAdd = (TextView) findViewById(R.id.btnAdd);
        if (null != add_id && !add_id.equals("")) {
            title.setText("编辑收货人");
            aiPresenter.loadAddressInfor(context, add_id);
        } else {
            add_id = "";
            title.setText("新建收货地址");
        }
        findViewById(R.id.lineLayContacts).setOnClickListener(this);
        findViewById(R.id.rlArea).setOnClickListener(this);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String consignee = editName.getText().toString().trim();
            String number = editPhone.getText().toString().trim();
            area = txtArea.getText().toString().trim();
            street = editStreet.getText().toString().trim();
            if (!consignee.equals("") && !number.equals("") && !area.equals("") &&
                    !street.equals("")) {
                btnAdd.setBackgroundColor(Color.parseColor("#ff5000"));
                btnAdd.setClickable(true);
                btnAdd.setOnClickListener(NewAddressActivity.this);
            } else {
                btnAdd.setBackgroundColor(Color.parseColor("#dbdbdb"));
                btnAdd.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_Left:
                KeyBoardUtils.closeKeybord(NewAddressActivity.this);
                break;
            case R.id.lineLayContacts:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CONTACT);
                break;
            case R.id.rlArea:
                Intent it = new Intent(context, SelectAreaActivity.class);
                startActivityForResult(it, 1);
                break;
            case R.id.btnAdd:
                consignee = editName.getText().toString().trim();
                number = editPhone.getText().toString().trim();
                naPresenter.setData(context, consignee, street, number, province,
                        city, district, add_id);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            switch (resultCode) {
                case AREA:
                    String area = "";
                    Bundle b = data.getExtras();
                    list = new ArrayList<>();
                    list = (ArrayList<Area>) b.getSerializable("arealist");
                    for (Area model : list) {
                        area = area + model.getRegion_name();
                    }
                    province = list.get(0).getRegion_id();
                    city = list.get(1).getRegion_id();
                    district = list.get(2).getRegion_id();
                    txtArea.setText(area);
                    break;
            }
        } else {
            switch (requestCode) {
                case REQUEST_CONTACT:
                    if (data == null) {
                        return;
                    }
                    Uri result = data.getData();
//                    String contactId = aliPayResponse.getLastPathSegment();
                    Cursor c = managedQuery(result, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        consignee = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        if (hasPhone.equalsIgnoreCase("1")) {
                            hasPhone = "true";
                        } else {
                            hasPhone = "false";
                        }
                        if (Boolean.parseBoolean(hasPhone)) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (phones.moveToNext()) {
                                number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                            phones.close();
                        }
                    }

                    editName.setText(consignee);
                    editPhone.setText(number);
                    break;
            }
        }
    }

    @Override
    public void getData(BaseModel model) {
        if (model.getCode().equals("1")) {
            Utils.finishActivityWithAnimation(this);
        } else if (model.getCode().equals("-220")) {
            itLogin(context);
        }
        Utils.showToast(context, model.getMsg());
    }

    @Override
    public void getAddressInfor(AddressInforModel model) {
        if (model.getCode().equals("1")) {
            if (!model.getData().equals("")) {
                editName.setText(model.getData().getUsername());
                editPhone.setText(model.getData().getTelnum());
                editStreet.setText(model.getData().getAddress());
                txtArea.setText(model.getData().getArea());
                province = model.getData().getProvince();
                city = model.getData().getCity();
                district = model.getData().getDistrict();
            } else
                Utils.showToast(context, model.getMsg());
        } else
            Utils.showToast(context, model.getMsg());
    }

    @Override
    public void getAddressList(AddressModel response) {

    }

    @Override
    public void delete(BaseModel model) {

    }
}
