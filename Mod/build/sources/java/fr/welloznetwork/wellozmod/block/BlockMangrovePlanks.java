package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMangrovePlanks extends Block
{
    public BlockMangrovePlanks()
    {
        super(Material.wood);
        setHardness(2.0F);
        setResistance(5.0F);
        setStepSound(soundTypeWood);
        setBlockName("mangrove_planks");
        setBlockTextureName("wellozmod:mangrove_planks");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}