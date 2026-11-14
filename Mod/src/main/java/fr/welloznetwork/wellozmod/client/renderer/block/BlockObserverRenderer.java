package fr.welloznetwork.wellozmod.client.renderer.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.block.BlockObserver;
import fr.welloznetwork.wellozmod.client.registry.CustomISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockObserverRenderer extends CustomISimpleBlockRenderingHandler
{
    protected void renderStandardInventoryCube(Block block, int metadata, int modelId, RenderBlocks renderBlocks, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        this.setRotation(5, renderBlocks);
        super.renderStandardInventoryCube(block, 5, modelId, renderBlocks, minX, minY, minZ, maxX, maxY, maxZ);
        this.resetRotation(renderBlocks);
    }
    
    public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks)
    {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        this.setRotation(metadata, renderBlocks);
        boolean flag = renderBlocks.renderStandardBlock(block, x, y, z);
        this.resetRotation(renderBlocks);
        return flag;
    }
    
    private void setRotation(int metadata, RenderBlocks renderBlocks)
    {
        int orientation = BlockPistonBase.getPistonOrientation(metadata);
        
        switch (orientation)
        {
            case 2:
                renderBlocks.uvRotateTop = 3;
                renderBlocks.uvRotateBottom = 3;
                break;
                
            case 5:
                renderBlocks.uvRotateTop = 2;
                renderBlocks.uvRotateBottom = 1;
                break;
                
            case 4:
                renderBlocks.uvRotateTop = 1;
                renderBlocks.uvRotateBottom = 2;
                break;
                
            case 1:
                renderBlocks.uvRotateNorth = 1;
                renderBlocks.uvRotateSouth = 2;
                renderBlocks.uvRotateWest = 3;
                renderBlocks.uvRotateEast = 3;
                break;
                
            case 0:
                renderBlocks.uvRotateNorth = 1;
                renderBlocks.uvRotateSouth = 2;
                break;
        }
    }
    
    private void resetRotation(RenderBlocks renderBlocks)
    {
        renderBlocks.flipTexture = false;
        renderBlocks.uvRotateNorth = 0;
        renderBlocks.uvRotateSouth = 0;
        renderBlocks.uvRotateWest = 0;
        renderBlocks.uvRotateEast = 0;
        renderBlocks.uvRotateTop = 0;
        renderBlocks.uvRotateBottom = 0;
    }
    
    public int getRenderId()
    {
        return BlockObserver.observerModel;
    }
}