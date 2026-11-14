package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import com.google.common.collect.ImmutableList;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBasaltColumns extends WorldGenerator
{
    private static final ImmutableList<Block> CAN_PLACE_ON = ImmutableList.of(RegistryBlocks.black_stone, RegistryBlocks.basalt);
    private int minHeight, maxHeight, minReach, maxReach;
    
    public WorldGenBasaltColumns(int minHeight, int maxHeight, int minReach, int maxReach)
    {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minReach = minReach;
        this.maxReach = maxReach;
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        int i = 32;
        
        if (!this.canPlaceAt(world, i, x, y, z))
        {
            return false;
        }
        
        else
        {
            int j = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
            
            boolean flag = random.nextFloat() < 0.9F;
            
            int size = Math.min(j, flag ? 5 : 8);
            int l = flag ? 50 : 15;
            
            boolean flag1 = false;
            
            for (int k = 0; k < l; k++)
            {
                int randomX = x + MathHelper.getRandomIntegerInRange(random, -size, size);
                int randomZ = z + MathHelper.getRandomIntegerInRange(random, -size, size);
                int i1 = j = WorldGenDeltas.dishManhattan(randomX, y, randomZ, x, y, z);
                
                if (i1 > 0)
                {
                    flag1 |= this.placeColumn(world, i, randomX, y, randomZ, i1, MathHelper.getRandomIntegerInRange(random, minReach, maxReach));
                }
            }
            
            return flag1;
        }
    }
    
    private boolean placeColumn(World world, int seaLevel, int x, int y, int z, int p_65171_, int size)
    {
        boolean flag = false;
        
        for (int x1 = -size; x1 <= size; x1++)
        {
            for (int z1 = -size; z1 <= size; z1++)
            {
                int offsetX = x1 + x;
                int offsetY;
                int offsetZ = z1 + z;
                int i = WorldGenDeltas.dishManhattan(x, y, z, offsetX, y, offsetZ);
                
                offsetY = this.isAirOrLavaOcean(world, seaLevel, offsetX, y, offsetZ) ? this.findSurface(world, seaLevel, offsetX, y, offsetZ, i) : this.findAir(world, offsetX, y, offsetZ, i);
                
                if (offsetY >= 0)
                {
                    int j = p_65171_ - i / 2;
                    
                    for (; j >= 0; j--)
                    {
                        if (this.isAirOrLavaOcean(world, seaLevel, offsetX, offsetY, offsetZ))
                        {
                            this.setBlockAndNotifyAdequately(world, offsetY, offsetY, offsetZ, RegistryBlocks.basalt, 0);
                            
                            offsetY++;
                            flag = true;
                        }
                        
                        else
                        {
                            if (world.getBlock(offsetX, offsetY, offsetZ) != RegistryBlocks.basalt)
                            {
                                break;
                            }
                            
                            offsetY++;
                        }
                    }
                }
            }
        }
        
        return flag;
    }
    
    private int findSurface(World world, int seaLevel, int x, int y, int z, int p_65162_)
    {
        while (y > 1 && p_65162_ > 0)
        {
            p_65162_--;
            
            if (this.canPlaceAt(world, seaLevel, x, y, z))
            {
                return y;
            }
            
            y--;
        }
        
        return 0;
    }
    
    private boolean canPlaceAt(World world, int seaLevel, int x, int y, int z)
    {
        if (!this.isAirOrLavaOcean(world, seaLevel, x, y, z))
        {
            return false;
        }
        
        else
        {
            Block block = world.getBlock(x, y - 1, z);
            
            return !block.isAir(world, x, y, z) && CAN_PLACE_ON.contains(block);
        }
    }
    
    private int findAir(World world, int x, int y, int z, int p_65176_)
    {
        while (y < world.getActualHeight() && p_65176_ > 0)
        {
            p_65176_--;
            
            Block block = world.getBlock(x, y, z);
            
            if (!CAN_PLACE_ON.contains(block))
            {
                return 0;
            }
            
            if (block.isAir(world, x, y, z))
            {
                return y;
            }
            
            y++;
        }
        
        return 0;
    }
    
    private boolean isAirOrLavaOcean(World world, int seaLevel, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        
        return block.isAir(world, x, y, z) || block.getMaterial() == Material.lava && y <= seaLevel;
    }
}