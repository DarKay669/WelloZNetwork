package fr.welloznetwork.wellozmod.mixins.client.renderer;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer
{
    @Shadow
    private Minecraft mc;
    
    @Overwrite
    private void renderFireInFirstPerson(float partialTicks)
    {
        Tessellator tessellator = Tessellator.instance;
        AxisAlignedBB axisAlignedBB = this.mc.thePlayer.boundingBox;
        
        boolean inFire = false;
        
        int minX = MathHelper.floor_double(axisAlignedBB.minX);
        int maxX = MathHelper.floor_double(axisAlignedBB.maxX);
        int minY = MathHelper.floor_double(axisAlignedBB.minY);
        int maxY = MathHelper.floor_double(axisAlignedBB.maxY);
        int minZ = MathHelper.floor_double(axisAlignedBB.minZ);
        int maxZ = MathHelper.floor_double(axisAlignedBB.maxZ);
        
        outer:
        for (int x = minX; x <= maxX; x++)
        {
            for (int y = minY; y <= maxY; y++)
            {
                for (int z = minZ; z <= maxZ; z++)
                {
                    if (this.mc.theWorld.getBlock(x, y, z) == RegistryBlocks.soul_fire)
                    {
                        inFire = true;
                        break outer;
                    }
                }
            }
        }
        
        IIcon iicon = inFire ? RegistryBlocks.soul_fire.getFireIcon(1) : Blocks.fire.getFireIcon(1);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        
        for (int i = 0; i < 2; i++)
        {
            GL11.glPushMatrix();
                        
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            
            float f2 = iicon.getMinU();
            float f3 = iicon.getMaxU();
            float f4 = iicon.getMinV();
            float f5 = iicon.getMaxV();
            float f6 = (0.0F - 1.0F) / 2.0F;
            float f7 = f6 + 1.0F;
            float f8 = 0.0F - 1.0F / 2.0F;
            float f9 = f8 + 1.0F;
            float f10 = -0.5F;
            
            GL11.glTranslatef((float) (-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            GL11.glRotatef((float) (i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
            
            tessellator.startDrawingQuads();
            
            tessellator.addVertexWithUV((double) f6, (double) f8, (double) f10, (double) f3, (double) f5);
            tessellator.addVertexWithUV((double) f7, (double) f8, (double) f10, (double) f2, (double) f5);
            tessellator.addVertexWithUV((double) f7, (double) f9, (double) f10, (double) f2, (double) f4);
            tessellator.addVertexWithUV((double) f6, (double) f9, (double) f10, (double) f3, (double) f4);
            
            tessellator.draw();
            
            GL11.glPopMatrix();
        }
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
    }
}