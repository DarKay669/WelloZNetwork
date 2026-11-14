package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemSweetBerries extends ItemFood
{
    public ItemSweetBerries()
    {
        super(2, true);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setUnlocalizedName("sweet_berries");
        setTextureName("wellozmod:sweet_berries");
    }
}