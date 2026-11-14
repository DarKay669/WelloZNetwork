package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemEmeraldAxe extends ItemAxe
{
	public ItemEmeraldAxe()
	{
		super(RegistryToolMaterials.EMERALD);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:emerald_axe");
		setUnlocalizedName("emerald_axe");
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