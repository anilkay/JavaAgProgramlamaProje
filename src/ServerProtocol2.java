public class ServerProtocol2 {
    static int id = 0;
    final int WAITING = 0;
    final int AUTHORİZED = 1;
    final int INVALIDAUTH = 2;
    int currentState = WAITING;

    public String handleInputPlanla(String input) {
        String[] allin = input.split("\\s");
        if (allin[1].equalsIgnoreCase("first")) {
            return "send login";
        }
        return "Unrecognized input";
    }
}
