package fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowPig;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowPig extends RenderLiving
{
    private static final ResourceLocation pigTextures = new ResourceLocation("wellozmod:textures/entity/snow/pig.png");
    
    public RenderSnowPig()
    {
        super(new ModelPig(), 1.0F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowPig entitySnowPig)
    {
        return pigTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowPig) entity);
    }
}