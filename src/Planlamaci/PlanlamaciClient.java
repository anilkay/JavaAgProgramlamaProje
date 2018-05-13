package Planlamaci;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class PlanlamaciThing extends Thread {
    Socket socket;
    PlanlamaciProtocol protocol;

    public PlanlamaciThing(Socket socket, PlanlamaciProtocol protocol) {
        this.socket = socket;
        this.protocol = protocol;
    }

    @Override
    public void run() {
        super.run();

        try {
            Scanner s = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println("Planlamacı");
            writer.flush();
            System.out.println("Flush yapıldı");
            String message = s.nextLine();
            System.out.println(message);
            while (true) {
                message = s.nextLine();
                String takeAction = protocol.inputResponse(message);
                if (takeAction.equalsIgnoreCase("Give Credits")) {
                    String userName = "anil"; //Bunu Scannera yollama işlemini gerçekleştireceğiz.
                    String password = "123";
                    writer.println("username " + userName);
                    writer.println("pass " + password);
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Ben planlamacı olarak emrediyorum");
    }
}

class planlamaScanner extends Thread {
    Socket socket;
    PlanlamaciProtocol protocol;

    public planlamaScanner(Socket socket, PlanlamaciProtocol protocol) {
        this.socket = socket;
        this.protocol = protocol;
    }

    @Override
    public void run() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner consoleScanner = new Scanner(System.in);
        writer.flush();
        while (true) {
            String is = consoleScanner.nextLine();
            writer.println(is);
            writer.flush();
            System.out.println(is);
        }
    }
}

public class PlanlamaciClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 5891);
        PlanlamaciProtocol protocol = new PlanlamaciProtocol();
        //Bu şekilde protokolleri aynılatırarak senkronizasyona erişimi sağlıyoruz.
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new PlanlamaciThing(socket,protocol));
        executorService.submit(new planlamaScanner(socket,protocol));
        System.out.println("İçsel olaylar");

        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}
