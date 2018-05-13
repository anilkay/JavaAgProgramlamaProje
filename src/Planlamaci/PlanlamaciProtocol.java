package Planlamaci;

public class PlanlamaciProtocol {
    static int id = 0;
    final int WAITING = 0;
    final int AUTHORİZED = 1;
    final int INVALIDAUTH = 2;
    final int AUTHORİZATİONSTATE = 4;
    final int USERNAMESTATE = 5;
    final int PASSWORDSTATE = 6;
    final int AUTHENTİCATEDSTATE = 7;
    int currentState = WAITING;

    public String inputResponse(String input) {
        if (input.equalsIgnoreCase("login")) {
            currentState = AUTHORİZATİONSTATE;
        }
        switch (currentState) {
            case AUTHORİZATİONSTATE:
                currentState = USERNAMESTATE;
                return "Give Credits:";
            case USERNAMESTATE:
                currentState = PASSWORDSTATE;
                return "login scanner";
            case PASSWORDSTATE:
                currentState = AUTHENTİCATEDSTATE;
                return "pass scanner";
            case AUTHENTİCATEDSTATE:
                break;
        }
        return "";
    }
}
