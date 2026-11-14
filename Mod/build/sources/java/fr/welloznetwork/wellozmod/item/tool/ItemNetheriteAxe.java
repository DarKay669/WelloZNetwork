package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemNetheriteAxe extends ItemAxe
{
	public ItemNetheriteAxe()
	{
		super(RegistryToolMaterials.NETHERITE);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:netherite_axe");
		setUnlocalizedName("netherite_axe");
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