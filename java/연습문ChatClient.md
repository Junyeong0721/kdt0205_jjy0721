# 소스 코드
```
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        Socket socket = null;
        PrintWriter out = null;
        Scanner scanner = new Scanner(System.in);

        try {
            socket = new Socket("localhost", 5000);
            System.out.println("서버에 연결되었습니다.");


            final BufferedReader in = new BufferedReader(
            	    new InputStreamReader(socket.getInputStream()));
            	out = new PrintWriter(
            	    new OutputStreamWriter(socket.getOutputStream()), true); 

            Thread receiver = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("서버와의 연결이 종료되었습니다.");
                }
            });
            receiver.start();

            String userMessage;
            while (true) {
                System.out.print("메시지 입력 (bye 입력 시 종료): ");
                userMessage = scanner.nextLine();
                out.println(userMessage);
                if (userMessage.equalsIgnoreCase("bye")) break;
            }

        } catch (IOException e) {
            System.out.println("서버에 연결할 수 없습니다: " + e.getMessage());
        } finally {
            try {
                scanner.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
                System.out.println("클라이언트 종료됨.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```
