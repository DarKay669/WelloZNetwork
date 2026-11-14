package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentWitherHunter extends Enchantment
{
	public EnchantmentWitherHunter()
	{
		super(69, 4, EnumEnchantmentType.armor);
		setName("wither_hunter");
	}
	
	public int getMaxLevel() 
    {
        return 4;
    }
	
	public int getMinLevel()
    {
        return 1;
    }
	
	public int getMinEnchantability(int enchantmentLevel)
	{
		return 5;
	}
}