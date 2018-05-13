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

    public String responsePlanla(String input) {
        return "Give Credits";
    }

    public String responseIsmakinesi(String input) {
        IsMakinesi ismakinesi = new IsMakinesi("isim", MakineTuru.CNC);
        ismakinesi.makineId = IdUtils.makineId++;
        System.out.println(ismakinesi.statu);
        Databases.ısMakinesiListesi.add(ismakinesi);
        return "ismakinesi baglandın";
    }

    public String handleInputPlanlama(String input) {
        return "";
    }
}
