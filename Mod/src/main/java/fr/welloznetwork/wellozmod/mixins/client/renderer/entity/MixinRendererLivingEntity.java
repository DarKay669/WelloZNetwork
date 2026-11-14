package fr.welloznetwork.wellozmod.mixins.client.renderer.entity;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.init.RegistryPotions;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity extends Render
{
    @Inject(method = "renderModel", at = @At("HEAD"), cancellable = true)
    private void renderOutlines(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo callbackInfo)
    {
        if (entityLivingBase.isPotionActive(RegistryPotions.GLOWING) || CommandSpectator.isSpectator())
        {
            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
            GL11.glLineWidth(10.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            
            ((IAccessorRendererLivingEntity) (Object) this).getMainModel().render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            GL11.glPopAttrib();
        }
    }
}