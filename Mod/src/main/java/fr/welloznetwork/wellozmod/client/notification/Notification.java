package fr.welloznetwork.wellozmod.client.notification;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.mixins.client.gui.IMixinGuiScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

@SideOnly(Side.CLIENT)
public class Notification
{
    private NotificationType notificationType;
    private String title, message;
    private long start, fadeIn, fadeOut, end;
    private boolean isOneRect;
    
    public Notification(NotificationType notificationType, String title, String message, int time)
    {
        this.notificationType = notificationType;
        this.title = title;
        this.message = message;
        this.isOneRect = false;
        
        this.fadeIn = 200 * time;
        this.fadeOut = this.fadeIn + 500 * time;
        this.end = this.fadeOut + this.fadeIn;
    }
    
    public Notification(NotificationType notificationType, String title, int time)
    {
        this(notificationType, title, "", time);
        this.isOneRect = true;
    }
    
    public void show()
    {
        this.start = System.currentTimeMillis();
    }
    
    public boolean isShown()
    {
        return this.getTime() <= this.end;
    }
    
    private long getTime()
    {
        return System.currentTimeMillis() - this.start;
    }
    
    public void render()
    {
        double offset = 0;
        int height = 30;
        long time = this.getTime();
        
        int titleWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(this.title);
        int messageWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(this.message);
        int width = Math.max(titleWidth, messageWidth) + 20;
        
        if (time < this.fadeIn)
        {
            offset = Math.tanh(time / (double) (this.fadeIn) * 3.0D) * width;
        }
        
        else if (time > this.fadeOut)
        {
            offset = (Math.tanh(3.0D - (time - this.fadeOut) / (double) (this.end - this.fadeOut) * 3.0D) * width);
        }
        
        else
        {
            offset = width;
        }
        
        Color color = new Color(0, 0, 0, 220), color1;
        
        if (this.notificationType == NotificationType.INFO)
        {
            color1 = new Color(41, 128, 185);
        }
        
        else if (this.notificationType == NotificationType.MUSIC)
        {
            color1 = new Color(221, 143, 255);
        }
        
        else if (this.notificationType == NotificationType.ACHIEVEMENT)
        {
            color1 = new Color(10, 132, 255);
        }
        
        else if (this.notificationType == NotificationType.JOB)
        {
            color1 = new Color(4, 172, 64);
        }
        
        else if (this.notificationType == NotificationType.SERVER)
        {
            color1 = new Color(32, 242, 160);
        }
        
        else if (this.notificationType == NotificationType.WARNING)
        {
            color1 = new Color(255, 206, 10);
        }
        
        else
        {
            color1 = new Color(231, 76, 60);
            color = new Color(Math.max(0, Math.min(255, (int) (Math.sin(time / 100.0) * 255.0 / 2 + 127.5))), 0, 0, 220);
        }
        
        int screenWidth, screenHeight;
        
        if (Minecraft.getMinecraft().currentScreen != null)
        {
            screenWidth = ((IMixinGuiScreenAccessor) Minecraft.getMinecraft().currentScreen).getWidth();
            screenHeight = ((IMixinGuiScreenAccessor) Minecraft.getMinecraft().currentScreen).getHeight();
        }
        
        else
        {
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            screenWidth = scaledResolution.getScaledWidth();
            screenHeight = scaledResolution.getScaledHeight();
        }
        
        if (this.isOneRect)
        {
            this.drawNotification(screenWidth - offset, screenHeight - 5 - 15, screenWidth, screenHeight - 5, color.getRGB());
            this.drawNotification(screenWidth - offset, screenHeight - 5 - 15, screenWidth - offset + 4, screenHeight - 5, color1.getRGB());
            
            Minecraft.getMinecraft().fontRenderer.drawString(this.title, (int) (screenWidth - offset + 8), screenHeight - 2 - 15, Color.WHITE.getRGB());
        }
        
        else
        {
            this.drawNotification(screenWidth - offset, screenHeight - 5 - height, screenWidth, screenHeight - 5, color.getRGB());
            this.drawNotification(screenWidth - offset, screenHeight - 5 - height, screenWidth - offset + 4, screenHeight - 5, color1.getRGB());
            
            Minecraft.getMinecraft().fontRenderer.drawString(this.title, (int) (screenWidth - offset + 8), screenHeight - 2 - height, Color.WHITE.getRGB());
            Minecraft.getMinecraft().fontRenderer.drawString(this.message, (int) (screenWidth - offset + 8), screenHeight - 15, Color.WHITE.getRGB());
        }
    }
    
    private void drawNotification(double left, double top, double right, double bottom, int color)
    {
        if (left < right)
        {
            double i = left;
            left = right;
            right = i;
        }
        
        if (top < bottom)
        {
            double j = top;
            top = bottom;
            bottom = j;
        }
        
        Tessellator tessellator = Tessellator.instance;
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        
        GL11.glColor4f((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, (float) (color >> 24 & 255) / 255.0F);
        
        tessellator.startDrawingQuads();
        tessellator.addVertex(left, bottom, 0.0D);
        tessellator.addVertex(right, bottom, 0.0D);
        tessellator.addVertex(right, top, 0.0D);
        tessellator.addVertex(left, top, 0.0D);
        tessellator.draw();
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}