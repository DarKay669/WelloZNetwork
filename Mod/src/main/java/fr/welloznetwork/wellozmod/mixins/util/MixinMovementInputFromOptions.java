package fr.welloznetwork.wellozmod.mixins.util;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import fr.welloznetwork.wellozmod.init.RegistryEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.MovementInputFromOptions;

@Mixin(MovementInputFromOptions.class)
public class MixinMovementInputFromOptions
{
    @ModifyConstant(method = "updatePlayerMoveState", constant = @Constant(doubleValue = 0.3D))
    private double getSwiftSneakModifier(double constant)
    {
        return constant + (EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.SWIFT_SNEAK.effectId, Minecraft.getMinecraft().thePlayer.getEquipmentInSlot(2)) * 0.15);
    }
}