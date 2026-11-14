package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemCopperIngot extends Item
{
    public ItemCopperIngot()
    {
        setUnlocalizedName("copper_ingot");
        setTextureName("wellozmod:copper_ingot");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}