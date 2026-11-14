package fr.welloznetwork.wellozmod.modules.hud.client.ui;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.time.TimeType;
import fr.welloznetwork.wellozmod.modules.hud.MHUD;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class UITimeChanger extends GuiScreen
{
    private GuiScreen parentScreen;
    
    public UITimeChanger(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
    }
    
    public void initGui()
    {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 85, this.height / 4 + 24 + (byte) - 16, 170, 20, I18n.format("gui.timeChangerMod")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 85, this.height / 4 + 46 + (byte) - 16, 170, 20, I18n.format("gui.timeChangerMod1")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 85, this.height / 4 + 68 + (byte) - 16, 170, 20, I18n.format("gui.timeChangerMod2")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 85, this.height / 4 + 90 + (byte) - 16, 170, 20, I18n.format("gui.timeChangerMod3")));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 85, this.height / 4 + 112 + (byte) - 16, 170, 20, I18n.format("gui.timeChangerMod4")));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 85, this.height / 4 + 156 + (byte) - 16, 170, 20, I18n.format("gui.done")));
        
        super.initGui();
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        this.drawCenteredString(this.fontRendererObj, "Time Changer", this.width / 2, 40, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton.id == 0)
        {
            MHUD.CLIENT_LISTENER.timeType = TimeType.VANILLA;
        }
        
        else if (guiButton.id == 1)
        {
            MHUD.CLIENT_LISTENER.timeType = TimeType.DAY;
        }

        else if (guiButton.id == 2)
        {
            MHUD.CLIENT_LISTENER.timeType = TimeType.SUNSET;
        }
        
        else if (guiButton.id == 3)
        {
            MHUD.CLIENT_LISTENER.timeType = TimeType.NIGHT;
        }
        
        else if (guiButton.id == 4)
        {
            MHUD.CLIENT_LISTENER.timeType = TimeType.FAST;
        }
        
        else if (guiButton.id == 5)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        super.actionPerformed(guiButton);
    }
}