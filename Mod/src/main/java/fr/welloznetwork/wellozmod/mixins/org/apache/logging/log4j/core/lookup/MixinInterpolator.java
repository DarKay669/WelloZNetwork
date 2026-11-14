package fr.welloznetwork.wellozmod.mixins.org.apache.logging.log4j.core.lookup;

import java.util.Map;

import org.apache.logging.log4j.core.lookup.Interpolator;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Interpolator.class)
public class MixinInterpolator
{
    @Shadow
    private Map<String, StrLookup> lookups;
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(StrLookup strLookup, CallbackInfo callbackInfo)
    {
        if (this.lookups.containsKey("jndi"))
        {
            this.lookups.remove("jndi");
        }
    }
}