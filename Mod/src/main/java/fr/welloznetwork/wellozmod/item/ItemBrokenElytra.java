package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemBrokenElytra extends Item
{
    public ItemBrokenElytra()
    {
        setMaxStackSize(1);
        setUnlocalizedName("broken_elytra");
        setTextureName("wellozmod:broken_elytra");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}