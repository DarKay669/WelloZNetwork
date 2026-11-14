package fr.welloznetwork.wellozmod.modules.hud.client.ui;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.render.ChatMode;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class UIChatMode extends GuiScreen
{
    private GuiScreen parentScreen;
    
    public UIChatMode(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
    }
    
    public void initGui()
    {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 85, this.height / 4 + 24 + (byte) - 16, 170, 20, I18n.format("gui.normal.chatMode")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 85, this.height / 4 + 46 + (byte) - 16, 170, 20, I18n.format("gui.adaptive.chatMode")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 85, this.height / 4 + 68 + (byte) - 16, 170, 20, I18n.format("gui.clear.chatMode")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 85, this.height / 4 + 112 + (byte) - 16, 170, 20, I18n.format("gui.done")));
        
        super.initGui();
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        this.drawCenteredString(this.fontRendererObj, "Chat Mode", this.width / 2, 40, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton.id == 0)
        {
            RegistrySettings.chatMode = ChatMode.NORMAL;
            RegistrySettings.updateConfiguration("modules", "chatmode", "NORMAL");
        }
        
        else if (guiButton.id == 1)
        {
            RegistrySettings.chatMode = ChatMode.ADAPTIVE;
            RegistrySettings.updateConfiguration("modules", "chatmode", "ADAPTIVE");
        }
        
        else if (guiButton.id == 2)
        {
            RegistrySettings.chatMode = ChatMode.CLEAR;
            RegistrySettings.updateConfiguration("modules", "chatmode", "CLEAR");
        }
        
        else if (guiButton.id == 3)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        super.actionPerformed(guiButton);
    }
}