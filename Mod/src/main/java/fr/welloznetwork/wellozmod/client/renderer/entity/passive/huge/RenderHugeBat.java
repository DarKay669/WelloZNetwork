package fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeBat;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderHugeBat extends RenderLiving
{
    private static final ResourceLocation batTextures = new ResourceLocation("textures/entity/bat.png");
    private float scale;
    
    public RenderHugeBat(ModelBase modelBase, float x, float y)
    {
        super(modelBase, x * y);
        this.scale = y;
    }
    
    protected void preRenderCallback(EntityHugeBat entityHugeBat, float scale)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }
    
    protected ResourceLocation getEntityTexture(EntityHugeBat entityHugeBat)
    {
        return batTextures;
    }
    
    protected void preRenderCallback(EntityLivingBase entityLivingBase, float scale)
    {
        this.preRenderCallback((EntityHugeBat) entityLivingBase, scale);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityHugeBat) entity);
    }
}