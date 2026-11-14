package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCoral extends WorldGenerator
{
    private static Random RANDOM = new Random();
    private static Block[] CAN_BE_PLACED_ON = {Blocks.sand, Blocks.gravel, Blocks.dirt, Blocks.stone, Blocks.clay, Blocks.sponge};
    private static final Block[] CORAL_DEAD_FAN_BLOCK = {RegistryBlocks.brain_dead_fan, RegistryBlocks.bubble_dead_fan, RegistryBlocks.fire_dead_fan, RegistryBlocks.horn_dead_fan, RegistryBlocks.tube_dead_fan};
    private static Block[] CORAL_BUSH = {RegistryBlocks.brain_coral, RegistryBlocks.bubble_coral, RegistryBlocks.fire_coral, RegistryBlocks.horn_coral, RegistryBlocks.tube_coral, RegistryBlocks.brain_dead_coral, RegistryBlocks.bubble_dead_coral, RegistryBlocks.fire_dead_coral, RegistryBlocks.horn_dead_coral, RegistryBlocks.tube_dead_coral, RegistryBlocks.brain_coral_fan, RegistryBlocks.bubble_coral_fan, RegistryBlocks.fire_coral_fan, RegistryBlocks.horn_coral_fan, RegistryBlocks.tube_coral_fan};
    
    private static boolean canSpawn(Block block)
    {
        for (int i = 0; i < CAN_BE_PLACED_ON.length; i++)
        {
            if (CAN_BE_PLACED_ON[i] == block)
            {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean isCoralBlock(Block block)
    {
        for (int i = 0; i < CORAL_DEAD_FAN_BLOCK.length; i++)
        {
            if (CORAL_DEAD_FAN_BLOCK[i] == block)
            {
                return true;
            }
        }
        
        return false;
    }
    
    private static boolean isCoralBush(Block block)
    {
        for (int i = 0; i < CORAL_BUSH.length; i++)
        {
            if (CORAL_BUSH[i] == block)
            {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        Block above = world.getBlock(x, y + 1, z);
        Block below = world.getBlock(x, y - 1, z);
        
        if (world.getBlock(x, y, z) == Blocks.water && world.getBlock(x, y + 4, z) == Blocks.water && below != Blocks.water)
        {
            for (int i = 0; i < 2 + random.nextInt(5); i++)
            {
                //Block block = CORAL_DEAD_FAN_BLOCK[random.nextInt(CORAL_DEAD_FAN_BLOCK.length)];
                
                //generateDisk(world, x, y, z, 5 + random.nextInt(5), 1 + random.nextInt(3), block);
                
                if (random.nextBoolean())
                {
                    y = y - random.nextInt(3);
                }
                
                else
                {
                    y = y + random.nextInt(3);
                }
                
                if (random.nextBoolean())
                {
                    if (random.nextBoolean())
                    {
                        x = x - random.nextInt(5);
                    }
                    
                    else
                    {
                        x = x + random.nextInt(5);
                    }
                }
                
                else
                {
                    if (random.nextBoolean())
                    {
                        z = z + random.nextInt(5);
                    }
                    
                    else
                    {
                        z = z - random.nextInt(5);
                    }
                }
            }
            
            generateBush(world, x, y, z);
            return true;
        }
        
        return false;
    }
    
    private void generateBush(World world, int x, int y, int z)
    {
        for (int r = -15; r < 15; r++)
        {
            for (int s = -10; s < 10; s++)
            {
                for (int t = -15; t < 15; t++)
                {
                    if (world.getBlock(x + r, y + s, z + t) == Blocks.water && world.getBlock(x + r, y + s - 1, z + t).getMaterial() != Material.water && world.getBlock(x + r, y + s + 1, z + t) == Blocks.water && RANDOM.nextInt(12) == 0)
                    {
                        world.setBlock(x + r, y + s, z + t, CORAL_BUSH[RANDOM.nextInt(CORAL_BUSH.length)]);
                    }
                }
            }
        }
    }
    
    private static void generateCircle(World world, int x, int y, int z, int radius, Block block)
    {
        for (int j = -radius; j <= radius; j = (j + 1))
        {
            for (int k = 0; k <= 0; k = (k + 1))
            {
                for (int m = -radius; m <= radius; m = (m + 1))
                {
                    int maxX = (int) Math.sqrt((double)(((j * j) + (k * k)) + (m * m)));
                    
                    if (maxX < (radius + 1))
                    {
                        world.setBlock(x + j, y + k, z + m, block);
                    }
                }
            }
        }
    }
    
    private static void generateDisk(World world, int x, int y, int z, int radius, int layers, Block block)
    {
        for (int j = -layers; j <= layers; j = (j + 1))
        {
            generateCircle(world, x, y + j, z, (j > 0) ? radius - j : radius + j, block);
        }
    }
}