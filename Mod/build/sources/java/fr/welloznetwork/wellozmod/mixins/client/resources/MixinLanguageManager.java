package fr.welloznetwork.wellozmod.mixins.client.resources;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.LanguageManager;

@Mixin(LanguageManager.class)
public class MixinLanguageManager
{
    @Inject(method = "parseLanguageMetadata", at = @At("RETURN"))
    private void afterParseLanguageMetadata(List<IResourcePack> list, CallbackInfo callbackInfo)
    {        
        ((IMixinLanguageManagerAccessor) this).getLanguageMap().entrySet().removeIf(entry -> 
        {
            return !(entry.getKey().startsWith("en_US") || entry.getKey().startsWith("fr_FR"));
        });
    }
}