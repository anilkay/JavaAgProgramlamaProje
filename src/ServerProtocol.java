import Ismakinesi.IsMakinesi;
import Ismakinesi.MakineTuru;
import Utils.Databases;
import Utils.IdUtils;

public class ServerProtocol {
    static int id=0;
    final int WAITING = 0;
    final int AUTHORİZED = 1;
    final int INVALIDAUTH = 2;
    int currentState=WAITING;
    public int isMakinesiId;
    public ServerProtocol(String clientType) {
        this.ClientType = clientType;
        if(clientType.equalsIgnoreCase("ismakinesi")){
            currentState=AUTHORİZED;
            responseIsmakinesi(clientType);
        }
    }

    String ClientType;

    public String determineFunction(String input) {
        if (ClientType.equalsIgnoreCase("planlamaci")) {
            return responsePlanla(input);
        } else {
            return responseIsmakinesi(input);
        }
    }

    public String[] determineFunction2(String input) {
        String[] splittedInput = input.split("\\s");
        if (splittedInput[0].equalsIgnoreCase("planlamaci")) {
            if (splittedInput[1].equalsIgnoreCase("status")) {
                return new String[]{"statu", splittedInput[2]};
            }
            if (splittedInput[1].equalsIgnoreCase("work")) {
                return new String[]{"ata", splittedInput[2], splittedInput[3]}; //2 type 3 birim
            }
            if (splittedInput[1].equalsIgnoreCase("login")) {
                //Login mevzusu burada yapılacak.
            }
        }
        return new String[]{"geçersiz"};
    }
    public String responsePlanla(String input) {
        return "Give Credits";
    }

    public String responseIsmakinesi(String input) {
        IsMakinesi ismakinesi = new IsMakinesi("isim", MakineTuru.CNC);
        ismakinesi.makineId = IdUtils.makineId++;
        System.out.println(ismakinesi.statu);
        Databases.ısMakinesiListesi.add(ismakinesi);
        this.isMakinesiId = isMakinesiId;
        return "ismakinesi baglandın " + ismakinesi.makineId;
    }

    public String handleInputPlanlama(String input) {
        return "";
    }
}
