package com.synertone.ftpmoudle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.donkingliang.imageselector.utils.StringUtils;
import com.synertone.ftpmoudle.model.FTPUserModel;
import com.synertone.ftpmoudle.utils.GsonUtils;
import com.synertone.ftpmoudle.utils.SharedPreferenceManager;
import net.gotev.uploadservice.ftp.FTPUploadRequest;
import net.gotev.uploadservice.ftp.UnixPermissions;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import butterknife.BindView;
import butterknife.OnClick;
public class FTPMainActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.ll_settings)
    LinearLayout llSettings;
    @BindView(R.id.ll_picture_see)
    LinearLayout llPictureSee;
    @BindView(R.id.ll_picture_upload)
    LinearLayout llPictureUpload;
    private  final String ftpServicePath ="/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftp_main);
        initView();
    }

    private void initView() {
        tvBarTitle.setText(R.string.picture_manager);
    }

    @OnClick({R.id.iv_back, R.id.ll_settings, R.id.ll_picture_see, R.id.ll_picture_upload})
    public void onViewClicked(View view) {
        Intent intent;
        String ftpuser = SharedPreferenceManager.getString(this, "ftpuser");
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_settings:
                intent=new Intent(this,FTPSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_picture_see:
                if(StringUtils.isEmptyString(ftpuser)){
                    Toast.makeText(this,"请先设置参数！",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent=new Intent(this,FTPPictureBrowseActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_picture_upload:
                if(StringUtils.isEmptyString(ftpuser)){
                    Toast.makeText(this,"请先设置参数！",Toast.LENGTH_SHORT).show();
                    return;
                }
                ImageSelectorUtils.openPhotoUpload(this,12,new ImageSelectorUtils.UploadListener(){
                    @Override
                    public void load(List<String> images) {
                        final String uploadId = UUID.randomUUID().toString();
                        String ftpuser = SharedPreferenceManager.getString(FTPMainActivity.this, "ftpuser");
                        if(StringUtils.isEmptyString(ftpuser)){
                            Toast.makeText(FTPMainActivity.this,"请先设置参数！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FTPUserModel ftpUserModel = GsonUtils.fromJson(ftpuser, FTPUserModel.class);
                        final FTPUploadRequest request = new FTPUploadRequest(FTPMainActivity.this, uploadId, ftpUserModel.getIpAddress(), Integer.parseInt(ftpUserModel.getIpPort()))
                                .setMaxRetries(FTPUploadRequest.MAX_RETRIES)
                                .setNotificationConfig(getNotificationConfig(uploadId, R.string.ftp_upload))
                                .setUsernameAndPassword(ftpUserModel.getUserName(), ftpUserModel.getUserPassword())
                                .setCreatedDirectoriesPermissions(new UnixPermissions("777"))
                                .setSocketTimeout(5000)
                                .setConnectTimeout(5000);
                        for(String localPath:images){
                            String remotePath = generateRemotePath(localPath);
                            try {
                                request.addFileToUpload(localPath, remotePath);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        request.startUpload();
                    }
                });
                break;
        }
    }
    private String generateRemotePath(String subtitle) {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
        String[] split = subtitle.split("/");
        return ftpServicePath+sf.format(time)+"/"+split[split.length-1];
    }
}
