package fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowCow;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowCow extends RenderLiving
{
    private static final ResourceLocation cowTextures = new ResourceLocation("wellozmod:textures/entity/snow/cow.png");
    
    public RenderSnowCow()
    {
        super(new ModelCow(), 1.0F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowCow entitySnowCow)
    {
        return cowTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowCow) entity);
    }
}