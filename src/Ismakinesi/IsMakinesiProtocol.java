package Ismakinesi;

public class IsMakinesiProtocol {
    static int id=0;
    final int WAITING = 0;
    final int WORKING=5;
    int currentState=WAITING;
    public String ProcessInput(String input){
        return null;
    }

    public IsMakinesiProtocol() {
        currentState=WAITING;
    }

    public void toWaitingState(){
        currentState=WORKING;
    }
}
