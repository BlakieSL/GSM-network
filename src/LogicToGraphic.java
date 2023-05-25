import java.util.ArrayList;

public interface LogicToGraphic {
    ArrayList<Layout> getLayoutInter();
    void addLayoutInter(Layout layout);
    void addBSCLayoutInter(BSCLayout layout);
    void removeBSCLayoutInter();
    void setLayoutInter(int index,Layout layout);
    void removeLayoutInter(BSCLayout layout);
}
