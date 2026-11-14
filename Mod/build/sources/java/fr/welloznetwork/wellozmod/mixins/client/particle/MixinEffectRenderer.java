package fr.welloznetwork.wellozmod.mixins.client.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import fr.welloznetwork.wellozmod.util.ModUtil.RandomXoshiro256StarStar;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@Mixin(EffectRenderer.class)
public class MixinEffectRenderer
{
    public Random rand = new RandomXoshiro256StarStar();
    
    @Shadow
    protected World worldObj;
    
    @Shadow
    public void addEffect(EntityFX entityFX) {}
    
    @Overwrite
    public void addBlockDestroyEffects(int x, int y, int z, Block block, int p_147215_5_)
    {
        if (!block.isAir(this.worldObj, x, y, z) && !block.addDestroyEffects(this.worldObj, x, y, z, p_147215_5_, (EffectRenderer) (Object) this))
        {
            AxisAlignedBB axisAlignedBB = AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1, (double) y + 1, (double) z + 1);
            List<AxisAlignedBB> list = new ArrayList<>();
            
            try
            {
                block.addCollisionBoxesToList(this.worldObj, x, y, z, axisAlignedBB, list, null);
            }
            
            catch (NullPointerException e)
            {
                list.clear();
            }
            
            if (list.isEmpty())
            {
                list.add(block.getSelectedBoundingBoxFromPool(this.worldObj, x, y, z));
            }
            
            for (AxisAlignedBB axisAlignedBB1 : list)
            {
                axisAlignedBB1.minX -= x;
                axisAlignedBB1.maxX -= x;
                axisAlignedBB1.minY -= y;
                axisAlignedBB1.maxY -= y;
                axisAlignedBB1.minZ -= z;
                axisAlignedBB1.maxZ -= z;
                
                double x1 = Math.min(1.0D, axisAlignedBB1.maxX - axisAlignedBB1.minX);
                double y1 = Math.min(1.0D, axisAlignedBB1.maxY - axisAlignedBB1.minY);
                double z1 = Math.min(1.0D, axisAlignedBB1.maxZ - axisAlignedBB1.minZ);
                
                int i = Math.max(2, MathHelper.ceiling_double_int(x1 * 4.0D));
                int j = Math.max(2, MathHelper.ceiling_double_int(y1 * 4.0D));
                int k = Math.max(2, MathHelper.ceiling_double_int(z1 * 4.0D));
                
                for (int l = 0; l < i; l++)
                {
                    for (int m = 0; m < j; m++)
                    {
                        for (int n = 0; n < k; n++)
                        {
                            double g = ((double) l + 0.5D) / (double) i;
                            double h = ((double) m + 0.5D) / (double) j;
                            double o = ((double) n + 0.5D) / (double) k;
                            double p = g * x1 + axisAlignedBB1.minX;
                            double q = h * y1 + axisAlignedBB1.minY;
                            double r = o * z1 + axisAlignedBB1.minZ;
                            
                            this.addEffect(new EntityDiggingFX(this.worldObj, x + p, y + q, z + r, g - 0.5D, h - 0.5D, o - 0.5D, block, p_147215_5_).applyColourMultiplier(x, y, z));
                        }
                    }
                }
            }
        }
    }
    
    @Redirect(method = "renderParticles", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glDepthMask(Z)V", ordinal = 0, remap = false))
    private void skipGlDepthMask(boolean flag) {}
}