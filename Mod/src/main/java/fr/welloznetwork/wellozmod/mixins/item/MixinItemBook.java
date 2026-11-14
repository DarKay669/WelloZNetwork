package fr.welloznetwork.wellozmod.mixins.item;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;

@Mixin(ItemBook.class)
public class MixinItemBook
{
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.uncommon;
    }
}