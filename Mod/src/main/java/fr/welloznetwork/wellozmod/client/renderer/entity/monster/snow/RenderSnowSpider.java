package fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowSpider;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSnowSpider extends RenderLiving
{
    private static final ResourceLocation spiderTextures = new ResourceLocation("wellozmod:textures/entity/snow/spider.png");
    
    public RenderSnowSpider()
    {
        super(new ModelSpider(), 1.0F);
    }
    
    protected ResourceLocation getEntityTexture(EntitySnowSpider entitySnowCreeper)
    {
        return spiderTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntitySnowSpider) entity);
    }
}