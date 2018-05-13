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
            responseIsmakinesi();
        }
    }

    String ClientType;

    public String determineFunction() {
        if (ClientType.equalsIgnoreCase("planlamaci")) {
            return responsePlanla();
        } else {
           return responseIsmakinesi();
        }
    }

    public String responsePlanla() {
        return "Give Credits";
    }

    public String responseIsmakinesi() {
        return "ismakinesi baglandın";
    }
}
