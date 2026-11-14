package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentDeathBringer extends Enchantment
{
	public EnchantmentDeathBringer()
	{
		super(63, 4, EnumEnchantmentType.weapon);
		setName("death_bringer");
	}
	
	public int getMaxLevel()
    {
        return 3;
    }
	
	public int getMinLevel()
    {
        return 1;
    }
	
	public int getMinEnchantability(int enchantmentLevel)
	{
		return 13;
	}
}