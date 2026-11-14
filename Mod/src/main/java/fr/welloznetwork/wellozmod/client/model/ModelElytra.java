package fr.welloznetwork.wellozmod.client.model;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.entity.IClientEntityPlayer;
import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

@SideOnly(Side.CLIENT)
public class ModelElytra extends ModelBase
{
    private ModelRenderer rightWing, leftWing = new ModelRenderer(this, 22, 0);
    
    public ModelElytra()
    {
        this.leftWing.addBox(-10.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
        
        this.rightWing = new ModelRenderer(this, 22, 0);
        this.rightWing.mirror = true;
        this.rightWing.addBox(0.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
    }
    
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        this.leftWing.render(scale);
        this.rightWing.render(scale);
    }
    
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entity)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        
        float f = 0.2617994F;
        float f1 = -0.2617994F;
        float f2 = 0.0F;
        float f3 = 0.0F;
        
        if (entity instanceof IEntityPlayer && ((IEntityPlayer) entity).isElytraFlying())
        {
            float f4 = 1.0F;
            
            if (entity.motionY < 0.0D)
            {
                Vec3 vec3 = (Vec3.createVectorHelper(entity.motionX, entity.motionY, entity.motionZ)).normalize();
                
                f4 = 1.0F - (float) Math.pow(-vec3.yCoord, 1.5D);
            }
            
            f = f4 * 0.34906584F + (1.0F - f4) * f;
            f1 = f4 * -((float) Math.PI / 2.0F) + (1.0F - f4) * f1;
        }
        
        else if (entity.isSneaking())
        {
            f = ((float) Math.PI * 2.0F / 9.0F);
            f1 = -((float) Math.PI / 4.0F);
            f2 = 3.0F;
            f3 = 0.08726646F;
        }
        
        this.leftWing.rotationPointX = 5.0F;
        this.leftWing.rotationPointY = f2;
        
        if (entity instanceof IClientEntityPlayer)
        {
            IClientEntityPlayer iClientElytraPlayer = (IClientEntityPlayer) entity;
            
            iClientElytraPlayer.setRotateElytraX((float) (iClientElytraPlayer.getRotateElytraX() + (f - iClientElytraPlayer.getRotateElytraX()) * 0.1D));
            iClientElytraPlayer.setRotateElytraY((float) (iClientElytraPlayer.getRotateElytraY() + (f3 - iClientElytraPlayer.getRotateElytraY()) * 0.1D));
            iClientElytraPlayer.setRotateElytraZ((float) (iClientElytraPlayer.getRotateElytraZ() + (f1 - iClientElytraPlayer.getRotateElytraZ()) * 0.1D));
            
            this.leftWing.rotateAngleX = iClientElytraPlayer.getRotateElytraX();
            this.leftWing.rotateAngleY = iClientElytraPlayer.getRotateElytraY();
            this.leftWing.rotateAngleZ = iClientElytraPlayer.getRotateElytraZ();
        }
        
        else
        {
            this.leftWing.rotateAngleX = f;
            this.leftWing.rotateAngleZ = f1;
            this.leftWing.rotateAngleY = f3;
        }
        
        this.rightWing.rotationPointX = -this.leftWing.rotationPointX;
        this.rightWing.rotateAngleY = -this.leftWing.rotateAngleY;
        this.rightWing.rotationPointY = this.leftWing.rotationPointY;
        this.rightWing.rotateAngleX = this.leftWing.rotateAngleX;
        this.rightWing.rotateAngleZ = -this.leftWing.rotateAngleZ;
    }
    
    public void setLivingAnimations(EntityLivingBase entityLivingBase, float netHeadYaw, float headPitch, float partialTicks)
    {
        super.setLivingAnimations(entityLivingBase, netHeadYaw, headPitch, partialTicks);
    }
}