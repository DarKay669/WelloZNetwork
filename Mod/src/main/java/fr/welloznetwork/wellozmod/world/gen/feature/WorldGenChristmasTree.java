package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenChristmasTree extends WorldGenerator
{
    private static final List<Block> PRESENTS = Arrays.asList(RegistryBlocks.present_blue, RegistryBlocks.present_green, RegistryBlocks.present_red);
    
    public WorldGenChristmasTree()
    {
        super(true);
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        Block block = world.getBlock(x, y - 1, z), present = PRESENTS.get(random.nextInt(PRESENTS.size()));
        
        if (block == RegistryBlocks.warped_nylium)
        {
            for (int xss = x - 7; xss < x + 7; xss++)
            {
                for (int yss = z - 7; yss < z + 7; yss++)
                {
                    if (((xss - x) * (xss - x) + (yss - z) * (yss - z) < 29))
                    {
                        int x1 = xss;
                        int z1 = yss;
                        int y1 = world.getHeightValue(x1, z1);
                        
                        Block block1 = world.getBlock(x1, y1 - 1, z1);
                        
                        if (block1 != Blocks.air)
                        {
                            if (random.nextInt(80) == 0 && (block1 == RegistryBlocks.warped_nylium))
                            {
                                world.setBlock(x1, y1, z1, present);
                            }
                            
                            else if (block1 == Blocks.water)
                            {
                                world.setBlock(x1, y1 - 1, z1, Blocks.ice);
                                world.setBlock(x1, y1, z1, Blocks.snow_layer);
                            }
                            
                            else if (world.getBlock(x1, y1, z1)  != Blocks.snow_layer || world.getBlock(x1, y1, z1) != PRESENTS)
                            {
                                world.setBlock(x1, y1, z1, Blocks.snow_layer);
                            }
                        }
                    }
                }
            }
            
            for (int y1 = 0; y1 <= 8; y1++)
            {
                world.setBlock(x, y + y1, z, Blocks.log);
            }
            
            world.setBlock(x, y + 9, z, this.getBlock(random));
            
            world.setBlock(x + 0, y + 2, 1 + z, getBlock(random));
            world.setBlock(x + 0, y + 2, 2 + z, Blocks.log);
            world.setBlock(x + 0, y + 2, 3 + z, getBlock(random));
            world.setBlock(x + 0, y + 2, 4 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 2, -1 + z, getBlock(random));
            world.setBlock(x + 0, y + 2, -2 + z, getBlock(random));
            world.setBlock(x + 0, y + 2, -3 + z, getBlock(random));
            world.setBlock(x + 0, y + 2, -4 + z, getBlock(random));
            world.setBlock(x + 1, y + 2, 0 + z, getBlock(random));
            world.setBlock(x + 2, y + 2, 0 + z, getBlock(random));
            world.setBlock(x + 3, y + 2, 0 + z, getBlock(random));
            world.setBlock(x + 4, y + 2, 0 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 2, 0 + z, getBlock(random));
            world.setBlock(x + -2, y + 2, 0 + z, getBlock(random));
            world.setBlock(x + -3, y + 2, 0 + z, Blocks.log);
            world.setBlock(x + -4, y + 2, 0 + z, getBlock(random));

            world.setBlock(x + 1, y + 2, 1 + z, getBlock(random));
            world.setBlock(x + 1, y + 2, 2 + z, getBlock(random));
            world.setBlock(x + 1, y + 2, 3 + z, getBlock(random));
            world.setBlock(x + 1, y + 2, 4 + z, getBlock(random));
            world.setBlock(x + -1, y + 2, 1 + z, getBlock(random));
            world.setBlock(x + -1, y + 2, 2 + z, Blocks.log);
            world.setBlock(x + -1, y + 2, 3 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 2, 4 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 2, -1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 2, -2 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 2, -3 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 2, -4 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 2, -1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 2, -2 + z, Blocks.log);
            world.setBlock(x + -1, y + 2, -3 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 2, -4 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 2, 1 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 2, 1 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 2, 1 + z, this.getBlock(random));
            world.setBlock(x + 4, y + 2, 1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 2, -1 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 2, -1 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 2, -1 + z, this.getBlock(random));
            world.setBlock(x + 4, y + 2, -1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 2, 1 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 2, 1 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 2, 1 + z, this.getBlock(random));
            world.setBlock(x + -4, y + 2, 1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 2, -1 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 2, -1 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 2, -1 + z, this.getBlock(random));
            world.setBlock(x + -4, y + 2, -1 + z, this.getBlock(random));

            world.setBlock(x + 2, y + 2, 1 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 2, 1 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 2, 1 + z, this.getBlock(random));

            world.setBlock(x + -2, y + 2, 2 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 2, 2 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 2, 3 + z, this.getBlock(random));

            world.setBlock(x + 2, y + 2, -2 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 2, -2 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 2, -3 + z, this.getBlock(random));

            world.setBlock(x + -2, y + 2, -2 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 2, -2 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 2, -3 + z, this.getBlock(random));

            world.setBlock(x + 0, y + 3, 1 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 3, 2 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 3, 3 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 3, 4 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 3, -1 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 3, -2 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 3, -3 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 3, -4 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 3, 0 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 3, 0 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 3, 0 + z, this.getBlock(random));
            world.setBlock(x + 4, y + 3, 0 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 3, 0 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 3, 0 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 3, 0 + z, this.getBlock(random));
            world.setBlock(x + -4, y + 3, 0 + z, this.getBlock(random));

            world.setBlock(x + 1, y + 3, 1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 3, 2 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 3, 3 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 3, 1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 3, 2 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 3, 3 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 3, -1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 3, -2 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 3, -3 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 3, -1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 3, -2 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 3, -3 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 3, 1 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 3, 1 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 3, 1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 3, -1 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 3, -1 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 3, -1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 3, 1 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 3, 1 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 3, 1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 3, -1 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 3, -1 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 3, -1 + z, this.getBlock(random));

            world.setBlock(x + 2, y + 3, 2 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 3, 2 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 3, 3 + z, this.getBlock(random));

            world.setBlock(x + -2, y + 3, 2 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 3, 2 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 3, 3 + z, this.getBlock(random));

            world.setBlock(x + 2, y + 3, -2 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 3, -2 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 3, -3 + z, this.getBlock(random));

            world.setBlock(x + -2, y + 3, -1 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 3, -2 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 3, -3 + z, this.getBlock(random));

            world.setBlock(x + 0, y + 4, 1 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 4, 2 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 4, 3 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 4, -1 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 4, -2 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 4, -3 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 4, 0 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 4, 0 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 4, 0 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 4, 0 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 4, 0 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 4, 0 + z, this.getBlock(random));

            world.setBlock(x + 1, y + 4, 1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 4, 2 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 4, 3 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 4, 1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 4, 2 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 4, 3 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 4, -1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 4, -2 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 4, -3 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 4, -1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 4, -2 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 4, -3 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 4, 1 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 4, 1 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 4, 1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 4, -1 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 4, -1 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 4, -1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 4, 1 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 4, 1 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 4, 1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 4, -1 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 4, -1 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 4, -1 + z, this.getBlock(random));

            world.setBlock(x + 2, y + 4, 2 + z, this.getBlock(random));

            world.setBlock(x + -2, y + 4, 2 + z, this.getBlock(random));

            world.setBlock(x + 2, y + 4, -2 + z, this.getBlock(random));

            world.setBlock(x + -2, y + 4, -2 + z, this.getBlock(random));

            world.setBlock(x + 0, y + 5, 1 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 5, 2 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 5, 3 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 5, -1 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 5, -2 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 5, -3 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 5, 0 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 5, 0 + z, this.getBlock(random));
            world.setBlock(x + 3, y + 5, 0 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 5, 0 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 5, 0 + z, this.getBlock(random));
            world.setBlock(x + -3, y + 5, 0 + z, this.getBlock(random));

            world.setBlock(x + 1, y + 5, 1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 5, 2 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 5, 1 + z, Blocks.log);
            world.setBlock(x + -1, y + 5, 2 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 5, -1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 5, -2 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 5, -1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 5, -2 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 5, 1 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 5, 1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 5, -1 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 5, -1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 5, 1 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 5, 1 + z, Blocks.log);
            world.setBlock(x + -1, y + 5, -1 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 5, -1 + z, this.getBlock(random));

            world.setBlock(x + 2, y + 5, 2 + z, this.getBlock(random));

            world.setBlock(x + -2, y + 5, 2 + z, this.getBlock(random));

            world.setBlock(x + 2, y + 5, -2 + z, this.getBlock(random));

            world.setBlock(x + -2, y + 5, -2 + z, this.getBlock(random));

            world.setBlock(x + 0, y + 6, 1 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 6, 2 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 6, -1 + z, this.getBlock(random));
            world.setBlock(x + 0, y + 6, -2 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 6, 0 + z, this.getBlock(random));
            world.setBlock(x + 2, y + 6, 0 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 6, 0 + z, this.getBlock(random));
            world.setBlock(x + -2, y + 6, 0 + z, this.getBlock(random));

            world.setBlock(x + 1, y + 6, 1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 6, 1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 6, -1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 6, -1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 6, 1 + z, this.getBlock(random));
            world.setBlock(x + 1, y + 6, -1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 6, 1 + z, this.getBlock(random));
            world.setBlock(x + -1, y + 6, -1 + z, this.getBlock(random));

            world.setBlock(x + 0, y + 7, 1 + z, getBlock(random));
            world.setBlock(x + 0, y + 7, -1 + z, getBlock(random));
            world.setBlock(x + 1, y + 7, 0 + z, getBlock(random));
            world.setBlock(x + -1, y + 7, 0 + z, getBlock(random));

            world.setBlock(x + 1, y + 7, 1 + z, getBlock(random));
            world.setBlock(x + -1, y + 7, 1 + z, getBlock(random));
            world.setBlock(x + 1, y + 7, -1 + z, getBlock(random));
            world.setBlock(x + -1, y + 7, -1 + z, getBlock(random));
            world.setBlock(x + 1, y + 7, 1 + z, getBlock(random));
            world.setBlock(x + 1, y + 7, -1 + z, getBlock(random));
            world.setBlock(x + -1, y + 7, 1 + z, getBlock(random));
            world.setBlock(x + -1, y + 7, -1 + z, getBlock(random));

            world.setBlock(x + 0, y + 8, 1 + z, getBlock(random));
            world.setBlock(x + 0, y + 8, -1 + z, getBlock(random));
            world.setBlock(x + 1, y + 8, 0 + z, getBlock(random));
            world.setBlock(x + -1, y + 8, 0 + z, getBlock(random));
            
            world.setBlock(x, y - 1, z, RegistryBlocks.warped_nylium);
            
            for (int xss = x - 7; xss < x + 7; xss++)
            {
                for (int yss = z - 7; yss < z + 7; yss++)
                {
                    if (((xss - x) * (xss - x) + (yss - z) * (yss - z)) < 29)
                    {
                        int x1 = xss;
                        int z1 = yss;
                        int y1 = world.getHeightValue(x1, z1);
                        
                        Block block1 = world.getBlock(x1, y1 - 1, z1);
                        
                        if (block1 != Blocks.air)
                        {
                            if (random.nextInt(40) == 0 && (block1 == RegistryBlocks.warped_nylium))
                            {
                                world.setBlock(x1, y1, z1, present);
                            }
                            
                            else if (block1 == Blocks.water)
                            {
                                world.setBlock(x1, y1 - 1, z1, Blocks.ice);
                                world.setBlock(x1, y1, z1, Blocks.snow_layer);
                            }
                            
                            else if (world.getBlock(x1, y1, z1) != Blocks.snow_layer || world.getBlock(x1, y1, z1) != PRESENTS)
                            {
                                world.setBlock(x1, y1, z1, Blocks.snow_layer);
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    private Block getBlock(Random random)
    {
        return random.nextInt(4) == 0 ? RegistryBlocks.lights : RegistryBlocks.christmas_leaves;
    }
}