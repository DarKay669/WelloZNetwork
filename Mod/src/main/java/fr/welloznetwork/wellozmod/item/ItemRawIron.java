package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemRawIron extends Item
{
    public ItemRawIron()
    {
        setUnlocalizedName("raw_iron");
        setTextureName("wellozmod:raw_iron");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}