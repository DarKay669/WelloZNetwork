package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentImplants extends Enchantment
{
	public EnchantmentImplants()
	{
		super(66, 4, EnumEnchantmentType.weapon);
		setName("implants");
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
		return 20;
	}
}	