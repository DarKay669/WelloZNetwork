package fr.welloznetwork.wellozmod.client.registry;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

@Custom(ISimpleBlockRenderingHandler.class)
@SideOnly(Side.CLIENT)
public class CustomISimpleBlockRenderingHandler implements ISimpleBlockRenderingHandler
{
    protected static final Tessellator TESSELLATOR = Tessellator.instance;
    private int modelId;
    private boolean render3DInInventory = true;
    
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderBlocks)
    {
        if (block.getRenderType() == 2)
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
        }
        
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        
        TESSELLATOR.startDrawingQuads();
        this.renderInventoryModel(block, metadata, modelId, renderBlocks, block.getBlockBoundsMinX(), block.getBlockBoundsMinY(), block.getBlockBoundsMinZ(), block.getBlockBoundsMaxX(), block.getBlockBoundsMaxY(), block.getBlockBoundsMaxZ());
        TESSELLATOR.draw();
        
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    protected void renderInventoryModel(Block block, int metadata, int modelId, RenderBlocks renderBlocks, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        this.renderStandardInventoryCube(block, metadata, modelId, renderBlocks, block.getBlockBoundsMinX(), block.getBlockBoundsMinY(), block.getBlockBoundsMinZ(), block.getBlockBoundsMaxX(), block.getBlockBoundsMaxY(), block.getBlockBoundsMaxZ());
    }
    
    protected void renderStandardInventoryCube(Block block, int meta, int modelId, RenderBlocks renderBlocks, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        renderBlocks.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
        TESSELLATOR.setNormal(0.0F, -1.0F, 0.0F);
        renderBlocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 0, meta));
        TESSELLATOR.setNormal(0.0F, 1.0F, 0.0F);
        renderBlocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 1, meta));
        TESSELLATOR.setNormal(0.0F, 0.0F, -1.0F);
        renderBlocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 2, meta));
        TESSELLATOR.setNormal(0.0F, 0.0F, 1.0F);
        renderBlocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 3, meta));
        TESSELLATOR.setNormal(-1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 4, meta));
        TESSELLATOR.setNormal(1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 5, meta));
    }
    
    public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks) 
    {
        return this.renderStandardWorldCube(iBlockAccess, x, y, z, block, modelId, renderBlocks, block.getBlockBoundsMinX(), block.getBlockBoundsMinY(), block.getBlockBoundsMinZ(), block.getBlockBoundsMaxX(), block.getBlockBoundsMaxY(), block.getBlockBoundsMaxZ());
    }
    
    protected boolean renderStandardWorldCube(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks, double minX, double minY, double minZ, double maxF, double maxY, double maxZ)
    {
        renderBlocks.setRenderBounds(minX, minY, minZ, maxF, maxY, maxZ);
        return renderBlocks.renderStandardBlock(block, x, y, z);
    }
    
    public boolean shouldRender3DInInventory(int modelId)
    {
        return this.render3DInInventory;
    }
    
    public int getRenderId()
    {
        return this.modelId;
    }
    
    public CustomISimpleBlockRenderingHandler set2DInInventory()
    {
        this.render3DInInventory = false;
        return this;
    }
    
    public void renderRawFace(RenderBlocks renderBlocks, Block block, double x, double y, double z, double startX, double endX, double startY, double endY, double startZ, double endZ, double startU, double startV, double endU, double endV, IIcon iicon)
    {
        this.renderRawFace(renderBlocks, block, x, y, z, startX, endX, startY, endY, startZ, endZ, startU, startV, endU, endV, iicon);
    }
    
    public void renderRawFace(RenderBlocks renderBlocks, Block block, double x, double y, double z, double startX, double endX, double startY, double endY, double startZ, double endZ, double startU, double startV, double endU, double endV, IIcon iicon, int rotate)
    {
        double startUInterpolated, startVInterpolated, endUInterpolated, endVInterpolated, startUInterpolated2, startVInterpolated2, endUInterpolated2, endVInterpolated2;
        double endX1 = endX;
        double startX2 = startX;
        double endX2 = endX;
        double endZ1 = endZ;
        double startZ2 = startZ;
        double endZ2 = endZ;

        switch (rotate) 
        {
            case 1:
                endUInterpolated2 = startUInterpolated = iicon.getInterpolatedU(startU);
                startVInterpolated2 = startVInterpolated = iicon.getInterpolatedV(startV);
                startUInterpolated2 = endUInterpolated = iicon.getInterpolatedU(endU);
                endVInterpolated2 = endVInterpolated = iicon.getInterpolatedV(endV);
                endZ2 = startZ;
                endZ1 = endZ;
                startZ2 = endZ;
                break;
                
            case 2:
                startUInterpolated2 = endUInterpolated = iicon.getInterpolatedU(startU);
                endVInterpolated2 = endVInterpolated = iicon.getInterpolatedV(startV);
                endUInterpolated2 = startUInterpolated = iicon.getInterpolatedU(endU);
                startVInterpolated2 = startVInterpolated = iicon.getInterpolatedV(endV);
                endX2 = startX;
                endX1 = endX;
                startX2 = endX;
                break;
                
            case 3:
                endUInterpolated2 = endUInterpolated = iicon.getInterpolatedU(endU);
                startVInterpolated2 = endVInterpolated = iicon.getInterpolatedV(endV);
                startUInterpolated2 = startUInterpolated = iicon.getInterpolatedU(startU);
                endVInterpolated2 = startVInterpolated = iicon.getInterpolatedV(startV);
                break;
                
            case 0:
            default:
                endUInterpolated2 = endUInterpolated = iicon.getInterpolatedU(startU);
                startVInterpolated2 = endVInterpolated = iicon.getInterpolatedV(startV);
                startUInterpolated2 = startUInterpolated = iicon.getInterpolatedU(endU);
                endVInterpolated2 = startVInterpolated = iicon.getInterpolatedV(endV);
                break;
        }

        TESSELLATOR.setBrightness(block.getMixedBrightnessForBlock(renderBlocks.blockAccess, MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z)));
        TESSELLATOR.setColorOpaque_F(1, 1, 1);
        TESSELLATOR.addVertexWithUV(x + startX, y + startY, z + startZ, startUInterpolated, startVInterpolated);
        TESSELLATOR.addVertexWithUV(x + startX2, y + endY, z + startZ2, startUInterpolated2, startVInterpolated2);
        TESSELLATOR.addVertexWithUV(x + endX1, y + endY, z + endZ1, endUInterpolated, endVInterpolated);
        TESSELLATOR.addVertexWithUV(x + endX2, y + startY, z + endZ2, endUInterpolated2, endVInterpolated2);
    }
    
    public void renderRawDoubleSidedFace(RenderBlocks renderBlocks, Block block, double x, double y, double z, double startX, double endX, double startY, double endY, double startZ, double endZ, double startU, double startV, double endU, double endV, IIcon iicon, int rotate)
    {
        if (rotate == 1)
        {
            this.renderRawFace(renderBlocks, block, x, y, z, startX, endX, endY, startY, startZ, endZ, startU, startV, endU, endV, iicon, 1);
            this.renderRawFace(renderBlocks, block, x, y, z, endX, startX, endY, startY, startZ, endZ, endU, endV, startU, startV, iicon, 1);
        }
        
        else if (rotate == 2) 
        {
            this.renderRawFace(renderBlocks, block, x, y, z, startX, endX, endY, startY, startZ, endZ, startU, startV, endU, endV, iicon, 2);
            this.renderRawFace(renderBlocks, block, x, y, z, startX, endX, endY, startY, endZ, startZ, endU, endV, startU, startV, iicon, 2);
        }
        
        else
        {
            this.renderRawFace(renderBlocks, block, x, y, z, startX, endX, startY, endY, startZ, endZ, startU, startV, endU, endV, iicon, rotate);
            this.renderRawFace(renderBlocks, block, x, y, z, endX, startX, startY, endY, endZ, startZ, startU, startV, endU, endV, iicon, rotate);
        }
    }
    
    public void renderRawDoubleSidedFace(RenderBlocks renderBlocks, Block block, double x, double y, double z, double startX, double endX, double startY, double endY, double startZ, double endZ, double startU, double startV, double endU, double endV, IIcon iicon)
    {
        this.renderRawDoubleSidedFace(renderBlocks, block, x, y, z, startX, endX, startY, endY, startZ, endZ, startU, startV, endU, endV, iicon, 0);
    }
    
    public void renderFaceYNeg(RenderBlocks renderBlocks, Block block, double x, double y, double z) 
    {
        this.renderFaceYNeg(renderBlocks, block, x, y, z, 0, 0, 0);
    }
    
    public void renderFaceYNeg(RenderBlocks renderBlocks, Block block, double dx, double dy, double dz, double offx, double offy, double offz) 
    {
        renderBlocks.enableAO = false;

        int x = MathHelper.floor_double(dx);
        int y = MathHelper.floor_double(dy);
        int z = MathHelper.floor_double(dz);
        float f3 = 0.5F;
        int l = block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x, y, z);
        
        if (renderBlocks.renderAllFaces || block.shouldSideBeRendered(renderBlocks.blockAccess, x, y - 1, z, 0)) 
        {
            TESSELLATOR.setBrightness(renderBlocks.renderMinY + Math.abs(offy) > 0.0D ? l : block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x, MathHelper.floor_double(y - 1), z));
            TESSELLATOR.setColorOpaque_F(f3, f3, f3);
            renderBlocks.renderFaceYNeg(block, dx + offx, dy + offy, dz + offz, renderBlocks.getBlockIcon(block, renderBlocks.blockAccess, x, y, z, 0));
        }
    }
    
    public void renderFaceYPos(RenderBlocks renderBlocks, Block block, double x, double y, double z)
    {
        this.renderFaceYPos(renderBlocks, block, x, y, z, 0, 0, 0);
    }
    
    public void renderFaceYPos(RenderBlocks renderBlocks, Block block, double dx, double dy, double dz, double offx, double offy, double offz)
    {
        renderBlocks.enableAO = false;

        int x = MathHelper.floor_double(dx);
        int y = MathHelper.floor_double(dy);
        int z = MathHelper.floor_double(dz);

        int m = block.colorMultiplier(renderBlocks.blockAccess, x, y, z);
        float f = (float) (m >> 16 & 255) / 255.0F;
        float f1 = (float) (m >> 8 & 255) / 255.0F;
        float f2 = (float) (m & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }

        float f4 = 1.0F;
        float f7 = f4 * f;
        float f8 = f4 * f1;
        float f9 = f4 * f2;
        int l = block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x, y, z);

        if (renderBlocks.renderAllFaces || block.shouldSideBeRendered(renderBlocks.blockAccess, x, y + 1, z, 1)) 
        {
            TESSELLATOR.setBrightness(renderBlocks.renderMaxY - Math.abs(offy) < 1.0D ? l : block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x, y + 1, z));
            TESSELLATOR.setColorOpaque_F(f7, f8, f9);
            renderBlocks.renderFaceYPos(block, dx + offx, dy + offy, dz + offz, renderBlocks.getBlockIcon(block, renderBlocks.blockAccess, x, y, z, 1));
        }
    }
    
    public void renderFaceZNeg(RenderBlocks renderBlocks, Block block, double x, double y, double z)
    {
        this.renderFaceZNeg(renderBlocks, block, x, y, z, 0, 0, 0);
    }
    
    public void renderFaceZNeg(RenderBlocks renderBlocks, Block block, double dx, double dy, double dz, double offx, double offy, double offz) 
    {
        renderBlocks.enableAO = false;

        int x = MathHelper.floor_double(dx);
        int y = MathHelper.floor_double(dy);
        int z = MathHelper.floor_double(dz);
        float f5 = 0.8F;
        int l = block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x, y, z);

        if (renderBlocks.renderAllFaces || block.shouldSideBeRendered(renderBlocks.blockAccess, x, y, z - 1, 2)) 
        {
            TESSELLATOR.setBrightness(renderBlocks.renderMinZ + Math.abs(offz) > 0.0D ? l : block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x, y, z - 1));
            TESSELLATOR.setColorOpaque_F(f5, f5, f5);
            renderBlocks.renderFaceZNeg(block, dx + offx, dy + offy, dz + offz, renderBlocks.getBlockIcon(block, renderBlocks.blockAccess, x, y, z, 2));
        }
    }
    
    public void renderFaceZPos(RenderBlocks renderBlocks, Block block, double x, double y, double z)
    {
        this.renderFaceZPos(renderBlocks, block, x, y, z, 0, 0, 0);
    }
    
    public void renderFaceZPos(RenderBlocks renderBlocks, Block block, double dx, double dy, double dz, double offx, double offy, double offz)
    {
        renderBlocks.enableAO = false;

        int x = MathHelper.floor_double(dx);
        int y = MathHelper.floor_double(dy);
        int z = MathHelper.floor_double(dz);
        float f5 = 0.8F;
        int l = block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x, y, z);

        if (renderBlocks.renderAllFaces || block.shouldSideBeRendered(renderBlocks.blockAccess, x, y, z + 1, 3))
        {
            TESSELLATOR.setBrightness(renderBlocks.renderMaxZ - Math.abs(offz) < 1.0D ? l : block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x, y, z + 1));
            TESSELLATOR.setColorOpaque_F(f5, f5, f5);
            renderBlocks.renderFaceZPos(block, dx + offx, dy + offy, dz + offz, renderBlocks.getBlockIcon(block, renderBlocks.blockAccess, x, y, z, 3));
        }
    }
    
    public void renderFaceXNeg(RenderBlocks renderBlocks, Block block, double x, double y, double z)
    {
        this.renderFaceXNeg(renderBlocks, block, x, y, z, 0, 0, 0);
    }
    
    public void renderFaceXNeg(RenderBlocks renderBlocks, Block block, double dx, double dy, double dz, double offx, double offy, double offz)
    {
        renderBlocks.enableAO = false;

        int x = MathHelper.floor_double(dx);
        int y = MathHelper.floor_double(dy);
        int z = MathHelper.floor_double(dz);
        float f6 = 0.6F;
        int l = block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x, y, z);

        if (renderBlocks.renderAllFaces || block.shouldSideBeRendered(renderBlocks.blockAccess, x - 1, y, z, 4)) 
        {
            TESSELLATOR.setBrightness(renderBlocks.renderMinX + Math.abs(offx) > 0.0D ? l : block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x - 1, y, z));
            TESSELLATOR.setColorOpaque_F(f6, f6, f6);
            renderBlocks.renderFaceXNeg(block, dx + offx, dy + offy, dz + offz, renderBlocks.getBlockIcon(block, renderBlocks.blockAccess, x, y, z, 4));
        }
    }
    
    public void renderFaceXPos(RenderBlocks renderBlocks, Block block, double x, double y, double z)
    {
        this.renderFaceXPos(renderBlocks, block, x, y, z, 0, 0, 0);
    }
    
    public void renderFaceXPos(RenderBlocks renderBlocks, Block block, double dx, double dy, double dz, double offsetX, double offsetY, double offsetZ)
    {
        renderBlocks.enableAO = false;
        
        int x = MathHelper.floor_double(dx);
        int y = MathHelper.floor_double(dy);
        int z = MathHelper.floor_double(dz);
        float f6 = 0.6F;
        int l = block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x, y, z);
        
        if (renderBlocks.renderAllFaces || block.shouldSideBeRendered(renderBlocks.blockAccess, x + 1, y, z, 5))
        {
            TESSELLATOR.setBrightness(renderBlocks.renderMaxX - Math.abs(offsetX) < 1.0D ? l : block.getMixedBrightnessForBlock(renderBlocks.blockAccess, x + 1, y, z));
            TESSELLATOR.setColorOpaque_F(f6, f6, f6);
            renderBlocks.renderFaceXPos(block, dx + offsetX, dy + offsetY, dz + offsetZ, renderBlocks.getBlockIcon(block, renderBlocks.blockAccess, x, y, z, 5));
        }
    }
}