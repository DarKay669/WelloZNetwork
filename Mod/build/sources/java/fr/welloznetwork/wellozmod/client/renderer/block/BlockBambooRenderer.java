package fr.welloznetwork.wellozmod.client.renderer.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.block.BlockBamboo;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockBambooRenderer implements ISimpleBlockRenderingHandler
{
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderBlocks)
    {
        Tessellator tessellator = Tessellator.instance;

        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        block.setBlockBounds(0, 0, 0, 1, 1, 1);
        renderBlocks.setRenderBoundsFromBlock(block);
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderBlocks.renderFaceYNeg(block, 0, 0, 0, block.getIcon(0, metadata));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderBlocks.renderFaceYPos(block, 0, 0, 0, block.getIcon(1, metadata));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderBlocks.renderFaceZNeg(block, 0, 0, 0, block.getIcon(2, metadata));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderBlocks.renderFaceZPos(block, 0, 0, 0, block.getIcon(3, metadata));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXNeg(block, 0, 0, 0, block.getIcon(4, metadata));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXPos(block, 0, 0, 0, block.getIcon(5, metadata));
        tessellator.draw();

        GL11.glPopMatrix();
    }
    
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderBlocks)
    {
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        
        int l = block.colorMultiplier(world, x, y, z);
        float f = (float) (l >> 16 & 255) / 255.0F, f1 = (float) (l >> 8 & 255) / 255.0F, f2 = (float) (l & 255) / 255.0F;
        
        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F, f4 = (f * 30.0F + f1 * 70.0F) / 100.0F, f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        
        Tessellator tessellator = Tessellator.instance;
        boolean flag = false;
        float f3 = 0.5F, f4 = 1.0F, f5 = 0.8F, f6 = 0.6F, r = f3 * f, f8 = f4 * f, f9 = f5 * f, f10 = f6 * f, g = f3 * f1, f12 = f4 * f1, f13 = f5 * f1, f14 = f6 * f1, b = f3 * f2, f16 = f4 * f2, f17 = f5 * f2, f18 = f6 * f2;
        int m = block.getMixedBrightnessForBlock(world, x, y, z);
        
        if (renderBlocks.renderAllFaces || block.shouldSideBeRendered(world, x, y - 1, z, 0))
        {
            tessellator.setBrightness(renderBlocks.renderMinY > 0.0D ? m : block.getMixedBrightnessForBlock(world, x, y - 1, z));
            tessellator.setColorOpaque_F(r, g, b);
            renderBlocks.renderFaceYNeg(block, (double) x, (double) y, (double) z, renderBlocks.getBlockIcon(block, world, x, y, z, 0));
        }
        
        if (renderBlocks.renderAllFaces || block.shouldSideBeRendered(world, x, y + 1, z, 1))
        {
            tessellator.setBrightness(renderBlocks.renderMaxY < 1.0D ? m : block.getMixedBrightnessForBlock(world, x, y + 1, z));
            tessellator.setColorOpaque_F(f8, f12, f16);
            renderBlocks.renderFaceYPos(block, (double) x, (double) y, (double) z, renderBlocks.getBlockIcon(block, world, x, y, z, 1));
        }
        
        tessellator.setBrightness(m);
        tessellator.setColorOpaque_F(f9, f13, f17);
        tessellator.addTranslation(0.0F, 0.0F, 0.0625F);
        renderBlocks.renderFaceZNeg(block, (double) x, (double) y, (double) z + (0.0625F * 4), renderBlocks.getBlockIcon(block, world, x, y, z, 2));
        tessellator.addTranslation(0.0F, 0.0F, -0.0625F);
        tessellator.addTranslation(0.0F, 0.0F, -0.0625F);
        renderBlocks.renderFaceZPos(block, (double) x, (double) y, (double) z - (0.0625F * 4), renderBlocks.getBlockIcon(block, world, x, y, z, 3));
        tessellator.addTranslation(0.0F, 0.0F, 0.0625F);
        tessellator.setColorOpaque_F(f10, f14, f18);
        tessellator.addTranslation(0.0625F, 0.0F, 0.0F);
        renderBlocks.renderFaceXNeg(block, (double) x + (0.0625F * 4), (double) y, (double) z, renderBlocks.getBlockIcon(block, world, x, y, z, 4));
        tessellator.addTranslation(-0.0625F, 0.0F, 0.0F);
        tessellator.addTranslation(-0.0625F, 0.0F, 0.0F);
        renderBlocks.renderFaceXPos(block, (double) x - (0.0625F * 4), (double) y, (double) z, renderBlocks.getBlockIcon(block, world, x, y, z, 5));
        tessellator.addTranslation(0.0625F, 0.0F, 0.0F);
        
        return true;
    }
    
    public boolean shouldRender3DInInventory(int modelID)
    {
        return true;
    }
    
    public int getRenderId()
    {
        return BlockBamboo.bambooModel;
    }
}