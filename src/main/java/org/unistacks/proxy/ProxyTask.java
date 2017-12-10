package org.unistacks.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gyges on 2017/10/11
 */
public class ProxyTask implements Runnable {

    private Socket socketIn;
    private Socket socketOut;

    private long totalUpload = 0l;
    private long totalDownload = 0l;

    public ProxyTask(Socket socket) {
        this.socketIn = socket;
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String AUTHORED = "HTTP/1.1 200 Connection established\r\n\r\n";
    private static final String SERVERERROR = "HTTP/1.1 500 Connection FAILED\r\n\r\n";


    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\r\n").append("Request Time :" + sdf.format(new Date()));

            InputStream isIn = socketIn.getInputStream();
            OutputStream osIN = socketOut.getOutputStream();

            HttpHeader header = new HttpHeader();

        } catch (Exception e) {
            e.printStackTrace();
            if (!socketIn.isOutputShutdown()) {
                //如果还可以返回错误状态的话，返回内部错误
                try {
                    socketIn.getOutputStream().write(SERVERERROR.getBytes());
                } catch (IOException e1) {
                }
            }
        }

    }
}
