import java.util.ArrayList;

public class VBD extends Thread{
    private Inter inter;
    private LogicToLogic logicToLogic;
    private ArrayList<String> messages = new ArrayList<String>();
    private String message;
    private int frequency;
    private int ID;
    private boolean active;
    public VBD (int frequency){

        this.frequency = frequency;
    }
    public void InitLogic(LogicToLogic logicToLogic){
        this.logicToLogic = logicToLogic;
    }
    public void init(Inter inter){
        this.inter = inter;
    }
    public void addMessage(String message){
        messages.add(message);
    }
    public ArrayList<String> getSentMessages(){
        return messages;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getID(){
        return ID;
    }
    public synchronized void setActive(boolean active){
        this.active = active;
        if(active){
            notify();
        }
    }
    @Override
    public void run() {
        while(true){
            try{
                if(active){
                    System.out.println("Message: " + message + " sent by: " + ID);
                    logicToLogic.sendSMSFromVBDLayout(message);

                    Decoding decoding = new Decoding(message);
                    String decodedSenderNumber = decoding.senderNumber();
                    String decodedReceiverNumber = decoding.receiverNumber();
                    String message1 = decoding.message();

                    addMessage(message1);
                    Thread.sleep(frequency);
                } else{
                    synchronized (this){
                        while(!active){
                            wait();
                        }
                    }
                }
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    public int getRandomReceiverID(){
        int receiverNumber = inter.getRandomVRDNumber();
        return receiverNumber;
    }
}
