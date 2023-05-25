import java.util.ArrayList;
import java.util.Random;

public abstract class Layout {
}
class VBDLayout extends Layout{
    private final ArrayList<VBD> vbdList = new ArrayList<VBD>();
    public ArrayList<VBD> getVbdList() {
        return vbdList;
    }
    public void addVbd(VBD vbd) {
        vbdList.add(vbd);
    }
}
class BTSLayout extends Layout{
    private final ArrayList<BTS> btsList = new ArrayList<BTS>();
    public ArrayList<BTS> getBtsList() {
        return btsList;
    }
    public void addBts(BTS bts) {
        btsList.add(bts);
    }
    public BTS getBTSWithLeastMessages(){
        BTS  btsWithLeastMessages = null;
        int min = Integer.MAX_VALUE;
        for(BTS bts : btsList){
            if(bts.getMessages().size() < min){
                btsWithLeastMessages = bts;
                min = bts.getMessages().size();
            }
        }
        return btsWithLeastMessages;
    }
}
class BSCLayout extends Layout{
    private final ArrayList<BSC> bscList = new ArrayList<BSC>();
    public ArrayList<BSC> getBscList() {
        return bscList;
    }
    public void addBsc(BSC bsc) {
        bscList.add(bsc);
    }
    public BSC getBSCWithLeastMessages(){
        BSC  bscWithLeastMessages = null;
        int min = Integer.MAX_VALUE;
        for(BSC bsc : bscList){
            if(bsc.getMessages().size() < min){
                bscWithLeastMessages = bsc;
                min = bsc.getMessages().size();
            }
        }
        return bscWithLeastMessages;
    }
}
class VRDLayout extends Layout implements Inter{
    private final ArrayList<VRD> vrdList = new ArrayList<VRD>();
    public ArrayList<VRD> getVrdList() {
        return vrdList;
    }
    public void addVrd(VRD vrd) {
        vrdList.add(vrd);
    }
    public VRD findVRDByEncodedNumber(String message){
        Decoding decoding = new Decoding(message);
        String decodedNumberSender = decoding.senderNumber();
        String decodedNumberReceiver = decoding.receiverNumber();
        String messageText= decoding.message();

        int numberReceiver = (int) decoding.numberToInt(decodedNumberReceiver);
        for(VRD vrd : vrdList){
            if(vrd.getID() == numberReceiver){
                return vrd;
            }
        }
        return null;
    }
    @Override
    public int getRandomVRDNumber() {
        Random random = new Random();
        int index = random.nextInt(vrdList.size());
        VRD vrd = vrdList.get(index);
        int number = vrd.getID();
        return number;
    }
}