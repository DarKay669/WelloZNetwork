package fr.welloznetwork.wellozmod.modules.hud.client.ui;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.render.Colors;
import fr.welloznetwork.wellozmod.modules.hud.MHUD;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import fr.welloznetwork.wellozmod.modules.hud.util.HUDUtil.KeyMode;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class UIKeystrokes extends GuiScreen
{
    private boolean isDragging;
    public static int color = 15, colorPressed = 0;
    private int lastMouseX, lastMouseY, lastAddX, lastAddY;
    private final KeyMode mode;
    private GuiButton buttonToggle, buttonMode, buttonReset, buttonColor, buttonColorPressed;
    private GuiScreen parentScreen;
    
    public UIKeystrokes(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
        this.mode = KeyMode.values()[RegistrySettings.keystrokesMode];
    }
    
    private static void setAddX(int addX)
    {
        RegistrySettings.keystrokesX = addX;
    }
    
    private static void setAddY(int addY)
    {
        RegistrySettings.keystrokesY = addY;
    }
    
    public void initGui()
    {
        this.buttonList.add(this.buttonToggle = new GuiButton(0, this.width / 2 - 85, this.height / 4 + 46 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod4") + RegistrySettings.keystrokesEnabled));
        this.buttonList.add(this.buttonMode = new GuiButton(1, this.width / 2 - 85, this.height / 4 + 68 + (byte) - 16, 170, 20, I18n.format("gui.hud.keystrokesMod") + KeyMode.values()[RegistrySettings.keystrokesMode]));
        this.buttonList.add(this.buttonReset = new GuiButton(2, this.width / 2 - 85, this.height / 4 + 24 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod")));
        this.buttonList.add(this.buttonColor = new GuiButton(3, this.width / 2 - 85, this.height / 4 + 90 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod5") + Colors.values()[color]));
        this.buttonList.add(this.buttonColorPressed = new GuiButton(4, this.width / 2 - 85, this.height / 4 + 112 + (byte) - 16, 170, 20, I18n.format("gui.hud.keystrokesMod1") + Colors.values()[colorPressed]));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 85, this.height / 4 + 156 + (byte) - 16, 170, 20, I18n.format("gui.done")));
        
        super.initGui();
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        this.drawCenteredString(this.fontRendererObj, "Keystrokes", this.width / 2, 40, Color.WHITE.getRGB());
        
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
        if (!this.isDragging && RegistrySettings.keystrokesEnabled && mouseX > MHUD.CLIENT_LISTENER.minXKeystrokes && mouseX < MHUD.CLIENT_LISTENER.maxXKeystrokes && mouseY > MHUD.CLIENT_LISTENER.minYKeystrokes && mouseY < MHUD.CLIENT_LISTENER.maxYKeystrokes)
        {
            this.isDragging = true;
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.lastAddX = RegistrySettings.keystrokesX;
            this.lastAddY = RegistrySettings.keystrokesY;
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
        if (guiButton == this.buttonToggle)
        {
            RegistrySettings.keystrokesEnabled = !RegistrySettings.keystrokesEnabled;
            this.buttonToggle.displayString = I18n.format("gui.hud.cpsMod4") + RegistrySettings.keystrokesEnabled;
            
            RegistrySettings.updateConfiguration("modules", "keystrokes", RegistrySettings.keystrokesEnabled);
        }
        
        else if (guiButton == this.buttonReset)
        {
            RegistrySettings.keystrokesX = 2;
            RegistrySettings.keystrokesY = 40;
            
            RegistrySettings.updateConfiguration("modules", "keystrokesPosX", RegistrySettings.keystrokesX);
            RegistrySettings.updateConfiguration("modules", "keystrokesPosY", RegistrySettings.keystrokesY);
        }
        
        else if (guiButton == this.buttonMode)
        {
            RegistrySettings.keystrokesMode++;
            
            if (RegistrySettings.keystrokesMode == (KeyMode.values()).length)
            {
                RegistrySettings.keystrokesMode = 0;
            }
            
            this.buttonMode.displayString = I18n.format("gui.hud.keystrokesMod") + KeyMode.values()[RegistrySettings.keystrokesMode];
            
            RegistrySettings.updateConfiguration("modules", "keystrokesMode", RegistrySettings.keystrokesMode);
        }
        
        else if (guiButton == this.buttonColor)
        {
            color++;
            
            if (color == (Colors.values()).length)
            {
                color = 0;
            }
            
            this.buttonColor.displayString = I18n.format("gui.hud.cpsMod5") + Colors.values()[color];
            
            RegistrySettings.updateConfiguration("modules", "keystrokesColor", RegistrySettings.keystrokesColor);
        }
        
        else if (guiButton == this.buttonColorPressed)
        {
            colorPressed++;
            
            if (colorPressed == (Colors.values()).length)
            {
                colorPressed = 0;
            }
            
            this.buttonColorPressed.displayString = I18n.format("gui.hud.keystrokesMod1") + Colors.values()[colorPressed];
            
            RegistrySettings.updateConfiguration("modules", "keystrokesColorPressed", RegistrySettings.keystrokesColorPressed);
        }
        
        else if (guiButton.id == 5) 
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        super.actionPerformed(guiButton);
    }
}