package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Optional;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenAutumnTree extends WorldGenAbstractTree
{
    private static Block trunkBlock = Blocks.log;
    private static int trunkMetadata = 1;
    private static long lastSeed = 0;
    protected final AutumnTreeType type;
    
    public enum Element
    {
        LEAVES_AUTUMN_BROWN,
        LEAVES_AUTUMN_ORANGE,
        LEAVES_AUTUMN_RED,
        LEAVES_AUTUMN_YELLOW,
        CHERRY_LEAVES;
        
        private Optional<ItemStack> stack = Optional.absent();
        
        public ItemStack get()
        {
            return stack.get();
        }
        
        public boolean isPresent()
        {
            return stack.isPresent();
        }
        
        public void set(ItemStack stack)
        {
            this.stack = Optional.of(stack);
        }
    }
    
    public enum TreeSoilRegistry
    {
        INSTANCE;
        
        private static final List<Block> validSoil;
        
        static
        {
            validSoil = new ArrayList<Block>();
            addValidSoil(Blocks.grass);
            addValidSoil(Blocks.dirt);
            addValidSoil(Blocks.farmland);
        }
        
        public static void addValidSoil(Block soilBlock)
        {
            validSoil.add(soilBlock);
        }
        
        public static boolean isValidSoil(Block soil)
        {
            return validSoil.contains(soil);
        }
    }
    
    public enum AutumnTreeType
    {
        BROWN, ORANGE, RED, YELLOW, CHERRY;
        
        private ItemStack leaves = new ItemStack(Blocks.leaves);
        private static boolean loadedCustomBlocks = false;
        
        private static void loadCustomBlocks()
        {
            if (Element.LEAVES_AUTUMN_BROWN.isPresent())
                BROWN.leaves = Element.LEAVES_AUTUMN_BROWN.get();
            
            if (Element.LEAVES_AUTUMN_ORANGE.isPresent())
                ORANGE.leaves = Element.LEAVES_AUTUMN_ORANGE.get();
            
            if (Element.LEAVES_AUTUMN_RED.isPresent())
                RED.leaves = Element.LEAVES_AUTUMN_RED.get();
            
            if (Element.LEAVES_AUTUMN_YELLOW.isPresent())
                YELLOW.leaves = Element.LEAVES_AUTUMN_YELLOW.get();
            
            if (Element.CHERRY_LEAVES.isPresent())
                CHERRY.leaves = Element.CHERRY_LEAVES.get();
            
            loadedCustomBlocks = true;
        }
        
        public Item getItem()
        {
            if (!loadedCustomBlocks)
                loadCustomBlocks();
            
            return leaves.getItem();
        }
        
        public Block getBlock()
        {
            return Block.getBlockFromItem(getItem());
        }
        
        public int getMetadata()
        {
            if (!loadedCustomBlocks)
                loadCustomBlocks();
            
            return leaves.getItemDamage();
        }
    }
    
    private static boolean isBlockSuitableForGrowing(final World world, final int x, final int y, final int z)
    {
        final Block block = world.getBlock(x, y, z);
        return TreeSoilRegistry.isValidSoil(block);
    }
    
    private static boolean isRoomToGrow(final World world, final int x, final int y, final int z, final int height)
    {
        for (int i = y; i <= y + 1 + height; i++)
        {
            if (i < 0 || i >= 256)
                return false;
            
            int radius = 1;
            
            if (i == y)
                radius = 0;
            
            if (i >= y + 1 + height - 2)
                radius = 2;
            
            for (int x1 = x - radius; x1 <= x + radius; x1++)
            {
                for (int z1 = z - radius; z1 <= z + radius; z1++)
                {
                    final Block block = world.getBlock(x1, i, z1);
                    
                    if (block != null && !block.isLeaves(world, x1, i, z1) && !block.equals(Blocks.grass) && !block.isWood(world, x1, i, z1) && !block.isReplaceable(world, x1, i, z1))
                    {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    public static void setTrunkBlock(Block block, int metadata)
    {
        trunkBlock = block;
        trunkMetadata = metadata;
    }
    
    public WorldGenAutumnTree(boolean doBlockNotify, AutumnTreeType type)
    {
        super(doBlockNotify);
        this.type = type;
    }
    
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
        final int height = random.nextInt(2 + 1) + 4;
        
        if (y < 1 || y + height + 1 > 256)
            return false;
        
        if (!isBlockSuitableForGrowing(world, x, y - 1, z))
            return false;
        
        if (!isRoomToGrow(world, x, y, z, height))
            return false;
        
        world.setBlock(x, y - 1, z, Blocks.dirt);

        if (random.nextInt(5) == 0)
        {
            growLeaves(world, random, x, y, z, height, RegistryBlocks.brown_autumn_leaves, type.getMetadata());
            growTrunk(world, x, y, z, height, trunkBlock, trunkMetadata);
        }
        
        else if (random.nextInt(5) == 0)
        {
            growLeaves(world, random, x, y, z, height, RegistryBlocks.orange_autumn_leaves, type.getMetadata());
            growTrunk(world, x, y, z, height, trunkBlock, trunkMetadata);
        }

        else if (random.nextInt(5) == 0)
        {
            growLeaves(world, random, x, y, z, height, RegistryBlocks.red_autumn_leaves, type.getMetadata());
            growTrunk(world, x, y, z, height, trunkBlock, trunkMetadata);
        }
        
        else if (random.nextInt(5) == 0)
        {
            growLeaves(world, random, x, y, z, height, RegistryBlocks.yellow_autumn_leaves, type.getMetadata());
            growTrunk(world, x, y, z, height, trunkBlock, trunkMetadata);
        }
        
        else if (random.nextInt(5) == 0)
        {
            growLeaves(world, random, x, y, z, height, RegistryBlocks.cherry_leaves, type.getMetadata());
            growTrunk(world, x, y, z, height, RegistryBlocks.cherry_log, trunkMetadata);
        }
        
        return true;
    }
    
    private void growLeaves(final World world, final Random random, final int x, final int y, final int z, final int height, Block leaf, int leafMeta)
    {
        for (int y1 = y - 3 + height; y1 <= y + height; y1++)
        {
            final int canopyRow = y1 - (y + height);
            final int radius = 0 + 1 - canopyRow / 2;
            
            for (int x1 = x - radius; x1 <= x + radius; x1++)
            {
                final int xDistanceFromTrunk = x1 - x;
                
                for (int z1 = z - radius; z1 <= z + radius; z1++)
                {
                    final int zDistanceFromTrunk = z1 - z;
                    final Block block = world.getBlock(x1, y1, z1);
                    
                    if ((Math.abs(xDistanceFromTrunk) != radius || Math.abs(zDistanceFromTrunk) != radius || random.nextInt(2) != 0 && canopyRow != 0) && (block == null || block.canBeReplacedByLeaves(world, x1, y1, z1)))
                    {
                        setBlockAndNotifyAdequately(world, x1, y1, z1, leaf, leafMeta);
                    }
                }
            }
        }
    }
    
    private void growTrunk(final World world, final int x, final int y, final int z, final int height, Block wood, int woodMeta)
    {
        for (int y1 = 0; y1 < height; y1++)
        {
            final Block block = world.getBlock(x, y + y1, z);
            
            if (block == null || block.isReplaceable(world, x, y + y1, z) || block.isLeaves(world, x, y + y1, z))
            {
                setBlockAndNotifyAdequately(world, x, y + y1, z, wood, woodMeta);
            }
        }
    }
    
    public static long getLastSeed()
    {
        return lastSeed;
    }
}