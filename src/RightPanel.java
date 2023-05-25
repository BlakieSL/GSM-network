import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    JPanel additionalPanel;
    LogicToGraphic logicToGraphic;
    public RightPanel(LogicToGraphic logicToGraphic){
        this.logicToGraphic = logicToGraphic;
        setBackground(Color.white);
        setPreferredSize(new Dimension(200,300));
        setLayout(new BorderLayout());
        additionalPanel = new JPanel();
        additionalPanel.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(additionalPanel);
        JButton button = new JButton("Add");
        button.addActionListener(e -> {
            addVRDStation();
        });
        add(scrollPane, BorderLayout.CENTER);
        add(button, BorderLayout.NORTH);
    }
    public void addVRDStation(){
        VRDPanel vrdPanel = new VRDPanel(logicToGraphic);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        additionalPanel.add(vrdPanel, constraints);
        additionalPanel.revalidate();
        additionalPanel.repaint();
    }
}
