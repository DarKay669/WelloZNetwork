package fr.welloznetwork.wellozmod.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelSantaHat extends ModelBiped
{
    private ModelRenderer hatBrim, hat1, hat2, hat3, hat4, hat5, hat6, hatpoof;
    
    public ModelSantaHat(float partialTicks)
    {
        super(partialTicks);
        
        this.textureWidth = 64;
        this.textureHeight = 128;
        
        this.hatBrim = new ModelRenderer((ModelBase) this, 25, 2);
        this.hatBrim.addBox(0.0F, 0.0F, 0.0F, 7, 1, 8);
        this.hatBrim.setRotationPoint(-3.5F, -9.0F, -4.0F);
        this.hatBrim.setTextureSize(64, 128);
        this.hatBrim.mirror = true;
        setRotation(this.hatBrim, -0.122173F, 0.0F, 0.0F);
        
        this.hat1 = new ModelRenderer((ModelBase) this, 0, 0);
        this.hat1.addBox(0.0F, 0.0F, 0.0F, 6, 3, 6);
        this.hat1.setRotationPoint(-3.0F, -12.0F, -2.6F);
        this.hat1.setTextureSize(64, 128);
        this.hat1.mirror = true;
        setRotation(this.hat1, -0.1745329F, 0.0F, 0.0F);
        
        this.hat2 = new ModelRenderer((ModelBase) this, 0, 0);
        this.hat2.addBox(0.0F, 0.0F, 0.0F, 5, 3, 5);
        this.hat2.setRotationPoint(-2.5F, -13.8F, -1.1F);
        this.hat2.setTextureSize(64, 128);
        this.hat2.mirror = true;
        setRotation(this.hat2, -0.5410521F, 0.0F, 0.0F);
        
        this.hat3 = new ModelRenderer((ModelBase) this, 0, 0);
        this.hat3.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4);
        this.hat3.setRotationPoint(-2.0F, -14.666667F, 1.0F);
        this.hat3.setTextureSize(64, 128);
        this.hat3.mirror = true;
        setRotation(this.hat3, -0.6981317F, 0.0F, 0.0F);
        
        this.hat4 = new ModelRenderer((ModelBase) this, 0, 0);
        this.hat4.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
        this.hat4.setRotationPoint(-1.5F, -15.5F, 2.5F);
        this.hat4.setTextureSize(64, 128);
        this.hat4.mirror = true;
        setRotation(this.hat4, -0.9948377F, 0.0F, 0.0F);
        
        this.hat5 = new ModelRenderer((ModelBase) this, 0, 0);
        this.hat5.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2);
        this.hat5.setRotationPoint(-1.0F, -16.0F, 4.4F);
        this.hat5.setTextureSize(64, 128);
        this.hat5.mirror = true;
        setRotation(this.hat5, -1.012291F, 0.0F, 0.0F);
        
        this.hat6 = new ModelRenderer((ModelBase) this, 0, 0);
        this.hat6.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
        this.hat6.setRotationPoint(-0.5F, -16.0F, 6.0F);
        this.hat6.setTextureSize(64, 128);
        this.hat6.mirror = true;
        setRotation(this.hat6, -1.27409F, 0.0F, 0.0F);
        
        this.hatpoof = new ModelRenderer((ModelBase) this, 0, 39);
        this.hatpoof.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
        this.hatpoof.setRotationPoint(-1.0F, -16.5F, 5.6F);
        this.hatpoof.setTextureSize(64, 128);
        this.hatpoof.mirror = true;
        setRotation(this.hatpoof, 0.0F, 0.0F, 0.0F);
        
        this.bipedHead.addChild(this.hatBrim);
        this.bipedHead.addChild(this.hat1);
        this.bipedHead.addChild(this.hat2);
        this.bipedHead.addChild(this.hat3);
        this.bipedHead.addChild(this.hat4);
        this.bipedHead.addChild(this.hat5);
        this.bipedHead.addChild(this.hat6);
        this.bipedHead.addChild(this.hatpoof);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}