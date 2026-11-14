package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.dev.Debug;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.init.RegistryCapes;
import fr.welloznetwork.wellozmod.init.RegistryEmotes;
import fr.welloznetwork.wellozmod.network.play.server.S05PacketCape;
import fr.welloznetwork.wellozmod.network.play.server.S08PacketEmote;
import fr.welloznetwork.wellozmod.util.ModUtil.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UICosmetics extends GuiScreen
{
    private List<CosmeticButton> emotesButton = new ArrayList<>(), capesButton = new ArrayList<>();
    private List<String> emotes = new ArrayList<>(), capes = new ArrayList<>();
    private GuiScreen parentScreen;
    private int selectedTab = 0, removeCapeX, removeCapeY, removeCapeWidth, removeCapeHeight;
    
    public UICosmetics(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
    }
    
    public void initGui()
    {        
        this.buttonList.clear();
        
        this.buttonList.add(new GuiButton(0, this.width / 2 - 141, 20, 140, 20, "Emote"));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, 20, 140, 20, "Cape"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height - 30, 200, 20, I18n.format("gui.done")));
        
        this.emotesButton.clear();
        this.capesButton.clear();
        
        this.emotes.clear();
        this.emotes.addAll(RegistryEmotes.EMOTE.keySet());
        
        this.capes.clear();
        this.capes.addAll(RegistryCapes.CAPE.keySet());
        
        for (int i = 0; i < this.emotes.size(); i++)
        {
            this.emotesButton.add(new CosmeticButton(i, this.width / 2 - 140 + (i % 8) * 35, 60 + (i / 8) * 40, 32, 32, I18n.format("cosmetics.emote." + this.emotes.get(i)), this.emotes.get(i), new ResourceLocation(this.emotes.get(i))));
        }
        
        for (int i = 0; i < this.capes.size(); i++)
        {
            this.capesButton.add(new CosmeticButton(i, this.width / 2 - 140 + (i % 8) * 35, 60 + (i / 8) * 47, 32, 40, I18n.format("cosmetics.capes." + this.capes.get(i)), this.capes.get(i), new ResourceLocation(this.capes.get(i))));
        }
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        List<CosmeticButton> buttons = (this.selectedTab == 0) ? this.emotesButton : this.capesButton;
        
        CosmeticButton hovered = null;
        
        for (CosmeticButton button : buttons)
        {
            button.draw(mouseX, mouseY);
            
            if (button.isHovered(mouseX, mouseY))
            {
                hovered = button;
            }
        }
        
        if (hovered != null)
        {
            this.drawHoveringText(Arrays.asList(hovered.label), mouseX, mouseY, this.fontRendererObj);
        }
        
        if (this.selectedTab == 1)
        {
            this.removeCapeWidth = this.fontRendererObj.getStringWidth(I18n.format("gui.cosmetics.removeCape"));
            this.removeCapeHeight = this.fontRendererObj.FONT_HEIGHT;
            this.removeCapeX = this.width / 2 - this.removeCapeWidth / 2;
            this.removeCapeY = this.height - 60;
            
            this.fontRendererObj.drawStringWithShadow(I18n.format("gui.cosmetics.removeCape"), this.removeCapeX, this.removeCapeY, Color.WHITE.getRGB());
            
            if (mouseX >= this.removeCapeX && mouseX <= this.removeCapeX + this.removeCapeWidth && mouseY >= this.removeCapeY && mouseY <= this.removeCapeY + this.removeCapeHeight)
            {
                Gui.drawRect(this.removeCapeX, this.removeCapeY + this.removeCapeHeight, this.removeCapeX + this.removeCapeWidth, this.removeCapeY + this.removeCapeHeight + 1, -1);
            }
        }
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton.id == 0 || guiButton.id == 1)
        {
            this.selectedTab = guiButton.id;
        }
        
        else if (guiButton.id == 2)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        
        List<CosmeticButton> buttons = (this.selectedTab == 0) ? this.emotesButton : this.capesButton;
        
        for (CosmeticButton button : buttons)
        {
            if (button.isHovered(mouseX, mouseY))
            {
                if (!CommandSpectator.isSpectator())
                {
                    if (this.selectedTab == 0)
                    {
                        if (button.id >= 0 && button.id < this.emotes.size())
                        {
                            WelloZMod.proxy.getNetworkManager().sendToServer(new S08PacketEmote(this.emotes.get(button.id)));
                            this.mc.displayGuiScreen(null);
                        }
                    }
                    
                    else if (this.selectedTab == 1)
                    {
                        if (button.id >= 0 && button.id < this.capes.size()) 
                        {                            
                            if (Debug.isDebugMode() && this.mc.isIntegratedServerRunning())
                            {
                                RegistryCapes.setCape(this.mc.thePlayer.getCommandSenderName(), RegistryCapes.setCape(this.capes.get(button.id)));
                            }
                            
                            WelloZMod.proxy.getNetworkManager().sendToServer(new S05PacketCape(this.mc.thePlayer.getCommandSenderName(), this.capes.get(button.id)));
                            this.mc.displayGuiScreen(null);
                        }
                    }
                    
                    break;
                }
            }
        }
        
        if (this.selectedTab == 1)
        {
            if (Debug.isDebugMode() && this.mc.isIntegratedServerRunning())
            {
                RegistryCapes.setCape(this.mc.thePlayer.getCommandSenderName(), null);
            }
            
            if (mouseX >= this.removeCapeX && mouseX <= this.removeCapeX + this.removeCapeWidth && mouseY >= this.removeCapeY && mouseY <= this.removeCapeY + this.removeCapeHeight)
            {
                WelloZMod.proxy.getNetworkManager().sendToServer(new S05PacketCape(this.mc.thePlayer.getCommandSenderName(), null));
                this.mc.displayGuiScreen(null);
            }
        }
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    private class CosmeticButton
    {
        private int id, x, y, width, height;
        private String label, cosmeticId;
        private ResourceLocation cosmeticIcon;
        
        public CosmeticButton(int id, int x, int y, int width, int height, String label, String cosmeticId, ResourceLocation resourceLocation)
        {
            this.id = id;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.label = label;
            this.cosmeticId = cosmeticId;
            this.cosmeticIcon = new ResourceLocation("wellozmod:textures/gui/cosmetics/preview/" + this.cosmeticId + ".png");
        }
        
        private void draw(int mouseX, int mouseY)
        {
            Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.isHovered(mouseX, mouseY) ? 0xFFAAAAAA : 0xFF666666);
            
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.cosmeticIcon);
            
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            
            RenderUtils.drawSizedTexture(this.x, this.y, 0, 0, this.width, this.height, this.width, this.height);
        }
        
        private boolean isHovered(int mouseX, int mouseY)
        {
            return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
        }
    }
}