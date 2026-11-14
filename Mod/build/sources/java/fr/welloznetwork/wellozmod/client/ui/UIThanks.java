package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class UIThanks extends GuiScreen
{    
    private long initTime = 0L;
    private float alpha = 0.0F, alphaWann = 0.0F;
    private int timer;
    private CustomFontRenderer pixelNES, minecraftDungeonsRegular;
    
    public UIThanks()
    {
        this.pixelNES = CustomFontRenderer.initTTF("Pixel-NES.otf", 41);
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        long elapsedTime = System.currentTimeMillis() - this.initTime;
        
        this.alpha = Math.min(this.alpha + 0.02F, 1.0F);
        
        drawRect(0, 0, this.width, this.height, Color.BLACK.getRGB());
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, this.alpha);
        
        this.alpha = 0.0F;
        this.alphaWann = Math.min(this.alphaWann + 0.02F, 1.0F);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, this.alphaWann);
        
        this.minecraftDungeonsRegular = CustomFontRenderer.initTTF("Minecraft-Dungeons-Regular.ttf", (int) (40 * this.width / 1920.0F));
        
        if (elapsedTime < 25000L)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.width / 2.0F, this.height / 2.0F, 0.0F);
            
            this.pixelNES.drawCenteredString(I18n.format("gui.credits.thanks"), 0, 0, new Color(198, 158, 0).getRGB());
            
            GL11.glPopMatrix();
        }
        
        if (elapsedTime >= 15000L && elapsedTime < 25000L)
        {            
            this.alpha = 0.0F;
            this.alphaWann = Math.min(this.alphaWann + 0.02F, 1.0F);
                        
            GL11.glColor4f(1.0F, 1.0F, 1.0F, this.alphaWann);
            
            this.minecraftDungeonsRegular.drawBottomCenteredString(I18n.format("gui.credits.copyright.en"), (int) (-24 * this.width / 1920.0F), (int) (this.alphaWann * 255.0F) << 24 | Color.WHITE.getRGB());
            this.minecraftDungeonsRegular.drawBottomCenteredString(I18n.format("gui.credits.copyright.fr"), (int) (-48 * this.width / 1920.0F), (int) (this.alphaWann * 255.0F) << 24 | Color.WHITE.getRGB());
        }
        
        if (elapsedTime >= 25000L)
        {
            this.alphaWann = 0.0F;
            
            GL11.glPushMatrix();
            GL11.glTranslatef(this.width / 2.0F, this.height / 2.0F + 15.0F, 0.0F);
            
            this.pixelNES.drawCenteredString(I18n.format("gui.credits.pressKey"), 0, 0, ((int) ((0.1F + (float) (0.5F + 0.5F * Math.sin(this.timer / 6.625F))) * 0.9F * 255.0F) << 24) | (255 << 16) | (255 << 8) | 255);
            
            GL11.glPopMatrix();
        }
        
        GL11.glDisable(GL11.GL_BLEND);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.interact();
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        this.interact();
    }
    
    public void initGui()
    {
        if (this.initTime == 0L)
        {
            this.initTime = System.currentTimeMillis();
        }
    }
    
    public void updateScreen()
    {
        this.timer++;
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    private void interact()
    {
        if (System.currentTimeMillis() - this.initTime >= 25000L)
        {
            this.mc.displayGuiScreen(new UIMainMenu());
        }
    }
}