package fr.welloznetwork.wellozmod.client.renderer.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.block.BlockComposter;
import fr.welloznetwork.wellozmod.client.registry.CustomISimpleBlockRenderingHandler;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockComposterRenderer extends CustomISimpleBlockRenderingHandler
{
    protected void renderStandardInventoryCube(Block block, int metadata, int modelId, RenderBlocks renderBlocks, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        renderBlocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        
        TESSELLATOR.setNormal(0.0F, -1.0F, 0.0F);
        renderBlocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 0, metadata));
        TESSELLATOR.setNormal(0.0F, 1.0F, 0.0F);
        renderBlocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 1, metadata));
        TESSELLATOR.setNormal(0.0F, 0.0F, -1.0F);
        renderBlocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 2, metadata));
        TESSELLATOR.setNormal(0.0F, 0.0F, 1.0F);
        renderBlocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 3, metadata));
        TESSELLATOR.setNormal(-1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 4, metadata));
        TESSELLATOR.setNormal(1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 5, metadata));

        renderBlocks.setRenderBounds(0.875D, 0.125D, 0.125D, 0.125D, 1, 0.875D);
        TESSELLATOR.setNormal(0.0F, 0.0F, -1.0F);
        renderBlocks.renderFaceZNeg(block, 0, 0, 0, renderBlocks.getBlockIconFromSideAndMetadata(block, 2, metadata));
        TESSELLATOR.setNormal(0.0F, 0.0F, 1.0F);
        renderBlocks.renderFaceXPos(block, 0, 0, 0, renderBlocks.getBlockIconFromSideAndMetadata(block, 3, metadata));
        TESSELLATOR.setNormal(-1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceZPos(block, 0, 0, 0, renderBlocks.getBlockIconFromSideAndMetadata(block, 4, metadata));
        TESSELLATOR.setNormal(1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXNeg(block, 0, 0, 0, renderBlocks.getBlockIconFromSideAndMetadata(block, 5, metadata));

        renderBlocks.setRenderBounds(0, 0, 0, 1, 0.125D, 1);
        TESSELLATOR.setNormal(0.0F, 1.0F, 0.0F);
        renderBlocks.renderFaceYPos(block, 0, 0, 0, renderBlocks.getBlockIconFromSideAndMetadata(block, 0, metadata));
    }
    
    public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks)
    {
        renderBlocks.renderStandardBlock(block, x, y, z);
        
        if (block.shouldSideBeRendered(iBlockAccess, x, y + 1, z, 1))
        {
            boolean prevRenderFaces = renderBlocks.renderAllFaces;
            renderBlocks.renderAllFaces = true;
            renderBlocks.setRenderBounds(0.875D, 0.125D, 0.125D, 0.125D, 1.0D, 0.875D);
            this.renderFaceZNeg(renderBlocks, block, x, y, z);
            this.renderFaceXPos(renderBlocks, block, x, y, z);
            this.renderFaceZPos(renderBlocks, block, x, y, z);
            this.renderFaceXNeg(renderBlocks, block, x, y, z);
            renderBlocks.renderAllFaces = prevRenderFaces;
            int metadata = Math.min(iBlockAccess.getBlockMetadata(x, y, z), 9);
            
            if (!renderBlocks.hasOverrideBlockTexture())
            {
                if (metadata == 0)
                {
                    renderBlocks.setOverrideBlockTexture(RegistryBlocks.composter.getIcon(0, 0));
                }
                
                else if (metadata == 7)
                {
                    renderBlocks.setOverrideBlockTexture(((BlockComposter) block).fullComposterIcon);
                }
                
                else
                {
                    renderBlocks.setOverrideBlockTexture(((BlockComposter) block).composterIcon);
                }
                
                double offset = 0.125D * Math.min(metadata + 1, 7);
                
                if (metadata > 0)
                {
                    offset += 0.0625D;
                }
                
                renderBlocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, offset, 1.0D);
                this.renderFaceYPos(renderBlocks, block, x, y, z);
            }
            
            renderBlocks.clearOverrideBlockTexture();
        }
        
        return true;
    }
    
    public int getRenderId()
    {
        return BlockComposter.composterModel;
    }
}