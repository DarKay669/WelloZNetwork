package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemScute extends Item
{
    public ItemScute()
    {
        setUnlocalizedName("scute");
        setTextureName("wellozmod:scute");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}