package org.unistacks.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Gyges on 2017/10/12
 */
public class SimpleSocketServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        int port = 8089;
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            is = socket.getInputStream();
            byte[] b = new byte[1024];
            int n = is.read(b);
            System.out.println(new String(b,0,n));
            os = socket.getOutputStream();
            os.write(b,0,n);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
                serverSocket.close();
                socket.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
