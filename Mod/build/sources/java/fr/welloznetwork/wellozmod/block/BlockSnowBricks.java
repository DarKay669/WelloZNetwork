package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSnowBricks extends Block
{
    public BlockSnowBricks()
    {
        super(Material.rock);
        setHardness(1.3F);
        setStepSound(soundTypeSnow);
        setBlockName("snow_bricks");
        setBlockTextureName("wellozmod:snow_bricks");
        setStepSound(soundTypeStone);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
}