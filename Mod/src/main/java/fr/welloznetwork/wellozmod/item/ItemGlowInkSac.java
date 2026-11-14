package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemGlowInkSac extends Item
{
    public ItemGlowInkSac()
    {
        setUnlocalizedName("glow_ink_sac");
        setTextureName("wellozmod:glow_ink_sac");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}