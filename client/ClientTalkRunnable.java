package chatroom.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientTalkRunnable implements Runnable {
    private static Scanner sc = new Scanner(System.in);
    private final BufferedWriter bw;

    public ClientTalkRunnable(Socket socket) throws IOException {
        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private void sendMessage(String message) throws IOException {
        bw.write(message);
        bw.newLine();
        bw.flush();
    }

    @Override
    public void run() {
        while (true) {
            String message = sc.nextLine();
            try {
                sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
