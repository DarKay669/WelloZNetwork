package fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelRabbit;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowRabbit;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowRabbit extends RenderLiving
{
    private static final ResourceLocation rabbitTextures = new ResourceLocation("wellozmod:textures/entity/snow/rabbit.png");
    
    public RenderSnowRabbit()
    {
        super(new ModelRabbit(), 1.0F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowRabbit entitySnowRabbit)
    {
        return rabbitTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowRabbit) entity);
    }
}