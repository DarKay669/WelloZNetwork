package fr.welloznetwork.wellozmod.client.renderer.entity.monster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelEndermite;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderEndermite extends RenderLiving
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("wellozmod:textures/entity/endermite.png");
    
    public RenderEndermite()
    {
        super(new ModelEndermite(), 0.3F);
    }
    
    protected float getDeathMaxRotation(EntityLivingBase entityLivingBase)
    {
        return 180.0F;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TEXTURE;
    }
}