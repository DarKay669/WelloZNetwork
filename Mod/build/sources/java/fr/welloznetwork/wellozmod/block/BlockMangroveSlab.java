package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;

public class BlockMangroveSlab extends BlockSlab
{
    public BlockMangroveSlab()
    {
        super(false, Material.wood);
        setHardness(1.5F);
        setBlockName("mangrove_slab");
        setBlockTextureName("wellozmod:mangrove_planks");
        setStepSound(soundTypeWood);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public String func_150002_b(int p_150002_1_)
    {
        return null;
    }
}