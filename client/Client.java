package chatroom.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Scanner sc = new Scanner(System.in);

    private static String username;
    private static String serverAddress;
    private static Socket socket;

    private static BufferedWriter bw;
    private static BufferedReader br;


    private static Socket connect(String address) throws IOException {
        Socket socket = new Socket();

        try {
            socket.connect(new InetSocketAddress(address.split(":")[0], Integer.parseInt(address.split(":")[1])));
        } catch (Exception _) {
        }

        return socket;
    }

    private static void sendMessage(String message) throws IOException {
        bw.write(message);
        bw.newLine();
        bw.flush();
    }

    private static String receiveMessage() throws IOException {
        return br.readLine();
    }

    public static void main(String[] args) throws IOException {
        // 设置用户名
        System.out.print("[Client] 请设置用户名:");
        username = sc.nextLine();
        System.out.println("[Client] 用户名设置完成.");
        System.out.println("[Client] 你好," + username + "!");

        // 输入服务器地址并连接到服务器
        while (true) {
            System.out.print("[Client] 请输入聊天室地址:");
            serverAddress = sc.nextLine();
            socket = connect(serverAddress);
            if (socket.isConnected()) {
                // 初始化输入输出
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // 发送客户端信息(用户名)
                String clientInfo = username;
                sendMessage(clientInfo);

                break;
            } else System.out.println("[Client] 无法连接到聊天室.");
        }

        // 接收服务器信息(状态码)
        String serverInfo = receiveMessage();
        if ("0".equals(serverInfo.split(" ")[0])) {
            System.out.println("[Client] 已连接到聊天室.");
            new Thread(new ServerListenerRunnable(socket)).start();
            new Thread(new ClientTalkRunnable(socket)).start();
        } else
            System.out.println("[Client] 连接失败.");
    }


}

