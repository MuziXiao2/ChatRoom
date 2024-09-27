package chatroom.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectListenerRunnable implements Runnable {
    private final ServerSocket serverSocket;

    public ConnectListenerRunnable(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Socket clientSocket;
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                Server.clientSocketList.add(clientSocket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 监听客户端信息
            try {
                new Thread(new ClientListenerRunnable(clientSocket)).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
