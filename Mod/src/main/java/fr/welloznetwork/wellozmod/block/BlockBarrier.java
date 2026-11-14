package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockBarrier extends Block
{
    public BlockBarrier()
    {
        super(Material.anvil);
        setResistance(6000001.0F);
        setBlockUnbreakable();
        disableStats();
        setBlockName("barrier");
        setBlockTextureName("wellozmod:barrier");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        this.canBlockGrass = true;
    }
    
    public int getRenderType()
    {
        return -1;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public float getAmbientOcclusionLightValue()
    {
        return 1.0F;
    }
    
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int state, float chance, int fortune) {}
}