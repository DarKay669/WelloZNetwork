package fr.welloznetwork.wellozmod.mixins.entity.passive;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.init.RegistryPotions;
import net.minecraft.entity.passive.EntitySquid;

@Mixin(EntitySquid.class)
public class MixinEntitySquid
{
    @Inject(method = "onLivingUpdate", at = @At("TAIL"))
    private void levitationEffect(CallbackInfo callbackInfo)
    {
        EntitySquid entitySquid = (EntitySquid) (Object) this;
        
        if (!entitySquid.worldObj.isRemote)
        {
            entitySquid.motionX = 0.0D;
            entitySquid.motionZ = 0.0D;
            
            if (entitySquid.isPotionActive(RegistryPotions.LEVITATION))
            {
                entitySquid.motionY += 0.05D * (double) (entitySquid.getActivePotionEffect(RegistryPotions.LEVITATION).getAmplifier() + 1) - entitySquid.motionY;
            }
            
            else
            {
                entitySquid.motionY -= 0.08D;
            }
            
            entitySquid.motionY *= 0.9800000190734863D;
        }
        
        entitySquid.squidPitch = (float) ((double) entitySquid.squidPitch + (double) (-90.0F - entitySquid.squidPitch) * 0.02D);
    }
}