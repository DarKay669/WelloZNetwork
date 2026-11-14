package fr.welloznetwork.wellozmod.client.ui.v2;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.ui.UIMainMenu;
import fr.welloznetwork.wellozmod.modules.hud.client.ui.UIHUD;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class UINewIngameMenu extends GuiScreen
{
    private String displayString = I18n.format("gui.customGuiIngameMenu.disconnected");
    private int displayColor = new Color(100, 100, 100).getRGB(), hoverColor = Color.WHITE.getRGB(), disconnectCounter = 0;
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        GL11.glPushMatrix();
        GL11.glScalef(1.5F, 1.5F, 1.0F);
        
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.returnToGame"), this.width / 3, 60, this.isMouseOver(mouseX, mouseY, this.width / 3, 60, I18n.format("menu.returnToGame"), 1.5F) ? this.hoverColor : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, "Options", this.width / 3, 75, this.isMouseOver(mouseX, mouseY, this.width / 3, 75, "Options", 1.5F) ? this.hoverColor : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, "HUD", this.width / 3, 90, this.isMouseOver(mouseX, mouseY, this.width / 3, 90, "HUD", 1.5F) ? this.hoverColor : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.stats"), this.width / 3, 105, this.isMouseOver(mouseX, mouseY, this.width / 3, 105, I18n.format("gui.stats"), 1.5F) ? this.hoverColor : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.achievements"), this.width / 3, 120, this.isMouseOver(mouseX, mouseY, this.width / 3, 120, I18n.format("gui.achievements"), 1.5F) ? this.hoverColor : new Color(253, 191, 0).getRGB());
        this.drawCenteredString(this.fontRendererObj, this.displayString, this.width / 3, 135, this.isMouseOver(mouseX, mouseY, this.width / 3, 135, this.displayString, 1.5F) ? this.hoverColor : this.displayColor);
        
        GL11.glPopMatrix();
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 60, I18n.format("menu.returnToGame"), 1.5F))
            {
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 75, "Options", 1.5F))
            {
                this.mc.displayGuiScreen(new UINewOptions(this, this.mc.gameSettings));
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 90, "HUD", 1.5F))
            {
                this.mc.displayGuiScreen(new UIHUD(this));
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 105, I18n.format("gui.stats"), 1.5F))
            {
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 120, I18n.format("gui.achievements"), 1.5F))
            {
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
            }
            
            if (this.isMouseOver(mouseX, mouseY, this.width / 3, 135, this.displayString, 1.5F))
            {
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                this.mc.displayGuiScreen(new UIMainMenu());
            }
        }
    }
    
    public void updateScreen()
    {
        this.disconnectCounter++;
        
        if (!this.mc.isSingleplayer())
        {
            if (this.disconnectCounter < 20)
            {
                this.displayString = I18n.format("gui.customGuiIngameMenu.waiting1");
                this.displayColor = new Color(100, 100, 100).getRGB();
                this.hoverColor = new Color(100, 100, 100).getRGB();
            }
            
            else if (this.disconnectCounter > 20 && this.disconnectCounter < 40)
            {
                this.displayString = I18n.format("gui.customGuiIngameMenu.waiting2");
                this.displayColor = new Color(100, 100, 100).getRGB();
                this.hoverColor = new Color(100, 100, 100).getRGB();
            }
            
            else if (this.disconnectCounter > 40 && this.disconnectCounter < 60)
            {
                this.displayString = I18n.format("gui.customGuiIngameMenu.waiting3");
                this.displayColor = new Color(100, 100, 100).getRGB();
                this.hoverColor = new Color(100, 100, 100).getRGB();
            }
            
            else if (this.disconnectCounter > 60 && this.disconnectCounter < 80)
            {
                this.displayString = I18n.format("gui.customGuiIngameMenu.waiting4");
                this.displayColor = new Color(100, 100, 100).getRGB();
                this.hoverColor = new Color(100, 100, 100).getRGB();
            }
            
            else if (this.disconnectCounter > 80 && this.disconnectCounter < 100)
            {
                this.displayString = I18n.format("gui.customGuiIngameMenu.waiting5");
                this.displayColor = new Color(100, 100, 100).getRGB();
                this.hoverColor = new Color(100, 100, 100).getRGB();
            }
            
            else if (this.disconnectCounter > 100)
            {
                this.displayString = I18n.format("gui.customGuiIngameMenu.disconnected");
                this.displayColor = new Color(253, 191, 0).getRGB();
                this.hoverColor = Color.WHITE.getRGB();
            }
        }
        
        else
        {
            this.displayString = I18n.format("menu.returnToMenu");
            this.displayColor = new Color(253, 191, 0).getRGB();
            this.hoverColor = Color.WHITE.getRGB();
        }
    }
    
    private boolean isMouseOver(int mouseX, int mouseY, int textX, int textY, String text, float scale)
    {
        mouseX /= scale;
        mouseY /= scale;
        
        return mouseX >= textX - this.fontRendererObj.getStringWidth(text) / 2 && mouseX <= textX + this.fontRendererObj.getStringWidth(text) / 2 && mouseY >= textY && mouseY <= textY + this.fontRendererObj.FONT_HEIGHT;
    }
}