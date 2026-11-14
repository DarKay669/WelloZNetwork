package fr.welloznetwork.wellozmod.mixins.village;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.village.Village;
import net.minecraft.world.World;

@Mixin(Village.class)
public class MixinVillage
{
    @Shadow
    private World worlObj;
    
    @Inject(method = "isBlockDoor", at = @At("HEAD"), cancellable = true)
    private void newIsBlockDoor(int x, int y, int z, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (!this.worlObj.blockExists(x, y, z))
        {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}