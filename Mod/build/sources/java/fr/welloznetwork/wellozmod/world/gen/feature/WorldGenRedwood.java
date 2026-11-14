package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenRedwood extends WorldGenAbstractTree
{
    private enum TreeBlock
    {
        LEAVES(new ItemStack(Blocks.leaves)),
        TRUNK(new ItemStack(Blocks.log));
        
        private static boolean loadedCustomBlocks = false;
        
        private ItemStack itemStack;
        
        static {}
        
        private static void loadCustomBlocks()
        {
            loadedCustomBlocks = true;
        }
        
        TreeBlock(ItemStack itemStack)
        {
            this.itemStack = itemStack;
        }
        
        public Block getBlock()
        {
            if (!loadedCustomBlocks)
                loadCustomBlocks();
            return Block.getBlockFromItem(this.itemStack.getItem());
        }
        
        public int getMetadata()
        {
            if (!loadedCustomBlocks)
                loadCustomBlocks();
            return this.itemStack.getItemDamage();
        }
    }
    
    public WorldGenRedwood(boolean doNotify)
    {
        super(doNotify);
    }
    
    private static long lastSeed = 0L;
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        lastSeed = random.nextLong();
        
        return generateTree(world, new Random(lastSeed), x, y, z);
    }
    
    public boolean generate(World world, long seed, int x, int y, int z)
    {
        lastSeed = seed;
        
        return generateTree(world, new Random(seed), x, y, z);
    }
    
    private boolean generateTree(World world, Random random, int x, int y, int z)
    {
        int height = random.nextInt(30 + 32), j = 1 + random.nextInt(12), k = height - j, l = 2 + random.nextInt(6);
        
        if (y < 1 || y + height + 1 > 256)
        {
            return false;
        }
        
        for (int y1 = y; y1 <= y + 1 + height; y1++)
        {
            int k1 = 1;
            
            if (y1 - y < j)
            {
                k1 = 0;
            }
            
            else
            {
                k1 = l;
            }
            
            for (int x1 = x - k1; x1 <= x + k1; x1++)
            {
                for (int z1 = z - k1; z1 <= z + k1; z1++)
                {
                    if (y1 >= 0 && y1 < 256)
                    {
                        Block block = world.getBlock(x1, y1, z1);
                        
                        if (block != null && block.isLeaves((IBlockAccess) world, x1, y1, z1))
                        {
                            return false;
                        }
                    }
                    
                    else
                    {
                        return false;
                    }
                }
            }
        }
        
        world.setBlock(x, y - 1, z, Blocks.dirt);
        world.setBlock(x - 1, y - 1, z, Blocks.dirt);
        world.setBlock(x, y - 1, z - 1, Blocks.dirt);
        world.setBlock(x - 1, y - 1, z - 1, Blocks.dirt);
        
        int l1 = random.nextInt(2), j2 = 1;
        boolean flag1 = false;
        
        for (int i3 = 0; i3 <= k; i3++)
        {
            int m = y + height - i3;
            
            for (int x1 = x - l1; x1 <= x + l1; x1++)
            {
                int k4 = x1 - x;
                
                for (int z1 = z - l1; z1 <= z + l1; z1++)
                {
                    int i5 = z1 - z;
                    
                    if (Math.abs(k4) != l1 || Math.abs(i5) != l1 || l1 <= 0)
                    {
                        Block block = world.getBlock(x1, m, z1);
                        
                        if (block.isAir((IBlockAccess) world, x1, m, z1) || block.canBeReplacedByLeaves((IBlockAccess) world, x1, m, z1))
                        {
                            setBlockAndNotifyAdequately(world, x1, m, z1, TreeBlock.LEAVES.getBlock(), TreeBlock.LEAVES.getMetadata());
                        }
                        
                        block = world.getBlock(x1 - 1, m, z1);
                        
                        if (block.isAir((IBlockAccess) world, x1 - 1, m, z1) || block.canBeReplacedByLeaves((IBlockAccess) world, x1 - 1, m, z1))
                        {
                            setBlockAndNotifyAdequately(world, x1 - 1, m, z1, TreeBlock.LEAVES.getBlock(), TreeBlock.LEAVES.getMetadata());
                        }
                        
                        block = world.getBlock(x1, m, z1 - 1);
                        
                        if (block.isAir((IBlockAccess) world, x1, m, z1 - 1) || block.canBeReplacedByLeaves((IBlockAccess) world, x1, m, z1 - 1))
                        {
                            setBlockAndNotifyAdequately(world, x1, m, z1 - 1, TreeBlock.LEAVES.getBlock(), TreeBlock.LEAVES.getMetadata());
                        }
                        
                        block = world.getBlock(x1 - 1, m, z1 - 1);
                        
                        if (block.isAir((IBlockAccess) world, x1 - 1, m, z1 - 1) || block.canBeReplacedByLeaves((IBlockAccess) world, x1 - 1, m, z1 - 1))
                        {
                            setBlockAndNotifyAdequately(world, x1 - 1, m, z1 - 1, TreeBlock.LEAVES.getBlock(), TreeBlock.LEAVES.getMetadata());
                        }
                    }
                }
            }
        }
        
        if (l1 >= j2)
        {
            l1 = flag1 ? 1 : 0;
            flag1 = true;
            
            if (j2++ > l)
            {
                j2 = l;
            }
        }
        
        else
        {
            l1++;
        }
        
        int j3 = random.nextInt(3);
        
        for (int i = 0; i < height - j3; i++)
        {
            Block j4 = world.getBlock(x, y + i, z);
            
            if (j4 == null || j4.isLeaves((IBlockAccess) world, x, y + i, z))
            {
                setBlockAndNotifyAdequately(world, x, y + i, z, TreeBlock.TRUNK.getBlock(), 2);
                setBlockAndNotifyAdequately(world, x - 1, y + i, z, TreeBlock.TRUNK.getBlock(), 3);
                setBlockAndNotifyAdequately(world, x, y + i, z - 1, TreeBlock.TRUNK.getBlock(), 1);
                setBlockAndNotifyAdequately(world, x - 1, y + i, z - 1, TreeBlock.TRUNK.getBlock(), 0);
            }
        }
        
        return true;
    }
    
    public static long getLastSeed()
    {
        return lastSeed;
    }
}