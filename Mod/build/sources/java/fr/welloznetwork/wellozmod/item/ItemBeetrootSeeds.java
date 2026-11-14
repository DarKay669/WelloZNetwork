package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

public class ItemBeetrootSeeds extends ItemSeeds
{
    public ItemBeetrootSeeds()
    {
        super(RegistryBlocks.block_beetroot, Blocks.farmland);
        setUnlocalizedName("beetroot_seeds");
        setTextureName("wellozmod:beetroot_seeds");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}