import javax.swing.*;
import java.awt.*;

public class VRDPanel extends JPanel {
    public VRDPanel(LogicToGraphic logicToGraphic) {
        setLayout(new GridLayout(3, 2));
        setPreferredSize(new Dimension(197,70));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));


        VRD vrd = new VRD();
        int ID = 1000000000 + (int) (Math.random() * 899999999);
        vrd.setID(ID);
        VRDLayout vrdLayout = (VRDLayout) logicToGraphic.getLayoutInter().get(logicToGraphic.getLayoutInter().size()-1);
        vrdLayout.addVrd(vrd);
        logicToGraphic.setLayoutInter(logicToGraphic.getLayoutInter().size()-1, vrdLayout);

        JTextField textField = new JTextField(String.valueOf(ID));
        textField.setEditable(false);
        JLabel deviceIDLabel  = new JLabel("Device number: ");
        JLabel messageLabel = new JLabel();
        JButton terminateButton = new JButton("Terminate");
        terminateButton.addActionListener(e -> {
            vrdLayout.getVrdList().remove(vrd);
            logicToGraphic.setLayoutInter(logicToGraphic.getLayoutInter().size()-1, vrdLayout);

            Container parent = getParent();
            parent.remove(VRDPanel.this);
            parent.revalidate();
            parent.repaint();
        });
        JCheckBox checkBox = new JCheckBox("Clear count every 10 seconds");
        checkBox.addActionListener(e -> {
            boolean clearCount = checkBox.isSelected();
            vrd.setClearMessages(clearCount);
        });

        add(deviceIDLabel);
        add(textField);
        add(terminateButton);
        add(checkBox);
        add(messageLabel);

        new Thread(() -> {
            while(true){
                try{
                    Thread.sleep(100);
                    SwingUtilities.invokeLater(() -> {
                        messageLabel.setText("Received: " + vrd.getMessages().size());
                    });
                }catch (InterruptedException ignored){}
            }
        }).start();
    }
}
