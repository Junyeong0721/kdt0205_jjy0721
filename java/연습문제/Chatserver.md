# 소스 코드
```
import java.io.*;
import java.net.*;

public class Chatserver {
    public static void main(String[] args) {
        ServerSocket server = null;

        try {
            server = new ServerSocket(5000);
            System.out.println("서버 시작됨. 클라이언트 접속 대기 중...");

            while (true) {
                Socket socket = server.accept();
                System.out.println("클라이언트 접속됨: " + socket.getInetAddress());
                new ClientHandler(socket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (server != null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
        	in = new BufferedReader(
        		    new InputStreamReader(socket.getInputStream(), "UTF-8"));
        		out = new PrintWriter(
        		    new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)

            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("[클라이언트] : " + msg);
                if (msg.equalsIgnoreCase("bye")) break;
                out.println("[서버] : " + msg);
            }

        } catch (IOException e) {
            System.out.println("클라이언트 연결 오류");
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
                System.out.println("클라이언트 연결 종료됨");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```
