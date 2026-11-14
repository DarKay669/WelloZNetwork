package fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowChicken;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowChicken extends RenderLiving
{
    private static final ResourceLocation chickenTextures = new ResourceLocation("wellozmod:textures/entity/snow/chicken.png");
    
    public RenderSnowChicken()
    {
        super(new ModelChicken(), 0.3F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowChicken entitySnowChicken)
    {
        return chickenTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowChicken) entity);
    }
}