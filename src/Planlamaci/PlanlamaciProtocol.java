package Planlamaci;

public class PlanlamaciProtocol {
    static int id=0;
    final int WAITING = 0;
    final int AUTHORİZED = 1;
    final int INVALIDAUTH = 2;
    final int AUTHORİZATİONSTATE=4;
    int currentState=WAITING;
    public String inputResponse(String input){
        if(input.equalsIgnoreCase("login")){
            currentState=AUTHORİZATİONSTATE;
        }
        switch (currentState){
            case AUTHORİZATİONSTATE:
                return "Give Credits:";

        }
        return "";
    }
}
