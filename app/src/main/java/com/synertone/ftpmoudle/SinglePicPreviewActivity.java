package com.synertone.ftpmoudle;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.synertone.ftpmoudle.model.FTPPictureModel;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static android.animation.ObjectAnimator.ofFloat;
@RuntimePermissions()
public class SinglePicPreviewActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    FrameLayout btnBack;
    @BindView(R.id.tv_pic_name)
    TextView tvPicName;
    @BindView(R.id.rl_top_bar)
    RelativeLayout rlTopBar;
    @BindView(R.id.pv_image)
    PhotoView pvImage;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.rl_bottom_bar)
    RelativeLayout rlBottomBar;
    @BindView(R.id.ll_download)
    LinearLayout llDownload;
    private FTPPictureModel ftpPictureModel;
    private boolean isShowBar = false;
    private ViewTarget<ImageView, Drawable> into;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pic_preview);
        ftpPictureModel = getIntent().getParcelableExtra("model");
        initView();
        initData();
    }

    private void initData() {
        tvPicName.setText(ftpPictureModel.getName());
        into = Glide.with(this).load(ftpPictureModel.getUrl()).apply(new RequestOptions()
                .placeholder(R.drawable.iv_loading_app)
        ).into(pvImage);
    }

    private void initView() {
        rlTopBar.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rlTopBar.getLayoutParams();
                lp.topMargin = -rlTopBar.getHeight();
                rlTopBar.setLayoutParams(lp);
                FrameLayout.LayoutParams lbp = (FrameLayout.LayoutParams) rlBottomBar.getLayoutParams();
                lbp.bottomMargin = -rlBottomBar.getHeight();
                rlBottomBar.setLayoutParams(lbp);
            }
        });

    }

    @OnClick({R.id.btn_back,  R.id.pv_image,R.id.ll_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.pv_image:
                if (isShowBar) {
                    hideBar();
                } else {
                    showBar();
                }
                break;
            case R.id.ll_download:
                SinglePicPreviewActivityPermissionsDispatcher.saveBitmapToSDadWithPermissionCheck(this);
                break;
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    protected void saveBitmapToSDad() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(getApplicationContext(), "SD卡不存在或状态异常，不能写入", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap;
        FileOutputStream fos = null;
        if (into != null) {
            ImageView view = into.getView();
            Drawable drawable = view.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                 bitmap = bitmapDrawable.getBitmap();
                // 首先保存图片
                File appDir = new File(Environment.getExternalStorageDirectory(), "/ftpPictures/" + ftpPictureModel.getDate().replaceAll("-", ""));
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }
                String fileName = ftpPictureModel.getName();
                File file = new File(appDir, fileName);
                try {
                    fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    Toast.makeText(SinglePicPreviewActivity.this, "图片保存成功！"+"路径："+appDir.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                  try {
                      if(fos!=null){
                          fos.close();
                      }
                  }catch (Exception e){
                      e.printStackTrace();
                  }

                }
            } else {
                Toast.makeText(this, "图片加载中，请稍后！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "图片加载中，请稍后！", Toast.LENGTH_SHORT).show();
        }

    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForSDcard(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("需要SD卡写入权限！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    /**
     * 显示头部和尾部栏
     */
    private void showBar() {
        isShowBar = true;
        rlTopBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rlTopBar != null) {
                    ofFloat(rlTopBar, "translationY", -rlTopBar.getHeight(), rlTopBar.getHeight())
                            .setDuration(300).start();
                    ofFloat(rlBottomBar, "translationY", rlBottomBar.getHeight(), -rlBottomBar.getHeight())
                            .setDuration(300).start();
                }
            }
        }, 100);
    }

    /**
     * 隐藏头部和尾部栏
     */
    private void hideBar() {
        isShowBar = false;
        ofFloat(rlTopBar, "translationY", rlTopBar.getHeight(), -rlTopBar.getHeight())
                .setDuration(300).start();
        ofFloat(rlBottomBar, "translationY", -rlBottomBar.getHeight(), rlBottomBar.getHeight())
                .setDuration(300).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        SinglePicPreviewActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
