package fr.welloznetwork.wellozlauncher.ui.frame.pages;

import java.awt.Desktop;
import java.net.URI;
import java.util.UUID;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.welloznetwork.wellozlauncher.Constants;
import fr.welloznetwork.wellozlauncher.WelloZLauncher;
import fr.welloznetwork.wellozlauncher.ui.PanelManager;
import fr.welloznetwork.wellozlauncher.ui.frame.Page;
import fr.welloznetwork.wellozlauncher.util.LauncherUtil;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class PageHome extends Page
{
    private GridPane home = new GridPane(), background = new GridPane();
    private Saver saver = WelloZLauncher.getInstance().getSaver();
    private Button loginButton = new Button(Constants.LOGIN_BUTTON), signUpButton = new Button(Constants.SIGN_UP_BUTTON), noMinecraftAccount = new Button(Constants.NO_ACCOUNT);
    private Label copyright = new Label(Constants.LAUNCHER_COPYRIGHT);
    
    public void init(PanelManager panelManager)
    {
        super.init(panelManager);
        
        this.showLogin();
        this.showBackground();
        this.showCopyright();
        this.showLoginButton();
        this.showSignUpButton();
        this.showNoMinecraftAccountButton();
        
        this.home.getChildren().addAll(this.background, this.loginButton, this.signUpButton, this.copyright, this.noMinecraftAccount);
    }
    
    private void showLogin()
    {
        this.getLayout().getStyleClass().add(Constants.LAYOUT);
        
        ColumnConstraints columnConstraints = new ColumnConstraints();
        
        columnConstraints.setMinWidth(800.0D);
        columnConstraints.setMaxWidth(0.0D);
        
        this.getLayout().getColumnConstraints().addAll(columnConstraints, new ColumnConstraints());
        this.getLayout().add(this.home, 0, 0);
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
    
    private void showLoginButton()
    {
        this.setCanTakeAllSize(this.loginButton);
        this.setCenterV(this.loginButton);
        this.setCenterH(this.loginButton);
        this.loginButton.setMaxWidth(370.0D);
        this.loginButton.setMaxHeight(70.0D);
        this.loginButton.setTranslateY(-40.0D);
        this.loginButton.getStyleClass().add(Constants.LOGIN_MICROSOFT);
        
        this.loginButton.setOnMouseClicked(e ->
        {
            this.authenticate();
        });
    }
    
    private void showSignUpButton()
    {
        this.setCanTakeAllSize(this.signUpButton);
        this.setCenterV(this.signUpButton);
        this.setCenterH(this.signUpButton);
        this.signUpButton.setMaxWidth(130.0D);
        this.signUpButton.setMaxHeight(10.0D);
        this.signUpButton.setTranslateY(30.0D);
        this.signUpButton.getStyleClass().add(Constants.SIGN_UP);
        
        this.signUpButton.setOnMouseClicked(e -> 
        {
            try
            {
                Desktop.getDesktop().browse(new URI(Constants.MICROSOFT_WEB_SITE));
            }
            
            catch (Exception ex)
            {
                WelloZLauncher.getInstance().getLogger().printStackTrace(ex);
            }
        });
    }
    
    private void showNoMinecraftAccountButton()
    {
        this.setCanTakeAllSize(this.noMinecraftAccount);
        this.setCenterV(this.noMinecraftAccount);
        this.setCenterH(this.noMinecraftAccount);
        this.noMinecraftAccount.setMaxWidth(232.0D);
        this.noMinecraftAccount.setMaxHeight(10.0D);
        this.noMinecraftAccount.setTranslateY(110.0D);
        this.noMinecraftAccount.getStyleClass().add(Constants.SIGN_UP);
        
        this.noMinecraftAccount.setOnMouseClicked(e ->
        {
            TextInputDialog textInputDialog = new TextInputDialog("");
            
            textInputDialog.setTitle("Entrez votre nom d'utilisateur");
            textInputDialog.setHeaderText("");
            textInputDialog.showAndWait().ifPresent(username -> 
            {
                if (!this.isValidUsername(username))
                {
                    Alert alert = new Alert(AlertType.ERROR);
                    
                    alert.setTitle("Erreur"); //Error
                    alert.setHeaderText("");
                    alert.setContentText("Le nom d'utilisateur ne peut pas être vide, contenir des espaces ou des caractères spéciaux."); //The username can't be empty, contains space or special characters.
                    alert.showAndWait();
                    
                    return;
                }
                
                new Thread(() -> 
                {
                    boolean isRegisteredAccount = LauncherUtil.isRegisteredAccount(username);
                    
                    Platform.runLater(() -> 
                    {
                        if (isRegisteredAccount)
                        {
                            Alert alert = new Alert(AlertType.ERROR);
                            
                            alert.setTitle("Erreur");
                            alert.setHeaderText("");
                            alert.setContentText("Ce nom d'utilisateur appartient déjà à un compte Minecraft. Veuillez vous connectez avec votre compte Microsoft."); //This username already belongs to a Minecraft account. Please use the Microsoft login.
                            alert.showAndWait();
                        }
                        
                        else
                        {
                            this.authenticateWithoutMinecraftAccount(username);
                        }
                    });
                    
                }).start();
            });
        });
    }
    
    private void authenticate()
    {
        MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
        
        microsoftAuthenticator.loginWithAsyncWebview().whenComplete((response, error) -> 
        {
            if (error != null)
            {
                WelloZLauncher.getInstance().getLogger().err(error.toString());
                Platform.runLater(() -> this.setAlert(AlertType.ERROR, Constants.ERROR, error.getMessage(), null));
                
                return;
            }
            
            this.saver.set(Constants.ACCESS_TOKEN, response.getAccessToken());
            this.saver.set(Constants.REFRESH_TOKEN, response.getRefreshToken());
            this.saver.save();
            
            WelloZLauncher.getInstance().setAuthInfos(new AuthInfos(response.getProfile().getName(), response.getAccessToken(), response.getProfile().getId(), response.getXuid(), response.getClientId()));
            Platform.runLater(() -> this.panelManager.showPanel(new PageDownload()));
        });
    }
    
    private void authenticateWithoutMinecraftAccount(String username)
    {        
        this.saver.set(Constants.USERNAME, username);
        this.saver.set(Constants.ACCESS_TOKEN, "token-" + username);
        this.saver.save();
        
        WelloZLauncher.getInstance().setAuthInfos(new AuthInfos(username, "token-" + username, UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes()).toString()));
        
        Platform.runLater(() -> this.panelManager.showPanel(new PageDownload()));
    }
    
    private boolean isValidUsername(String username)
    {
        if (username == null || username.trim().isEmpty())
        {
            return false;
        }
        
        return username.matches("[A-Za-z0-9_-]+");
    }
    
    private void setAlert(AlertType alertType, String title, String headerText, String contentText)
    {
        Alert alert = new Alert(alertType);
        
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }
}