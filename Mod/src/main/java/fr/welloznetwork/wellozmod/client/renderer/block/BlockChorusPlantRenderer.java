package fr.welloznetwork.wellozmod.client.renderer.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.block.BlockChorusPlant;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class BlockChorusPlantRenderer extends BlockChorusFlowerRenderer
{
	private final Random rand = new Random();
	
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		renderer.renderAllFaces = true;
		
		long seed = x * 3129871L ^ y * 116129781L ^ z;
		seed = seed * seed * 42317861L + seed * 11L;
		this.rand.setSeed(seed);
		
		int noConUp = this.rand.nextInt(5);
		int noConDown = this.rand.nextInt(5);
		int noConWest = this.rand.nextInt(5);
		int noConEast = this.rand.nextInt(5);
		int noConNorth = this.rand.nextInt(5);
		int noConSouth = this.rand.nextInt(5);
		
		Block neighbourUp = world.getBlock(x + ForgeDirection.UP.offsetX, y + ForgeDirection.UP.offsetY, z + ForgeDirection.UP.offsetZ);
		Block neighbourDown = world.getBlock(x + ForgeDirection.DOWN.offsetX, y + ForgeDirection.DOWN.offsetY, z + ForgeDirection.DOWN.offsetZ);
		Block neighbourWest = world.getBlock(x + ForgeDirection.WEST.offsetX, y + ForgeDirection.WEST.offsetY, z + ForgeDirection.WEST.offsetZ);
		Block neighbourEast = world.getBlock(x + ForgeDirection.EAST.offsetX, y + ForgeDirection.EAST.offsetY, z + ForgeDirection.EAST.offsetZ);
		Block neighbourNorth = world.getBlock(x + ForgeDirection.NORTH.offsetX, y + ForgeDirection.NORTH.offsetY, z + ForgeDirection.NORTH.offsetZ);
		Block neighbourSouth = world.getBlock(x + ForgeDirection.SOUTH.offsetX, y + ForgeDirection.SOUTH.offsetY, z + ForgeDirection.SOUTH.offsetZ);
		float conWidth = 0.25F;
		
		if (neighbourUp == RegistryBlocks.chorus_flower || neighbourUp == Blocks.end_stone || neighbourUp == block) 
		{
			renderer.setRenderBounds(conWidth, (1.0F - conWidth), conWidth, (1.0F - conWidth), 1.0D, (1.0F - conWidth));
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConUp == 2 || noConUp == 3) 
		{
			renderer.setRenderBounds(conWidth, (1.0F - conWidth), conWidth, (1.0F - conWidth), 0.8125D, (1.0F - conWidth));
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConUp == 4)
		{
			renderer.setRenderBounds(0.3125D, (1.0F - conWidth), 0.3125D, 0.6875D, 0.875D, 0.6875D);
			renderer.renderStandardBlock(block, x, y, z);
		} 
		
		if (neighbourDown == RegistryBlocks.chorus_flower || neighbourDown == Blocks.end_stone || neighbourDown == block)
		{
			renderer.setRenderBounds(conWidth, 0.0D, conWidth, (1.0F - conWidth), conWidth, (1.0F - conWidth));
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConDown == 2 || noConDown == 3)
		{
			renderer.setRenderBounds(conWidth, 0.1875D, conWidth, (1.0F - conWidth), conWidth, (1.0F - conWidth));
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConDown == 4)
		{
			renderer.setRenderBounds(0.3125D, 0.125D, 0.3125D, 0.6875D, conWidth, 0.6875D);
			renderer.renderStandardBlock(block, x, y, z);
		} 
		
		if (neighbourWest == RegistryBlocks.chorus_flower || neighbourWest == Blocks.end_stone || neighbourWest == block) 
		{
			renderer.setRenderBounds(0.0D, conWidth, conWidth, conWidth, (1.0F - conWidth), (1.0F - conWidth));
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConWest == 2 || noConWest == 3) 
		{
			renderer.setRenderBounds(0.1875D, conWidth, conWidth, conWidth, (1.0F - conWidth), (1.0F - conWidth));
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConWest == 4) 
		{
			renderer.setRenderBounds(0.125D, 0.3125D, 0.3125D, conWidth, 0.6875D, 0.6875D);
			renderer.renderStandardBlock(block, x, y, z);
		} 
		
		if (neighbourEast == RegistryBlocks.chorus_flower || neighbourEast == Blocks.end_stone || neighbourEast == block)
		{
			renderer.setRenderBounds((1.0F - conWidth), conWidth, conWidth, 1.0D, (1.0F - conWidth), (1.0F - conWidth));
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConEast == 2 || noConEast == 3)
		{
			renderer.setRenderBounds((1.0F - conWidth), conWidth, conWidth, 0.8125D, (1.0F - conWidth), (1.0F - conWidth));
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConEast == 4) 
		{
			renderer.setRenderBounds((1.0F - conWidth), 0.3125D, 0.3125D, 0.875D, 0.6875D, 0.6875D);
			renderer.renderStandardBlock(block, x, y, z);
		} 
		
		if (neighbourNorth == RegistryBlocks.chorus_flower || neighbourNorth == Blocks.end_stone || neighbourNorth == block) 
		{
			renderer.setRenderBounds(conWidth, conWidth, 0.0D, (1.0F - conWidth), (1.0F - conWidth), conWidth);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConNorth == 2 || noConNorth == 3) 
		{
			renderer.setRenderBounds(conWidth, conWidth, 0.1875D, (1.0F - conWidth), (1.0F - conWidth), conWidth);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConNorth == 4)
		{
			renderer.setRenderBounds(0.3125D, 0.3125D, 0.125D, 0.6875D, 0.6875D, conWidth);
			renderer.renderStandardBlock(block, x, y, z);
		} 
		
		if (neighbourSouth == RegistryBlocks.chorus_flower || neighbourSouth == Blocks.end_stone || neighbourSouth == block) 
		{
			renderer.setRenderBounds(conWidth, conWidth, (1.0F - conWidth), (1.0F - conWidth), (1.0F - conWidth), 1.0D);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConSouth == 2 || noConSouth == 3)
		{
			renderer.setRenderBounds(conWidth, conWidth, (1.0F - conWidth), (1.0F - conWidth), (1.0F - conWidth), 0.8125D);
			renderer.renderStandardBlock(block, x, y, z);
		} else if (noConSouth == 4)
		{
			renderer.setRenderBounds(0.3125D, 0.3125D, (1.0F - conWidth), 0.6875D, 0.6875D, 0.875D);
			renderer.renderStandardBlock(block, x, y, z);
		} 
		
		renderer.setRenderBounds(conWidth, conWidth, conWidth, (1.0F - conWidth), (1.0F - conWidth), (1.0F - conWidth));
		renderer.renderStandardBlock(block, x, y, z);
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }
	
	public int getRenderId()
	{
		return BlockChorusPlant.chorusPlantModel;
	}
}