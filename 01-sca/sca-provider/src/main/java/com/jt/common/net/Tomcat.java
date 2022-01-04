package com.jt.common.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Tomcat {
    public static void main(String[] args) throws IOException {
        //构建一个Java服务端 Server Socket对象,并在指定端口监听
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("(+) ServerStated!");
        //等等客户端连接，并处理客户端请求

        while (true) {
            //等待客户端 Socket 连接 (accept 方法用于接收客户端请求)
            Socket client = serverSocket.accept();//阻塞式方法 (没有连接请求，线程休眠，让出cpu)
            System.out.println("(/) Client " + client);

            new Thread() {
                public void run() {

                    try {
                        //获取输出流对象，基于此对象向客户端响应数据
                        OutputStream outputStream = client.getOutputStream();
                        outputStream.write(("HTTP/1.1 200 OK \n\r" + //响应行
                                "Content-Type: text/html;charset=utf-8 \n\r" + //响应头
                                "\n\r" + //空行
                                "Echooooooo..." //响应体
                        ).getBytes());
                        outputStream.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();


        }

    }
}

