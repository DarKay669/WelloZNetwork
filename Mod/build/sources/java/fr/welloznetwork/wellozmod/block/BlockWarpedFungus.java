package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;

public class BlockWarpedFungus extends BlockBush
{    
    public BlockWarpedFungus()
    {
        super(Material.plants);
        setTickRandomly(true);
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName("warped_fungus");
        setBlockTextureName("wellozmod:warped_fungus");
        setBlockBounds(0.5F - 0.2F, 0.0F, 0.5F - 0.2F, 0.5F + 0.2F, 0.2F * 3.0F, 0.5F + 0.2F);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_FUNGUS);
    }
}