package fr.welloznetwork.wellozmod.modules.hud.client.ui;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.render.ButtonTheme;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class UITheme extends GuiScreen
{
    private final GuiScreen parentScreen;
    
    public UITheme(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
    }
    
    public void initGui()
    {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 85, this.height / 4 + 24 + (byte) - 16, 170, 20, I18n.format("gui.oldTheme")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 85, this.height / 4 + 46 + (byte) - 16, 170, 20, I18n.format("gui.newTheme")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 85, this.height / 4 + 68 + (byte) - 16, 170, 20, I18n.format("gui.legacyTheme")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 85, this.height / 4 + 112 + (byte) - 16, 170, 20, I18n.format("gui.done")));
        
        super.initGui();
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.theme"), this.width / 2, 40, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton.id == 0)
        {
            RegistrySettings.activeTheme = ButtonTheme.OLD;
            RegistrySettings.updateConfiguration("modules", "theme", "OLD");
        }
        
        else if (guiButton.id == 1)
        {
            RegistrySettings.activeTheme = ButtonTheme.NEW;
            RegistrySettings.updateConfiguration("modules", "theme", "NEW");
        }
        
        else if (guiButton.id == 2)
        {
            RegistrySettings.activeTheme = ButtonTheme.LEGACY;
            RegistrySettings.updateConfiguration("modules", "theme", "LEGACY");
        }
        
        else if (guiButton.id == 3)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        super.actionPerformed(guiButton);
    }
}