import java.util.ArrayList;

public class LayoutController implements LogicToGraphic, LogicToLogic{
    private final ArrayList<Layout> layoutList = new ArrayList<>();

    public void addLayout(Layout layout){
        layoutList.add(layout);
    }

    @Override
    public void sendSMSFromVBDLayout(String message){
        BTSLayout btsLayout = (BTSLayout)layoutList.get(1);
        BTS bts = btsLayout.getBTSWithLeastMessages();
        bts.receiveSMS(message,this);
    }
    @Override
    public void sendSMSFromBTSLayout(BTS bts, String message){
        int index = -1;
        for(int i=0;i<layoutList.size();i++){
            Layout layout = layoutList.get(i);
            if(layout instanceof BTSLayout btsLayout){
                if(btsLayout.getBtsList().contains(bts)){
                    index = i;
                    break;
                }
            }
        }
        if(index != -1){
            for(int i=index+1;i<layoutList.size();i++){
                Layout layout = layoutList.get(i);
                if(layout instanceof BSCLayout bscLayout){
                    BSC bsc = bscLayout.getBSCWithLeastMessages();
                    bsc.receiveSMS(message,this);
                    return;
                } else if(layout instanceof VRDLayout vrdLayout){
                    try{
                        VRD vrd = vrdLayout.findVRDByEncodedNumber(message);
                        Decoding decoding = new Decoding(message);
                        String decodedSenderNumber = decoding.senderNumber();
                        String decodedReceiverNumber = decoding.receiverNumber();
                        String message1 = decoding.message();
                        vrd.receiveSMS(message1,this);
                        return;
                    } catch (Exception e) {
                        System.out.println("Receiver not found");
                    }
                }
            }
        }
    }
    @Override
    public void sendSMSFromBSCLayout(BSC bsc, String message){
        int index = -1;
        for(int i=0;i<layoutList.size();i++){
            Layout layout = layoutList.get(i);
            if(layout instanceof BSCLayout bscLayout){
                if(bscLayout.getBscList().contains(bsc)){
                    index = i;
                    break;
                }
            }
        }
        if(index!= -1){
            for(int i=index+1;i<layoutList.size();i++){
                Layout layout=layoutList.get(i);
                if(layout instanceof BTSLayout btsLayout){
                    BTS bts = btsLayout.getBTSWithLeastMessages();
                    bts.receiveSMS(message,this);
                    return;
                } else if(layout instanceof BSCLayout bscLayout){
                    BSC bscNext = bscLayout.getBSCWithLeastMessages();
                    bscNext.receiveSMS(message,this);
                    return;
                }
            }
        }
    }

    @Override
    public ArrayList<Layout> getLayoutInter() {
        return layoutList;
    }
    @Override
    public void addLayoutInter(Layout layout) {
        layoutList.add(layout);
    }
    @Override
    public void addBSCLayoutInter(BSCLayout bscLayout) {
        layoutList.add(2, bscLayout);
    }
    @Override
    public void setLayoutInter(int index, Layout layout) {
        layoutList.set(index, layout);
        System.out.println("layout has been set");
    }
    @Override
    public void removeLayoutInter(BSCLayout bscLayout) {
        layoutList.remove(bscLayout);
    }
    @Override
    public void removeBSCLayoutInter() {
        BSCLayout  bscLayout = (BSCLayout)layoutList.get(2);
        for(BSC bsc : bscLayout.getBscList()){
            bsc.sendAllMessages(this);
        }
        layoutList.remove(bscLayout);
    }
}
