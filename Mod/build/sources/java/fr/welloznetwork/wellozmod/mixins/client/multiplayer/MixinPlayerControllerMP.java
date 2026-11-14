package fr.welloznetwork.wellozmod.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.command.CommandSpectator;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP
{
    @Inject(method = "onPlayerDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void isSpectatorDestroyBlock(int x, int y, int z, int side, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Inject(method = "clickBlock", at = @At("HEAD"), cancellable = true)
    private void isSpectatorClickBlock(int x, int y, int z, int side, CallbackInfo callbackInfo)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = "sendUseItem", at = @At("HEAD"), cancellable = true)
    private void isSpectatorSendUseItem(EntityPlayer entityPlayer, World world, ItemStack itemStack, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Inject(method = "attackEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;attackTargetEntityWithCurrentItem(Lnet/minecraft/entity/Entity;)V"), cancellable = true)
    private void isSpectatorAttackEntity(EntityPlayer entityPlayer, Entity entity, CallbackInfo callbackInfo)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = "interactWithEntitySendPacket", at = @At("TAIL"), cancellable = true)
    private void isSpectatorInteractWithEntitySendPacket(EntityPlayer entityPlayer, Entity entity, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}