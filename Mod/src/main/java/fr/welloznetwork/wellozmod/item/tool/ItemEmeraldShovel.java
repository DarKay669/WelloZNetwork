package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemEmeraldShovel extends ItemSpade
{
	public ItemEmeraldShovel()
	{
		super(RegistryToolMaterials.EMERALD);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:emerald_shovel");
		setUnlocalizedName("emerald_shovel");
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