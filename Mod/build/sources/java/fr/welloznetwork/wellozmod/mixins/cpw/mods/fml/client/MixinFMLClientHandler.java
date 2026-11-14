package fr.welloznetwork.wellozmod.mixins.cpw.mods.fml.client;

import java.util.concurrent.Semaphore;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import cpw.mods.fml.client.FMLClientHandler;
import fr.welloznetwork.wellozmod.client.ui.CustomSplashProgress;

@Mixin(value = FMLClientHandler.class, remap = false)
public class MixinFMLClientHandler
{
    @Redirect(method = "beginMinecraftLoading", at = @At(value = "INVOKE", target = "Lcpw/mods/fml/client/SplashProgress;start()V"))
    private void start()
    {
        CustomSplashProgress.start();
    }
    
    @Redirect(method = "haltGame", at = @At(value = "INVOKE", target = "Lcpw/mods/fml/client/SplashProgress;finish()V"))
    private void finish()
    {
        CustomSplashProgress.finish();
    }
    
    @Redirect(method = "finishMinecraftLoading", at = @At(value = "INVOKE", target = "Lcpw/mods/fml/client/SplashProgress;finish()V"))
    private void finish1()
    {
        CustomSplashProgress.finish();
    }
    
    @Redirect(method = "onInitializationComplete", at = @At(value = "INVOKE", target = "Lcpw/mods/fml/client/SplashProgress;finish()V"))
    private void finish2()
    {
        CustomSplashProgress.finish();
    }
    
    @Redirect(method = "processWindowMessages", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/Semaphore;tryAcquire()Z"))
    private boolean tryAcquire(Semaphore semaphore)
    {
        return CustomSplashProgress.mutex.tryAcquire();
    }
    
    @Redirect(method = "processWindowMessages", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/Semaphore;release()V"))
    private void release(Semaphore semaphore)
    {
        CustomSplashProgress.mutex.release();
    }
}