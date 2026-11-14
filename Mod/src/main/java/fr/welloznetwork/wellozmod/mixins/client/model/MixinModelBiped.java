package fr.welloznetwork.wellozmod.mixins.client.model;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.client.renderer.entity.layers.LayerElytra;
import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import fr.welloznetwork.wellozmod.init.RegistryEmotes;
import fr.welloznetwork.wellozmod.mixins.client.IMixinMinecraftAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

@Mixin(ModelBiped.class)
public class MixinModelBiped extends ModelBase
{
    @Shadow
    public ModelRenderer bipedHead;
    
    @Shadow
    public ModelRenderer bipedHeadwear;
    
    @Shadow
    public ModelRenderer bipedBody;
    
    @Shadow
    public ModelRenderer bipedRightArm;
    
    @Shadow
    public ModelRenderer bipedLeftArm;
    
    @Shadow
    public ModelRenderer bipedRightLeg;
    
    @Shadow
    public ModelRenderer bipedLeftLeg;
    
    @Inject(method = "setRotationAngles", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;isRiding:Z", ordinal = 0))
    private void setElytraAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entity, CallbackInfo callbackInfo)
    {        
        if (entity instanceof IEntityPlayer && ((IEntityPlayer) entity).getTicksElytraFlying() > 4)
        {
            float f = 1.0F;
            
            limbSwing = ageInTicks;
            
            this.bipedHead.rotateAngleY = this.bipedHeadwear.rotateAngleY = netHeadYaw * 0.017453292F;
            this.bipedHead.rotateAngleX = this.bipedHeadwear.rotateAngleX = -((float) Math.PI / 4.0F);
            
            this.bipedBody.rotateAngleY = 0.0F;
            
            this.bipedRightArm.rotationPointZ = 0.0F;
            this.bipedRightArm.rotationPointX = -5.0F;
            
            this.bipedLeftArm.rotationPointZ = 0.0F;
            this.bipedLeftArm.rotationPointX = 5.0F;
            
            f = (float) (entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
            f = f / 0.2F;
            f = f * f * f;
            
            if (f < 1.0F)
            {
                f = 1.0F;
            }
            
            this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
            this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
            this.bipedRightArm.rotateAngleZ = 0.0F;
            this.bipedLeftArm.rotateAngleZ = 0.0F;
            this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
            this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
            this.bipedRightLeg.rotateAngleY = 0.0F;
            this.bipedLeftLeg.rotateAngleY = 0.0F;
            this.bipedRightLeg.rotateAngleZ = 0.0F;
            this.bipedLeftLeg.rotateAngleZ = 0.0F;
        }
    }
    
    @Inject(method = "setRotationAngles", at = @At("RETURN"))
    private void renderElytra(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entity, CallbackInfo callbackInfo)
    {
        if (entity instanceof IEntityPlayer && (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0 || entity != Minecraft.getMinecraft().renderViewEntity))
        {
            LayerElytra.doRenderLayer((EntityLivingBase) entity, limbSwing, limbSwingAmount, ((IMixinMinecraftAccessor) Minecraft.getMinecraft()).getTimer().renderPartialTicks, ageInTicks, netHeadYaw, headPitch, 0.0625F);
        }
    }
    
    @Inject(method = "setRotationAngles", at = @At("RETURN"))
    private void renderEmote(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entity, CallbackInfo callbackInfo)
    {
        RegistryEmotes.updateEmotes(entity, this);
    }
}