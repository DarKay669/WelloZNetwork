package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenCoral;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockCoral extends Block
{
    private Block[] spawnBlocks = {Blocks.sand, Blocks.gravel, Blocks.dirt, Blocks.clay};
    
    public BlockCoral(String unlocalizedName)
    {
        super(Material.water);
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_CORAL);
    }
    
    private boolean canSpawn(Block block)
    {
        for (int i = 0; i < spawnBlocks.length; i++)
        {
            if (spawnBlocks[i] == block)
            {
                return true;
            }
        }
        
        return WorldGenCoral.isCoralBlock(block);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }
    
    public int getRenderType()
    {
        return 1;
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (!this.canBlockStay(world, x, y, z))
        {
            world.setBlock(x, y, z, Blocks.water);
        }
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, int block)
    {
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }
    
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        if (world.getBlock(x, y - 1, z) == Blocks.water || world.getBlock(x, y + 1, z).getMaterial() != Material.water)
        {
            return false;
        }
        
        else
        {
            return true;
        }
    }
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        Block above = world.getBlock(x, y + 1, z);
        Block below = world.getBlock(x, y - 1, z);
        return (world.getBlock(x, y, z) == Blocks.water) ? canSpawn(below) && above == Blocks.water : false;
    }
}