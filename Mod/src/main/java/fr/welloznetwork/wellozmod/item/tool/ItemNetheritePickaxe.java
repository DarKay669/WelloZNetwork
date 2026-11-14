package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemNetheritePickaxe extends ItemPickaxe
{
	public ItemNetheritePickaxe()
	{
		super(RegistryToolMaterials.NETHERITE);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("netherite_pickaxe");
		setTextureName("wellozmod:netherite_pickaxe");
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