package fr.welloznetwork.wellozmod.client.ui.inventory;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.inventory.ContainerEmpty;
import fr.welloznetwork.wellozmod.network.play.server.S00PacketOnlineDetector;
import fr.welloznetwork.wellozmod.tileentity.TileEntityOnlineDetector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UIOnlineDetector extends GuiContainer
{
    private GuiTextField usernameInput;
    private TileEntityOnlineDetector tileEntityOnlineDetector;
    private FontRenderer fontRendererObj;
    
    public UIOnlineDetector(TileEntityOnlineDetector tileEntityOnlineDetector)
    {
        super((Container) new ContainerEmpty());
        this.tileEntityOnlineDetector = tileEntityOnlineDetector;
        this.fontRendererObj = (Minecraft.getMinecraft()).fontRenderer;
        this.xSize = 161;
        this.ySize = 70;
    }
    
    public void initGui()
    {
        super.initGui();
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.usernameInput = new GuiTextField(this.fontRendererObj, x + 5, y + 20, 150, 15);
        this.usernameInput.setFocused(false);
        this.usernameInput.setCanLoseFocus(true);
        this.usernameInput.setText(this.tileEntityOnlineDetector.getPlayerName());
    }
    
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
    
    protected void mouseClicked(int clicked, int x, int y)
    {
        super.mouseClicked(clicked, x, y);
        this.usernameInput.mouseClicked(clicked, x, y);
    }
    
    protected void keyTyped(char typedChar, int pressedKey)
    {
        if (pressedKey == 1 || (!this.usernameInput.isFocused() && pressedKey == this.mc.gameSettings.keyBindInventory.getKeyCode()))
        {
            S00PacketOnlineDetector packet = new S00PacketOnlineDetector();
            packet.addInformations(this.usernameInput.getText(), this.tileEntityOnlineDetector);
            WelloZMod.proxy.getNetworkManager().sendToServer(packet);
            this.mc.thePlayer.closeScreen();
        }
        
        this.usernameInput.textboxKeyTyped(typedChar, pressedKey);
    }
    
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y)
    {
        this.mc.renderEngine.bindTexture(new ResourceLocation("wellozmod:textures/gui/containers/online_detector.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x1 = (this.width - this.xSize) / 2;
        int y1 = (this.height - this.ySize) / 2;
        drawTexturedModalRect(x1, y1, 0, 0, this.xSize, this.ySize);
        this.fontRendererObj.drawStringWithShadow(I18n.format("gui.onlineDetector") + this.tileEntityOnlineDetector.getPlayerName(), x1 + this.xSize / 2 - this.fontRendererObj.getStringWidth(I18n.format("gui.onlineDetector") + this.tileEntityOnlineDetector.getPlayerName()) / 2, y1 + 44, Color.white.getRGB());
        this.usernameInput.drawTextBox();
    }
    
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        this.fontRendererObj.drawString(I18n.format("tile.online_detector.name"), 8, 6, 4210752);
    }
    
    public void updateScreen()
    {
        super.updateScreen();
        this.usernameInput.updateCursorCounter();
    }
}