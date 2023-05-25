import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IntermediatePanel extends JPanel {
    private final JPanel mainPanel;
    private LogicToGraphic logicToGraphic;
    private ArrayList<JPanel> listPanels = new ArrayList<>();
    public IntermediatePanel(LogicToGraphic logicToGraphic) {
        this.logicToGraphic = logicToGraphic;
        ArrayList<Layout> layouts = logicToGraphic.getLayoutInter();
        setBackground(Color.white);
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, layouts.size()-2));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(mainPanel);

        for(Layout layout : layouts){
            if(!(layout instanceof VRDLayout || layout instanceof VBDLayout)){
                if(layout instanceof BSCLayout){
                    JPanel bscPanel = createDefaultBSC_Layout_Panel((BSCLayout) layout);
                    bscPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    mainPanel.add(bscPanel, BorderLayout.CENTER);
                    listPanels.add(0,bscPanel);
                }
                else if(layout instanceof BTSLayout){
                    JPanel btsPanel = createDefaultBTS_Layout_Panel((BTSLayout) layout);
                    btsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    mainPanel.add(btsPanel, BorderLayout.CENTER);
                }
            }
        }
        JButton addBSCLayoutButton = new JButton("Add BSC Layout");
        addBSCLayoutButton.addActionListener(e -> {
            BSCLayout newBSCLayout = new BSCLayout();
            logicToGraphic.addBSCLayoutInter(newBSCLayout);

            JPanel bscPanel = createDefaultBSC_Layout_Panel(newBSCLayout);
            bscPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            mainPanel.add(bscPanel,2);
            listPanels.add(0,bscPanel);
            revalidate();
            repaint();
        });
        JButton terminateBSCLayoutButton = new JButton("Terminate BSC Layout");
        terminateBSCLayoutButton.addActionListener(e->{
            if(listPanels.size() > 1) {
                JPanel bscPanel = listPanels.get(0);
                logicToGraphic.removeBSCLayoutInter();
                listPanels.remove(bscPanel);
                mainPanel.remove(bscPanel);
                revalidate();
                repaint();
            }
        });
        add(scrollPane, BorderLayout.CENTER);
        add(addBSCLayoutButton, BorderLayout.SOUTH);
        add(terminateBSCLayoutButton, BorderLayout.NORTH);
    }
    public JPanel createDefaultBSC_Layout_Panel(BSCLayout bscLayout)    {
        JPanel mainForThis = new JPanel(new BorderLayout());
        JPanel stationPanel = new JPanel();
        stationPanel.setLayout(new GridBagLayout());

        BSCPanel bscPanelDefaulte = new BSCPanel(bscLayout, logicToGraphic);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        stationPanel.add(bscPanelDefaulte, constraints);

        JButton addBSC = new JButton("Add BSC");
        addBSC.addActionListener(e -> {
            BSCPanel bscPanel = new BSCPanel(bscLayout, logicToGraphic);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 0.5;

            stationPanel.add(bscPanel, gbc);
            revalidate();
            repaint();
        });

        mainForThis.add(addBSC, BorderLayout.NORTH);
        mainForThis.add(stationPanel, BorderLayout.CENTER );
        return mainForThis;
    }
    public JPanel createDefaultBTS_Layout_Panel(BTSLayout btsLayout){
        JPanel mainForThis = new JPanel(new BorderLayout());
        JPanel stationPanel = new JPanel();
        stationPanel.setLayout(new GridBagLayout());

        //visualization of BTS that always exists on panel;
        BTSPanel btsPanelDefaulte = new BTSPanel(btsLayout, logicToGraphic);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        stationPanel.add(btsPanelDefaulte, constraints);

        JButton addBTS = new JButton("Add BTS");
        addBTS.addActionListener(e -> {
            BTSPanel btsPanel = new BTSPanel(btsLayout, logicToGraphic);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 0.5;

            stationPanel.add(btsPanel, gbc);
            revalidate();
            repaint();
        });
        mainForThis.add(addBTS, BorderLayout.NORTH);
        mainForThis.add(stationPanel, BorderLayout.CENTER );
        return mainForThis;
    }
}
