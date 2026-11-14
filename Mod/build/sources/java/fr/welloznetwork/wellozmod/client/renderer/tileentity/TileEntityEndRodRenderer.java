package fr.welloznetwork.wellozmod.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class TileEntityEndRodRenderer extends TileEntitySpecialRenderer
{
    private RenderBlocks renderBlocks = new RenderBlocks();
    
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        
        this.bindTexture(TextureMap.locationBlocksTexture);
        
        switch (ForgeDirection.getOrientation(tileEntity.getBlockMetadata()))
        {
            case DOWN:
                GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
                GL11.glTranslated(0.0D, -1.0D, -1.0D);
                break;
                
            case EAST:
                GL11.glRotated(270.0D, 0.0D, 0.0D, 1.0D);
                GL11.glTranslated(-1.0D, 0.0D, 0.0D);
                break;
                
            case NORTH:
                GL11.glRotated(270.0D, 1.0D, 0.0D, 0.0D);
                GL11.glTranslated(0.0D, -1.0D, 0.0D);
                break;

            case SOUTH:
                GL11.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
                GL11.glTranslated(0.0D, 0.0D, -1.0D);
                break;
                
            case WEST:
                GL11.glRotated(90.0D, 0.0D, 0.0D, 1.0D);
                GL11.glTranslated(0.0D, -1.0D, 0.0D);
                break;
                
            default:
                break;
        }
        
        this.renderRod(this.renderBlocks, tileEntity.getBlockType(), tileEntity.getBlockMetadata());
        
        GL11.glPopMatrix();
    }
    
    public static void renderRod(RenderBlocks renderBlocks, Block block, int metadata)
    {
        Tessellator tessellator = Tessellator.instance;
        
        double x = 0.4375D, y = 0.0D, z = 0.4375D;
        
        renderBlocks.setRenderBounds(0.0D, 0.0625D, 0.0D, 0.125D, 1.0D, 0.125D);
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        
        renderBlocks.renderFaceYNeg(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 0, metadata));
        
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        
        renderBlocks.renderFaceYPos(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 1, metadata));
        
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        
        renderBlocks.renderFaceZNeg(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 2, metadata));
        
        tessellator.setNormal(0.0F, 0.0F, 10.F);
        
        renderBlocks.renderFaceZPos(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 3, metadata));
        
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        
        renderBlocks.renderFaceXNeg(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 4, metadata));
        
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        
        renderBlocks.renderFaceXPos(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 5, metadata));
        
        tessellator.draw();
        
        x = 0.25D;
        y = 0.0D;
        z = 0.25D;
        
        renderBlocks.setRenderBounds(0.125D, 0.0D, 0.125D, 0.375D, 0.0625D, 0.375D);
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        
        renderBlocks.renderFaceYNeg(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 0, metadata));
        
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        
        renderBlocks.renderFaceYPos(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 1, metadata));
        y = -0.8125D;
        
        renderBlocks.setRenderBounds(0.125D, 0.8125D, 0.125D, 0.375D, 0.875D, 0.375D);
        
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        
        renderBlocks.renderFaceZNeg(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 2, metadata));
        
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        
        renderBlocks.renderFaceZPos(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 3, metadata));
        
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        
        renderBlocks.renderFaceXNeg(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 4, metadata));
        
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        
        renderBlocks.renderFaceXPos(block, x, y, z, renderBlocks.getBlockIconFromSideAndMetadata(block, 5, metadata));
        
        tessellator.draw();
    }
}