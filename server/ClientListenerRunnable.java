package chatroom.server;

import java.io.*;
import java.net.Socket;

class ClientListenerRunnable implements Runnable {
    private BufferedReader br;
    private BufferedWriter bw;
    private String[] clientInfo;


    public ClientListenerRunnable(Socket clientSocket) throws IOException {
        br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        clientInfo = br.readLine().split(" ");
    }

    @Override
    public void run() {
        try {
            while (true) {

                System.out.println("[Server] 用户 " + clientInfo[0] + " 连接到聊天室.");
                sendMessage("0");

                talk(br, clientInfo[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void talk(BufferedReader br, String username) throws IOException {
        while (true) {
            String message = br.readLine();
            System.out.println("<" + username + "> " + message);

            for (Socket s : Server.clientSocketList)
                sendMessage(s, "<" + username + "> " + message);
        }
    }

    public void sendMessage(String message) throws IOException {
        bw.write(message);
        bw.newLine();
        bw.flush();
    }

    public void sendMessage(Socket s, String message) throws IOException {
        bw.write(message);
        bw.newLine();
        bw.flush();
    }
}