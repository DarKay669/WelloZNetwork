package fr.welloznetwork.wellozmod.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelAquaCreeper extends ModelBase
{
    private ModelRenderer head, body, back1, back2, back3, back4, top, side1, side2;
    
    public ModelAquaCreeper()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8);
        this.head.setRotationPoint(0.0F, 18.0F, -7.0F);
        this.setRotation(this.head, 0.0F, 0.0F, 0.0F);
        
        this.body = new ModelRenderer(this, 32, 0);
        this.body.addBox(-2.0F, -4.0F, 0.0F, 4, 8, 12);
        this.body.setRotationPoint(0.0F, 18.0F, -7.0F);
        this.setRotation(this.body, 0.0F, 0.0F, 0.0F);
        
        this.back1 = new ModelRenderer(this, 0, 16);
        this.back1.addBox(0.0F, 0.0F, 0.0F, 1, 4, 5);
        this.back1.setRotationPoint(1.5F, 18.0F, 5.0F);
        this.setRotation(this.back1, 0.0F, 0.6981317F, 0.0F);
        
        this.back2 = new ModelRenderer(this, 0, 16);
        this.back2.addBox(0.0F, -2.0F, 0.0F, 1, 4, 5);
        this.back2.setRotationPoint(1.5F, 16.0F, 5.0F);
        this.setRotation(this.back2, 0.0F, 0.6981317F, 0.0F);
        
        this.back3 = new ModelRenderer(this, 0, 16);
        this.back3.addBox(-1.0F, -2.0F, 0.0F, 1, 4, 5);
        this.back3.setRotationPoint(-1.5F, 16.0F, 5.0F);
        this.setRotation(this.back3, 0.0F, -0.6981317F, 0.0F);
        
        this.back4 = new ModelRenderer(this, 0, 16);
        this.back4.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 5);
        this.back4.setRotationPoint(-1.5F, 18.0F, 5.0F);
        this.setRotation(this.back4, 0.0F, -0.6981317F, 0.0F);
        
        this.top = new ModelRenderer(this, 12, 16);
        this.top.addBox(-0.5F, -4.0F, 0.0F, 1, 4, 8);
        this.top.setRotationPoint(0.0F, 18.0F, -4.0F);
        this.setRotation(this.top, 0.5235988F, 0.0F, 0.0F);
        
        this.side1 = new ModelRenderer(this, 0, 16);
        this.side1.addBox(0.0F, -2.0F, 0.0F, 1, 4, 5);
        this.side1.setRotationPoint(1.5F, 18.0F, -7.0F);
        this.setRotation(this.side1, 0.0F, 0.3490659F, 0.0F);
        
        this.side2 = new ModelRenderer(this, 0, 16);
        this.side2.addBox(-1.0F, -2.0F, 0.0F, 1, 4, 5);
        this.side2.setRotationPoint(-1.5F, 18.0F, -7.0F);
        this.setRotation(this.side2, 0.0F, -0.3490659F, 0.0F);
    }
    
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float partialTicks)
    {
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, partialTicks);
        
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, partialTicks, entity);
        
        this.head.render(partialTicks);
        this.body.render(partialTicks);
        this.back1.render(partialTicks);
        this.back2.render(partialTicks);
        this.back3.render(partialTicks);
        this.back4.render(partialTicks);
        this.top.render(partialTicks);
        this.side1.render(partialTicks);
        this.side2.render(partialTicks);
    }
    
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float partialTicks, Entity entity)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, partialTicks, entity);
        
        this.back1.rotateAngleY = -MathHelper.cos(limbSwing * 1.2F) * 1.2F * limbSwingAmount + 0.6981317F;
        this.back2.rotateAngleY = -MathHelper.cos(limbSwing * 1.2F) * 1.2F * limbSwingAmount + 0.6981317F;
        this.back3.rotateAngleY = -MathHelper.cos(limbSwing * 1.2F) * 1.2F * limbSwingAmount - 0.6981317F;
        this.back4.rotateAngleY = -MathHelper.cos(limbSwing * 1.2F) * 1.2F * limbSwingAmount - 0.6981317F;
        this.side1.rotateAngleY = MathHelper.cos(limbSwing * 1.0F) * 0.5F * limbSwingAmount + 0.3490659F;
        this.side2.rotateAngleY = MathHelper.cos(limbSwing * 1.0F) * 0.5F * limbSwingAmount - 0.3490659F;
    }
    
    private void setRotation(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}