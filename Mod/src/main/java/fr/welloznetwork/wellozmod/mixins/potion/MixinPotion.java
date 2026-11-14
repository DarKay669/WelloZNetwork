package fr.welloznetwork.wellozmod.mixins.potion;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.potion.Potion;

@Mixin(Potion.class)
public class MixinPotion
{
    @Shadow
    @Final
    @Mutable
    private static Potion[] potionTypes;
    
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void init(CallbackInfo callbackInfo)
    {
        Potion[] newArray = new Potion[39];
        System.arraycopy(potionTypes, 0, newArray, 0, potionTypes.length);
        potionTypes = newArray;
    }
}