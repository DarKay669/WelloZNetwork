package fr.welloznetwork.wellozmod.mixins.village;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.village.VillageCollection;
import net.minecraft.world.World;

@Mixin(VillageCollection.class)
public class MixinVillageCollection
{
    @Shadow
    private World worldObj;
    
    @Inject(method = "isWoodenDoorAt", at = @At("HEAD"), cancellable = true)
    private void newIsWoodenDoorAt(int x, int y, int z, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (!this.worldObj.blockExists(x, y, z))
        {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}