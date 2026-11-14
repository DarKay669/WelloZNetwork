package fr.welloznetwork.wellozlauncher.ui.frame;

import fr.flowarg.flowlogger.ILogger;
import fr.welloznetwork.wellozlauncher.Constants;
import fr.welloznetwork.wellozlauncher.WelloZLauncher;
import fr.welloznetwork.wellozlauncher.ui.PanelManager;
import javafx.animation.FadeTransition;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class Page implements IPage
{
    private ILogger logger;
    private GridPane gridPane = new GridPane();
    protected PanelManager panelManager;
    
    public Page()
    {
        this.logger = WelloZLauncher.getInstance().getLogger();
    }
    
    public void init(PanelManager panelManager)
    {
        this.panelManager = panelManager;
        this.setCanTakeAllSize(this.gridPane);
    }
    
    public GridPane getLayout()
    {
        return this.gridPane;
    }
    
    public void onShow()
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500.0D), this.gridPane);
        
        fadeTransition.setFromValue(0.0D);
        fadeTransition.setToValue(1.0D);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }
    
    public String getStylesheetPath()
    {
        return Constants.STYLESHEET_PATH;
    }
    
    public void setLeft(Node node)
    {
        GridPane.setHalignment(node, HPos.LEFT);
    }
    
    public void setRight(Node node)
    {
        GridPane.setHalignment(node, HPos.RIGHT);
    }
    
    public void setTop(Node node)
    {
        GridPane.setValignment(node, VPos.TOP);
    }
    
    public void setBottom(Node node)
    {
        GridPane.setValignment(node, VPos.BOTTOM);
    }
    
    public void setBaseLine(Node node)
    {
        GridPane.setValignment(node, VPos.BASELINE);
    }
    
    public void setCenterH(Node node)
    {
        GridPane.setHalignment(node, HPos.CENTER);
    }
    
    public void setCenterV(Node node)
    {
        GridPane.setValignment(node, VPos.CENTER);
    }
}