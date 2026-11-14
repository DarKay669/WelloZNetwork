package fr.welloznetwork.wellozmod.client.renderer.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.tileentity.TileEntityEmeraldChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public class ItemEmeraldChestRenderer implements IItemRenderer
{
	private ModelChest modelChest = new ModelChest();
	
	public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType)
	{
		return true;
	}
	
	public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper)
	{
		return true;
	}
	
	public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... data)
	{
	    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPushMatrix();
		GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
		
		if (itemRenderType == ItemRenderType.INVENTORY)
		{
		    GL11.glTranslatef(0.0F, -0.1F, 0.0F);
		}
		
		if (itemRenderType != ItemRenderType.ENTITY)
		{
		    GL11.glTranslatef(-1.0F, 0.0F, 0.0F);
		}
		
		else
		{
		    GL11.glTranslatef(-0.5F, -0.45F, -0.5F);
		}
		
        TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityEmeraldChest(), 0.0D, 0.0D, 0.0D, 0.0F);
		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}
}