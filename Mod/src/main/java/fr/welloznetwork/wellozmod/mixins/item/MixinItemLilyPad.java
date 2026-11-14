package fr.welloznetwork.wellozmod.mixins.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemLilyPad;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ItemLilyPad.class)
public class MixinItemLilyPad
{
    @Inject(method = "onItemRightClick(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    void serverAuthoritativeOnItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer, CallbackInfoReturnable<ItemStack> callbackInfoReturnable)
    {
        if (world.isRemote)
        {
            callbackInfoReturnable.setReturnValue(itemStack);
        }
    }
}