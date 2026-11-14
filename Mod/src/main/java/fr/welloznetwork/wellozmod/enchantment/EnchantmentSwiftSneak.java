package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class EnchantmentSwiftSneak extends Enchantment
{
    public EnchantmentSwiftSneak()
    {
        super(71, 1, EnumEnchantmentType.armor_legs);
        setName("swift_sneak");
    }
    
    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 25;
    }
    
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 50;
    }
    
    public int getMaxLevel()
    {
        return 3;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack itemStack)
    {
        return itemStack != null && itemStack.getItem() == Items.book; 
    }
}