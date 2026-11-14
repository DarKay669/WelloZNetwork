package fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowWolf;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowWolf extends RenderLiving
{
    private static final ResourceLocation wolfTextures = new ResourceLocation("wellozmod:textures/entity/snow/wolf.png");
    
    public RenderSnowWolf()
    {
        super(new ModelWolf(), 0.5F);
        this.setRenderPassModel(new ModelWolf());
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowWolf entitySnowWolf)
    {
        return wolfTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowWolf) entity);
    }
}