package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemZarkoniumAxe extends ItemAxe
{
	public ItemZarkoniumAxe()
	{
		super(RegistryToolMaterials.ZARKONIUM);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:zarkonium_axe");
		setUnlocalizedName("zarkonium_axe");
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