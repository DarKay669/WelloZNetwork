package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class ItemNetheriteHoe extends ItemHoe
{
	public ItemNetheriteHoe()
	{
		super(RegistryToolMaterials.NETHERITE);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:netherite_hoe");
		setUnlocalizedName("netherite_hoe");
	}
	
	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
	    if (repair.getItem() == RegistryItems.netherite && repair.getItemDamage() == 0)
		{
			return true;
		}
		
		return false;
	}
}