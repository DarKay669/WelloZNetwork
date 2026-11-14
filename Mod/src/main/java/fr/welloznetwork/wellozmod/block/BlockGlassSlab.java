package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;

public class BlockGlassSlab extends BlockSlab
{
    public BlockGlassSlab()
    {
        super(false, Material.glass);
        setHardness(0.8F);
        setLightOpacity(0);
        setBlockName("glass_slab");
        setBlockTextureName("glass");
        setStepSound(soundTypeGlass);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public String func_150002_b(int p_150002_1)
    {
        return null;
    }
}