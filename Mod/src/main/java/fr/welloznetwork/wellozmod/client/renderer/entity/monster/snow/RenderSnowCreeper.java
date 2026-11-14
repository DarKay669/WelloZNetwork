package fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowCreeper;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowCreeper extends RenderLiving
{
    private static final ResourceLocation creeperTextures = new ResourceLocation("wellozmod:textures/entity/snow/creeper.png");
    
    public RenderSnowCreeper()
    {
        super(new ModelCreeper(), 0.5F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowCreeper entitySnowCreeper)
    {
        return creeperTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowCreeper) entity);
    }
}