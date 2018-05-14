package Ismakinesi;


import Utils.Databases;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class doWork extends Thread {
    Socket socket;
    int isMakinesiId;
    int birimhiz;
    int gelenis;

    public doWork(Socket socket, int isMakinesiId, int birimhiz, int gelenis) {
        this.socket = socket;
        this.isMakinesiId = isMakinesiId;
        this.birimhiz = birimhiz;
        this.gelenis = gelenis;
    }

    @Override
    public void run() {

        long now = System.currentTimeMillis();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.println("ismakinesi busy " + isMakinesiId);
        writer.flush();
        try {
            Thread.sleep(1000 * 60 * gelenis / birimhiz);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writer.println("ismakinesi empty " + isMakinesiId);
        writer.flush();
        //İsmakinesi //boş hale geliyor.


    }
}

class PlanlamaciThing2 extends Thread {
    IsMakinesiProtocol protocol;
    int isMakinesiId;
    int birimhiz;

    public String MyWork(int gelenis) throws IOException, InterruptedException {
        long now = System.currentTimeMillis();
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println("ismakinesi busy " + isMakinesiId);
        writer.flush();
        Thread.sleep(1000 * 60 * gelenis / birimhiz);
        writer.println("ismakinesi empty " + isMakinesiId);
        writer.flush();
        //İsmakinesi //boş hale geliyor.
        return "";

    }

    Socket socket;
    ExecutorService executorService;

    public PlanlamaciThing2(Socket socket, ExecutorService executorService) {
        this.socket = socket;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        super.run();
        protocol = new IsMakinesiProtocol();
        try {
            Scanner s = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            MakineTuru makineTuru = MakineTuru.CNC;
            int birimhiz = 5;
            String makineismi = "isim";
            writer.println("ismakinesi " + makineTuru.name() + " " + birimhiz + " " + makineismi);
            writer.flush();
            String message = s.nextLine();
            System.out.println("Okundu");
            if (message.equalsIgnoreCase("ismakinesi baglandın")) {
                System.out.println("Serverla bağlantı gerçeklendi");
                writer.println("Kabul edildi");
                writer.flush(); //Bunu yazıp bitireceğiz işlemi.
                //Yapılacak diğer şeyler burada verilecek
            }
            while (true) {
                System.out.println(message);
                message = s.nextLine();
                String[] allmsg = message.split("\\s");

                if (allmsg[1].equalsIgnoreCase("work")) {
                    if (isMakinesiId == Integer.parseInt(allmsg[0])) {
                        if (Databases.ısMakinesiListesi.get(isMakinesiId).statu.equalsIgnoreCase("empty")) {
                            doWork dowork2 = new doWork(socket, isMakinesiId, birimhiz, Integer.parseInt(allmsg[3]));
                            dowork2.start();
                        } else {
                            while (true) {
                                if (Databases.ısMakinesiListesi.get(isMakinesiId).statu.equalsIgnoreCase("empty")) {
                                    doWork dowork2 = new doWork(socket, isMakinesiId, birimhiz, Integer.parseInt(allmsg[3]));
                                    dowork2.start();
                                    break;
                                }

                            }
                        }
                    }
                } else if (allmsg[1].equalsIgnoreCase("connect")) {
                    System.out.println("Connect state");
                    isMakinesiId = Integer.parseInt(allmsg[2]);
                    System.out.println("ismakinesi id " + isMakinesiId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            executorService.shutdownNow(); //Executor servis ile birtakım oyunlar gerçekleştirildi.
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Ben is makinesi olarak emir bekliyorum");
    }
}

public class IsMakinesiClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 5891);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new PlanlamaciThing2(socket, executorService));

        System.out.println("Bağlandım");
        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}
