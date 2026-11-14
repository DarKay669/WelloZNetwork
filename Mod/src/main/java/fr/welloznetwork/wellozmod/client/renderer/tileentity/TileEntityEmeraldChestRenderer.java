package fr.welloznetwork.wellozmod.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.block.BlockEmeraldChest;
import fr.welloznetwork.wellozmod.tileentity.TileEntityEmeraldChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TileEntityEmeraldChestRenderer extends TileEntitySpecialRenderer
{
	private ModelChest modelChest = new ModelChest();
	
	public void renderTileEntityAt(TileEntityEmeraldChest tileEntityEmeraldChest, double x, double y, double z, float partialTicks)
	{
	    int i;
	    
	    if (!tileEntityEmeraldChest.hasWorldObj())
	    {
	        i = 0;
	    }
	    
	    else
	    {
	        Block block = tileEntityEmeraldChest.getBlockType();
	        i = tileEntityEmeraldChest.getBlockMetadata();
	        
	        if (block instanceof BlockEmeraldChest && i == 0)
	        {
	            try
	            {
	                ((BlockChest) block).func_149954_e(tileEntityEmeraldChest.getWorldObj(), tileEntityEmeraldChest.xCoord, tileEntityEmeraldChest.yCoord, tileEntityEmeraldChest.zCoord);
	            }
	            
	            catch (ClassCastException e)
	            {
	                FMLLog.severe("Attempted to render a chest at %d, %d, %d that was not a chest", tileEntityEmeraldChest.xCoord, tileEntityEmeraldChest.yCoord, tileEntityEmeraldChest.zCoord);
	            }
	            
	            i = tileEntityEmeraldChest.getBlockMetadata();
	        }
	    }
	    
	    ModelChest modelChest;
	    modelChest = this.modelChest;
	    this.bindTexture(new ResourceLocation("wellozmod:textures/models/chests/emerald.png"));
	    
	    GL11.glPushMatrix();
	    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
	    GL11.glScalef(1.0F, -1.0F, -1.0F);
	    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	    short short1 = 0;
	    
	    if (i == 2)
	    {
	        short1 = 180;
	    }
	    
	    if (i == 3)
	    {
	        short1 = 0;
	    }
	    
	    if (i == 4)
	    {
	        short1 = 90;
	    }
	    
	    if (i == 5)
	    {
	        short1 = -90;
	    }
	    
	    GL11.glRotatef((float) short1, 0.0F, 1.0F, 0.0F);
	    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	    
	    float f1 = tileEntityEmeraldChest.prevLidAngle + (tileEntityEmeraldChest.lidAngle - tileEntityEmeraldChest.prevLidAngle) * partialTicks;
	    float f2;
	    
	    f1 = 1.0F - f1;
	    f1 = 1.0F - f1 * f1 * f1;
	    modelChest.chestLid.rotateAngleX = -(f1 * (float) Math.PI / 2.0F);
	    modelChest.renderAll();
	    
	    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	    GL11.glPopMatrix();
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
	{
	    this.renderTileEntityAt((TileEntityEmeraldChest) tileEntity, x, y, z, partialTicks);
	}
}