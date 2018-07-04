package com.synertone.ftpmoudle.model;

public class FTPUserModel {
    private String ipAddress;
    private String ipPort;
    private String userName;
    private String userPassword;
    private boolean useSSL=false;
    private String secureSocketProtocol;
    private boolean implicitSecurity;
    private int connectTimeout = 15000;
    private int socketTimeout = 130000;
    private boolean compressedFileTransfer;

    public FTPUserModel(String ipAddress, String ipPort, String userName, String userPassword) {
        this.ipAddress = ipAddress;
        this.ipPort = ipPort;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public String getSecureSocketProtocol() {
        return secureSocketProtocol;
    }

    public void setSecureSocketProtocol(String secureSocketProtocol) {
        this.secureSocketProtocol = secureSocketProtocol;
    }

    public boolean isImplicitSecurity() {
        return implicitSecurity;
    }

    public void setImplicitSecurity(boolean implicitSecurity) {
        this.implicitSecurity = implicitSecurity;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public boolean isCompressedFileTransfer() {
        return compressedFileTransfer;
    }

    public void setCompressedFileTransfer(boolean compressedFileTransfer) {
        this.compressedFileTransfer = compressedFileTransfer;
    }
}
