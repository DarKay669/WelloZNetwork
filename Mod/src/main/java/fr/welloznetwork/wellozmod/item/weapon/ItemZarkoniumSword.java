package fr.welloznetwork.wellozmod.item.weapon;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemZarkoniumSword extends ItemSword
{
	public ItemZarkoniumSword() 
	{
		super(RegistryToolMaterials.ZARKONIUM);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:zarkonium_sword");
		setUnlocalizedName("zarkonium_sword");
	}
	
	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
		if (repair.getItem() == RegistryItems.zarkonium)
		{
			return true;
		}
		
		return false;
	}
}