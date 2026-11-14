package fr.welloznetwork.wellozmod.mixins.cpw.mods.fml.common;

import java.net.MalformedURLException;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.common.ModContainer;

@Mixin(value = Loader.class, remap = false)
public class MixinLoader
{
    @Shadow
    private List<ModContainer> mods;
    
    @Shadow
    private ModClassLoader modClassLoader;
    
    @Inject(method = "loadMods", at = @At(value = "INVOKE", target = "Lcpw/mods/fml/common/LoadController;transition(Lcpw/mods/fml/common/LoaderState;Z)V", ordinal = 1), remap = false)
    private void newLoadMods(CallbackInfo callbackInfo)
    {
        for (ModContainer modContainer : this.mods)
        {
            try
            {
                this.modClassLoader.addFile(modContainer.getSource());
            }
            
            catch (MalformedURLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}