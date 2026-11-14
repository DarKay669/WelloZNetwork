package fr.welloznetwork.wellozlauncher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.welloznetwork.wellozlauncher.client.discord.IDiscordRPC;
import fr.welloznetwork.wellozlauncher.client.discord.RPC;
import fr.welloznetwork.wellozlauncher.ui.PanelManager;
import fr.welloznetwork.wellozlauncher.ui.frame.pages.PageDownload;
import fr.welloznetwork.wellozlauncher.ui.frame.pages.PageHome;
import fr.welloznetwork.wellozlauncher.util.LauncherUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WelloZLauncher extends Application
{
    private static WelloZLauncher instance;
    private PanelManager panelManager;
    private ILogger logger;
    private Path launcherDirectory = GameDirGenerator.createGameDir(Constants.GAME_DIRECTORY, true);
    private Saver saver;
    private AuthInfos authInfos = null;
    
    public WelloZLauncher()
    {
        instance = this;
        this.logger = new Logger("[" + Constants.LAUNCHER_NAME + "]", this.launcherDirectory.resolve(Constants.LAUNCHER_LOG));
        
        if (Files.notExists(this.launcherDirectory))
        {
            try
            {
                Files.createDirectory(this.launcherDirectory);
            }
            
            catch (IOException e)
            {
                this.logger.err(Constants.ERROR_GAME_DIRECTORY);
                this.logger.printStackTrace(e);
            }
        }
        
        this.saver = new Saver(this.launcherDirectory.resolve(Constants.LAUNCHER_PROPERTIES));
        this.saver.load();
    }
    
    public void start(Stage primaryStage)
    {        
        this.loadFonts();
        
        this.panelManager = new PanelManager(this, primaryStage);
        this.panelManager.init();
        
        if (this.isLoggedIn())
        {
            this.panelManager.showPanel(new PageDownload());
        }
        
        else
        {
            this.panelManager.showPanel(new PageHome());
        }
        
        RPC.init();
    }
    
    public boolean isLoggedIn()
    {
        if (this.saver.get(Constants.ACCESS_TOKEN) != null && this.saver.get(Constants.REFRESH_TOKEN) != null)
        {
            try
            {
                MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
                MicrosoftAuthResult microsoftAuthResult = microsoftAuthenticator.loginWithRefreshToken(this.saver.get(Constants.REFRESH_TOKEN));
                
                this.saver.set(Constants.ACCESS_TOKEN, microsoftAuthResult.getAccessToken());
                this.saver.set(Constants.REFRESH_TOKEN, microsoftAuthResult.getRefreshToken());
                this.saver.save();
                
                this.setAuthInfos(new AuthInfos(microsoftAuthResult.getProfile().getName(), microsoftAuthResult.getAccessToken(), microsoftAuthResult.getProfile().getId(), microsoftAuthResult.getXuid(), microsoftAuthResult.getClientId()));
                
                return true;
            }
            
            catch (MicrosoftAuthenticationException e)
            {
                this.saver.remove(Constants.ACCESS_TOKEN);
                this.saver.remove(Constants.REFRESH_TOKEN);
                this.saver.save();
                
                WelloZLauncher.getInstance().getLogger().printStackTrace(e);
            }
        }
        
        if (this.saver.get(Constants.ACCESS_TOKEN) != null && this.saver.get(Constants.USERNAME) != null)
        {
            if (LauncherUtil.isRegisteredAccount(this.saver.get(Constants.USERNAME)))
            {
                this.saver.remove(Constants.ACCESS_TOKEN);
                this.saver.remove(Constants.USERNAME);
                this.saver.save();
                
                return false;
            }
            
            this.setAuthInfos(new AuthInfos(this.saver.get(Constants.USERNAME), this.saver.get(Constants.ACCESS_TOKEN), UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.saver.get(Constants.USERNAME)).getBytes()).toString()));
            
            return true;
        }
        
        return false;
    }
    
    private void loadFonts()
    {
        Font montserratRegular = Font.loadFont(getClass().getResourceAsStream("/assets/Montserrat-Regular.ttf"), 0.0D);
        Font montserratBold = Font.loadFont(getClass().getResourceAsStream("/assets/Montserrat-Bold.ttf"), 0.0D);
    }
    
    public void stop()
    {
        IDiscordRPC.INSTANCE.Discord_Shutdown();
        Platform.exit();
        System.exit(0);
    }
    
    public ILogger getLogger()
    {
        return logger;
    }
    
    public static WelloZLauncher getInstance()
    {
        return instance;
    }
    
    public Saver getSaver()
    {
        return this.saver;
    }
    
    public void setAuthInfos(AuthInfos authInfos)
    {
        this.authInfos = authInfos;
    }
    
    public AuthInfos getAuthInfos()
    {
        return this.authInfos;
    }
    
    public Path getLauncherDirectory()
    {
        return this.launcherDirectory;
    }
}