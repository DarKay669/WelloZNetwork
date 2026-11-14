package fr.welloznetwork.wellozmod.mixins.item;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;

@Mixin(ItemSimpleFoiled.class)
public class MixinItemSimpleFoiled
{
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.rare;
    }
}