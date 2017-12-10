package org.unistacks.socket;

import java.io.*;
import java.net.*;

/**
 * Created by Gyges on 2017/10/9
 */
public class UDPServer {

    public static void main(String args[]) throws Exception
    {
        //监听8080端口
        DatagramSocket serverSocket = new DatagramSocket(8080);
        byte[] receiveData = new byte[4096];
//        byte[] sendData = new byte[1024];
        while(true)
        {
            //构造数据包接收数据
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            //接收数据
            serverSocket.receive(receivePacket);
            //解析数据
            String sentence = new String( receivePacket.getData());
            System.out.println("RECEIVED: " + sentence);
        }
    }

}
