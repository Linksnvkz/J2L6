import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Socket socket = null;

        try {
            socket = new Socket("localhost", 8080);
            System.out.println("connection initialized");
            Scanner in =  new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            Thread thread = new Thread(() -> {

                while (true) {
                    String msg = in.nextLine();
                    System.out.println("fromServer: " + new SimpleDateFormat(
                            "yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime())
                            + "\n" + msg + "\n");
                }

            });
            thread.start();
            thread.setDaemon(true);

            while (true) {
                String message = sc.nextLine();
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
