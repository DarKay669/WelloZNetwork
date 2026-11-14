package fr.welloznetwork.wellozlauncher.ui;

import fr.welloznetwork.wellozlauncher.Constants;
import fr.welloznetwork.wellozlauncher.WelloZLauncher;
import fr.welloznetwork.wellozlauncher.ui.frame.IPage;
import fr.welloznetwork.wellozlauncher.util.LauncherUtil;
import fr.welloznetwork.wellozlauncher.util.LauncherUtil.DistributionOS;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PanelManager
{
    private final WelloZLauncher launcher;
    private final Stage primaryStage;
    private GridPane gridPane;
    private final GridPane contentPane = new GridPane();
    
    public PanelManager(WelloZLauncher launcher, Stage primaryStage)
    {
        this.launcher = launcher;
        this.primaryStage = primaryStage;
    }
    
    public void init()
    {
        this.primaryStage.setTitle(Constants.LAUNCHER_NAME);
        this.primaryStage.setWidth(800);
        this.primaryStage.setHeight(500);
        this.primaryStage.initStyle(StageStyle.DECORATED);
        this.primaryStage.setResizable(false);
        this.primaryStage.centerOnScreen();
        
        if (LauncherUtil.getOperatingSystemType() != DistributionOS.MACOS)
        {
            this.primaryStage.getIcons().add(new Image(Constants.FAVICON_PATH));
        }
        
        this.gridPane = new GridPane();
        
        this.primaryStage.setScene(new Scene(this.gridPane));
        
        this.gridPane.add(this.contentPane, 0, 1);
        
        GridPane.setVgrow(this.contentPane, Priority.ALWAYS);
        GridPane.setHgrow(this.contentPane, Priority.ALWAYS);
        
        this.primaryStage.show();
    }
    
    public void showPanel(IPage panel)
    {
        this.contentPane.getChildren().clear();
        this.contentPane.getChildren().add(panel.getLayout());
        
        if (panel.getStylesheetPath() != null)
        {
            this.primaryStage.getScene().getStylesheets().clear();
            this.primaryStage.getScene().getStylesheets().add(panel.getStylesheetPath());
        }
        
        panel.init(this);
        panel.onShow();
    }
    
    public Stage getPrimaryStage()
    {
        return this.primaryStage;
    }
    
    public WelloZLauncher getLauncher()
    {
        return this.launcher;
    }
    
    private class Delta
    {
        double x, y;
    }
}