package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;

public class BlockCherrySlab extends BlockSlab
{
    public BlockCherrySlab()
    {
        super(false, Material.wood);
        setHardness(1.5F);
        setBlockName("cherry_slab");
        setBlockTextureName("wellozmod:cherry_planks");
        setStepSound(RegistrySounds.SOUND_TYPE_CHERRY_WOOD);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public String func_150002_b(int p_150002_1_)
    {
        return null;
    }
}