package fr.welloznetwork.wellozmod.mixins.command;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.command.ICommandPermission;
import fr.welloznetwork.wellozmod.init.RegistryGrades;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

@Mixin(CommandBase.class)
public class MixinCommandBase
{
    @Inject(method = "canCommandSenderUseCommand", at = @At("HEAD"), cancellable = true)
    private void newCanCommandSenderUseCommand(ICommandSender iCommandSender, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if ((Object) this instanceof ICommandPermission && iCommandSender instanceof EntityPlayer)
        {
            callbackInfoReturnable.setReturnValue(RegistryGrades.getGrade(((EntityPlayer) iCommandSender).getUniqueID()).ordinal() >= ((ICommandPermission) (Object) this).getPermissionType().ordinal());
        }
    }
}