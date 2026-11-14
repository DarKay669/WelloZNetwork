package fr.welloznetwork.wellozmod.client.renderer.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.block.BlockBarrel;
import fr.welloznetwork.wellozmod.client.registry.CustomISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockBarrelRenderer extends CustomISimpleBlockRenderingHandler
{
    protected void renderStandardInventoryCube(Block block, int metadata, int modelId, RenderBlocks renderBlocks, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        super.renderStandardInventoryCube(block, 1, modelId, renderBlocks, minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks)
    {
        int l = BlockPistonBase.getPistonOrientation(renderBlocks.blockAccess.getBlockMetadata(x, y, z));
        
        switch (l)
        {
            case 0:
                renderBlocks.uvRotateEast = 3;
                renderBlocks.uvRotateWest = 3;
                renderBlocks.uvRotateSouth = 3;
                renderBlocks.uvRotateNorth = 3;
                break;
                
            case 1:
                break;
                
            case 2:
                renderBlocks.uvRotateSouth = 1;
                renderBlocks.uvRotateNorth = 2;
                renderBlocks.uvRotateEast = 3;
                break;
                
            case 3:
                renderBlocks.uvRotateSouth = 2;
                renderBlocks.uvRotateNorth = 1;
                renderBlocks.uvRotateWest = 3;
                renderBlocks.uvRotateTop = 3;
                renderBlocks.uvRotateBottom = 3;
                break;
                
            case 4:
                renderBlocks.uvRotateEast = 1;
                renderBlocks.uvRotateWest = 2;
                renderBlocks.uvRotateNorth = 3;
                renderBlocks.uvRotateTop = 2;
                renderBlocks.uvRotateBottom = 1;
                break;
                
            case 5:
                renderBlocks.uvRotateEast = 2;
                renderBlocks.uvRotateWest = 1;
                renderBlocks.uvRotateSouth = 3;
                renderBlocks.uvRotateTop = 1;
                renderBlocks.uvRotateBottom = 2;
                break;
        }
        
        boolean flag = renderBlocks.renderStandardBlock(block, x, y, z);
        
        renderBlocks.uvRotateEast = 0;
        renderBlocks.uvRotateWest = 0;
        renderBlocks.uvRotateSouth = 0;
        renderBlocks.uvRotateNorth = 0;
        renderBlocks.uvRotateTop = 0;
        renderBlocks.uvRotateBottom = 0;
        
        return flag;
    }
    
    public int getRenderId()
    {
        return BlockBarrel.barrelModel;
    }
}