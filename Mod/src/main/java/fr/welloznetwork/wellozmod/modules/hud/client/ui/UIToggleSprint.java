package fr.welloznetwork.wellozmod.modules.hud.client.ui;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.render.Colors;
import fr.welloznetwork.wellozmod.modules.hud.MHUD;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class UIToggleSprint extends GuiScreen
{
    private boolean isDragging;
    public static int color = 15;
    private int lastMouseX, lastMouseY, lastAddX, lastAddY;
    private GuiButton buttonReset, buttonToggle, buttonColor;
    private GuiScreen parentScreen;
    
    public UIToggleSprint(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
    }
    
    private static void setAddX(int addX)
    {
        RegistrySettings.toggleSprintX = addX;
    }
    
    private static void setAddY(int addY)
    {
        RegistrySettings.toggleSprintY = addY;
    }
    
    public void initGui()
    {
        this.buttonList.add(this.buttonReset = new GuiButton(0, this.width / 2 - 85, this.height / 4 + 24 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod")));
        this.buttonList.add(this.buttonToggle = new GuiButton(1, this.width / 2 - 85, this.height / 4 + 46 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod4") + (RegistrySettings.toggleSprintEnabled)));
        this.buttonList.add(this.buttonColor = new GuiButton(2, this.width / 2 - 85, this.height / 4 + 68 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod5") + Colors.values()[color]));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 85, this.height / 4 + 112 + (byte) - 16, 170, 20, I18n.format("gui.done")));
        
        super.initGui();
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        this.drawCenteredString(this.fontRendererObj, "Toggle Sprint", this.width / 2, 40, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void mouseMovedOrUp(int mouseX, int mouseY, int state)
    {
        if (state == 0)
        {
            this.isDragging = false;
        }
        
        super.mouseMovedOrUp(mouseX, mouseY, state);
    }
    
    protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceClick)
    {
        if (!this.isDragging && RegistrySettings.toggleSprintEnabled && mouseX > MHUD.CLIENT_LISTENER.minXToggleSprint && mouseX < MHUD.CLIENT_LISTENER.maxXToggleSprint && mouseY > MHUD.CLIENT_LISTENER.minYToggleSprint && mouseY < MHUD.CLIENT_LISTENER.maxYToggleSprint)
        {
            this.isDragging = true;
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.lastAddX = RegistrySettings.toggleSprintX;
            this.lastAddY = RegistrySettings.toggleSprintY;
        }
        
        else if (this.isDragging)
        {
            setAddX(this.lastAddX + mouseX - this.lastMouseX);
            setAddY(this.lastAddY + mouseY - this.lastMouseY);
        }
        
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceClick);
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton == this.buttonReset)
        {
            RegistrySettings.toggleSprintX = 2;
            RegistrySettings.toggleSprintY = 2;
            
            RegistrySettings.updateConfiguration("modules", "toggleSprintPosX", RegistrySettings.toggleSprintX);
            RegistrySettings.updateConfiguration("modules", "toggleSprintPosY", RegistrySettings.toggleSprintY);
        }
        
        else if (guiButton == this.buttonToggle)
        {
            RegistrySettings.toggleSprintEnabled = !RegistrySettings.toggleSprintEnabled;
            this.buttonToggle.displayString = I18n.format("gui.hud.cpsMod4") + RegistrySettings.toggleSprintEnabled;
            
            RegistrySettings.updateConfiguration("modules", "toggleSprint", RegistrySettings.toggleSprintEnabled);
        }
        
        else if (guiButton == this.buttonColor)
        {
            color++;
            
            if (color == (Colors.values()).length)
            {
                color = 0;
            }
            
            this.buttonColor.displayString = I18n.format("gui.hud.cpsMod5") + Colors.values()[color];
            
            RegistrySettings.updateConfiguration("modules", "toggleSprintColor", RegistrySettings.toggleSprintColor);
        }
        
        else if (guiButton.id == 3)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        super.actionPerformed(guiButton);
    }
}