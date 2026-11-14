package fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowBat;
import net.minecraft.client.model.ModelBat;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowBat extends RenderLiving
{
    private static final ResourceLocation batTextures = new ResourceLocation("wellozmod:textures/entity/snow/bat.png");
    
    public RenderSnowBat()
    {
        super(new ModelBat(), 1.0F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowBat entitySnowChicken)
    {
        return batTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowBat) entity);
    }
}