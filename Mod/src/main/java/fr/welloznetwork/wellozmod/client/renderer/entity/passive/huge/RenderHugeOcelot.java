package fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeOcelot;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderHugeOcelot extends RenderLiving
{
    private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
    private float scale;
    
    public RenderHugeOcelot(ModelBase modelBase, float x, float y)
    {
        super(modelBase, x * y);
        this.scale = y;
    }
    
    protected void preRenderCallback(EntityHugeOcelot entityHugeOcelot, float scale)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }
    
    protected ResourceLocation getEntityTexture(EntityHugeOcelot entityHugeOcelot)
    {
        return ocelotTextures;
    }
    
    protected void preRenderCallback(EntityLivingBase entityLivingBase, float scale)
    {
        this.preRenderCallback((EntityHugeOcelot) entityLivingBase, scale);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityHugeOcelot) entity);
    }
}