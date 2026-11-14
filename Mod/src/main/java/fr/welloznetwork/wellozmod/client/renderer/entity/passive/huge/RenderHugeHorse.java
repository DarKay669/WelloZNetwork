package fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeHorse;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderHugeHorse extends RenderLiving
{
    private static final ResourceLocation horseTextures = new ResourceLocation("textures/entity/horse/horse_black.png");
    private float scale;
    
    public RenderHugeHorse(ModelBase modelBase, float x, float y)
    {
        super(modelBase, x * y);
        this.scale = y;
    }
    
    protected void preRenderCallback(EntityHugeHorse entityHugeHorse, float scale)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }
    
    protected ResourceLocation getEntityTexture(EntityHugeHorse entityHugeHorse)
    {
        return horseTextures;
    }
    
    protected void preRenderCallback(EntityLivingBase entityLivingBase, float scale)
    {
        this.preRenderCallback((EntityHugeHorse) entityLivingBase, scale);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityHugeHorse) entity);
    }
}