package com.synertone.ftpmoudle.netFtp;

import android.support.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.synertone.ftpmoudle.model.FTPUserModel;
import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.ftp.FTPUploadTaskParameters;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.IOException;
import java.io.InputStream;

public class FTPDataFetcher implements DataFetcher<InputStream> {

    private final String model;
    private final FTPUserModel ftpUserModel;
    private InputStream inputStream;
    private FTPClient ftpClient;
    public FTPDataFetcher(String model, FTPUserModel ftpUserModel) {
        this.model = model;
        this.ftpUserModel = ftpUserModel;
    }

    @Override
    public void loadData(Priority priority, DataCallback<? super InputStream> callback) {
        if (ftpUserModel != null) {
            try {
                configFTPClient();
                String ftpUrl = model.substring(4);
                inputStream= ftpClient.retrieveFileStream(ftpUrl);
                callback.onDataReady(inputStream);
                inputStream.close();
                ftpClient.completePendingCommand();
                ftpClient.logout();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(inputStream!=null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (ftpClient.isConnected()) {
                    try {
                        ftpClient.disconnect();
                    } catch (Exception exc) {
                    }
                }
                ftpClient = null;

            }
        }

    }

    private void configFTPClient() throws Exception {
        if (ftpUserModel.isUseSSL()) {
            String secureProtocol = ftpUserModel.getSecureSocketProtocol();

            if (secureProtocol == null || secureProtocol.isEmpty())
                secureProtocol = FTPUploadTaskParameters.DEFAULT_SECURE_SOCKET_PROTOCOL;
            ftpClient = new FTPSClient(secureProtocol, ftpUserModel.isImplicitSecurity());
        } else {
            ftpClient = new FTPClient();
        }
        ftpClient.setBufferSize(UploadService.BUFFER_SIZE);
        ftpClient.setDefaultTimeout(ftpUserModel.getConnectTimeout());
        ftpClient.setConnectTimeout(ftpUserModel.getConnectTimeout());
        ftpClient.setAutodetectUTF8(true);
        ftpClient.connect(ftpUserModel.getIpAddress(), Integer.parseInt(ftpUserModel.getIpPort()));

        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            throw new Exception("Can't connect to " + ftpUserModel.getIpAddress()
                    + ":" + ftpUserModel.getIpPort()
                    + ". The server response is: " + ftpClient.getReplyString());
        }
        if (!ftpClient.login(ftpUserModel.getUserName(), ftpUserModel.getUserPassword())) {
            throw new Exception("Error while performing login on " + ftpUserModel.getIpAddress()
                    + ":" + ftpUserModel.getIpPort()
                    + " with username: " + ftpUserModel.getUserName()
                    + ". Check your credentials and try again.");
        }
        // to prevent the socket timeout on the control socket during file transfer,
        // set the control keep alive timeout to a half of the socket timeout
        int controlKeepAliveTimeout = ftpUserModel.getSocketTimeout() / 2 / 1000;
        ftpClient.setSoTimeout(ftpUserModel.getSocketTimeout());
        ftpClient.setControlKeepAliveTimeout(controlKeepAliveTimeout);
        ftpClient.setControlKeepAliveReplyTimeout(controlKeepAliveTimeout * 1000);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setFileTransferMode(ftpUserModel.isCompressedFileTransfer() ?
                FTP.COMPRESSED_TRANSFER_MODE : FTP.STREAM_TRANSFER_MODE);
    }

    @Override
    public void cleanup() {
        // Intentionally empty only because we're not opening an InputStream or another I/O resource!
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void cancel() {
        // Intentionally empty.
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }

}
