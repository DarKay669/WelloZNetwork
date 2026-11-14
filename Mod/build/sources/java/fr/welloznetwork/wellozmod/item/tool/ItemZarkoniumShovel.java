package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemZarkoniumShovel extends ItemSpade
{
	public ItemZarkoniumShovel()
	{
		super(RegistryToolMaterials.ZARKONIUM);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:zarkonium_shovel");
		setUnlocalizedName("zarkonium_shovel");
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