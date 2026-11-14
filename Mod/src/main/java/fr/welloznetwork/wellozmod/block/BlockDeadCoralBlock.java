package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDeadCoralBlock extends Block
{
    public BlockDeadCoralBlock(String unlocalizedName)
    {
        super(Material.plants);
        setHarvestLevel("pickaxe", 0);
        setResistance(6.0F);
        setHardness(1.5F);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_CORAL);
    }
}