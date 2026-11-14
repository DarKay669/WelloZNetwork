package fr.welloznetwork.wellozmod.mixins.util;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.ChatAllowedCharacters;

@Mixin(ChatAllowedCharacters.class)
public class MixinChatAllowedCharacters
{
    @Inject(method = "isAllowedCharacter", at = @At("HEAD"), cancellable = true)
    private static void newIsAllowedCharacter(char character, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (character == '\u00A7')
        {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}