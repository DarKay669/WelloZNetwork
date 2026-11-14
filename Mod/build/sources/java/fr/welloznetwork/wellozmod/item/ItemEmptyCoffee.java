package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemEmptyCoffee extends Item
{
    public ItemEmptyCoffee()
    {
        setUnlocalizedName("empty_coffee");
        setTextureName("wellozmod:empty_coffee");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
}