package Ismakinesi;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class PlanlamaciThing2 extends Thread{
 IsMakinesiProtocol protocol;
    int isMakinesiId;
    int birimhiz;

    public String MyWork() throws IOException {
        long now = System.currentTimeMillis();
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println("ismakinesi busy " + isMakinesiId);
        while (now + birimhiz * 60 * 1000 >= System.currentTimeMillis()) { //Şuna uzun bir biçimde bak arkadaşım

        }
        writer.println("ismakinesi empty " + isMakinesiId);
        return "";

    }
    Socket socket;
    ExecutorService executorService;
    public PlanlamaciThing2(Socket socket,ExecutorService executorService) {
        this.socket = socket;
        this.executorService=executorService;
    }

    @Override
    public void run() {
        super.run();
        protocol=new IsMakinesiProtocol();
        try {
            Scanner s = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            MakineTuru makineTuru = MakineTuru.CNC;
            int birimhiz = 5;
            String makineismi = "isim";
            writer.println("ismakinesi " + makineTuru.name() + " " + birimhiz + " " + makineismi);
            writer.flush();
            String message=s.nextLine();
            System.out.println("Okundu");
            if(message.equalsIgnoreCase("ismakinesi baglandın")){
                System.out.println("Serverla bağlantı gerçeklendi");
                writer.println("Kabul edildi");
                writer.flush();
                //Yapılacak diğer şeyler burada verilecek
            }
            while(true){
                System.out.println(message);
                message=s.nextLine();
                if(message.equalsIgnoreCase("dowork")){
                    MyWork();
                }
            }
        } catch (IOException e) {
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
    public static void main(String []args) throws IOException, InterruptedException {
        Socket socket=new Socket(InetAddress.getLocalHost(),5891);
        ExecutorService executorService=Executors.newCachedThreadPool();
        executorService.submit(new PlanlamaciThing2(socket,executorService));

        System.out.println("Bağlandım");
        executorService.awaitTermination(1,TimeUnit.DAYS);
    }
}
