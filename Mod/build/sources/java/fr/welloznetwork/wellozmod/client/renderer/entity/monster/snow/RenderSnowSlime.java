package fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowSlime;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowSlime extends RenderLiving
{
    private static final ResourceLocation slimeTextures = new ResourceLocation("wellozmod:textures/entity/snow/slime.png");
    
    public RenderSnowSlime()
    {
        super(new ModelSlime(16), 0.25F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowSlime entitySnowCreeper)
    {
        return slimeTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowSlime) entity);
    }
}