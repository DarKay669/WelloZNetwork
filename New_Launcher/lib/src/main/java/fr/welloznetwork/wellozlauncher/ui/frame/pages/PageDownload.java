package fr.welloznetwork.wellozlauncher.ui.frame.pages;

import java.awt.Desktop;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.FlowUpdater.FlowUpdaterBuilder;
import fr.flowarg.flowupdater.download.DownloadList.DownloadInfo;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.download.json.Mod;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder.ForgeVersionType;
import fr.flowarg.flowupdater.versions.VanillaVersion.VanillaVersionBuilder;
import fr.flowarg.flowupdater.versions.VersionType;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.welloznetwork.wellozlauncher.Constants;
import fr.welloznetwork.wellozlauncher.WelloZLauncher;
import fr.welloznetwork.wellozlauncher.ui.PanelManager;
import fr.welloznetwork.wellozlauncher.ui.frame.Page;
import fr.welloznetwork.wellozlauncher.util.LauncherUtil.Status;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class PageDownload extends Page
{
    private GridPane download = new GridPane(), background = new GridPane();
    private Saver saver = WelloZLauncher.getInstance().getSaver();
    private Button launchButton = new Button(Constants.PLAY), logOutButton = new Button(Constants.LOG_OUT), settingsButton = new Button(Constants.SETTINGS);
    private Label status = new Label(), file = new Label(), copyright = new Label(Constants.LAUNCHER_COPYRIGHT), connectedAs = new Label(Constants.CONNECTED_AS);
    private ProgressBar progressBar = new ProgressBar();
    
    public void init(PanelManager panelManager)
    {
        super.init(panelManager);
        
        this.showDownload();
        this.showBackground();
        this.showCopyright();
        this.showProgress();
        this.showPlayButton();
        this.showConnectedAs();
        this.showLogOutButton();
        this.showSettingsButton();
        
        this.download.getChildren().addAll(this.background, this.launchButton, this.progressBar, this.status, this.file, this.logOutButton, this.settingsButton, this.copyright, this.connectedAs);
    }
    
    private void showDownload()
    {
        this.getLayout().getStyleClass().add(Constants.LAYOUT);
        
        ColumnConstraints columnConstraints = new ColumnConstraints();
        
        columnConstraints.setMinWidth(800.0D);
        columnConstraints.setMaxWidth(0.0D);
        
        this.getLayout().getColumnConstraints().addAll(columnConstraints, new ColumnConstraints());
        this.getLayout().add(this.download, 0, 0);
        
        this.progressBar.setVisible(false);
    }
    
    private void showBackground()
    {
        GridPane background = new GridPane();
        this.setCanTakeAllSize(background);
        this.getLayout().add(background, 1, 0);
        this.getLayout().getStyleClass().add(Constants.LOGIN);
        
        this.background.setMaxWidth(400.0D);
        this.background.setMaxHeight(300.0D);
        this.background.setTranslateX(200.0D);
        this.background.setTranslateY(-20.0D);
        this.background.getStyleClass().add(Constants.BACKGROUND);
    }
    
    private void showCopyright()
    {
        this.copyright.setFont(Font.font(Constants.FONT_MONTSERRAT_BOLD, 10.0D));
        this.copyright.setTranslateX(-18.0D);
        this.copyright.setTranslateY(-2.0D);
        this.setCenterH(this.copyright);
        this.setCanTakeAllSize(this.copyright);
        this.setBottom(this.copyright);
        this.setRight(this.copyright);
        this.copyright.getStyleClass().add(Constants.COPYRIGHT); 
        
        this.copyright.setOnMouseClicked(e -> 
        {            
            try
            {
                Desktop.getDesktop().browse(new URI(Constants.WEB_SITE));
            }
            
            catch (Exception ex)
            {
                WelloZLauncher.getInstance().getLogger().printStackTrace(ex);
            }
        });
    }
    
    private void showConnectedAs()
    {
        this.connectedAs.setFont(Font.font(Constants.FONT_MONTSERRAT_BOLD, 18.0D));
        this.connectedAs.setTranslateX(2.0D);
        this.connectedAs.setTranslateY(90.0D);
        this.setCenterV(this.connectedAs);
        this.setCenterH(this.connectedAs);
        this.setCanTakeAllSize(this.connectedAs);
        this.setTop(this.connectedAs);
        
        if (WelloZLauncher.getInstance().getAuthInfos() != null)
        {
            this.connectedAs.setText(Constants.CONNECTED_AS + WelloZLauncher.getInstance().getAuthInfos().getUsername());
        }
    }
    
    private void showLogOutButton()
    {
        this.setCanTakeAllSize(this.logOutButton);
        this.setCenterV(this.logOutButton);
        this.setCenterH(this.logOutButton);
        this.logOutButton.setMaxWidth(120.0D);
        this.logOutButton.setMaxHeight(10.0D);
        this.logOutButton.setTranslateY(30.0D);
        this.logOutButton.getStyleClass().add(Constants.SIGN_UP);
        
        this.logOutButton.setOnMouseClicked(e ->
        {
            WelloZLauncher.getInstance().getSaver().remove(Constants.ACCESS_TOKEN);
            WelloZLauncher.getInstance().getSaver().remove(Constants.REFRESH_TOKEN);
            
            this.panelManager.showPanel(new PageHome());
        });
    }
    
    private void showPlayButton()
    {
        this.setCanTakeAllSize(this.launchButton);
        this.setCenterH(this.launchButton);
        this.setCenterV(this.launchButton);
        this.launchButton.setMaxWidth(370.0D);
        this.launchButton.setMaxHeight(70.0D);
        this.launchButton.setTranslateY(-40.0D);
        this.launchButton.getStyleClass().add(Constants.LOGIN_MICROSOFT);
        
        this.launchButton.setOnMouseClicked(e ->
        {
            this.logOutButton.setDisable(true);
            this.settingsButton.setDisable(true);
            this.launchButton.setDisable(true);
            this.progressBar.setVisible(true);
            
            this.startDownload();
        });
    }
    
    private void showSettingsButton()
    {
        this.settingsButton.setFont(Font.font(Constants.FONT_MONTSERRAT_REGULAR, 15.0D));
        this.setCenterH(this.settingsButton);
        this.setCanTakeAllSize(this.settingsButton);
        this.setTop(this.settingsButton);
        this.setRight(this.settingsButton);
        this.settingsButton.setTranslateX(-20.0D);
        this.settingsButton.setTranslateY(6.0D);
        this.settingsButton.getStyleClass().add(Constants.SETTINGS_BUTTON);
        
        this.settingsButton.setOnMouseClicked(e ->
        {
            this.panelManager.showPanel(new PageSettings());
        });
    }
    
    private void showProgress()
    {
        this.status.setFont(Font.font(Constants.FONT_MONTSERRAT_REGULAR, 15.0D));
        this.file.setFont(Font.font(Constants.FONT_MONTSERRAT_REGULAR, 15.0D));
        
        this.progressBar.getStyleClass().add(Constants.PROGRESS_BAR);
        this.status.getStyleClass().add(Constants.PROGRESS_STATUS);
        this.file.getStyleClass().add(Constants.PROGRESS_STATUS);
        
        this.progressBar.setTranslateY(90.0D);
        this.setCenterH(this.progressBar);
        this.setCanTakeAllSize(this.progressBar);
        
        this.status.setTranslateY(40.0D);
        this.setCenterH(this.status);
        this.setCanTakeAllSize(this.status);
        
        this.file.setTranslateY(65.0D);
        this.setCenterH(this.file);
        this.setCanTakeAllSize(this.file);
    }
    
    private void startDownload()
    {
        this.download.getChildren().clear();
        this.setProgress(0.0D, 0.0D);
        this.download.getChildren().addAll(this.progressBar, this.status, this.file);
        
        new Thread(this::update).start();
    }
    
    private void launchGame(String mcVersion)
    {        
        try
        {
            ExternalLaunchProfile externalLaunchProfile = MinecraftLauncher.createExternalProfile(new GameInfos(Constants.GAME_DIRECTORY, true, new GameVersion(mcVersion, GameType.V1_7_10), new GameTweak[] {GameTweak.FORGE}), GameFolder.FLOW_UPDATER, WelloZLauncher.getInstance().getAuthInfos());
            externalLaunchProfile.getVmArgs().add(this.getMemoryArguments());
                
            ExternalLauncher externalLauncher = new ExternalLauncher(externalLaunchProfile);
            
            Platform.runLater(() -> this.panelManager.getPrimaryStage().hide());
            
            Process process = externalLauncher.launch();
            
            Platform.runLater(() ->
            {
                try
                {
                    process.waitFor();
                    Platform.exit();
                }
                    
                catch (InterruptedException e)
                {
                    WelloZLauncher.getInstance().getLogger().printStackTrace(e);
                }
            });
        }
            
        catch (Exception e)
        {
            WelloZLauncher.getInstance().getLogger().printStackTrace(e);
        }
    }
    
    private void update()
    {
        IProgressCallback iProgressCallback = new IProgressCallback()
        {
            private final DecimalFormat decimalFormat = new DecimalFormat("#.#");
            private String status = "", percent = "0.0%";
            private long lastUpdate = 0;
            
            public void step(Step step)
            {
                Platform.runLater(() -> 
                {
                    this.status = Status.valueOf(step.name()).getStatus();
                    PageDownload.this.setStatus(String.format("%s, (%s)", this.status, this.percent));
                });
            }
            
            public void update(DownloadInfo downloadInfo)
            {
                long now = System.currentTimeMillis();
                
                if (now - this.lastUpdate > 100)
                {
                    this.lastUpdate = now;
                    
                    Platform.runLater(() ->
                    {
                        this.percent = this.decimalFormat.format(downloadInfo.getDownloadedBytes() * 100.0D / downloadInfo.getTotalToDownloadBytes()) + "%";
                        PageDownload.this.setStatus(String.format("%s, (%s)", this.status, this.percent));
                        PageDownload.this.setProgress(downloadInfo.getDownloadedBytes(), downloadInfo.getTotalToDownloadBytes());
                    });
                }
            }
            
            public void onFileDownloaded(Path path)
            {
                Platform.runLater(() -> 
                {
                    PageDownload.this.file.setText("..." + path.toString().replace(WelloZLauncher.getInstance().getLauncherDirectory().toFile().getAbsolutePath(), ""));
                });
            }
        };
        
        try
        {
            try
            {
                List<String> modsList = Mod.getModsFromJson(Constants.MODS_URL).stream().map(mod -> mod.getDownloadURL().substring(mod.getDownloadURL().lastIndexOf('/') + 1)).collect(Collectors.toList());
                
                Path modsDirectory = WelloZLauncher.getInstance().getLauncherDirectory().resolve("mods");
                
                if (Files.exists(modsDirectory))
                {
                    Files.walk(modsDirectory).filter(path -> !path.toString().endsWith(".jar") || !modsList.contains(path.getFileName().toString())).forEach(path -> 
                    {
                        try
                        {
                            WelloZLauncher.getInstance().getLogger().info("Removed file: " + path);
                            Files.delete(path);
                        }
                        
                        catch (Exception e)
                        {
                            WelloZLauncher.getInstance().getLogger().printStackTrace(e);
                        }
                    });
                }
            }
            
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            WelloZLauncher.getInstance().getLogger().info("FlowUpdater initialization");
            final FlowUpdater flowUpdater = new FlowUpdaterBuilder().withVanillaVersion(new VanillaVersionBuilder().withName(Constants.MC_VERSION).withVersionType(VersionType.FORGE).build()).withForgeVersion(new ForgeVersionBuilder(ForgeVersionType.OLD).withForgeVersion(Constants.FORGE_VERSION).withMods(Mod.getModsFromJson(Constants.MODS_URL)).build()).withLogger(WelloZLauncher.getInstance().getLogger()).withProgressCallback(iProgressCallback).build();
            
            WelloZLauncher.getInstance().getLogger().info("Download starting");
            flowUpdater.update(WelloZLauncher.getInstance().getLauncherDirectory());
            
            WelloZLauncher.getInstance().getLogger().info("Download completed");
            
            this.launchGame(flowUpdater.getVanillaVersion().getName());
        }
        
        catch (Exception e)
        {
            WelloZLauncher.getInstance().getLogger().err("Erreur during updating: " + e.getMessage());
            e.printStackTrace();
            Platform.runLater(() -> this.panelManager.getPrimaryStage().show());
        }
    }
    
    private String getMemoryArguments()
    {
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
        
        return "-" + Constants.MEMORY + memoryValue + "M";
    }
    
    private void setStatus(String status)
    {
        this.status.setText(status);
    }
    
    private void setProgress(double current, double max)
    {
        this.progressBar.setProgress(current / max);
    }
}