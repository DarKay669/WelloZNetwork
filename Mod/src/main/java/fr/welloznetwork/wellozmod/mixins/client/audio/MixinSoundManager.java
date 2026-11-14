package fr.welloznetwork.wellozmod.mixins.client.audio;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.audio.SoundManager;

@Mixin(SoundManager.class)
public class MixinSoundManager
{
    @Inject(method = "reloadSoundSystem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundManager;loadSoundSystem()V"))
    public void snooze(CallbackInfo callbackInfo) throws InterruptedException
    {
        Thread.sleep(333L);
    }
}