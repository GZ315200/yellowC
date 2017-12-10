package org.unistacks.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Gyges on 2017/10/12
 */
public class SimpleSocketClient {

    public static void main(String[] args) {
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        String serverIP = "127.0.0.1";
        int port = 8080;
        String[] a = {"1","2","3"};
        try {
            socket = new Socket(serverIP, port);
            os = socket.getOutputStream();
            for (int i = 0; i < a.length; i ++) {
                os.write(a[i].getBytes());
                is = socket.getInputStream();
                byte[] b = new byte[1024];
                int n = is.read(b);
                System.out.println("callback is " + n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                os.close();
                socket.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
