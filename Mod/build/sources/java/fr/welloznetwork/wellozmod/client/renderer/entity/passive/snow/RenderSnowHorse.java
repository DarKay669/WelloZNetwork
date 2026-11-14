package fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowHorse;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowHorse extends RenderLiving
{
    private static final ResourceLocation horseTextures = new ResourceLocation("wellozmod:textures/entity/snow/horse.png");
    
    public RenderSnowHorse()
    {
        super(new ModelHorse(), 1.0F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowHorse entitySnowHorse)
    {
        return horseTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowHorse) entity);
    }
}