package fr.welloznetwork.wellozmod.modules.hud.util;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import fr.welloznetwork.venus.lib.render.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

public class HUDUtil
{
    public static void drawOutlinedBlock(float partialTicks, double xCoord, double yCoord, double zCoord, int color)
    {
        float posX = (float) Minecraft.getMinecraft().thePlayer.posX;
        float posY = (float) Minecraft.getMinecraft().thePlayer.posY;
        float posZ = (float) Minecraft.getMinecraft().thePlayer.posZ;
        float prevPosX = (float) Minecraft.getMinecraft().thePlayer.prevPosX;
        float prevPosY = (float) Minecraft.getMinecraft().thePlayer.prevPosY;
        float prevPosZ = (float) Minecraft.getMinecraft().thePlayer.prevPosZ;
        float prevPosX1 = prevPosX + (posX - prevPosX) * partialTicks;
        float prevPosY1 = prevPosY + (posY - prevPosY) * partialTicks;
        float prevPosZ1  = prevPosZ + (posZ - prevPosZ) * partialTicks;
        
        Tessellator tessellator = Tessellator.instance;
        
        tessellator.startDrawing(1);
        tessellator.setColorRGBA_I(color, 255);
        tessellator.setBrightness(200);
        
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
        
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
        
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
        
        tessellator.draw();
    }
    
    public static void drawStringWithHueRainbowEffect(String string, int x, int y, float brightness, boolean shadow)
    {
        int xPos = x;
        
        for (int i = 0; i < string.length(); i++)
        {
            String s = string.charAt(i) + "";
            
            if (shadow)
            {
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(s, xPos, y, FontRenderer.rainbowHueEffect(i * 3500000L, brightness, 100).getRGB());
            }
            
            Minecraft.getMinecraft().fontRenderer.drawString(s, xPos, y, FontRenderer.rainbowHueEffect(i * 3500000L, brightness, 100).getRGB());
            xPos += Minecraft.getMinecraft().fontRenderer.getStringWidth(s);
        }
    }
    
    public static void drawCenteredStringWithScaledEffect(String string, float scale, int color)
    {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
        
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        
        int x = (int) ((screenWidth / scale) / scale);
        int y = (int) ((screenHeight / scale) / scale);
        
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(string, x - Minecraft.getMinecraft().fontRenderer.getStringWidth(string) / 2, y - 10, color);
        
        GL11.glPopMatrix();
    }
    
