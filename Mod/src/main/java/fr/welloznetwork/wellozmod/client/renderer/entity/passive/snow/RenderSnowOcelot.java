package fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowOcelot;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowOcelot extends RenderLiving
{
    private static final ResourceLocation ocelotTextures = new ResourceLocation("wellozmod:textures/entity/snow/ocelot.png");
    
    public RenderSnowOcelot()
    {
        super(new ModelOcelot(), 1.0F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowOcelot entitySnowOcelot)
    {
        return ocelotTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowOcelot) entity);
    }
}