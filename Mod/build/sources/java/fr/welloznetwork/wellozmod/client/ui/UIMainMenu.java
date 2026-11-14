package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;
import java.io.IOException;
import java.util.Calendar;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.dev.Debug;
import fr.welloznetwork.venus.lib.render.GLSLSandboxShader;
import fr.welloznetwork.venus.lib.time.TimeUtils;
import fr.welloznetwork.wellozmod.client.audio.CustomMusicTicker;
import fr.welloznetwork.wellozmod.client.audio.CustomMusicTicker.MusicType;
import fr.welloznetwork.wellozmod.util.ModUtil.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UIMainMenu extends GuiScreen
{
    private long initTime = System.currentTimeMillis();
    private GLSLSandboxShader backgroundShader;
    private int timer;
    private boolean isKeyDown = false, cancelled;
    private CustomMusicTicker customMusicTicker;
    private CustomFontRenderer pixelNES, minecraftDungeonsRegular;
    private String copyrightWidth = "WELLOZNETWORK - " + TimeUtils.nowZoned().getYear() + I18n.format("gui.main.copyright");
    
    public UIMainMenu()
    {
        try
        {
            this.backgroundShader = new GLSLSandboxShader("/assets/shaders/noise.fsh");
        }
        
        catch (IOException e)
        {
            throw new IllegalStateException("Failed to load background shader" + e);
        }
        
        this.pixelNES = CustomFontRenderer.initTTF("Pixel-NES.otf", 41);
        this.minecraftDungeonsRegular = CustomFontRenderer.initTTF("Minecraft-Dungeons-Regular.ttf", 40);
        FMLClientHandler.instance().setupServerList();
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        this.backgroundShader.useShader(this.width, this.height, mouseX, mouseY, (System.currentTimeMillis() - this.initTime) / 1000.0F);
        
        GL11.glBegin(GL11.GL_QUADS);
        
        GL11.glVertex2f(-1.0F, -1.0F);
        GL11.glVertex2f(-1.0F, 1.0F);
        GL11.glVertex2f(1.0F, 1.0F);
        GL11.glVertex2f(1.0F, -1.0F);
        
        GL11.glEnd();
        
        GL20.glUseProgram(0);
        
        this.drawGradientRect(0, 0, this.width, this.height, 0x88000000, 0x88000000);
        
        this.mc.getTextureManager().bindTexture(new ResourceLocation("wellozmod:textures/gui/menu/title/logo.png"));
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        int textureWidth = 1280, textureHeight = 400;
        
        GL11.glPushMatrix();
        GL11.glTranslatef((this.width - textureWidth * 0.2F) / 2.0F - 10.0F, this.height / 2.0F - textureHeight * 0.2F - 30.0F, 0.0F);
        GL11.glScalef(0.2F, 0.2F, 0.2F);
        
        RenderUtils.drawSizedTexture(0, 0, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2.0F, this.height / 2.0F + 15.0F, 0.0F);
        GL11.glScalef(16.0F / 20.0F, 16.0F / 20.0F, 16.0F / 20.0F);
        
        if (!this.isKeyDown)
        {
            this.minecraftDungeonsRegular.drawCenteredString(I18n.format("gui.main.pressKey"), 0, 0, ((int) ((0.1F + (float) (0.5F + 0.5F * Math.sin(this.timer / 6.625F))) * 0.9F * 255.0F) << 24) | (255 << 16) | (255 << 8) | 255);
        }
        
        else
        {
            this.minecraftDungeonsRegular.drawCenteredString(I18n.format("gui.main.connecting") + new String(new char[(this.timer / 10) % 4]).replace('\0', '.'), 0, 0, Color.WHITE.getRGB());
        }
        
        GL11.glPopMatrix();
        
        float scale = 8.0F / 24.0F;
        int rawTextWidth = this.pixelNES.getStringWidth(this.copyrightWidth), rawTextHeight = this.pixelNES.getFontHeight(), textWidth = (int) (rawTextWidth * scale), textHeight = (int) (rawTextHeight * scale), xStart = this.width / 2 - textWidth / 2, yStart = this.height - 30, xEnd = xStart + textWidth, yEnd = yStart + textHeight;
        
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2.0F, this.height - 30.0F, 0.0F);
        GL11.glScalef(scale, scale, scale);
        
        this.pixelNES.drawCenteredString(this.copyrightWidth, 0, 10, Color.WHITE.getRGB());
        
        if (mouseX >= xStart && mouseX <= xEnd && mouseY >= yStart && mouseY <= yEnd)
        {
            this.drawHorizontalLine(-rawTextWidth / 2, rawTextWidth / 2, this.pixelNES.getFontHeight() + 10, Color.WHITE.getRGB());
        }
        
        this.pixelNES.drawCenteredString("COPYRIGHT MOJANG AB. DO NOT DISTRIBUTE! THANKS TO FORGE FOR THE LOADER", 0 / scale, 15 / scale, Color.WHITE.getRGB());
        
        GL11.glPopMatrix();
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void keyTyped(char typedChar, int keyCode) 
    {
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_TAB || keyCode == Keyboard.KEY_LCONTROL || keyCode == Keyboard.KEY_LSHIFT || keyCode == Keyboard.KEY_RSHIFT || keyCode == Keyboard.KEY_LMENU || keyCode == Keyboard.KEY_CAPITAL || keyCode == Keyboard.KEY_F1 || keyCode == Keyboard.KEY_F2 || keyCode == Keyboard.KEY_F3 || keyCode == Keyboard.KEY_F4 || keyCode == Keyboard.KEY_F5 || keyCode == Keyboard.KEY_F6 || keyCode == Keyboard.KEY_F7 || keyCode == Keyboard.KEY_F8 || keyCode == Keyboard.KEY_F9 || keyCode == Keyboard.KEY_F10 || keyCode == Keyboard.KEY_F11 || keyCode == Keyboard.KEY_F12 || keyCode == Keyboard.KEY_F13 || keyCode == Keyboard.KEY_F14 || keyCode == Keyboard.KEY_F15 || keyCode == Keyboard.KEY_F16 || keyCode == Keyboard.KEY_F17 || keyCode == Keyboard.KEY_F18 || keyCode == Keyboard.KEY_F19 || keyCode == Keyboard.KEY_NUMLOCK || keyCode == Keyboard.KEY_SYSRQ || keyCode == Keyboard.KEY_RMENU || keyCode == Keyboard.KEY_FUNCTION || keyCode == Keyboard.KEY_CLEAR || keyCode == Keyboard.KEY_LMETA || keyCode == Keyboard.KEY_RMETA || keyCode == Keyboard.KEY_APPS || keyCode == Keyboard.KEY_POWER || keyCode == Keyboard.KEY_SLEEP)
        {
            return;
        }
        
        else
        {
            this.connect();
        }
        
        super.keyTyped(typedChar, keyCode);
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            float scale = 8.0F / 24.0F;
            
            int x = this.width / 2 - (int) (this.pixelNES.getStringWidth(this.copyrightWidth) * scale) / 2;
            int y = this.height - 30;
            int x1 = x + (int) (this.pixelNES.getStringWidth(this.copyrightWidth) * scale);
            int y1 = y + (int) (this.pixelNES.getFontHeight() * scale);
            
            if (!this.isKeyDown && mouseX >= x && mouseX <= x1 && mouseY >= y && mouseY <= y1)
            {
                this.mc.displayGuiScreen(new UICredits());
                return;
            }
            
            else
            {
                this.connect();
            }
        }
        
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void initGui()
    {
        this.initTime = System.currentTimeMillis();
        
        if (this.customMusicTicker == null)
        {
            this.customMusicTicker = new CustomMusicTicker(this.mc);
        }
        
        if (UIOptions.newFont)
        {
            this.customMusicTicker.playMenuMusic(MusicType.TITLE_THEME);
        }
        
        else if (Calendar.getInstance().get(Calendar.MONTH) + 1 == 10 && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1 == 31)
        {
            this.customMusicTicker.playMenuMusic(MusicType.HALLOWEEN);
        }
        
        else if (Calendar.getInstance().get(Calendar.MONTH) + 1 == 12)
        {
            this.customMusicTicker.playMenuMusic(MusicType.CHRISTMAS);
        }
        
        super.initGui();
    }
    
    public void updateScreen()
    {
        this.timer++;
        super.updateScreen();
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    private void connect()
    {
        if (Debug.isDebugMode())
        {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        
        else
        {
            this.isKeyDown = true;
            this.customMusicTicker.stopMenuMusic();
            
            if (UIOptions.newFont)
            {
                this.customMusicTicker.playMenuMusic(MusicType.SERVER_LOADING);
            }
            
            FMLClientHandler.instance().connectToServer(this, new ServerData("WelloZNetwork", "mc.welloznetwork.fr"));
        }
    }
}