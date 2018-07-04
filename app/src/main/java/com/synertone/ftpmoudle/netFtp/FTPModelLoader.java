package com.synertone.ftpmoudle.netFtp;

import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import com.synertone.ftpmoudle.model.FTPUserModel;


import java.io.InputStream;

/**
 * Loads an {@link InputStream} from a Base 64 encoded String.
 */
public final class FTPModelLoader implements ModelLoader<String, InputStream> {
  // From: ftp:/developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/Data_URIs.png
  private static final String DATA_URI_PREFIX = "ftp:";
  private FTPUserModel ftpUserModel;
  public FTPModelLoader(FTPUserModel ftpUserModel) {
    this.ftpUserModel=ftpUserModel;
  }

  @Nullable
  @Override
  public LoadData<InputStream> buildLoadData(String model, int width, int height, Options options) {
    return new LoadData<>(new ObjectKey(model), new FTPDataFetcher(model,ftpUserModel));
  }

  @Override
  public boolean handles(String model) {
    return model.startsWith(DATA_URI_PREFIX);
  }
}
