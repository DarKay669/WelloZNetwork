package fr.welloznetwork.wellozmod.mixins.inventory;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.init.RegistryEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@Mixin(Container.class)
public abstract class MixinContainer
{
    @Shadow
    public List inventorySlots;
    
    @Shadow
    public abstract Slot getSlot(int slotId);
    
    @Inject(method = "slotClick", at = @At("HEAD"), cancellable = true)
    private void getBindingCurseModifier(int slotId, int mouseButton, int shiftHeld, EntityPlayer entityPlayer, CallbackInfoReturnable<ItemStack> callbackInfoReturnable)
    {
        if (slotId >= 5 && slotId <= 8)
        {
            Slot slot = this.getSlot(slotId);
            
            if (slot != null)
            {
                ItemStack itemStack = slot.getStack();
                
                if (itemStack != null && EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.BINDING_CURSE.effectId, itemStack) > 0)
                {
                    if (!entityPlayer.capabilities.isCreativeMode)
                    {                        
                        callbackInfoReturnable.setReturnValue(itemStack);
                        callbackInfoReturnable.cancel();
                    }
                }
            }
        }
    }
}