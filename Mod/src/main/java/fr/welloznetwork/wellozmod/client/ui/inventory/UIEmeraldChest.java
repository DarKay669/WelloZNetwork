package fr.welloznetwork.wellozmod.client.ui.inventory;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.inventory.ContainerEmeraldChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UIEmeraldChest extends GuiContainer
{
    private IInventory iinventory;
    
    public UIEmeraldChest(InventoryPlayer inventoryPlayer, IInventory iinventory)
    {
        super(new ContainerEmeraldChest(inventoryPlayer, iinventory));
        
        this.iinventory = iinventory;
        this.xSize = 238;
        this.ySize = 256;
    }
    
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation("wellozmod:textures/gui/containers/emerald_chest.png"));
        int x1 = (this.width - this.xSize) / 2;
        int y1 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x1, y1, 0, 0, this.xSize, this.ySize);
    }
}