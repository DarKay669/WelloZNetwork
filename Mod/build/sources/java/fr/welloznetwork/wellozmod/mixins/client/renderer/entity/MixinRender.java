package fr.welloznetwork.wellozmod.mixins.client.renderer.entity;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

@Mixin(Render.class)
public class MixinRender
{
    @Shadow
    protected RenderManager renderManager;
    
    @Overwrite
    private void renderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks)
    {
        AxisAlignedBB axisAlignedBB = entity.boundingBox;
        
        boolean inFire = false;
        
        int minX = MathHelper.floor_double(axisAlignedBB.minX);
        int maxX = MathHelper.floor_double(axisAlignedBB.maxX);
        int minY = MathHelper.floor_double(axisAlignedBB.minY);
        int maxY = MathHelper.floor_double(axisAlignedBB.maxY);
        int minZ = MathHelper.floor_double(axisAlignedBB.minZ);
        int maxZ = MathHelper.floor_double(axisAlignedBB.maxZ);
        
        outer:
        for (int x1 = minX; x1 <= maxX; x1++)
        {
            for (int y1 = minY; y1 <= maxY; y1++)
            {
                for (int z1 = minZ; z1 <= maxZ; z1++)
                {
                    if (entity.worldObj.getBlock(x1, y1, z1) == RegistryBlocks.soul_fire)
                    {
                        inFire = true;
                        break outer;
                    }
                }
            }
        }
        
        GL11.glDisable(GL11.GL_LIGHTING);
        
        IIcon iicon = inFire ? RegistryBlocks.soul_fire.getFireIcon(0) : Blocks.fire.getFireIcon(0), iicon1 = inFire ? RegistryBlocks.soul_fire.getFireIcon(1) : Blocks.fire.getFireIcon(1);
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        
        float f1 = entity.width * 1.4F, f2 = 0.5F, f3 = 0.0F, f4 = entity.height / f1, f5 = (float) (entity.posY - entity.boundingBox.minY), f6 = 0.0F;
        int i = 0;
        
        GL11.glScalef(f1, f1, f1);
        
        Tessellator tessellator = Tessellator.instance;
        
        GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.0F, -0.3F + (float) ((int) f4) * 0.02F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        tessellator.startDrawingQuads();
        
        while (f4 > 0.0F)
        {
            IIcon iicon2 = i % 2 == 0 ? iicon : iicon1;
            this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            float f7 = iicon2.getMinU(), f8 = iicon2.getMinV(), f9 = iicon2.getMaxU(), f10 = iicon2.getMaxV();
            
            if (i / 2 % 2 == 0)
            {
                float f11 = f9;
                
                f9 = f7;
                f7 = f11;
            }
            
            tessellator.addVertexWithUV((double) (f2 - f3), (double) (0.0F - f5), (double) f6, (double) f9, (double) f10);
            tessellator.addVertexWithUV((double) (-f2 - f3), (double) (0.0F - f5), (double) f6, (double) f7, (double) f10);
            tessellator.addVertexWithUV((double) (-f2 - f3), (double) (1.4F - f5), (double) f6, (double) f7, (double) f8);
            tessellator.addVertexWithUV((double) (f2 - f3), (double) (1.4F - f5), (double) f6, (double) f9, (double) f8);
            
            f4 -= 0.45F;
            f5 -= 0.45F;
            f2 *= 0.9F;
            f6 += 0.03F;
            i++;
        }
        
        tessellator.draw();
        
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
    }
    
    @Inject(method = "func_147906_a", at = @At("HEAD"), cancellable = true)
    private void renderLivingLabel(Entity entity, String str, double x, double y, double z, int maxDistance, CallbackInfo callbackInfo)
    {
        if (entity instanceof EntityPlayer)
        {
            callbackInfo.cancel();
        }
    }
}