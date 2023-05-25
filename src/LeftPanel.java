import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {
    JPanel additionalPanel;
    LogicToGraphic logicToGraphic;
    public LeftPanel(LogicToGraphic logicToGraphic){
        this.logicToGraphic = logicToGraphic;
        setBackground(Color.white);
        setPreferredSize(new Dimension(200,300));
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        additionalPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener((e)->{
            String message = JOptionPane.
                    showInputDialog(this, "Enter Message");
            if(message != null){
                addVBDStation(message);
            }
        });
        additionalPanel.setLayout(new GridBagLayout());
        scrollPane.setViewportView(additionalPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(addButton, BorderLayout.NORTH);
    }
    public void addVBDStation(String message){
        VBDPanel vbdPanel = new VBDPanel(message,logicToGraphic);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        additionalPanel.add(vbdPanel, constraints);
        additionalPanel.revalidate();
        additionalPanel.repaint();
    }
}
