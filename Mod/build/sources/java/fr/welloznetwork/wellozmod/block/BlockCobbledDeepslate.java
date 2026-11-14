package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCobbledDeepslate extends Block
{
    public BlockCobbledDeepslate()
    {
        super(Material.rock);
        setHardness(3.5F);
        setResistance(6.0F);
        setHarvestLevel("pickaxe", 0);
        setBlockName("cobbled_deepslate");
        setBlockTextureName("wellozmod:cobbled_deepslate");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_COBBLED_DEEPSLATE);
    }
}