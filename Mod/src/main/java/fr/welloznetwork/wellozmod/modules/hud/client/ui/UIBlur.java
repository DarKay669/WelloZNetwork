package fr.welloznetwork.wellozmod.modules.hud.client.ui;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class UIBlur extends GuiScreen
{
    private GuiButton buttonToggle;
    private GuiScreen parentScreen;
    
    public UIBlur(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
    }
    
    public void initGui()
    {
        this.buttonList.add(buttonToggle = new GuiButton(0, this.width / 2 - 85, this.height / 4 + 24 + (byte) - 16, 170, 20, I18n.format("gui.hud.cpsMod4") + (RegistrySettings.blurEnabled)));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 85, this.height / 4 + 68 + (byte) - 16, 170, 20, I18n.format("gui.done")));
        
        super.initGui();
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        this.drawCenteredString(this.fontRendererObj, "Blur", this.width / 2, 40, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton == this.buttonToggle)
        {
            RegistrySettings.blurEnabled = !RegistrySettings.blurEnabled;
            this.buttonToggle.displayString = I18n.format("gui.hud.cpsMod4") + RegistrySettings.blurEnabled;
            
            RegistrySettings.updateConfiguration("modules", "blur", RegistrySettings.blurEnabled);
        }
        
        else if (guiButton.id == 1)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        super.actionPerformed(guiButton);
    }
}