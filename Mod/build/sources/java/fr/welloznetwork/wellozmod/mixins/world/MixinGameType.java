package fr.welloznetwork.wellozmod.mixins.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.command.CommandSpectator;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.WorldSettings.GameType;

@Mixin(GameType.class)
public class MixinGameType
{
    @Inject(method = "configurePlayerCapabilities", at = @At("HEAD"), cancellable = true)
    public void configurePlayerCapabilities1(PlayerCapabilities playerCapabilities, CallbackInfo callbackInfo)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfo.cancel();
            
            playerCapabilities.allowFlying = true;
            playerCapabilities.isCreativeMode = false;
            playerCapabilities.disableDamage = true;
            playerCapabilities.isFlying = true;
        }
    }
    
    @Inject(method = "isAdventure", at = @At("HEAD"), cancellable = true)
    private void isAdventureOrSpectator(CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}