package com.synertone.ftpmoudle;

import android.app.Application;
import net.gotev.uploadservice.Logger;
import net.gotev.uploadservice.UploadService;
/**
 * @author gotev (Aleksandar Gotev)
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        // Set upload service debug log messages level
        Logger.setLogLevel(Logger.LogLevel.DEBUG);
        // Set up the Http Stack to use. If you omit this or comment it, HurlStack will be
        // used by default
        // setup backoff multiplier
        UploadService.BACKOFF_MULTIPLIER = 2;
    }
}
