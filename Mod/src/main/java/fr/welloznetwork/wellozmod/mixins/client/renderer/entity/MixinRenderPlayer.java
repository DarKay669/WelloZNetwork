package fr.welloznetwork.wellozmod.mixins.client.renderer.entity;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer
{    
    @Inject(method = "rotateCorpse(Lnet/minecraft/client/entity/AbstractClientPlayer;FFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;rotateCorpse(Lnet/minecraft/entity/EntityLivingBase;FFF)V", shift = Shift.AFTER))
    private void rotateForElytra(AbstractClientPlayer abstractClientPlayer, float strafe, float forward, float partialTicks, CallbackInfo callbackInfo)
    {
        if (abstractClientPlayer instanceof IEntityPlayer)
        {
            if (((IEntityPlayer) abstractClientPlayer).isElytraFlying())
            {
                float f = ((IEntityPlayer) abstractClientPlayer).getTicksElytraFlying() + partialTicks;
                float f1 = MathHelper.clamp_float(f * f / 100.0F, 0.0F, 0.0F);
                
                GL11.glRotatef(f1 * (-90.0F - abstractClientPlayer.rotationPitch), 1.0F, 0.0F, 0.0F);
                
                Vec3 vec3 = abstractClientPlayer.getLook(partialTicks);
                
                double d0 = abstractClientPlayer.motionX * abstractClientPlayer.motionX + abstractClientPlayer.motionZ * abstractClientPlayer.motionZ;
                double d1 = vec3.xCoord * vec3.xCoord + vec3.zCoord * vec3.zCoord;
                
                if (d0 > 0.0D && d1 > 0.0D)
                {
                    double d2 = (abstractClientPlayer.motionX * vec3.xCoord + abstractClientPlayer.motionZ * vec3.zCoord) / (Math.sqrt(d0) * Math.sqrt(d1));
                    double d3 = abstractClientPlayer.motionX * vec3.zCoord - abstractClientPlayer.motionZ * vec3.xCoord;
                    
                    GL11.glRotatef((float) (Math.signum(d3) * Math.acos(Math.min(d2, 1))) * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
                }
            }
        }
    }
}