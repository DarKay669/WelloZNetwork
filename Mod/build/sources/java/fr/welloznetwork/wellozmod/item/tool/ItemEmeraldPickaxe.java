package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemEmeraldPickaxe extends ItemPickaxe
{
	public ItemEmeraldPickaxe()
	{
		super(RegistryToolMaterials.EMERALD);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:emerald_pickaxe");
		setUnlocalizedName("emerald_pickaxe");
	}
	
	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
		if (repair.getItem() == Items.emerald)
		{
			return true;
		}
		
		return false;
	}
}