package fr.welloznetwork.wellozmod.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelElf extends ModelBase
{
    private ModelRenderer head, nose, earLeft, earRight, chest, pants, rightarm, leftarm, rightleg, rightshoe, leftleg, leftshoe, hatbrim, hat1, hat2, hat3, hat4, hat5, hat6, hatpoof;
    private float hatX = -0.2F;
    
    public ModelElf()
    {
        this.textureWidth = 64;
        this.textureHeight = 128;
        
        this.head = new ModelRenderer(this, 0, 53);
        this.head.addBox(-3.0F, -8.0F, -3.0F, 6, 6, 6);
        this.head.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.head.setTextureSize(64, 128);
        this.head.mirror = true;
        setRotation(this.head, 0.0F, 0.0F, 0.0F);
        
        this.nose = new ModelRenderer(this, 50, 29);
        this.nose.addBox(-0.5F, -11.0F, -5.0F, 1, 1, 3);
        this.nose.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.nose.setTextureSize(64, 128);
        this.nose.mirror = true;
        setRotation(this.nose, 0.0F, 0.0F, 0.0F);
        
        this.earRight = new ModelRenderer(this, 56, 0);
        this.earRight.addBox(-0.6F, -14.5F, -3.5F, 1, 4, 1);
        this.earRight.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.earRight.setTextureSize(64, 128);
        this.earRight.mirror = true;
        setRotation(this.earRight, -0.3141593F, 0.0F, 0.2617994F);
        
        this.earLeft = new ModelRenderer(this, 56, 0);
        this.earLeft.addBox(-0.3F, -14.5F, -3.5F, 1, 4, 1);
        this.earLeft.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.earLeft.setTextureSize(64, 128);
        this.earLeft.mirror = true;
        setRotation(this.earLeft, -0.3141593F, 0.0F, -0.2617994F);
        
        this.chest = new ModelRenderer(this, 19, 41);
        this.chest.addBox(0.0F, 0.0F, 0.0F, 7, 7, 4);
        this.chest.setRotationPoint(-3.5F, 4.0F, -2.5F);
        this.chest.setTextureSize(64, 128);
        this.chest.mirror = true;
        setRotation(this.chest, 0.0F, 0.0F, 0.0F);
        
        this.pants = new ModelRenderer(this, 15, 16);
        this.pants.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4);
        this.pants.setRotationPoint(0.0F, 11.0F, -0.5F);
        this.pants.setTextureSize(64, 128);
        this.pants.mirror = true;
        setRotation(this.pants, 0.0F, 0.0F, 0.0F);
        
        this.rightarm = new ModelRenderer(this, 40, 16);
        this.rightarm.addBox(-3.0F, -2.0F, -2.0F, 3, 9, 3);
        this.rightarm.setRotationPoint(-3.5F, 6.0F, 0.0F);
        this.rightarm.setTextureSize(64, 128);
        this.rightarm.mirror = true;
        setRotation(this.rightarm, 0.0F, 0.0F, 0.1396263F);
        
        this.leftarm = new ModelRenderer(this, 40, 16);
        this.leftarm.addBox(-1.0F, -2.0F, -2.0F, 3, 9, 3);
        this.leftarm.setRotationPoint(4.5F, 6.0F, 0.0F);
        this.leftarm.setTextureSize(64, 128);
        this.leftarm.mirror = true;
        setRotation(this.leftarm, 0.0F, 0.0F, -0.1396263F);
        
        this.rightleg = new ModelRenderer(this, 0, 16);
        this.rightleg.addBox(-2.0F, 0.0F, -2.0F, 3, 9, 3);
        this.rightleg.setRotationPoint(-1.7F, 15.0F, 0.0F);
        this.rightleg.setTextureSize(64, 128);
        this.rightleg.mirror = true;
        setRotation(this.rightleg, 0.0F, 0.0F, 0.0F);
        
        this.rightshoe = new ModelRenderer(this, 0, 29);
        this.rightshoe.addBox(-5.3F, -8.5F, -4.4F, 4, 3, 6);
        this.rightshoe.setRotationPoint(2.7F, 15.0F, 0.0F);
        this.rightshoe.setTextureSize(64, 128);
        this.rightshoe.mirror = true;
        setRotation(this.rightshoe, 0.0F, 0.0F, 0.0F);
        
        this.leftleg = new ModelRenderer(this, 0, 16);
        this.leftleg.addBox(-2.0F, 0.0F, -2.0F, 3, 9, 3);
        this.leftleg.setRotationPoint(2.7F, 15.0F, 0.0F);
        this.leftleg.setTextureSize(64, 128);
        this.leftleg.mirror = true;
        setRotation(this.leftleg, 0.0F, 0.0F, 0.0F);
        
        this.leftshoe = new ModelRenderer(this, 0, 29);
        this.leftshoe.addBox(-5.3F, -8.5F, -4.4F, 4, 3, 6);
        this.leftshoe.setRotationPoint(2.7F, 15.0F, 0.0F);
        this.leftshoe.setTextureSize(64, 128);
        this.leftshoe.mirror = true;
        setRotation(this.leftshoe, 0.0F, 0.0F, 0.0F);
        
        this.hatbrim = new ModelRenderer(this, 25, 2);
        this.hatbrim.addBox(0.0F, -6.0F, 0.0F, 7, 1, 8);
        this.hatbrim.setRotationPoint(-3.5F, -2.8F, -5.0F);
        this.hatbrim.setTextureSize(64, 128);
        this.hatbrim.mirror = true;
        setRotation(this.hatbrim, -0.122173F, 0.0F, 0.0F);
        
        this.hat1 = new ModelRenderer(this, 0, 0);
        this.hat1.addBox(0.0F, -6.0F, this.hatX, 6, 3, 6);
        this.hat1.setRotationPoint(-3.0F, -5.0F, -3.5F);
        this.hat1.setTextureSize(64, 128);
        this.hat1.mirror = true;
        setRotation(this.hat1, -0.1745329F, 0.0F, 0.0F);
        
        this.hat2 = new ModelRenderer(this, 0, 0);
        this.hat2.addBox(0.0F, -6.0F, this.hatX - 1.8F, 5, 3, 5);
        this.hat2.setRotationPoint(-2.5F, -6.8F, -2.1F);
        this.hat2.setTextureSize(64, 128);
        this.hat2.mirror = true;
        setRotation(this.hat2, -0.5410521F, 0.0F, 0.0F);
        
        this.hat3 = new ModelRenderer(this, 0, 0);
        this.hat3.addBox(0.0F, -6.0F, this.hatX - 2.8F, 4, 3, 4);
        this.hat3.setRotationPoint(-2.0F, -7.666667F, -0.5F);
        this.hat3.setTextureSize(64, 128);
        this.hat3.mirror = true;
        setRotation(this.hat3, -0.6981317F, 0.0F, 0.0F);
        
        this.hat4 = new ModelRenderer(this, 0, 0);
        this.hat4.addBox(0.0F, -3.6F, this.hatX - 4.0F, 3, 2, 3);
        this.hat4.setRotationPoint(-1.5F, -8.5F, 1.5F);
        this.hat4.setTextureSize(64, 128);
        this.hat4.mirror = true;
        setRotation(this.hat4, -0.9948377F, 0.0F, 0.0F);
        
        this.hat5 = new ModelRenderer(this, 0, 0);
        this.hat5.addBox(1.0F, -6.0F, this.hatX - 4.8F, 2, 3, 2);
        this.hat5.setRotationPoint(-1.0F, -9.0F, 3.4F);
        this.hat5.setTextureSize(64, 128);
        this.hat5.mirror = true;
        setRotation(this.hat5, -1.012291F, 0.0F, 0.0F);
        
        this.hat6 = new ModelRenderer(this, 0, 0);
        this.hat6.addBox(0.0F, -6.0F, this.hatX, 1, 2, 1);
        this.hat6.setRotationPoint(-0.5F, -9.0F, 5.0F);
        this.hat6.setTextureSize(64, 128);
        this.hat6.mirror = true;
        setRotation(this.hat6, -1.27409F, 0.0F, 0.0F);
        
        this.hatpoof = new ModelRenderer(this, 0, 39);
        this.hatpoof.addBox(0.0F, -6.0F, 0.0F, 2, 2, 2);
        this.hatpoof.setRotationPoint(-1.0F, -9.5F, 4.6F);
        this.hatpoof.setTextureSize(64, 128);
        this.hatpoof.mirror = true;
        setRotation(this.hatpoof, 0.0F, 0.0F, 0.0F);
        
        this.head.addChild(this.earLeft);
        this.head.addChild(this.earRight);
        this.head.addChild(this.nose);
        this.head.addChild(this.hatbrim);
        this.head.addChild(this.hat1);
        this.head.addChild(this.hat2);
        this.head.addChild(this.hat3);
        this.leftleg.addChild(this.leftshoe);
        this.rightleg.addChild(this.rightshoe);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.head.render(f5);
        this.chest.render(f5);
        this.pants.render(f5);
        this.rightarm.render(f5);
        this.leftarm.render(f5);
        this.rightleg.render(f5);
        this.leftleg.render(f5);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        this.head.rotateAngleX = f4 / 57.295776F;
        this.head.rotateAngleY = f3 / 57.295776F;
        
        this.leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        this.leftleg.rotateAngleY = 0.0F;
        
        this.rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
        this.rightleg.rotateAngleY = 0.0F;
        
        this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        this.leftarm.rotateAngleZ = 0.0F;
        
        this.rightarm.rotateAngleX = MathHelper.cos(f * 0.6663F + 3.1415927F) * 1.4F * f1;
        this.rightarm.rotateAngleZ = 0.0F;
    }
}