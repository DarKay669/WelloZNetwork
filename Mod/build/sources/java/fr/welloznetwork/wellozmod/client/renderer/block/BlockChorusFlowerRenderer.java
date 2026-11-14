package fr.welloznetwork.wellozmod.client.renderer.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.block.BlockChorusFlower;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockChorusFlowerRenderer implements ISimpleBlockRenderingHandler
{
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks r)
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		r.setRenderBounds(0.125D, 0.875D, 0.125D, 0.875D, 1.0D, 0.875D);
		renderCube(r, block, meta);
		r.setRenderBounds(0.0D, 0.125D, 0.125D, 0.125D, 0.875D, 0.875D);
		renderCube(r, block, meta);
		r.setRenderBounds(0.125D, 0.125D, 0.0D, 0.875D, 0.875D, 0.125D);
		renderCube(r, block, meta);
		r.setRenderBounds(0.125D, 0.125D, 0.875D, 0.875D, 0.875D, 1.0D);
		renderCube(r, block, meta);
		r.setRenderBounds(0.875D, 0.125D, 0.125D, 1.0D, 0.875D, 0.875D);
		renderCube(r, block, meta);
		r.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D);
		renderCube(r, block, meta);
	}
	
	private void renderCube(RenderBlocks r, Block block, int meta)
	{
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setNormal(0.0F, -1.0F, 0.0F);
		r.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, r.getBlockIconFromSideAndMetadata(block, 0, meta));
		t.setNormal(0.0F, 1.0F, 0.0F);
		r.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, r.getBlockIconFromSideAndMetadata(block, 1, meta));
		t.setNormal(0.0F, 0.0F, -1.0F);
		r.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, r.getBlockIconFromSideAndMetadata(block, 2, meta));
		t.setNormal(0.0F, 0.0F, 1.0F);
		r.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, r.getBlockIconFromSideAndMetadata(block, 3, meta));
		t.setNormal(-1.0F, 0.0F, 0.0F);
		r.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, r.getBlockIconFromSideAndMetadata(block, 4, meta));
		t.setNormal(1.0F, 0.0F, 0.0F);
		r.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, r.getBlockIconFromSideAndMetadata(block, 5, meta));
		t.draw();
	}
	
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks r)
	{
		r.renderAllFaces = true;
		
		r.setRenderBounds(0.125D, 0.875D, 0.125D, 0.875D, 1.0D, 0.875D);
		r.renderStandardBlock(block, x, y, z);
		
		r.setRenderBounds(0.0D, 0.125D, 0.125D, 0.125D, 0.875D, 0.875D);
		r.renderStandardBlock(block, x, y, z);
		
		r.setRenderBounds(0.125D, 0.125D, 0.0D, 0.875D, 0.875D, 0.125D);
		r.renderStandardBlock(block, x, y, z);
		
		r.setRenderBounds(0.125D, 0.125D, 0.875D, 0.875D, 0.875D, 1.0D);
		r.renderStandardBlock(block, x, y, z);
		
		r.setRenderBounds(0.875D, 0.125D, 0.125D, 1.0D, 0.875D, 0.875D);
		r.renderStandardBlock(block, x, y, z);
		
		r.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D);
		r.renderStandardBlock(block, x, y, z);
		
		r.renderAllFaces = false;
		return true;
	}
	
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}
	
	public int getRenderId()
	{
		return BlockChorusFlower.chorusFlowerModel;
	}
}