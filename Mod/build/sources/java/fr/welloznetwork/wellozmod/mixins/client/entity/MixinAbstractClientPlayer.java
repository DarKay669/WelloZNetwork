package fr.welloznetwork.wellozmod.mixins.client.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.ACape;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeAuthor;
import fr.welloznetwork.wellozmod.client.entity.IClientEntityPlayer;
import fr.welloznetwork.wellozmod.init.RegistryCapes;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

@Mixin(AbstractClientPlayer.class)
public class MixinAbstractClientPlayer implements IClientEntityPlayer
{
    private float rotateElytraX, rotateElytraY, rotateElytraZ;
    
    public float getRotateElytraX()
    {
        return this.rotateElytraX;
    }
    
    public float getRotateElytraY()
    {
        return this.rotateElytraY;
    }
    
    public float getRotateElytraZ()
    {
        return this.rotateElytraZ;
    }
    
    public void setRotateElytraX(float rotateElytraX)
    {
        this.rotateElytraX = rotateElytraX;
    }
    
    public void setRotateElytraY(float rotateElytraY)
    {
        this.rotateElytraY = rotateElytraY;
    }
    
    public void setRotateElytraZ(float rotateElytraZ)
    {
        this.rotateElytraZ = rotateElytraZ;
    }
    
    @Inject(method = "getLocationCape", at = @At("HEAD"), cancellable = true)
    private void setLocationCape(CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable)
    {
        AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) (Object) this;
        ACape cape = RegistryCapes.getCape(abstractClientPlayer.getCommandSenderName());
        
        if (cape != null)
        {
            callbackInfoReturnable.setReturnValue(cape.getLocationCape());
        }
        
        if (Constants.MOD_AUTHOR.equalsIgnoreCase(abstractClientPlayer.getCommandSenderName()))
        {
            callbackInfoReturnable.setReturnValue(new CapeAuthor().getLocationCape());
        }
    }
}