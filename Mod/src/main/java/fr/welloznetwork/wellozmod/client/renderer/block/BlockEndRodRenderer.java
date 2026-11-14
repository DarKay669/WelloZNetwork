package fr.welloznetwork.wellozmod.client.renderer.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.block.BlockEndRod;
import fr.welloznetwork.wellozmod.client.renderer.tileentity.TileEntityEndRodRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockEndRodRenderer implements ISimpleBlockRenderingHandler
{
    public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
    {
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        TileEntityEndRodRenderer.renderRod(renderer, block, meta);
    }
    
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        return true;
    }
    
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }
    
    public int getRenderId()
    {
        return BlockEndRod.endRodModel;
    }
}