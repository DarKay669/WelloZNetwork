package fr.welloznetwork.wellozmod.client.model;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelTechnobladeCrown extends ModelBase
{
    public ModelRenderer modelRenderer = new ModelRenderer(this, 0, 0);
    private float field_78145_g = 4.0F, field_78151_h = 4.0F;
    
    public ModelTechnobladeCrown()
    {
        this(6, 0);
    }
    
    public ModelTechnobladeCrown(int p_i1154_1_, float p_i1154_2_)
    {
        this.modelRenderer.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, p_i1154_2_ + 0.5F);
        this.modelRenderer.setRotationPoint(0.0F, (float) (18 - p_i1154_1_), -6.0F);
    }
    
    public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
    {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, entity);
        
        if (this.isChild)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, this.field_78145_g * p_78088_7_, this.field_78151_h * p_78088_7_);
            this.modelRenderer.render(p_78088_7_);
            GL11.glPopMatrix();
        }
        
        else
        {
            this.modelRenderer.render(p_78088_7_);
        }
    }
    
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
    {
        this.modelRenderer.rotateAngleX = p_78087_5_ / (180.0F / (float) Math.PI);
        this.modelRenderer.rotateAngleZ = p_78087_4_ / (180.0F / (float) Math.PI);
    }
}