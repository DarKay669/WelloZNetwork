package fr.welloznetwork.wellozlauncher.ui.frame.pages;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.welloznetwork.wellozlauncher.Constants;
import fr.welloznetwork.wellozlauncher.WelloZLauncher;
import fr.welloznetwork.wellozlauncher.ui.PanelManager;
import fr.welloznetwork.wellozlauncher.ui.frame.Page;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

public class PageSettings extends Page
{
    private GridPane settings = new GridPane();
    private Saver saver = WelloZLauncher.getInstance().getSaver();
    private Button backButton = new Button(Constants.BACK), saveButton = new Button(Constants.SAVE);
    private Label maxMemory = new Label(Constants.ALLOCATE_MEMORY);
    private ComboBox<String> comboBox = new ComboBox<>();
    
    public void init(PanelManager panelManager)
    {
        super.init(panelManager);
        
        this.showSettings();
        this.showBackground();
        this.showRamSelector();
        this.showSaveButton();
        this.showBackButton();
        
        this.settings.getChildren().addAll(this.backButton, this.saveButton, this.maxMemory, this.comboBox);
    }
    
    private void showSettings()
    {
        this.getLayout().getStyleClass().add(Constants.LAYOUT);
        
        ColumnConstraints columnConstraints = new ColumnConstraints();
        
        columnConstraints.setMinWidth(800.0D);
        columnConstraints.setMaxWidth(0.0D);
        
        this.getLayout().getColumnConstraints().addAll(columnConstraints, new ColumnConstraints());
        this.getLayout().add(this.settings, 0, 0);
    }
    
    private void showBackground()
    {
        GridPane background = new GridPane();
        this.setCanTakeAllSize(background);
        this.getLayout().add(background, 1, 0);
        this.getLayout().getStyleClass().add(Constants.LOGIN);
    }
    
    private void showRamSelector()
    {        
        this.maxMemory.setFont(Font.font(Constants.FONT_MONTSERRAT_BOLD, 18.0D));
        this.setCanTakeAllSize(this.maxMemory);
        this.setCenterV(this.maxMemory);
        this.setCenterH(this.maxMemory);
        this.maxMemory.setTranslateY(-140.0D);
        
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory globalMemory = systemInfo.getHardware().getMemory();
        
        this.comboBox.getStyleClass().add(Constants.SETTINGS_MEMORY);
        
        for (int i = 512; i < Math.ceil(globalMemory.getTotal() / Math.pow(1024, 2)); i += 512)
        {
            this.comboBox.getItems().add(i / 1024.0 + " " + Constants.GO);
        }
        
        int memoryValue = 1024;
        
        try
        {            
            if (this.saver.get(Constants.MEMORY) != null)
            {
                memoryValue = Integer.parseInt(this.saver.get(Constants.MEMORY));
            }
            
            else
            {
                throw new NumberFormatException();
            }
        }
        
        catch (NumberFormatException e)
        {
            this.saver.set(Constants.MEMORY, String.valueOf(memoryValue));
            this.saver.save();
            WelloZLauncher.getInstance().getLogger().printStackTrace(e);
        }
        
        if (this.comboBox.getItems().contains(memoryValue / 1024.0D + " " + Constants.GO))
        {
            this.comboBox.setValue(memoryValue / 1024.0D + " " + Constants.GO);
        }
        
        else
        {
            this.comboBox.setValue("1.0 " + Constants.GO);
        }
        
        this.setCanTakeAllSize(this.comboBox);
        this.setCenterV(this.comboBox);
        this.setCenterH(this.comboBox);
        this.comboBox.setTranslateY(-100.0D);
    }
    
    private void showSaveButton()
    {
        this.saveButton.setFont(Font.font(Constants.FONT_MONTSERRAT_REGULAR, 13.0D));
        this.setCanTakeAllSize(this.saveButton);
        this.setCenterV(this.saveButton);
        this.setCenterH(this.saveButton);
        this.saveButton.setMaxWidth(105.0D);
        this.saveButton.setMaxHeight(10.0D);
        this.saveButton.setTranslateY(30.0D);
        this.saveButton.getStyleClass().add(Constants.SETTINGS_BUTTON);
        
        this.saveButton.setOnMouseClicked(e -> 
        {
            double memoryValue = Double.parseDouble(this.comboBox.getValue().replace(Constants.GO, ""));
            memoryValue *= 1024;
            
            this.saver.set(Constants.MEMORY, String.valueOf((int) memoryValue));
            
            this.panelManager.showPanel(new PageDownload());
        });
    }
    
    private void showBackButton()
    {
        this.backButton.setFont(Font.font(Constants.FONT_MONTSERRAT_REGULAR, 15.0D));
        this.setCenterH(this.backButton);
        this.setCanTakeAllSize(this.backButton);
        this.setTop(this.backButton);
        this.setRight(this.backButton);
        this.backButton.setTranslateX(-20.0D);
        this.backButton.setTranslateY(6.0D);
        this.backButton.getStyleClass().add(Constants.SETTINGS_BUTTON);
                
        this.backButton.setOnMouseClicked(e -> 
        {
            this.panelManager.showPanel(new PageDownload());
        });
    }
}