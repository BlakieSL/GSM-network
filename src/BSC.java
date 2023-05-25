import java.util.ArrayList;

public class BSC implements Actions{
    private int ID;
    private final ArrayList<String> messages = new ArrayList<>();
    public ArrayList<String> getMessages() {
        return messages;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getID() {
        return ID;
    }
    public boolean isDeleted;
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    @Override
    public void receiveSMS(String message, LayoutController layoutController) {
            messages.add(message);
            new Thread(() -> {
                try {
                    System.out.println("Message arrived to BSC: " + ID + " message: " + message);
                    Thread.sleep((long) (Math.random() * 10000 + 5000));
                    layoutController.sendSMSFromBSCLayout(this, message);
                    messages.remove(message);
                } catch (InterruptedException e) {
                }
            }).start();
    }
    public void sendAllMessages(LayoutController layoutController) {
        new Thread(() -> {
            try{
                Thread.sleep(0);
                for(int i = 0; i < messages.size(); i++){
                   layoutController.sendSMSFromBSCLayout(this, messages.get(i));
                }
            } catch (InterruptedException e) {}
        }).start();
    }
}
