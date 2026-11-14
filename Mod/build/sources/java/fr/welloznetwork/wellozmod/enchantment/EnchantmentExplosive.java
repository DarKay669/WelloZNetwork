package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentExplosive extends Enchantment
{
    public EnchantmentExplosive()
    {
        super(64, 2, EnumEnchantmentType.armor);
        setName("explosive");
    }
    
    public int getMaxLevel()
    {
        return 3;
    }
    
    public int getMinEnchantability(int enchantmentLevel)
    {
        switch (enchantmentLevel)
        {
            case 1:
                return 15;
                
            case 2:
                return 25;
        }
        
        return 100;
    }
    
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return getMinEnchantability(enchantmentLevel) + 10;
    }
}