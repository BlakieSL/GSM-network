import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame  {
    private final LogicToGraphic logicToGraphic;
    public MainFrame(LogicToGraphic logicToGraphic){
        this.logicToGraphic = logicToGraphic;

        LeftPanel leftPanel = new LeftPanel(logicToGraphic);
        this.getContentPane().add(leftPanel, BorderLayout.LINE_START);

        IntermediatePanel intermidiatePanel = new IntermediatePanel(logicToGraphic);
        this.getContentPane().add(intermidiatePanel, BorderLayout.CENTER);

        RightPanel rightPanel = new RightPanel(logicToGraphic);
        this.getContentPane().add(rightPanel, BorderLayout.LINE_END);

        this.setSize(1200,900);
        this.setVisible(true);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveToBinaryFile();
                System.exit(1);
            }
        });
    }
    public void saveToBinaryFile(){
        BinarySaving binarySaving = new BinarySaving();
        VBDLayout vbdLayout = (VBDLayout) logicToGraphic.getLayoutInter().get(0);
        for(VBD vbd : vbdLayout.getVbdList()){
            binarySaving.write(vbd.getSentMessages(),vbd);
        }
    }
}
