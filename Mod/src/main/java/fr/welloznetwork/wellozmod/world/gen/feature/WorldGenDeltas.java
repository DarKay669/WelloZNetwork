package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import com.google.common.collect.ImmutableList;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenDeltas extends WorldGenerator
{
    private static final ImmutableList<Block> CAN_REPLACE = ImmutableList.of(RegistryBlocks.black_stone, RegistryBlocks.basalt);
    private int minSize = 7, maxSize = 3, rimMinSize = 0, rimMaxSize = 2;
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        boolean flag = false;
        boolean flag1 = random.nextDouble() < 0.9D;
        
        int rimXSize = flag1 ? MathHelper.getRandomIntegerInRange(random, this.minSize, this.maxSize) : 0;
        int rimZSize = flag1 ? MathHelper.getRandomIntegerInRange(random, this.minSize, this.maxSize) : 0;
        
        boolean flag2 = flag1 && rimXSize != 0 && rimZSize != 0;
        
        int xSize = MathHelper.getRandomIntegerInRange(random, this.rimMinSize, this.rimMaxSize);
        int zSize = MathHelper.getRandomIntegerInRange(random, this.rimMinSize, this.rimMaxSize);
        int size = Math.max(xSize, zSize);
        
        for (int x1 = -xSize; x1 <= xSize; x1++)
        {
            for (int z1 = -zSize; z1 <= zSize; z1++)
            {
                int xOffset = x1 + x;
                int zOffset = z1 + z;
                
                if (Math.abs(xOffset - x) + Math.abs(zOffset - z) > size + size)
                {
                    break;
                }
                
                if (dishManhattan(x, y, z, xOffset, y, zOffset) > size)
                {
                    continue;
                }
                
                if (this.isClear(world, xOffset, y, zOffset))
                {
                    if (flag2)
                    {
                        flag = true;
                        
                        this.setBlockAndNotifyAdequately(world, xOffset, y, zOffset, RegistryBlocks.magma, 0);
                    }
                    
                    if (this.isClear(world, xOffset + rimXSize, y, zOffset + rimZSize))
                    {
                        flag = true;
                        
                        this.setBlockAndNotifyAdequately(world, xOffset + rimXSize, y, zOffset + rimZSize, Blocks.lava, 0);
                    }
                }
            }
        }
        
        return flag;
    }
    
    private boolean isClear(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        
        if (block == Blocks.lava)
        {
            return false;
        }
        
        else if (!CAN_REPLACE.contains(block))
        {
            return false;
        }
        
        else
        {
            for (ForgeDirection forgeDirection : ForgeDirection.VALID_DIRECTIONS)
            {
                boolean flag = world.isAirBlock(x + forgeDirection.offsetX, y + forgeDirection.offsetY, z + forgeDirection.offsetZ);
                
                if (flag && forgeDirection != ForgeDirection.UP || !flag && forgeDirection != ForgeDirection.UP)
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public static int dishManhattan(int x, int y, int z, int x1, int y1, int z1)
    {
        return (int) ((float) Math.abs(x1 - x) + (float) Math.abs(y1 - y) + (float) Math.abs(z1 - z));
    }
}