package fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowEnderman;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowEnderman extends RenderLiving
{
    private static final ResourceLocation endermanTextures = new ResourceLocation("wellozmod:textures/entity/snow/enderman.png");
    
    public RenderSnowEnderman()
    {
        super(new ModelEnderman(), 0.5F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowEnderman entitySnowCreeper)
    {
        return endermanTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowEnderman) entity);
    }
}