package fr.welloznetwork.wellozmod.mixins.client.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;

@Mixin(GuiGameOver.class)
public class MixinGuiGameOver extends GuiScreen implements GuiYesNoCallback
{
    private int timer = 0;
    private float alpha = 0.0F;
    
    @Overwrite
    public void initGui() {}
    
    @Overwrite
    protected void actionPerformed(GuiButton guiButton) {}
    
    @Overwrite
    public void confirmClicked(boolean result, int id) {}
    
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        this.drawRect(0, 0, this.width, this.height, ((int) (this.alpha * 255.0F) << 24));
        
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2.0F, this.height / 2.0F - 20.0F, 0.0F);
        GL11.glScalef(3.0F, 3.0F, 3.0F);
        GL11.glRotatef(4.5F, 0.0F, 0.0F, 1.0F);
        
        String message = I18n.format("gui.gameOver");
        
        int xOffset = -this.mc.fontRenderer.getStringWidth(message) / 3;
        
        for (int i = 0; i < message.length(); i++)
        {
            char c = message.charAt(i);
            
            GL11.glPushMatrix();
            GL11.glTranslatef(xOffset, (float) Math.sin(((this.mc.getSystemTime() + i * 60L) % 1000L) / 1000.0F * Math.PI) * 3.8F, 0.0F);
            
            this.drawCenteredString(this.fontRendererObj, String.valueOf(c), 0, 0, Color.WHITE.getRGB());
            
            GL11.glPopMatrix();
            
            xOffset += this.mc.fontRenderer.getCharWidth(c);
        }
        
        GL11.glPopMatrix();
        
        int timer = 5 - (this.timer / 20);
        
        if (timer > 0)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.gameOver.respawn") + " " + timer + " " + I18n.format("gui.gameOver.time") + (timer > 1 ? "s" : " "), this.width - this.fontRendererObj.getStringWidth(I18n.format("gui.gameOver.respawn") + " " + timer + " " + I18n.format("gui.gameOver.time") + (timer > 1 ? "s" : " ")) - 2, this.height - 10, Color.WHITE.getRGB());
        }
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Overwrite
    public void updateScreen()
    {
        this.timer++;
        
        if (this.timer < 20)
        {
            this.alpha = (float) this.timer / 20.0F;
        }
        
        if (this.timer >= 100)
        {
            this.mc.thePlayer.respawnPlayer();
            this.mc.displayGuiScreen(null);
        }
    }
}