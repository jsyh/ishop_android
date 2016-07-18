package com.jsyh.xjd.activity.me;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.presenter.ChangeUserInfoPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.ChangeUserInfoView;
import com.jsyh.shopping.uilibrary.uiutils.ImageUtils;

import java.io.File;

/**
 * Created by sks on 2015/9/16.
 * 我的账户
 */
public class MeAccountActivity extends BaseActivity implements View.OnClickListener,
        ChangeUserInfoView {
    private Context context;
    TextView title;
    ImageView back;
    private ImageView avatar;
    public final static int PHOTO_GRAPH = 1;// 拍照
    public final static int SELECT_PICTURE = 0;// 相册选择
    public final static int SAVE_PHOTO = 2;

    public static String filePath = "";
    private TextView txtUserName, txtEmail, txtSex, txtBirthday;
    private String mTextUserName;
    private ChangeUserInfoPresenter changeUserInfoPresenter;
    private String nickName;
    private String birthday;
    private int sex;

    private Uri imageUri;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        setContentView(R.layout.acitivity_meaccout);
        context = this;
        changeUserInfoPresenter = new ChangeUserInfoPresenter(this);

        findViewById(R.id.rlAvatr).setOnClickListener(this);
        findViewById(R.id.rlUserName).setOnClickListener(this);
        findViewById(R.id.rlSex).setOnClickListener(this);
        findViewById(R.id.rlBirthday).setOnClickListener(this);
        findViewById(R.id.rlChangePassword).setOnClickListener(this);

        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtSex = (TextView) findViewById(R.id.txtSex);
        txtBirthday = (TextView) findViewById(R.id.txtBirthday);

        avatar = (ImageView) findViewById(R.id.imgAvatar);
        showData();
    }

    @Override
    public void initTitle() {
        super.initTitle();
        findViewById(R.id.fl_Left).setOnClickListener(this);
        ((TextView) findViewById(R.id.title)).setText("个人资料");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    private void showData() {
        if (ConfigValue.uInfor != null) {
            nickName = ConfigValue.uInfor.getNick_name();
            txtUserName.setText(nickName);
        }
        txtEmail.setText(ConfigValue.uInfor.getMobile());

        if (ConfigValue.uInfor.getSex() != null) {
            sex = Integer.parseInt(ConfigValue.uInfor.getSex());
        }
        switch (sex) {
            case 0:
                txtSex.setText("男");
                break;
            case 1:
                txtSex.setText("女");
                break;
            case 2:
                txtSex.setText("保密");
                break;
        }
        if (ConfigValue.uInfor.getBirthday() != null) {
            birthday = ConfigValue.uInfor.getBirthday();
            txtBirthday.setText(birthday);
        }
        initHeadPhoto();
    }

    //图片保存在本地，查看本地有无头像，若有则显示，没有则不显示

    private void initHeadPhoto() {
        String filePath = ConfigValue.HEAD_PHOTO_DIR + File.separator + ConfigValue.uInfor.getMobile() + ".jpg";
        File file = new File(filePath);
        if (file.exists()) {
            if (bitmap == null)
                bitmap = ImageUtils.compressBitmap(filePath, 200, 200);
            if (bitmap != null) {
                avatar.setImageBitmap(bitmap);
            }
        }
    }

    //相机拍照获取图片
    private void takePictures() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(ConfigValue.HEAD_PHOTO_DIR + File.separator);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, "newhead.jpg");
            filePath = file.getAbsolutePath();
            imageUri = Uri.fromFile(file);
            Intent intent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, PHOTO_GRAPH);
        } else
            Toast.makeText(context, "未插入SD卡", Toast.LENGTH_SHORT).show();
    }

    private void cardSelect() {
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        File dir = new File(ConfigValue.HEAD_PHOTO_DIR + File.separator);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "newhead.jpg");
        imageUri = Uri.fromFile(file);

        startActivityForResult(it, SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MeAccountActivity.PHOTO_GRAPH:
                    /*Bitmap bitmap = BitmapUtil.compressBitmap(filePath, 0, 0);
                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
                            getContentResolver(), bitmap, null, null));*/
                startImageAction(imageUri, 200, 200,
                        SAVE_PHOTO, true);
                break;
            case MeAccountActivity.SELECT_PICTURE:
                Uri uri1 = null;
                if (data == null) {
                    return;
                }
                uri1 = data.getData();
                startImageAction(uri1, 200, 200,
                        SAVE_PHOTO, true);
                break;
            case MeAccountActivity.SAVE_PHOTO:
                if (data == null)
                    return;
                else
                    showPhoto(data);
                filePath = "";
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 剪裁照片，并将剪裁之后的照片存在imageUri中，照片的名称问newhead.jpg,若提交更改，则将newhead.jpg重命名为head.jpg
     *
     * @param uri
     * @param outputX
     * @param outputY
     * @param requestCode
     * @param isCrop
     */
    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        Intent intent = null;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, SAVE_PHOTO);
    }

    private void showPhoto(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            if (bitmap != null) {
                avatar.setImageBitmap(bitmap);
                /*if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }*/
                //、并且用用户名保存图片
                File file = new File(ConfigValue.HEAD_PHOTO_DIR + File.separator + "newhead.jpg");
                if (file.exists()) {
                    file.renameTo(new File(ConfigValue.HEAD_PHOTO_DIR + File.separator + ConfigValue.uInfor.getMobile() + ".jpg"));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        File file = new File(ConfigValue.HEAD_PHOTO_DIR + File.separator + "newhead.jpg");
        if (file.exists())
            file.delete();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.rlAvatr:
                changePhoto();
                break;
            case R.id.rlUserName:
                Intent intent = new Intent(this, NicknameActivity.class);
                intent.putExtra("sex", sex + "");
                intent.putExtra("birthday", birthday);
                Utils.startActivityWithAnimation(this, intent);
                break;
            //性别
            case R.id.rlSex:
                changeSex();
                break;
            //出生年月
            case R.id.rlBirthday:
                changeBirthday();
                break;
            //修改密码
            case R.id.rlChangePassword:
                Intent intent1 = new Intent(context, ChangePasswordActivity.class);
                Utils.startActivityWithAnimation(this, intent1);
                break;
        }
    }

    private void changeBirthday() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Toast.makeText(this, year + "-" + monthOfYear + "-" + dayOfMonth,Toast.LENGTH_SHORT).show();
                birthday = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                changeUserInfoPresenter.getUserInfo(context, String.valueOf(sex), birthday, nickName);
            }
        }, 2000, 0, 1);
        datePickerDialog.show();
    }

    private void changeSex() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final int[] newSex = new int[1];
        mBuilder.setItems(new String[]{"男", "女", "保密"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        newSex[0] = 0;
                        break;
                    case 1:
                        newSex[0] = 1;
                        break;
                    case 2:
                        newSex[0] = 2;
                        break;
                }
                if (newSex[0] != sex) {
                    sex = newSex[0];
                    changeUserInfoPresenter.getUserInfo(context, newSex[0] + "", birthday, nickName);
                }
            }
        }).show();
    }

    private void changePhoto() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setItems(new String[]{"拍照", "从相册选择", "取消"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        takePictures();
                        break;
                    case 1:
                        cardSelect();
                        break;
                }
            }
        });
        mBuilder.show();
    }

    @Override
    public void result(BaseModel model) {
        if (null != model && Integer.parseInt(model.getCode()) > 0) {
            if (model.getCode().equals("1")) {
                ConfigValue.uInfor.setSex(String.valueOf(sex));
                ConfigValue.uInfor.setBirthday(birthday);
                showData();
            }
            Utils.showToast(context, model.getMsg());
        } else
            Utils.showToast(context, "提交失败");
    }
}
