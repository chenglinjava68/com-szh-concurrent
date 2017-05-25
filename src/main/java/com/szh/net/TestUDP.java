package com.szh.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//主线程为server端接受，子线程为client发送
public class TestUDP {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        UDPClient udpClient = new UDPClient();
        Thread t1 = new Thread(udpClient);
        t1.start();
        UDPServerFunc();
    }

    static void UDPServerFunc() {
        try {
            DatagramSocket dsServer = new DatagramSocket(7777);
            System.out.println("Server DatagramSocket Port" + dsServer.getLocalPort());
            byte[] buf = new byte[1024];
            DatagramPacket dpServer = new DatagramPacket(buf, 0, buf.length);
            dsServer.receive(dpServer);
            System.out.println(dpServer.getAddress().getHostAddress() + " :: "
                    + new String(dpServer.getData(), 0, dpServer.getLength()));
            System.out.println("Server DatagramPacket Port" + dpServer.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UDPClient implements Runnable {
    @Override
    public void run() {
        try {
            DatagramSocket dsClient = new DatagramSocket(6666);
            System.out.println("Client DatagramSocket Port" + dsClient.getLocalPort());
            byte[] buf = "hello this is UDPTest".getBytes();
            DatagramPacket dpClient =
                    new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 7777);
            Thread.sleep(1000);
            System.out.println("Client : " + "wakeup");
            System.out.println("Client DatagramPacket Port" + dpClient.getPort());
            dsClient.send(dpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}