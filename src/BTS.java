import java.util.ArrayList;

public class BTS implements Actions{
    private int ID;//station id
    private final ArrayList<String> messages = new ArrayList<>();
    public ArrayList<String> getMessages() {
        return messages;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public int getID(){
        return ID;
    }
    @Override
    public void receiveSMS(String message, LayoutController layoutController) {
        messages.add(message);
        new Thread(()->{
            try{
                System.out.println("Message arrived to BTS: " + ID + " message: " + message);
                Thread.sleep(3000);
                layoutController.sendSMSFromBTSLayout(this,message);
                messages.remove(message);
            }catch (InterruptedException ignored){}
        }).start();
    }
}
