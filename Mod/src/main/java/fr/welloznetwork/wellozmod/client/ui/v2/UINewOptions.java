package fr.welloznetwork.wellozmod.client.ui.v2;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.ui.UIControls;
import fr.welloznetwork.wellozmod.client.ui.UILanguage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.client.settings.GameSettings;

@SideOnly(Side.CLIENT)
public class UINewOptions extends GuiScreen
{
    private GuiScreen lastScreen;
    private GameSettings gameSettings;
    
    public UINewOptions(GuiScreen guiScreen, GameSettings gameSettings)
    {
        this.lastScreen = guiScreen;
        this.gameSettings = gameSettings;
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        GL11.glPushMatrix();
        GL11.glScalef(1.5F, 1.5F, 1.0F);
        
        this.drawCenteredString(this.fontRendererObj, "Musique & sons", this.width / 3, 50, this.isMouseOver(mouseX, mouseY, this.width / 3, 50, "Musique & sons", 1.5F) ? new Color(255, 255, 255).getRGB() : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, "Options graphiques", this.width / 3, 65, this.isMouseOver(mouseX, mouseY, this.width / 3, 65, "Options graphiques", 1.5F) ? new Color(255, 255, 255).getRGB() : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, "Langue", this.width / 3, 80, this.isMouseOver(mouseX, mouseY, this.width / 3, 80, "Langue", 1.5F) ? new Color(255, 255, 255).getRGB() : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, "Packs de ressources", this.width / 3, 95, this.isMouseOver(mouseX, mouseY, this.width / 3, 95, "Packs de ressources", 1.5F) ? new Color(255, 255, 255).getRGB() : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, "Contrôles", this.width / 3, 110, this.isMouseOver(mouseX, mouseY, this.width / 3, 110, "Contrôles", 1.5F) ? new Color(255, 255, 255).getRGB() : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, "Paramètres multijoueur", this.width / 3, 125, this.isMouseOver(mouseX, mouseY, this.width / 3, 125, "Paramètres multijoueur", 1.5F) ? new Color(255, 255, 255).getRGB() : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, "Terminé", this.width / 3, 140, this.isMouseOver(mouseX, mouseY, this.width / 3, 140, "Terminé", 1.5F) ? new Color(255, 255, 255).getRGB() : new Color(253, 191, 0).getRGB());
        
        GL11.glPopMatrix();
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            this.gameSettings.saveOptions();
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 50, "Musique & sons", 1.5F))
            {
                this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.gameSettings));
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 65, "Options graphiques", 1.5F))
            {
                this.mc.displayGuiScreen(new GuiVideoSettings(this, this.gameSettings));
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 80, "Langue", 1.5F))
            {
                this.mc.displayGuiScreen(new UILanguage(this, this.gameSettings, this.mc.getLanguageManager()));
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 95, "Pack de ressources", 1.5F))
            {
                this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 110, "Contrôles", 1.5F))
            {
                this.mc.displayGuiScreen(new UIControls(this, this.gameSettings));
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 125, "Paramètres multijoueur", 1.5F))
            {
                this.mc.displayGuiScreen(new ScreenChatOptions(this, this.gameSettings));
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 140, "Terminé", 1.5F))
            {
                this.mc.displayGuiScreen(this.lastScreen);
            }
        }
    }
    
    private boolean isMouseOver(int mouseX, int mouseY, int textX, int textY, String text, float scale)
    {
        mouseX /= scale;
        mouseY /= scale;
        
        return mouseX >= textX - this.fontRendererObj.getStringWidth(text) / 2 && mouseX <= textX + this.fontRendererObj.getStringWidth(text) / 2 && mouseY >= textY && mouseY <= textY + this.fontRendererObj.FONT_HEIGHT;
    }
}