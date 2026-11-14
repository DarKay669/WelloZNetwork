package fr.welloznetwork.wellozmod.mixins.entity.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryGrades;
import net.minecraft.entity.player.EntityPlayerMP;

@Mixin(EntityPlayerMP.class)
public class MixinEntityPlayerMP
{
    @Inject(method = "closeContainer", at = @At("HEAD"))
    private void newCloseContainer(CallbackInfo callbackInfo)
    {
        WelloZMod.proxy.getServerEventRegister().playersClosedContainers.add((EntityPlayerMP) (Object) this);
    }
    
    @Inject(method = "canCommandSenderUseCommand(ILjava/lang/String;)Z", at = @At("HEAD"), cancellable = true)
    private void newCanCommandSenderUseCommand(int permLevel, String commandName, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        callbackInfoReturnable.setReturnValue(RegistryGrades.getGrade((EntityPlayerMP) (Object) this).ordinal() >= permLevel);
    }
}