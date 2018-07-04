package com.synertone.ftpmoudle.netFtp;

import android.content.Context;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.donkingliang.imageselector.utils.StringUtils;
import com.synertone.ftpmoudle.model.FTPUserModel;
import com.synertone.ftpmoudle.utils.GsonUtils;
import com.synertone.ftpmoudle.utils.SharedPreferenceManager;


import java.io.InputStream;


public class FTPModelLoaderFactory implements ModelLoaderFactory<String, InputStream> {
  private FTPUserModel ftpUserModel;
  public FTPModelLoaderFactory(Context context) {
    String ftpuser = SharedPreferenceManager.getString(context, "ftpuser");
    if(StringUtils.isNotEmptyString(ftpuser)){
      ftpUserModel = GsonUtils.fromJson(ftpuser, FTPUserModel.class);
    }
  }

  @Override
  public ModelLoader<String, InputStream> build(MultiModelLoaderFactory multiFactory) {
    return new FTPModelLoader(ftpUserModel);
  }

  @Override
  public void teardown() {
    // Do nothing.
  }
}
