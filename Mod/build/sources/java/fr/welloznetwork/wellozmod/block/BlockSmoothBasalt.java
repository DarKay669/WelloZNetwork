package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSmoothBasalt extends Block
{
    public BlockSmoothBasalt()
    {
        super(Material.rock);
        setHarvestLevel("pickaxe", 0);
        setResistance(4.2F);
        setHardness(1.25F);
        setBlockName("smooth_basalt");
        setBlockTextureName("wellozmod:smooth_basalt");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_BASALT);
    }
}