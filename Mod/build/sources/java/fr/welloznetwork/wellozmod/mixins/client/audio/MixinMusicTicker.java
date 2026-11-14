package fr.welloznetwork.wellozmod.mixins.client.audio;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.client.ui.UICredits;
import fr.welloznetwork.wellozmod.client.ui.UIMainMenu;
import fr.welloznetwork.wellozmod.client.ui.UIThanks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;

@Mixin(MusicTicker.class)
public class MixinMusicTicker
{
    @Shadow
    private Minecraft field_147677_b;
    
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void newUpdate(CallbackInfo callbackInfo)
    {
        if (this.field_147677_b.currentScreen instanceof UIMainMenu || this.field_147677_b.currentScreen instanceof UICredits || this.field_147677_b.currentScreen instanceof UIThanks)
        {
            callbackInfo.cancel();
        }
    }
}