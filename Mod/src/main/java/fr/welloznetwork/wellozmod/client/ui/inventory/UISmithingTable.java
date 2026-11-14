package fr.welloznetwork.wellozmod.client.ui.inventory;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.inventory.ContainerSmithingTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UISmithingTable extends GuiContainer
{
    public final ContainerSmithingTable containerSmithingTable;
    
    public UISmithingTable(ContainerSmithingTable containerSmithingTable)
    {
        super(containerSmithingTable);
        this.inventorySlots = containerSmithingTable;
        this.containerSmithingTable = containerSmithingTable;
        this.xSize = 176;
        this.ySize = 166;
    }
    
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("wellozmod:textures/gui/containers/smithing_table.png"));
        final int offsetX = (this.width - this.xSize) / 2;
        final int offsetY = (this.height - this.ySize) / 2;
        drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        
        if (this.containerSmithingTable.unable())
        {
            drawTexturedModalRect(offsetX + 99, offsetY + 45, this.xSize, 0, 28, 21);
        }
    }
    
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(I18n.format("gui.smithing.upgrade"), 60, 18, 0x000000);
    }
}