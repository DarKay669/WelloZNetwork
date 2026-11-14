package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemZaruxPickaxe extends ItemPickaxe
{
	public ItemZaruxPickaxe()
	{
		super(RegistryToolMaterials.ZARUX);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:zarux_pickaxe");
		setUnlocalizedName("zarux_pickaxe");
	}
	
	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
	    if (repair.getItem() == RegistryItems.zarux)
        {
            return true;
        }
	    
		return false;
	}
}