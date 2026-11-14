package fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowZombie;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowZombie extends RenderLiving
{
    private static final ResourceLocation zombieTextures = new ResourceLocation("wellozmod:textures/entity/snow/zombie.png");
    
    public RenderSnowZombie()
    {
        super(new ModelZombie(), 0.5F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowZombie entitySnowCreeper)
    {
        return zombieTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowZombie) entity);
    }
}