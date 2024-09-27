package chatroom.server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private static Scanner sc = new Scanner(System.in);

    private static int port;
    private static String host;
    private static ServerSocket serverSocket;
    static ArrayList<Socket> clientSocketList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // 设置端口
        System.out.print("[Server] 请设置端口:");
        port = sc.nextInt();

        // 获取服务器地址
        host = InetAddress.getLocalHost().getHostAddress();

        // 启动服务器
        serverSocket = new ServerSocket(port);
        System.out.println("[Server] 服务器已启动.");
        System.out.println("[Server] 地址:" + host + ":" + port);

        // 监听客户端连接
        new Thread(new ConnectListenerRunnable(serverSocket)).start();
    }
}


