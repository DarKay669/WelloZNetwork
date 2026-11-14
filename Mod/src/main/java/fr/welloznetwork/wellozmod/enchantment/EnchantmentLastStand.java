package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentLastStand extends Enchantment
{
    public EnchantmentLastStand()
    {
        super(67, 2, EnumEnchantmentType.armor);
        setName("last_stand");
    }
    
    public int getMaxLevel()
    {
        return 2;
    }
    
    public int getMinEnchantability(int enchantmentLevel)
    {
        switch (enchantmentLevel)
        { 
            case 1:
                return 15;
        }
        
        return 25;
    }
    
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return getMinEnchantability(enchantmentLevel) + 10;
    }
}