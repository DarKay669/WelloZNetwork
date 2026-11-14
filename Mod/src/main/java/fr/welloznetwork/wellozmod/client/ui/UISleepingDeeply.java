package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.network.play.server.S06PacketTeleport;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class UISleepingDeeply extends GuiScreen
{
    private int timer = 0;
    private float alpha = 0.0F;
    private EntityPlayer entityPlayer;
    
    public UISleepingDeeply(EntityPlayer entityPlayer)
    {
        this.entityPlayer = entityPlayer;
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        drawRect(0, 0, this.width, this.height, ((int) (this.alpha * 255.0F) << 24));
        
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2.0F, this.height / 2.0F - 20.0F, 0.0F);
        GL11.glScalef(3.0F, 3.0F, 3.0F);
        
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.sleepingDeeply"), 0, 0, Color.WHITE.getRGB());
        
        GL11.glPopMatrix();
        
        int timer = 10 - (this.timer / 20);
        
        if (timer > 0)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.sleepingDeeply1") + " " + timer + " " + I18n.format("gui.gameOver.time") + (timer > 1 ? "s" : " "), this.width - this.fontRendererObj.getStringWidth(I18n.format("gui.sleepingDeeply1") + " " + timer + " " + I18n.format("gui.gameOver.time") + (timer > 1 ? "s": " ")) -2, this.height - 10, Color.WHITE.getRGB());
        }
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void updateScreen()
    {        
        this.timer++;
        
        if (this.timer < 20)
        {
            this.alpha = (float) this.timer / 20.0F;
        }
        
        if (this.timer >= 200)
        {
            WelloZMod.proxy.getNetworkManager().sendToServer(new S06PacketTeleport());
            
            this.mc.displayGuiScreen(null);
        }
    }
    
    protected void keyTyped(char typedChar, int keyCode) {}
}