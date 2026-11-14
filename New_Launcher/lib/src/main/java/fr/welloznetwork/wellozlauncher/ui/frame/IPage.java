package fr.welloznetwork.wellozlauncher.ui.frame;

import fr.welloznetwork.wellozlauncher.ui.PanelManager;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public interface IPage
{
    void init(PanelManager panelManager);
    
    GridPane getLayout();
    
    void onShow();
    
    String getStylesheetPath();
    
    void setLeft(Node node);
    
    void setRight(Node node);
    
    void setTop(Node node);
    
    void setBottom(Node node);
    
    void setBaseLine(Node node);
    
    void setCenterH(Node node);
    
    void setCenterV(Node node);
    
    default void setCanTakeAllSize(Node node)
    {
        GridPane.setHgrow(node, Priority.ALWAYS);
        GridPane.setVgrow(node, Priority.ALWAYS);
    }
    
    default void setCanTakeAllWidth(Node... nodes)
    {
        for (Node node : nodes)
        {
            GridPane.setHgrow(node, Priority.ALWAYS);
        }
    }
}