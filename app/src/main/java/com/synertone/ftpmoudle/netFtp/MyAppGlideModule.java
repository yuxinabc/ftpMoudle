package com.synertone.ftpmoudle.netFtp;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

@GlideModule
public class MyAppGlideModule extends AppGlideModule {
  @Override
  public void registerComponents(Context context, Glide glide, Registry registry) {
    registry.prepend(String.class, InputStream.class, new FTPModelLoaderFactory(context));
  }
}
