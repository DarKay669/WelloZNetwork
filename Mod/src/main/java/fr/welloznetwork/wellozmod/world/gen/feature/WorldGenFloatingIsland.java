package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFloatingIsland extends WorldGenerator
{
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        boolean canGenerate = true;
        
        for (int x1 = x - 6; x1 < x + 12; x1++)
        {
            for (int y1 = y - 6; y1 < y + 17; y1++)
            {
                for (int z1 = z - 6; z1 < z + 12; z1++)
                {
                    if (world.getBlock(x1, y1, z1) != Blocks.air)
                    {
                        canGenerate = false;
                    }
                }
            }
        }
        
        if (y + 11 <= world.getHeight() && canGenerate)
        {
            for (int z1 = 1; z1 < 2; z1++)
            {
                world.setBlock(x, y, z + z1, Blocks.stone);
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                world.setBlock(x + x1, y, z, Blocks.stone);
            }
            
            for (int z1 = -2; z1 < 3; z1++)
            {
                world.setBlock(x, y + 1, z + z1, Blocks.stone);
            }
            
            for (int x1 = -2; x1 < 3; x1++)
            {
                world.setBlock(x + x1, y + 1, z, Blocks.stone);
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                for (int z1 = 1; z1 > -2; z1--)
                {
                    if (x1 != 0 || z1 != 0)
                    {
                        world.setBlock(x + x1, y + 1, z + z1, Blocks.stone);
                    }
                }
            }
            
            world.setBlock(x + 1, y + 1, z + 1, Blocks.stone);
            world.setBlock(x - 1, y + 1, z - 1, Blocks.stone);
            
            for (int z1 = -2; z1 < 3; z1++)
            {
                world.setBlock(x, y + 2, z + z1, RegistryBlocks.warped_nylium);
            }
            
            for (int x1 = -2; x1 < 3; x1++)
            {
                world.setBlock(x + x1, y + 2, z, RegistryBlocks.warped_nylium);
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                for (int z1 = 1; z1 > -2; z1--)
                {
                    if (x1 != 0 || z1 != 0)
                    {
                        world.setBlock(x + x1, y + 2, z + z1, RegistryBlocks.warped_nylium);
                    }
                }
            }
            
            world.setBlock(x + 1, y + 2, z + 1, RegistryBlocks.warped_nylium);
            world.setBlock(x - 1, y + 2, z - 1, RegistryBlocks.warped_nylium);
            world.setBlock(x, y + 10, z, this.setRandomBlock(world, random));
            
            for (int z1 = -1; z1 < 2; z1++)
            {
                if (z1 != 0)
                {
                    world.setBlock(x, y + 5, z + z1, Blocks.log);
                }
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                if (x1 != 0)
                {
                    world.setBlock(x + x1, y + 5, z, Blocks.log);
                }
            }
            
            for (int z1 = -2; z1 < 3; z1++)
            {
                if (z1 != 0 || z1 != 1)
                {
                    world.setBlock(x, y + 5, z + z1, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -2; x1 < 3; x1++)
            {
                if (x1 != 0 || x1 != 1)
                {
                    world.setBlock(x + x1, y + 5, z, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                if (x1 != 0)
                {
                    world.setBlock(x + x1, y + 5, z, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                if (x1 != 0)
                {
                    world.setBlock(x + x1, y + 5, z + 2, this.setRandomBlock(world, random));
                }
            }
            
            for (int z1 = -1; z1 < 2; z1++)
            {
                if (z1 != 0)
                {
                    world.setBlock(x - 2, y + 5, z + z1, this.setRandomBlock(world, random));
                }
            }
            
            for (int z1 = -1; z1 < 2; z1++)
            {
                if (z1 != 0)
                {
                    world.setBlock(x + 2, y + 5, z + z1, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                for (int z1 = 1; z1 > -2; z1--)
                {
                    if (x1 != 0 || z1 != 0)
                    {
                        world.setBlock(x + x1, y + 5, z + z1, this.setRandomBlock(world, random));
                    }
                }
            }
            
            world.setBlock(x + 1, y + 5, z + 1, this.setRandomBlock(world, random));
            world.setBlock(x - 1, y + 5, z - 1, this.setRandomBlock(world, random));
            
            for (int z1 = -2; z1 < 3; z1++)
            {
                if (z1 != 0 || z1 != 1)
                {
                    world.setBlock(x, y + 6, z + z1, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -2; x1 < 3; x1++)
            {
                if (x1 != 0 || x1 != 1)
                {
                    world.setBlock(x + x1, y + 6, z, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                for (int z1 = 1; z1 > -2; z1--)
                {
                    if (x1 != 0 || z1 != 0)
                    {
                        world.setBlock(x + x1, y + 6, z + z1, this.setRandomBlock(world, random));
                    }
                }
            }
            
            world.setBlock(x + 1, y + 6, z + 1, this.setRandomBlock(world, random));
            world.setBlock(x - 1, y + 6, z - 1, this.setRandomBlock(world, random));
            
            for (int z1 = -1; z1 < 2; z1++)
            {
                if (z1 != 0)
                {
                    world.setBlock(x, y + 6, z + z1, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                if (x1 != 0)
                {
                    world.setBlock(x + x1, y + 6, z, this.setRandomBlock(world, random));
                }
            }
            
            for (int z1 = -1; z1 < 2; z1++)
            {
                if (z1 != 0)
                {
                    world.setBlock(x, y + 7, z + z1, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                if (x1 != 0)
                {
                    world.setBlock(x + x1, y + 7, z, this.setRandomBlock(world, random));
                }
            }
            
            for (int z1 = -1; z1 < 2; z1++)
            {
                if (z1 != 0)
                {
                    world.setBlock(x, y + 8, z + z1, Blocks.log);
                }
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                if (x1 != 0)
                {
                    world.setBlock(x + x1, y + 8, z, Blocks.log);
                }
            }
            
            for (int z1 = -2; z1 < 3; z1++)
            {
                if (z1 != 0 || z1 != 1)
                {
                    world.setBlock(x, y + 8, z + z1, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -2; x1 < 3; x1++)
            {
                if (x1 != 0 || x1 != 1)
                {
                    world.setBlock(x + x1, y + 8, z, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                for (int z1 = 1; z1 > -2; z1--)
                {
                    if (x1 != 0 || z1 != 0)
                    {
                        world.setBlock(x + x1, y + 8, z + z1, this.setRandomBlock(world, random));
                    }
                }
            }
            
            world.setBlock(x + 1, y + 8, z + 1, this.setRandomBlock(world, random));
            world.setBlock(x - 1, y + 8, z - 1, this.setRandomBlock(world, random));
            
            for (int z1 = -1; z1 < 2; z1++)
            {
                if (z1 != 0)
                {
                    world.setBlock(x, y + 9, z + z1, this.setRandomBlock(world, random));
                }
            }
            
            for (int x1 = -1; x1 < 2; x1++)
            {
                if (x1 != 0)
                {
                    world.setBlock(x + x1, y + 9, z, this.setRandomBlock(world, random));
                }
            }
            
            for (int y1 = y + 3; y1 <= y + 9; y1++)
            {
                world.setBlock(x, y1, z, Blocks.log);
            }
            
            return true;
        }
        
        return false;
    }
    
    private Block setRandomBlock(World world, Random random)
    {
        return random.nextInt(3) == 0 ? Blocks.leaves2 : Blocks.leaves;
    }
}