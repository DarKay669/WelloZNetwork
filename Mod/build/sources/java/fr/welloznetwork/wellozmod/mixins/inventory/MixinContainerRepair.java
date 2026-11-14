package fr.welloznetwork.wellozmod.mixins.inventory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemStack;

@Mixin(ContainerRepair.class)
public class MixinContainerRepair
{
    @Redirect(method = {"updateRepairOutput", "updateItemName"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setStackDisplayName(Ljava/lang/String;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack setStackDisplayName(ItemStack itemStack, String displayName)
    {
        itemStack.setStackDisplayName(displayName);
        itemStack.stackTagCompound.getCompoundTag("display").setBoolean("Renamed", true);
        
        return itemStack;        
    }
}