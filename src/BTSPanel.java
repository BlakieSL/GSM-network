import javax.swing.*;
import java.awt.*;

public class BTSPanel extends JPanel {
    public BTSPanel(BTSLayout btsLayout, LogicToGraphic logicToGraphic) {
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createLineBorder(Color.black));
//
        int ID = (int) (Math.random() * 10000000);
        BTS bts = new BTS();
        bts.setID(ID);
        btsLayout.addBts(bts);
        logicToGraphic.setLayoutInter(logicToGraphic.getLayoutInter().indexOf(btsLayout),btsLayout);
//
        JButton terminateButton = new JButton("Terminate");
        terminateButton.addActionListener(e->{
            btsLayout.getBtsList().remove(bts);
            logicToGraphic.setLayoutInter(logicToGraphic.getLayoutInter().indexOf(btsLayout),btsLayout);
            Container parent = getParent();
            parent.remove(BTSPanel.this);
            parent.revalidate();
            parent.repaint();
        });

        add(new JLabel("Station ID: " +ID));
        add(terminateButton);
    }
}
