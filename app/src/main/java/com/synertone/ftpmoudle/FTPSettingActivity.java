package com.synertone.ftpmoudle;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.came.viewbguilib.ButtonBgUi;
import com.donkingliang.imageselector.utils.StringUtils;
import com.synertone.ftpmoudle.model.FTPUserModel;
import com.synertone.ftpmoudle.utils.GsonUtils;
import com.synertone.ftpmoudle.utils.SharedPreferenceManager;
import butterknife.BindView;
import butterknife.OnClick;

public class FTPSettingActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_ip_address)
    TextView tvIpAddress;
    @BindView(R.id.et_ip_address)
    EditText etIpAddress;
    @BindView(R.id.tv_port)
    TextView tvPort;
    @BindView(R.id.et_ip_port)
    EditText etIpPort;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_ok)
    ButtonBgUi btnOk;
    @BindView(R.id.btn_cancel)
    ButtonBgUi btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftpsetting);
        initView();
    }

    private void initView() {
        String ftpuser = SharedPreferenceManager.getString(this, "ftpuser");
        if(StringUtils.isNotEmptyString(ftpuser)){
            FTPUserModel ftpUserModel = GsonUtils.fromJson(ftpuser, FTPUserModel.class);
            etIpAddress.setText(ftpUserModel.getIpAddress());
            etIpAddress.setSelection(etIpAddress.getText().length());
            etIpPort.setText(ftpUserModel.getIpPort());
            etUsername.setText(ftpUserModel.getUserName());
            etPassword.setText(ftpUserModel.getUserPassword());
        }
        tvBarTitle.setText("参数设置");
    }

    @OnClick({R.id.iv_back, R.id.btn_ok, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_ok:
                if(checkParams()){
                    FTPUserModel ftpUserModel=new FTPUserModel(etIpAddress.getText().toString(),
                            etIpPort.getText().toString(),etUsername.getText().toString(),
                            etPassword.getText().toString());
                    String ftpuser = GsonUtils.toJson(ftpUserModel);
                    SharedPreferenceManager.saveString(this,"ftpuser",ftpuser);
                }else{
                    Toast.makeText(this,"参数不能为空！",Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private boolean checkParams() {
      return  StringUtils.isNotEmptyString(etIpAddress.getText().toString())&&
                StringUtils.isNotEmptyString(etIpPort.getText().toString())&&
                StringUtils.isNotEmptyString(etUsername.getText().toString())&&
                StringUtils.isNotEmptyString(etPassword.getText().toString());
    }
}
