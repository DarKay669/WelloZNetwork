package fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelPenguin;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntityPenguin;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderPenguin extends RenderLiving
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("wellozmod:textures/entity/penguin/penguin.png");
    
    public RenderPenguin()
    {
        super(new ModelPenguin(), 0.5F);
    }
    
    private void renderPenguin(EntityPenguin entityPenguin, double x, double y, double z, float yaw, float pitch)
    {
        this.doRender((EntityLiving) entityPenguin, x, y, z, yaw, pitch);
    }
    
    private float getWingRotation(EntityPenguin entityPenguin, float time)
    {
        float length = entityPenguin.lastFlapLength + (entityPenguin.flapLength - entityPenguin.lastFlapLength) * time;
        float intensity = entityPenguin.lastFlapIntensity + (entityPenguin.flapIntensity - entityPenguin.lastFlapIntensity) * time;
        return (MathHelper.sin(length) + 1.0F) * intensity;
    }
    
    protected float handleRotationFloat(EntityLivingBase entityLivingBase, float time)
    {
        return this.getWingRotation((EntityPenguin) entityLivingBase, time);
    }
    
    public void doRenderLiving(EntityLiving entityLiving, double x, double y, double z, float yaw, float pitch)
    {
        this.renderPenguin((EntityPenguin) entityLiving, x, y, z, yaw, pitch);
    }
    
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch)
    {
        this.renderPenguin((EntityPenguin) entity, x, y, z, yaw, pitch);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TEXTURE;
    }
}