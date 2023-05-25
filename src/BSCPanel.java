import javax.swing.*;
import java.awt.*;

public class BSCPanel extends JPanel {
    public BSCPanel(BSCLayout bscLayout, LogicToGraphic logicToGraphic) {
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setPreferredSize(new Dimension(70,70));
//
        BSC bsc = new BSC();
        int ID = (int) (Math.random() * 10000000);
        bsc.setID(ID);
        bscLayout.addBsc(bsc);
        logicToGraphic.setLayoutInter(logicToGraphic.getLayoutInter().indexOf(bscLayout),bscLayout);
//
        JButton terminateButton = new JButton("Terminate");
        terminateButton.addActionListener(e ->{
            bscLayout.getBscList().remove(bsc);

            logicToGraphic.setLayoutInter(logicToGraphic.getLayoutInter().indexOf(bscLayout),bscLayout);
            Container parent = getParent();
            parent.remove(BSCPanel.this);
            parent.revalidate();
            parent.repaint();
        });
        add(new JLabel("Station ID: " +ID));
        add(terminateButton);
    }
}
