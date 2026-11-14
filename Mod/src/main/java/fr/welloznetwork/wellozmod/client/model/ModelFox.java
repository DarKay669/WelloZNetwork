package fr.welloznetwork.wellozmod.client.model;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelFox extends ModelBase
{
    private ModelRenderer body, mane, leg1, leg2, leg3, leg4, head, nose, ear1, ear2, tail;
    
    public ModelFox()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body = new ModelRenderer(this, 23, 14);
        this.body.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 8);
        this.body.setRotationPoint(0.0F, 14.0F, -1.0F);
        this.body.setTextureSize(64, 32);
        this.body.mirror = true;
        
        this.setRotation(this.body, 0.0F, 0.0F, 0.0F);
        
        this.mane = new ModelRenderer(this, 23, 0);
        this.mane.addBox(-4.0F, -3.0F, 0.0F, 8, 7, 6);
        this.mane.setRotationPoint(0.0F, 13.0F, -7.0F);
        this.mane.setTextureSize(64, 32);
        this.mane.mirror = true;
        
        this.setRotation(this.mane, 0.0F, 0.0F, 0.0F);
        
        this.leg1 = new ModelRenderer(this, 0, 18);
        this.leg1.addBox(-1.0F, -1.0F, -1.0F, 2, 8, 2);
        this.leg1.setRotationPoint(-1.5F, 17.0F, 5.0F);
        this.leg1.setTextureSize(64, 32);
        this.leg1.mirror = true;
        
        this.setRotation(this.leg1, 0.0F, 0.0F, 0.0F);
        
        this.leg2 = new ModelRenderer(this, 0, 18);
        this.leg2.addBox(-1.0F, -1.0F, -1.0F, 2, 8, 2);
        this.leg2.setRotationPoint(1.5F, 17.0F, 5.0F);
        this.leg2.setTextureSize(64, 32);
        this.leg2.mirror = true;
        
        this.setRotation(this.leg2, 0.0F, 0.0F, 0.0F);
        
        this.leg3 = new ModelRenderer(this, 0, 18);
        this.leg3.addBox(-1.0F, -1.0F, -1.0F, 2, 8, 2);
        this.leg3.setRotationPoint(-1.5F, 17.0F, -5.0F);
        this.leg3.setTextureSize(64, 32);
        this.leg3.mirror = true;
        
        this.setRotation(this.leg3, 0.0F, 0.0F, 0.0F);
        
        this.leg4 = new ModelRenderer(this, 0, 18);
        this.leg4.addBox(-1.0F, -1.0F, -1.0F, 2, 8, 2);
        this.leg4.setRotationPoint(1.5F, 17.0F, -5.0F);
        this.leg4.setTextureSize(64, 32);
        this.leg4.mirror = true;
        
        this.setRotation(this.leg4, 0.0F, 0.0F, 0.0F);
        
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 4);
        this.head.setRotationPoint(0.0F, 13.5F, -7.0F);
        this.head.setTextureSize(64, 32);
        this.head.mirror = true;
        
        this.setRotation(this.head, 0.0F, 0.0F, 0.0F);
        
        this.nose = new ModelRenderer(this, 0, 10);
        this.nose.addBox(-1.5F, -1.0F, -3.0F, 3, 3, 3);
        this.nose.setRotationPoint(0.0F, 14.5F, -10.0F);
        this.nose.setTextureSize(64, 32);
        this.nose.mirror = true;
        
        this.setRotation(this.nose, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.head, this.nose);
        
        this.ear1 = new ModelRenderer(this, 15, 11);
        this.ear1.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 1);
        this.ear1.setRotationPoint(-2.0F, 10.5F, -8.0F);
        this.ear1.setTextureSize(64, 32);
        this.ear1.mirror = true;
        
        this.setRotation(this.ear1, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.head, this.ear1);
        
        this.ear2 = new ModelRenderer(this, 15, 11);
        this.ear2.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 1);
        this.ear2.setRotationPoint(2.0F, 10.5F, -8.0F);
        this.ear2.setTextureSize(64, 32);
        this.ear2.mirror = true;
        
        this.setRotation(this.ear2, 0.0F, 0.0F, 0.0F);
        this.convertToChild(this.head, this.ear2);
        
        this.tail = new ModelRenderer(this, 0, 19);
        this.tail.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 10);
        this.tail.setRotationPoint(0.0F, 13.0F, 6.0F);
        this.tail.setTextureSize(64, 32);
        this.tail.mirror = true;
        
        this.setRotation(this.tail, -0.6363323F, 0.0F, 0.0F);
    }
    
    public void render(Entity entity, float x, float y, float z, float rotationYaw, float rotationPitch, float partialTicks)
    {
        super.render(entity, x, y, z, rotationYaw, rotationPitch, partialTicks);
        this.setRotationAngles(x, y, z, rotationYaw, rotationPitch, partialTicks, entity);
        
        if (this.isChild)
        {
            GL11.glPushMatrix();
            GL11.glScalef(1.5F / 2.0F, 1.5F / 2.0F, 1.5F / 2.0F);
            GL11.glTranslatef(0.0F, 13.0F * partialTicks, 3.0F * partialTicks);
            
            this.head.render(partialTicks);
            
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(0.75F / 2.0F, 0.75F / 2.0F, 0.75F / 2.0F);
            GL11.glTranslatef(0.0F, 40.0F * partialTicks, 0.0F);
            
            this.body.render(partialTicks);
            this.mane.render(partialTicks);
            this.leg1.render(partialTicks);
            this.leg2.render(partialTicks);
            this.leg3.render(partialTicks);
            this.leg4.render(partialTicks);
            this.tail.render(partialTicks);
            
            GL11.glPopMatrix();
        }
        
        else
        {
            GL11.glPushMatrix();
            GL11.glScalef(1.5F / 2.0F, 1.5F / 2.0F, 1.5F / 2.0F);
            GL11.glTranslatef(0.0F, 8.0F * partialTicks, 0.0F);
            
            this.body.render(partialTicks);
            this.mane.render(partialTicks);
            this.leg1.render(partialTicks);
            this.leg2.render(partialTicks);
            this.leg3.render(partialTicks);
            this.leg4.render(partialTicks);
            this.head.render(partialTicks);
            this.tail.render(partialTicks);
            
            GL11.glPopMatrix();
        }
    }
    
    public void setRotationAngles(float x, float y, float z, float rotationYaw, float rotationPitch, float partialTicks, Entity entity)
    {
        super.setRotationAngles(x, y, z, rotationYaw, rotationPitch, partialTicks, entity);
        
        this.head.rotateAngleX = rotationPitch / 57.295776F;
        this.head.rotateAngleY = rotationYaw / 57.295776F;
        this.tail.rotateAngleY = MathHelper.cos(x * 0.6662F) * 1.4F * y;
        this.leg1.rotateAngleX = MathHelper.cos(x * 0.6662F) * 1.4F * y;
        this.leg2.rotateAngleX = MathHelper.cos(x * 0.6662F + 3.1415927F) * 1.4F * y;
        this.leg3.rotateAngleX = MathHelper.cos(x * 0.6662F + 3.1415927F) * 1.4F * y;
        this.leg4.rotateAngleX = MathHelper.cos(x * 0.6662F) * 1.4F * y;
    }
    
    private void setRotation(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    private void convertToChild(ModelRenderer parent, ModelRenderer child)
    {
        child.rotationPointX -= parent.rotationPointX;
        child.rotationPointY -= parent.rotationPointY;
        child.rotationPointZ -= parent.rotationPointZ;
        child.rotateAngleX -= parent.rotateAngleX;
        child.rotateAngleY -= parent.rotateAngleY;
        child.rotateAngleZ -= parent.rotateAngleZ;
        
        parent.addChild(child);
    }
}