    public static void drawBottomCenteredString(String string, int y, int color)
    {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(screenWidth / 1.0F, screenHeight - 10, 0.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(string, - Minecraft.getMinecraft().fontRenderer.getStringWidth(string) / 1, y, color);
        
        GL11.glPopMatrix();
    }
    
    public static enum KeyMode
    {
        ZQSD(Arrays.asList(new Keys[] {new Keys("Z", (Minecraft.getMinecraft()).gameSettings.keyBindForward, 21, 1, 18, 18), new Keys("Q", (Minecraft.getMinecraft()).gameSettings.keyBindLeft, 1, 21, 18, 18), new Keys("S", (Minecraft.getMinecraft()).gameSettings.keyBindBack, 21, 21, 18, 18), new Keys("D", (Minecraft.getMinecraft()).gameSettings.keyBindRight, 41, 21, 18, 18)})),
        ZQSD_MOUSE(Arrays.asList(new Keys[] {new Keys("Z", (Minecraft.getMinecraft()).gameSettings.keyBindForward, 21, 1, 18, 18), new Keys("Q", (Minecraft.getMinecraft()).gameSettings.keyBindLeft, 1, 21, 18, 18), new Keys("S", (Minecraft.getMinecraft()).gameSettings.keyBindBack, 21, 21, 18, 18), new Keys("D", (Minecraft.getMinecraft()).gameSettings.keyBindRight, 41, 21, 18, 18), new Keys("LMB", (Minecraft.getMinecraft()).gameSettings.keyBindAttack, 1, 41, 28, 18), new Keys("RMB", (Minecraft.getMinecraft()).gameSettings.keyBindUseItem, 31, 41, 28, 18)})),
        ZQSD_JUMP_MOUSE(Arrays.asList(new Keys[] {new Keys("Z", (Minecraft.getMinecraft()).gameSettings.keyBindForward, 21, 1, 18, 18), new Keys("Q", (Minecraft.getMinecraft()).gameSettings.keyBindLeft, 1, 21, 18, 18), new Keys("S", (Minecraft.getMinecraft()).gameSettings.keyBindBack, 21, 21, 18, 18), new Keys("D", (Minecraft.getMinecraft()).gameSettings.keyBindRight, 41, 21, 18, 18), new Keys("LMB", (Minecraft.getMinecraft()).gameSettings.keyBindAttack, 1, 41, 28, 18), new Keys("RMB", (Minecraft.getMinecraft()).gameSettings.keyBindUseItem, 31, 41, 28, 18), new Keys(EnumChatFormatting.STRIKETHROUGH + "" + EnumChatFormatting.BOLD + "---", (Minecraft.getMinecraft()).gameSettings.keyBindJump, 1, 61, 58, 12)})),
        
        WASD(Arrays.asList(new Keys[] {new Keys("W", (Minecraft.getMinecraft()).gameSettings.keyBindForward, 21, 1, 18, 18), new Keys("A", (Minecraft.getMinecraft()).gameSettings.keyBindLeft, 1, 21, 18, 18), new Keys("S", (Minecraft.getMinecraft()).gameSettings.keyBindBack, 21, 21, 18, 18), new Keys("D", (Minecraft.getMinecraft()).gameSettings.keyBindRight, 41, 21, 18, 18)})),
        WASD_MOUSE(Arrays.asList(new Keys[] {new Keys("W", (Minecraft.getMinecraft()).gameSettings.keyBindForward, 21, 1, 18, 18), new Keys("A", (Minecraft.getMinecraft()).gameSettings.keyBindLeft, 1, 21, 18, 18), new Keys("S", (Minecraft.getMinecraft()).gameSettings.keyBindBack, 21, 21, 18, 18), new Keys("D", (Minecraft.getMinecraft()).gameSettings.keyBindRight, 41, 21, 18, 18), new Keys("LMB", (Minecraft.getMinecraft()).gameSettings.keyBindAttack, 1, 41, 28, 18), new Keys("RMB", (Minecraft.getMinecraft()).gameSettings.keyBindUseItem, 31, 41, 28, 18)})),
        WASD_JUMP_MOUSE(Arrays.asList(new Keys[] {new Keys("W", (Minecraft.getMinecraft()).gameSettings.keyBindForward, 21, 1, 18, 18), new Keys("A", (Minecraft.getMinecraft()).gameSettings.keyBindLeft, 1, 21, 18, 18), new Keys("S", (Minecraft.getMinecraft()).gameSettings.keyBindBack, 21, 21, 18, 18), new Keys("D", (Minecraft.getMinecraft()).gameSettings.keyBindRight, 41, 21, 18, 18), new Keys("LMB", (Minecraft.getMinecraft()).gameSettings.keyBindAttack, 1, 41, 28, 18), new Keys("RMB", (Minecraft.getMinecraft()).gameSettings.keyBindUseItem, 31, 41, 28, 18), new Keys(EnumChatFormatting.STRIKETHROUGH + "" + EnumChatFormatting.BOLD + "---", (Minecraft.getMinecraft()).gameSettings.keyBindJump, 1, 61, 58, 12)}));
        
        private final List<Keys> keys;
        private int width, height;
        
        KeyMode(List<Keys> keys)
        {
            this.width = 0;
            this.height = 0;
            this.keys = keys;
            
            for (Keys key : keys) 
            {
                this.width = Math.max(this.width, key.getX() + key.getWidth() + 10);
                this.height = Math.max(this.height, key.getY() + key.getHeight() + 10);
            }
        }
        
        public List<Keys> getKeys()
        {
            return this.keys;
        }
        
        public int getWidth()
        {
            return this.width;
        }
        
        public int getHeight()
        {
            return this.height;
        }
    }
    
    public static class Keys
    {
        private final String keyName;
        private final KeyBinding keyBinding;
        private final int x, y, width, height;
        
        public Keys(String keyName, KeyBinding keyBinding, int x, int y, int width, int height)
        {
            this.keyName = keyName;
            this.keyBinding = keyBinding;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
        public String getKeyName()
        {
            return this.keyName;
        }
        
        public boolean getIsKeyPressed()
        {
            return this.keyBinding.getIsKeyPressed();
        }
        
        public int getX()
        {
            return this.x;
        }
        
        public int getY()
        {
            return this.y;
        }
        
        public int getWidth()
        {
            return this.width;
        }
        
        public int getHeight()
        {
            return this.height;
        }
    }
}