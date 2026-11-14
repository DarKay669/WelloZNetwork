package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemCookedCod extends ItemFood
{
    public ItemCookedCod()
    {
        super(6, true);
        setUnlocalizedName("cooked_cod");
        setTextureName("wellozmod:cooked_cod");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}