package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCoarseDirt extends Block
{
    public BlockCoarseDirt()
    {
        super(Material.ground);
        setHardness(0.5F);
        setHarvestLevel("shovel", 0);
        setStepSound(soundTypeGravel);
        setBlockName("coarse_dirt");
        setBlockTextureName("wellozmod:coarse_dirt");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean canSustainPlant(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection forgeDirection, IPlantable iPlantable)
    {
        return Blocks.dirt.canSustainPlant(iBlockAccess, x, y, z, forgeDirection, iPlantable);
    }
}