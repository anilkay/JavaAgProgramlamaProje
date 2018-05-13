import Utils.Databases;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class ServerThing  extends Thread {
    Socket socket;

    public String getAllStatus() {
        final String[] all = new String[1];
        all[0] = "";
        Databases.ısMakinesiListesi.forEach(i -> {
            all[0] += i.toString() + "\n";
        });

        return all[0];
    }

    public ServerThing(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        try {
            Scanner s=new Scanner(socket.getInputStream());
            PrintWriter writer=new PrintWriter(socket.getOutputStream());
            String clientType=s.nextLine();
            ServerProtocol protocol=new ServerProtocol(clientType);
            String message = protocol.determineFunction();
            String message2 = "";
            while (true) {
                writer.println(message);
                writer.flush();

                message2 = s.nextLine();
                System.out.println(message2);
                message = protocol.determineFunction();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Rundım piştim");
    }
}

public class MServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5891);
        while (true) {
            System.out.println("Accept öncesii");
            Socket socket = serverSocket.accept(); //Düzgün bir şekilde çalışıyor gibi görünüyor.
            new ServerThing(socket).start();
            System.out.println("Bağlantı gerçekleşti");
        }
    }
}
