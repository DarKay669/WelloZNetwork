package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemRawCopper extends Item
{
    public ItemRawCopper()
    {
        setUnlocalizedName("raw_copper");
        setTextureName("wellozmod:raw_copper");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}