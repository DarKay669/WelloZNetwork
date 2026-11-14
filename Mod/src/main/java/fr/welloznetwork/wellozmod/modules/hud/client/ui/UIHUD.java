package fr.welloznetwork.wellozmod.modules.hud.client.ui;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.Constants;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class UIHUD extends GuiScreen
{
    private GuiScreen parentScreen;
    
    public UIHUD(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
    }
    
    public void initGui()
    {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 178 + (byte) - 16, I18n.format("gui.done")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 24 + (byte) - 16, "CPS"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 46 + (byte) - 16, "Keystrokes"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 68 + (byte) - 16, "Reach Display"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 90 + (byte) - 16, "Blur"));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 112 + (byte) - 16, "Time Changer"));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 100, this.height / 4 + 134 + (byte) - 16, "Toggle Sprint"));
        
        if (Constants.V2)
        {
            this.buttonList.add(new GuiButton(7, this.width / 2 - 100, this.height / 4 + 156 + (byte) - 16, I18n.format("gui.theme")));
            this.buttonList.add(new GuiButton(8, this.width / 2 - 100, this.height / 4 + 178 + (byte) - 16, I18n.format("Chat Mode")));
        }
        
        super.initGui();
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton.id == 0)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        else if (guiButton.id == 1)
        {
            this.mc.displayGuiScreen(new UICPS(this));
        }
        
        else if (guiButton.id == 2)
        {
            this.mc.displayGuiScreen(new UIKeystrokes(this));
        }

        else if (guiButton.id == 3)
        {
            this.mc.displayGuiScreen(new UIReachDisplay(this));
        }
        
        else if (guiButton.id == 4)
        {
            this.mc.displayGuiScreen(new UIBlur(this));
        }
        
        else if (guiButton.id == 5)
        {
            this.mc.displayGuiScreen(new UITimeChanger(this));
        }
        
        else if (guiButton.id == 6)
        {
            this.mc.displayGuiScreen(new UIToggleSprint(this));
        }
        
        else if (guiButton.id == 7)
        {
            this.mc.displayGuiScreen(new UITheme(this));
        }
        
        else if (guiButton.id == 8)
        {
            this.mc.displayGuiScreen(new UIChatMode(this));
        }
        
        super.actionPerformed(guiButton);
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks) 
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.hud"), this.width / 2, 40, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}