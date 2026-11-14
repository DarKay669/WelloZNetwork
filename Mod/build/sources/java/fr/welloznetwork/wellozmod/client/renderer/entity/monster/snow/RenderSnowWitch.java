package fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowWitch;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowWitch extends RenderLiving
{
    private static final ResourceLocation witchTextures = new ResourceLocation("wellozmod:textures/entity/snow/witch.png");
    
    public RenderSnowWitch()
    {
        super(new ModelWitch(0.0F), 0.5F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowWitch entitySnowCreeper)
    {
        return witchTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowWitch) entity);
    }
}