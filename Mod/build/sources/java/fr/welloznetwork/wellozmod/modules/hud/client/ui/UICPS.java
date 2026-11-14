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
public class UICPS extends GuiScreen
{
    private boolean isDragging;
    public static int color = 15;
    private int cps, lastMouseX, lastMouseY, lastAddX, lastAddY;
    private GuiButton buttonReset, buttonAllign, buttonAllign2, buttonAllign3, buttonToggle, buttonColor;
    private GuiScreen parentScreen;
    
    public UICPS(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
    }
    
    private static void setAddX(int addX)
    {
        RegistrySettings.cpsX = addX;
    }
    
    private static void setAddY(int addY)
    {
        RegistrySettings.cpsY = addY;
    }
    
    public void initGui()
    {
        this.buttonList.add(this.buttonReset = new GuiButton(0, this.width / 2 - 85, this.height / 4 + 24 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod")));
        this.buttonList.add(this.buttonAllign = new GuiButton(1, this.width / 2 - 85, this.height / 4 + 68 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod1")));
        this.buttonList.add(this.buttonAllign2 = new GuiButton(2, this.width / 2 - 85, this.height / 4 + 90 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod2")));
        this.buttonList.add(this.buttonAllign3 = new GuiButton(3, this.width / 2 - 85, this.height / 4 + 112 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod3")));
        this.buttonList.add(this.buttonToggle = new GuiButton(4, this.width / 2 - 85, this.height / 4 + 46 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod4") + RegistrySettings.cpsEnabled));
        this.buttonList.add(this.buttonColor = new GuiButton(5, this.width / 2 - 85, this.height / 4 + 134 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod5") + Colors.values()[color]));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 85, this.height / 4 + 178 + (byte) - 16, 170, 20, I18n.format("gui.done")));
        
        super.initGui();
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        this.drawCenteredString(this.fontRendererObj, "CPS", this.width / 2, 40, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void updateScreen()
    {        
        super.updateScreen();
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
        if (!this.isDragging && RegistrySettings.cpsEnabled && mouseX > MHUD.CLIENT_LISTENER.minXCPS && mouseX < MHUD.CLIENT_LISTENER.maxXCPS && mouseY > MHUD.CLIENT_LISTENER.minYCPS && mouseY < MHUD.CLIENT_LISTENER.maxYCPS)
        {
            this.isDragging = true;
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.lastAddX = RegistrySettings.cpsX;
            this.lastAddY = RegistrySettings.cpsY;
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
            RegistrySettings.cpsX = 2;
            RegistrySettings.cpsY = Integer.valueOf(80);
            
            RegistrySettings.updateConfiguration("modules", "cpsPosX", RegistrySettings.cpsX);
            RegistrySettings.updateConfiguration("modules", "cpsPosY", RegistrySettings.cpsY);
        }
        
        if (guiButton == this.buttonToggle)
        {
            RegistrySettings.cpsEnabled = !RegistrySettings.cpsEnabled;
            this.buttonToggle.displayString = I18n.format("gui.hud.cpsMod4") + RegistrySettings.cpsEnabled;
            
            RegistrySettings.updateConfiguration("modules", "cps", RegistrySettings.cpsEnabled);
        }
        
        else if (guiButton == this.buttonAllign)
        {
            RegistrySettings.cpsX = RegistrySettings.keystrokesX + 1;
            RegistrySettings.cpsY = Integer.valueOf(RegistrySettings.keystrokesY + 41);
        }
        
        else if (guiButton == this.buttonAllign2)
        {
            RegistrySettings.cpsX = RegistrySettings.keystrokesX + 1;
            RegistrySettings.cpsY = Integer.valueOf(RegistrySettings.keystrokesY + 61);
        }
        
        else if (guiButton == this.buttonAllign3)
        {
            RegistrySettings.cpsX = RegistrySettings.keystrokesX + 1;
            RegistrySettings.cpsY = Integer.valueOf(RegistrySettings.keystrokesY + 75);
        }
        
        else if (guiButton == this.buttonColor)
        {
            color++;
            
            if (color == (Colors.values()).length)
            {
                color = 0;
            }
            
            this.buttonColor.displayString = I18n.format("gui.hud.cpsMod5") + Colors.values()[color];
            
            RegistrySettings.updateConfiguration("modules", "cpsColor", RegistrySettings.cpsColor);
        }
        
        else if (guiButton.id == 6)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        super.actionPerformed(guiButton);
    }
}