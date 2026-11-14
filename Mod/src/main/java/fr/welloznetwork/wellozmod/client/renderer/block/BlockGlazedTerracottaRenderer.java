package fr.welloznetwork.wellozmod.client.renderer.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.block.BlockGlazedTerracotta;
import fr.welloznetwork.wellozmod.client.registry.CustomISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockGlazedTerracottaRenderer extends CustomISimpleBlockRenderingHandler
{
    protected void renderStandardInventoryCube(Block block, int metadata, int modelId, RenderBlocks renderBlocks, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        renderBlocks.uvRotateTop = 1;
        renderBlocks.uvRotateNorth = 2;
        renderBlocks.uvRotateSouth = 1;
        renderBlocks.uvRotateWest = 3;
        renderBlocks.uvRotateBottom = 1;
        
        super.renderStandardInventoryCube(block, metadata, modelId, renderBlocks, minX, minY, minZ, maxX, maxY, maxZ);
        
        renderBlocks.uvRotateTop = 0;
        renderBlocks.uvRotateNorth = 0;
        renderBlocks.uvRotateSouth = 0;
        renderBlocks.uvRotateWest = 0;
        renderBlocks.uvRotateBottom = 0;
    }
    
    public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks)
    {
        renderBlocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        int orientation = iBlockAccess.getBlockMetadata(x, y, z);
        
        renderBlocks.uvRotateTop = orientation > 1 ? 5 - orientation : orientation;
        renderBlocks.uvRotateNorth = orientation > 0 ? (orientation % 3) + 1 : 0;
        renderBlocks.uvRotateEast = orientation < 2 ? 1 - orientation : orientation;
        int uvRotateSouthAndBottom = orientation > 1 ? 2 * orientation - 4 : 3 - 2 * orientation;
        renderBlocks.uvRotateSouth = uvRotateSouthAndBottom;
        renderBlocks.uvRotateWest = orientation > 1 ? 3 - orientation : orientation + 2;
        renderBlocks.uvRotateBottom = uvRotateSouthAndBottom;
        
        boolean flag = renderBlocks.renderStandardBlock(block, x, y, z);
        
        renderBlocks.uvRotateTop = 0;
        renderBlocks.uvRotateNorth = 0;
        renderBlocks.uvRotateEast = 0;
        renderBlocks.uvRotateSouth = 0;
        renderBlocks.uvRotateWest = 0;
        renderBlocks.uvRotateBottom = 0;
        
        return flag;
    }
    
    public int getRenderId()
    {
        return BlockGlazedTerracotta.terracottaModel;
    }
}