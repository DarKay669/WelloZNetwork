package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemPhantomMembrane extends Item
{
    public ItemPhantomMembrane()
    {
        setUnlocalizedName("phantom_membrane");
        setTextureName("wellozmod:phantom_membrane");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}