import java.util.ArrayList;

public class VRD implements Actions {
    private final ArrayList<String> messages = new ArrayList<String>();
    private int ID;
    private boolean showAmountOfSms;
    private boolean clearMessages = false;
    public VRD(){
        startClearing();
    }
    public ArrayList<String> getMessages(){
        return messages;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public int getID(){
        return ID;
    }
    public void setShowAmountOfSms(boolean showAmountOfSms){
        this.showAmountOfSms = showAmountOfSms;
    }
    public synchronized void setClearMessages(boolean clearMessages){
        this.clearMessages = clearMessages;
        if(clearMessages){
            notify();
        }
    }
    public void startClearing(){
        new Thread(()->{
            while(true) {
                try {
                    if (clearMessages) {
                        messages.clear();
                        System.out.println("Cleared");
                        Thread.sleep(10000);
                    } else {
                        synchronized (this) {
                            while (!clearMessages) {
                                wait();
                            }
                        }
                    }
                } catch (InterruptedException e){}//Thread.currentThread().interrupt();
            }
        }).start();
    }
    @Override
    public void receiveSMS(String message, LayoutController layoutController) {
        messages.add(message);
        System.out.println("Message was successfully received: " + ID + " message: " + message);
    }
}
