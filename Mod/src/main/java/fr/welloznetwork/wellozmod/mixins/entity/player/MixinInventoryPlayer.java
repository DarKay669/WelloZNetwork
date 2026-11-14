package fr.welloznetwork.wellozmod.mixins.entity.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.init.RegistryEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

@Mixin(InventoryPlayer.class)
public abstract class MixinInventoryPlayer implements IInventory
{
    @Shadow
    public EntityPlayer player;
    
    @Inject(method = "getCurrentItem", at = @At(value = "HEAD"), cancellable = true)
    public void isSpectatorGetCurrentItem(CallbackInfoReturnable<ItemStack> callbackInfoReturnable)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfoReturnable.setReturnValue(null);
        }
    }
    
    @Inject(method = "dropAllItems", at = @At("HEAD"))
    private void getVanishingCurseModifier(CallbackInfo callbackInfo)
    {
        InventoryPlayer inventoryPlayer = (InventoryPlayer) (Object) this;
        
        for (int i = 0; i < inventoryPlayer.mainInventory.length; i++)
        {
            ItemStack itemStack = inventoryPlayer.mainInventory[i];
            
            if (itemStack != null && EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.VANISHING_CURSE.effectId, itemStack) > 0)
            {
                inventoryPlayer.mainInventory[i] = null;
            }
        }
        
        for (int i = 0; i < inventoryPlayer.armorInventory.length; i++)
        {
            ItemStack itemStack = inventoryPlayer.armorInventory[i];
            
            if (itemStack != null && EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.VANISHING_CURSE.effectId, itemStack) > 0)
            {
                inventoryPlayer.armorInventory[i] = null;
            }
        }
    }
}