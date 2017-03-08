package com.szh.net;
//主进程中为server 子进程中为client
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestTCP {
    public static void main(String[] args){
        try {
            ServerSocket ssever = new ServerSocket(7777);
            System.out.println("ServerSocket Port:" + ssever.getLocalPort());
            Thread tclient = new Thread(new TCPClient());
            tclient.start();
        
            Socket s1 = ssever.accept();
            InputStream serverIn = s1.getInputStream();
            OutputStream serverOut = s1.getOutputStream();
            System.out.println("Server : "+"connected host : "+"addr --"+s1.getInetAddress().getHostAddress()
                                    +";port --"+s1.getPort());
            BufferedReader serverBR = new BufferedReader(new InputStreamReader(serverIn));
            BufferedWriter serverBW = new BufferedWriter(new OutputStreamWriter(serverOut));
            System.out.println("ready");
//            String serverStr ="aaaaaaaa";//测试数据用
            String serverStr = serverBR.readLine();
            while (true) {
                if (serverStr.equalsIgnoreCase("over")) {
                    s1.close();
                    break;
                }
                System.out.println("Server receive string : " +serverStr);
                Thread.sleep(500);
                serverBW.write(serverStr.toUpperCase());
                serverBW.newLine();
                serverBW.flush();
                serverStr = serverBR.readLine();
            }
            
        } catch (Exception e) {
            System.out.println("main problem");
        }
    }
}
class TCPClient implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            Socket sclient = new Socket("localhost", 7777);
            System.out.println("Client Socket Port"+sclient.getLocalPort());
            BufferedReader consleBR = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter clientBW = new BufferedWriter(new OutputStreamWriter(sclient.getOutputStream()));
            BufferedReader clientBR = new BufferedReader(new InputStreamReader(sclient.getInputStream()));
            
            String consoleStr = consleBR.readLine();
            String receiveStr = null;
            while(true){
                if (consoleStr.equalsIgnoreCase("Over")) {
                    clientBW.write("over");
                    clientBW.newLine();
                    clientBW.flush();
                    break;
                }
                clientBW.write(consoleStr);
                clientBW.newLine();
                clientBW.flush();
//                SOP.sop("console : "+consoleStr);
                receiveStr = clientBR.readLine();
                System.out.println("Client receive string : "+receiveStr);
                consoleStr = consleBR.readLine();
            }
            clientBR.close();
            clientBW.close();
            consleBR.close();
            
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException ...");
        } catch (InterruptedException e1) {
            System.out.println("InterruptedException ...");
        }catch (BindException e2) {
            System.out.println("BindException ...");
        }catch (IOException e) {
            System.out.println("IOEcxeption ... ");
        }
    }

}