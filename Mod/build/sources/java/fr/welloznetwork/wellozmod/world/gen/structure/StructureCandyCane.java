package fr.welloznetwork.wellozmod.world.gen.structure;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class StructureCandyCane extends WorldGenerator
{
    private static final Block[] PRESENTS = new Block[] {RegistryBlocks.present_blue, RegistryBlocks.present_green, RegistryBlocks.present_red};
    
    private Block[] getValidSpawnBlocks()
    {
        return new Block[] {Blocks.snow, Blocks.snow_layer};
    }
    
    private boolean locationIsValidSpawn(World world, int x, int y, int z)
    {
        int distanceToAir = 0;
        Block checkId = world.getBlock(x, y, z);
        
        while (checkId != Blocks.air)
        {
            distanceToAir++;
            checkId = world.getBlock(x, y + distanceToAir, z);
        }
        
        if (distanceToAir > 3)
        {
            return false;
        }
        
        y += distanceToAir - 1;
        
        Block blockId = world.getBlock(x, y, z);
        Block blockIdAbove = world.getBlock(x, y + 1, z);
        Block blockIdBelow = world.getBlock(x, y - 1, z);
        
        for (Block block : this.getValidSpawnBlocks())
        {
            if (blockIdAbove != Blocks.air)
            {
                return false;
            }
            
            if (blockId == block)
            {
                return true;
            }
            
            if (blockId == Blocks.snow && blockIdBelow == block)
            {
                return true;
            }
        }
        
        return false;
    }
    
    private void setBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        Block block1 = world.getBlock(x, y, z);
        
        if (block1.isAir(world, x, y, z) || block1.isLeaves(world, x, y, z))
        {
            world.setBlock(x, y, z, block, metadata, 2);
        }
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        if (!this.locationIsValidSpawn(world, x, y, z) || !this.locationIsValidSpawn(world, x + 14, y, z) || !this.locationIsValidSpawn(world, x + 14, y, z + 16) || !this.locationIsValidSpawn(world, x, y, z + 16))
        {
            return false;
        }
        
        y--;
        
        world.setBlock(x + 0, y + 0, z + 0, PRESENTS[random.nextInt(3)]);
        world.setBlock(x + 0, y + 1, z + 0, RegistryBlocks.candy_cane_block);
        world.setBlock(x + 0, y + 2, z + 0, RegistryBlocks.candy_cane_block);
        world.setBlock(x + 0, y + 3, z + 0, RegistryBlocks.candy_cane_block);
        world.setBlock(x + 0, y + 4, z + 0, RegistryBlocks.candy_cane_block);
        world.setBlock(x + 0, y + 5, z + 0, RegistryBlocks.candy_cane_block);
        world.setBlock(x + 0, y + 6, z + 0, RegistryBlocks.candy_cane_block);
        world.setBlock(x + 1, y + 6, z + 0, RegistryBlocks.candy_cane_block, 4, 2);
        world.setBlock(x + 2, y + 4, z + 0, RegistryBlocks.candy_cane_block);
        world.setBlock(x + 2, y + 5, z + 0, RegistryBlocks.candy_cane_block);
        world.setBlock(x + 2, z + 6, z + 0, RegistryBlocks.candy_cane_block);
        
        return true;
    }
}