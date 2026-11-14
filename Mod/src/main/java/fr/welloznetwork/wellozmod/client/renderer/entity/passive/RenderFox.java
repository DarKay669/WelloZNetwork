package fr.welloznetwork.wellozmod.client.renderer.entity.passive;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelFox;
import fr.welloznetwork.wellozmod.entity.passive.EntityFox;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderFox extends RenderLiving
{
    private static final ResourceLocation FOX_TEXTURES = new ResourceLocation("wellozmod:textures/entity/fox/fox.png");
    
    public RenderFox()
    {
        super(new ModelFox(), 0.0F);
        this.shadowSize = 0.4F;
    }
    
    protected ResourceLocation getEntityTexture(EntityFox entityFox)
    {
        return FOX_TEXTURES;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityFox) entity);
    }
}