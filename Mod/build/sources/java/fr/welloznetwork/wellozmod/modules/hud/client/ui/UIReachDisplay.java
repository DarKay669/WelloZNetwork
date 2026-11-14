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
public class UIReachDisplay extends GuiScreen
{
    private boolean isDragging;
    public static int color = 15;
    private int lastMouseX, lastMouseY, lastreachDisplayX, lastreachDisplayY;
    private GuiButton buttonReset, buttonToggle, buttonColor;
    private GuiScreen parentScreen;
    
    public UIReachDisplay(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
    }
    
    private static void setreachDisplayX(int reachDisplayX)
    {
        RegistrySettings.reachDisplayX = reachDisplayX;
    }
    
    private static void setreachDisplayY(int reachDisplayY)
    {
        RegistrySettings.reachDisplayY = reachDisplayY;
    }
    
    public void initGui() 
    {
        this.buttonList.add(this.buttonReset = new GuiButton(0, this.width / 2 - 85, this.height / 4 + 24 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod")));
        this.buttonList.add(this.buttonToggle = new GuiButton(1, this.width / 2 - 85, this.height / 4 + 46  + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod4") + (RegistrySettings.reachDisplayEnabled)));
        this.buttonList.add(this.buttonColor = new GuiButton(2, this.width / 2 - 85, this.height / 4 + 68 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod5") + Colors.values()[color]));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 85, this.height / 4 + 112 + (byte) - 16, 170, 20, I18n.format("gui.done")));
        
        super.initGui();
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        this.drawCenteredString(this.fontRendererObj, "Reach Display", this.width / 2, 40, Color.WHITE.getRGB());
        
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
        if (!this.isDragging && RegistrySettings.reachDisplayEnabled && mouseX > MHUD.CLIENT_LISTENER.minXReachDisplay && mouseX < MHUD.CLIENT_LISTENER.maxXReachDisplay && mouseY > MHUD.CLIENT_LISTENER.minYReachDisplay && mouseY < MHUD.CLIENT_LISTENER.maxYReachDisplay)
        {
            this.isDragging = true;
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.lastreachDisplayX = RegistrySettings.reachDisplayX;
            this.lastreachDisplayY = RegistrySettings.reachDisplayY;
        }
        
        else if (this.isDragging)
        {
            setreachDisplayX(this.lastreachDisplayX + mouseX - this.lastMouseX);
            setreachDisplayY(this.lastreachDisplayY + mouseY - this.lastMouseY);
        }
        
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceClick);
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton == this.buttonReset)
        {
            RegistrySettings.reachDisplayX = 2;
            RegistrySettings.reachDisplayY = 16;
            
            RegistrySettings.updateConfiguration("modules", "reachDisplayPosX", RegistrySettings.reachDisplayX);
            RegistrySettings.updateConfiguration("modules", "reachDisplayPosY", RegistrySettings.reachDisplayY);
        }
        
        else if (guiButton == this.buttonToggle)
        {
            RegistrySettings.reachDisplayEnabled = !RegistrySettings.reachDisplayEnabled;
            this.buttonToggle.displayString = I18n.format("gui.hud.cpsMod4") + RegistrySettings.reachDisplayEnabled;
            
            RegistrySettings.updateConfiguration("modules", "reachDisplay", RegistrySettings.reachDisplayEnabled);
        }
        
        else if (guiButton == this.buttonColor)
        {
            color++;
            
            if (color == (Colors.values()).length)
            {
                color = 0;
            }
            
            this.buttonColor.displayString = I18n.format("gui.hud.cpsMod5") + Colors.values()[color];
            
            RegistrySettings.updateConfiguration("modules", "reachDisplayColor", RegistrySettings.reachDisplayColor);
        }
        
        else if (guiButton.id == 3)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        super.actionPerformed(guiButton);
    }
}