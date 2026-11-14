package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentMending extends Enchantment
{
    public EnchantmentMending()
    {
        super(68, 0, EnumEnchantmentType.breakable);
        setName("mending");
    }
    
    public int getMaxLevel()
    {
        return 1;
    }
    
    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 25;
    }
    
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 50;
    }
}