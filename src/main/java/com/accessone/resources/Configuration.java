package com.accessone.resources;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
public class Configuration {
    private static Configuration instance = null;
    private String host = "127.0.0.1";
    private int port = 8082;
    private String webviewHost = "127.0.0.1";
    private int webviewPort = 3141;

    protected Configuration() {
    }

    public static Configuration getInstance() {
        if(instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebviewHost() {
        return this.webviewHost;
    }

    public void setWebviewHost(String webviewHost) {
        this.webviewHost = webviewHost;
    }

    public int getWebviewPort() {
        return this.webviewPort;
    }

    public void setWebviewPort(int webviewPort) {
        this.webviewPort = webviewPort;
    }
}
