import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class VBDPanel extends JPanel {
    public VBDPanel(String message, LogicToGraphic logicToGraphic) {
        setLayout(new GridLayout(2,1));
        setPreferredSize(new Dimension(197,70));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //updating vbd layout with new vbd and then updating layout list
        int ID = 1000000000 + (int) (Math.random() * 899999999);

        VBD vbd = new VBD( 5000);
        vbd.setID(ID);
        VRDLayout vrdLayout = (VRDLayout) logicToGraphic.getLayoutInter().get(logicToGraphic.getLayoutInter().size()-1);
        vbd.init(vrdLayout);
        vbd.InitLogic((LogicToLogic) logicToGraphic);
        int numberOfReceiver = vbd.getRandomReceiverID();
        String encodedMessage = workWithMessage(message, ID, numberOfReceiver);
        vbd.setMessage(encodedMessage);

        ArrayList<Layout> layoutList = logicToGraphic.getLayoutInter();
        VBDLayout vbdLayout = (VBDLayout) layoutList.get(0);
        vbdLayout.addVbd(vbd);
        logicToGraphic.setLayoutInter(0,vbdLayout);
        vbd.setActive(true);
        vbd.start();

        //
        JPanel additionalPanel = new JPanel(new GridLayout(2,2));
        JPanel additionalPanel2 = new JPanel(new GridLayout(2,2));
        JLabel deviceNumberL = new JLabel("Device number:");
        JTextField deviceNumberF = new JTextField(String.valueOf(ID));
        deviceNumberF.setEditable(false);
        JLabel messageLabel = new JLabel("Message:" + message);
        JButton terminateButton = new JButton("Terminate");
        terminateButton.addActionListener( e -> {
            vbd.interrupt();
            vbdLayout.getVbdList().remove(vbd);
            logicToGraphic.setLayoutInter(0,vbdLayout);

            Container parent = getParent();
            parent.remove(VBDPanel.this);
            parent.revalidate();
            parent.repaint();
        });
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 10000,5000 );
        slider.addChangeListener(e -> {
            int frequency = (int) slider.getValue();
            vbd.setFrequency(frequency);
        });
        JComboBox<String> comboBox = new JComboBox<>
                (new String[]{"ACTIVE", "WAITING"});
        comboBox.addActionListener(e->{
            boolean active = (boolean) Objects.equals(comboBox.getSelectedItem(), "ACTIVE");
            vbd.setActive(active);
        });

        additionalPanel.add(deviceNumberL);
        additionalPanel.add(deviceNumberF);
        additionalPanel.add(terminateButton);
        additionalPanel.add(messageLabel);

        additionalPanel2.add(new JLabel("Frequency:"));
        additionalPanel2.add(slider);
        additionalPanel2.add(new JLabel("Status:"));
        additionalPanel2.add(comboBox);

        add(additionalPanel);
        add(additionalPanel2);
    }
    public String workWithMessage(String message, int numberSender, int numberReceiver){
        Encoding encoding = new Encoding();
        String numberOfSender = String.valueOf(numberSender);
        String numberOfReceiver = String.valueOf(numberReceiver);
        String codedMessage =  encoding.encoded(message, numberOfSender, numberOfReceiver);
        return codedMessage;
    }
}
