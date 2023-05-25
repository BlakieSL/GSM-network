public class Testrer1 {
    public static void main(String[] args) {
        LayoutController layoutController = new LayoutController();
        startSettings(layoutController);
        MainFrame mainFrame = new MainFrame(layoutController);
    }
    public static void startSettings(LayoutController layoutController){

        VBDLayout firstVBD_layout = new VBDLayout();
        BTSLayout firstBTS_layout = new BTSLayout();
        BSCLayout firstBSC_layout = new BSCLayout();
        BTSLayout secondBTS_layout = new BTSLayout();
        VRDLayout firstVRD_layout = new VRDLayout();

        layoutController.addLayout(firstVBD_layout);
        layoutController.addLayout(firstBTS_layout);
        layoutController.addLayout(firstBSC_layout);
        layoutController.addLayout(secondBTS_layout);
        layoutController.addLayout(firstVRD_layout);

    }
}
