package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentSweepingEdge extends Enchantment
{
    public EnchantmentSweepingEdge()
    {
        super(74, 2, EnumEnchantmentType.weapon);
        setName("sweeping_edge");
    }
    
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 5 + (enchantmentLevel - 1) * 9;
    }
    
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }
    
    public int getMaxLevel()
    {
        return 3;
    }
    
    public static float getSweepingDamageRatio(int enchantmentLevel)
    {
        return 1.0F - 1.0F / (float) (enchantmentLevel + 1);
    }
}