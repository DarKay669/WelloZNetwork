package fr.welloznetwork.wellozmod.mixins.inventory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.command.CommandSpectator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;

@Mixin(ContainerChest.class)
public abstract class MixinContainerChest extends Container
{
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/IInventory;openInventory()V"))
    private void ignoreOpenInventoryInSpectator(IInventory chestInventory, IInventory playerInventory, IInventory chestInventory1)
    {
        if (!(playerInventory instanceof InventoryPlayer) || !CommandSpectator.isSpectator())
        {
            chestInventory.openInventory();
        }
    }
    
    @Inject(method = "onContainerClosed", at = @At(value = "HEAD"), cancellable = true)
    private void ignoreCloseInventoryInSpectator(EntityPlayer entityPlayer, CallbackInfo callbackInfo)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfo.cancel();
        }
    }
}