package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemGlowBerries extends ItemFood
{
    public ItemGlowBerries()
    {
        super(2, true);
        setUnlocalizedName("glow_berries");
        setTextureName("wellozmod:glow_berries");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}