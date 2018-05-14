import Ismakinesi.IsMakinesi;
import Utils.Databases;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class ServerThing extends Thread {
    Socket socket;

    public String getAllStatus() {
        final String[] all = new String[1];
        all[0] = "";
        Databases.ısMakinesiListesi.forEach(i -> {
            all[0] += i.toString() + "\t";
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
            Scanner s = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            String clientType = s.nextLine();
            ServerProtocol protocol = new ServerProtocol(clientType);
            String message = protocol.determineFunction("");

            String message2 = "";
            while (true) {
                writer.println(message);
                writer.flush();
                writer.println("conn connect " + protocol.isMakinesiId);
                message2 = s.nextLine();
                System.out.println(message2);
                String mesajt1[] = message2.split("\\s");
                if (mesajt1[0].equalsIgnoreCase("ismakinesi")) {
                    if (mesajt1[1].equalsIgnoreCase("busy")) {
                        IsMakinesi isler = Databases.ısMakinesiListesi.get(Integer.parseInt(mesajt1[2]));
                        isler.statu = "busy";
                    }
                    if (mesajt1[1].equalsIgnoreCase("empty")) {
                        IsMakinesi isler = Databases.ısMakinesiListesi.get(Integer.parseInt(mesajt1[2]));
                        isler.statu = "empty";
                    }
                }
                if (mesajt1[0].equalsIgnoreCase("planlamaci")) {
                    if (mesajt1[1].equalsIgnoreCase("work")) {
                        int makineIdsi = Databases.ısMakinesiListesi.get(Integer.parseInt(mesajt1[3])).makineId;
                        writer.println(makineIdsi + " work" + " cnc " + mesajt1[3]);
                    }
                }
                message = protocol.determineFunction(message2);
                String[] gelenler = protocol.determineFunction2(message2);

                if (gelenler[0].equalsIgnoreCase("statu")) {
                    String hepsi = getAllStatus();
                    // Planlamaci planlamaci = Databases.planlamaciListesi.get(Integer.parseInt(gelenler[1]));
                    //PrintWriter writer1 = planlamaci.writer;
                    writer.println("status " + hepsi); //Böylece hepsini yollayacağız
                    writer.flush();
                    //İlk gelişte planlamaci objesi oluşturmamız lazım
                }
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
            //Planlamacıyı bağlamaya çalış
        }
    }
}
