import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class Server {


    public static void main(String[] args) {

        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8080);
            System.out.println("Server started");
            socket = server.accept();
            System.out.println("New Client " + socket);
            Scanner in =  new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            Thread thread = new Thread(() -> {
                while (true) {
                    String message = sc.nextLine();
                    out.println(message);
                }
            });
            thread.start();
            thread.setDaemon(true);

            while (true) {
                String msg = in.nextLine();
                if (msg.equals("/end")) break;
                System.out.println("fromClient: " + new SimpleDateFormat(
                        "yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime())
                        + "\n" + msg + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                server.close();
                System.out.println("Server closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}