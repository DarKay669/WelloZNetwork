package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentBindingCurse extends Enchantment
{
    public EnchantmentBindingCurse()
    {
        super(73, 1, EnumEnchantmentType.armor);
        setName("binding_curse");
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