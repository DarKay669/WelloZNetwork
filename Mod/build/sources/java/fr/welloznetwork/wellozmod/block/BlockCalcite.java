package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCalcite extends Block
{
    public BlockCalcite()
    {
        super(Material.rock);
        setResistance(0.75F);
        setHardness(0.75F);
        setHarvestLevel("pickaxe", 0);
        setBlockName("calcite");
        setBlockTextureName("wellozmod:calcite");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_CALCITE);
    }
}