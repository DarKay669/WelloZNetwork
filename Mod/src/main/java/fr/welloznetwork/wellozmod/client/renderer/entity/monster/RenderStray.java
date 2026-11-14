package fr.welloznetwork.wellozmod.client.renderer.entity.monster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderStray extends RenderSkeleton
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("wellozmod:textures/entity/skeleton/stray.png"), OVERLAY_TEXTURE = new ResourceLocation("wellozmod:textures/entity/skeleton/stray_overlay.png");
    private final ModelZombie modelZombie;
    
    public RenderStray()
    {
        super();
        this.modelZombie = new ModelZombie(0.5F, true);
    }
    
    protected int shouldRenderPass(EntityLivingBase entityLivingBase, int renderPass, float partialTicks)
    {
        if (renderPass == 1)
        {
            this.setRenderPassModel(this.modelZombie);
            this.bindTexture(OVERLAY_TEXTURE);
            return 1;
        }
        
        return super.shouldRenderPass(entityLivingBase, renderPass, partialTicks);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity)
    {
        return TEXTURE;
    }
}