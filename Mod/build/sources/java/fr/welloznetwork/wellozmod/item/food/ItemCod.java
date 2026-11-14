package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemCod extends ItemFood
{
    public ItemCod()
    {
        super(2, true);
        setUnlocalizedName("raw_cod");
        setTextureName("wellozmod:raw_cod");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}