package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentVanishingCurse extends Enchantment
{
    public EnchantmentVanishingCurse()
    {
        super(75, 1, EnumEnchantmentType.all);
        setName("vanishing_curse");
    }
    
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 25;
    }
    
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 50;
    }
    
    public int getMaxLevel()
    {
        return 1;
    }
    
    public boolean isCurse()
    {
        return true;
    }
}