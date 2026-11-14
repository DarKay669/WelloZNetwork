package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemCandyCane extends ItemFood
{
    public ItemCandyCane()
    {
        super(3, 0.4F, false);
        setUnlocalizedName("candy_cane");
        setTextureName("wellozmod:candy_cane");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
}