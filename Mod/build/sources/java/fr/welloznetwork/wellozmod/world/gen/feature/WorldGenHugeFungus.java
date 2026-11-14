package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import fr.welloznetwork.wellozmod.block.BlockCrimsonNylium;
import fr.welloznetwork.wellozmod.block.BlockWarpedNylium;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenHugeFungus extends WorldGenAbstractTree
{
    private boolean planted;
    public Block log;
    private Block leaf;
    private int logMetadata, leafMetadata;
    
    public WorldGenHugeFungus(boolean notify, int logMetadata, int leafMetadata, Block log, Block leaf)
    {
        this(notify, logMetadata, leafMetadata, log, leaf, false);
    }
    
    public WorldGenHugeFungus(boolean notify, int logMetadata, int leafMetadata, Block log, Block leaf, boolean planted)
    {
        super(notify);
        
        this.logMetadata = logMetadata;
        this.leafMetadata = leafMetadata;
        this.log = log;
        this.leaf = leaf;
        this.planted = planted;
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        Block block = world.getBlock(x, y - 1, z);
        
        if (block instanceof BlockWarpedNylium || block instanceof BlockCrimsonNylium)
        {
            int i = MathHelper.getRandomIntegerInRange(random, 4, 13);
            
            if (random.nextInt(12) == 0)
            {
                i *= 2;
            }
            
            boolean flag = !this.planted && random.nextFloat() < 0.06F;
            
            world.setBlock(x, y, z, Blocks.air);
            
            if (!this.planted)
            {
                world.setBlock(x, y - 1, z, Blocks.netherrack);
            }
            
            this.placeStem(world, random, x, y, z, i, flag);
            this.placeLeaf(world, random, x, y, z, i, flag);
            
            return true;
        }
        
        return false;
    }
    
    protected boolean isReplaceable(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        
        return block instanceof BlockLeavesBase || block instanceof BlockSapling || block instanceof BlockFlower || block.isReplaceable(world, x, y, z);
    }
    
    private void placeStem(World world, Random random, int x, int y, int z, int p_285249_, boolean p_285355_)
    {
        int i = p_285355_ ? 1 : 0;
        
        for (int j = -i; j <= i; j++)
        {
            for (int k = -i; k <= i; k++)
            {
                boolean flag = p_285355_ && MathHelper.abs(j) == i && MathHelper.abs(k) == i;
                
                for (int l = 0; l < p_285249_; l++)
                {
                    int offsetX = x + j;
                    int offsetY = y + l;
                    int offsetZ = z + k;
                    
                    if (this.isReplaceable(world, offsetX, offsetY, offsetZ))
                    {
                        if (this.planted)
                        {
                            if (!world.isAirBlock(offsetX, offsetY - 1, offsetZ))
                            {
                                world.setBlockToAir(offsetX, offsetY, offsetZ);
                            }
                            
                            this.setBlockAndNotifyAdequately(world, offsetX, offsetY, offsetZ, this.log, this.logMetadata);
                        }
                        
                        else if (flag)
                        {
                            if (random.nextFloat() < 0.1F)
                            {
                                this.setBlockAndNotifyAdequately(world, offsetX, offsetY, offsetZ, this.log, this.logMetadata);
                            }
                        }
                        
                        else
                        {
                            this.setBlockAndNotifyAdequately(world, offsetX, offsetY, offsetZ, this.log, this.logMetadata);
                        }
                    }
                }
            }
        }
    }
    
    private void placeLeaf(World world, Random random, int x, int y, int z, int p_285156_, boolean p_285265_)
    {
        boolean flag = this.leaf == RegistryBlocks.warped_wart && this.leafMetadata == 0;
        
        int i = Math.min(random.nextInt(1 + p_285156_ / 3) + 5, p_285156_);
        int j = p_285156_ - i;
        
        for (int k = j; k <= p_285156_; k++)
        {
            int l = k < p_285156_ - random.nextInt(3) ? 2 : 1;
            
            if (i > 8 && k < j + 4)
            {
                l = 3;
            }
            
            if (p_285265_)
            {
                l++;
            }
            
            for (int i1 = -l; i1 <= l; i1++)
            {
                for (int j1 = -l; j1 <= l; j1++)
                {
                    boolean flag1 = i1 == -l || i1 == l;
                    boolean flag2 = j1 == -l || j1 == l;
                    boolean flag3 = !flag1 && !flag2 && k != p_285156_;
                    boolean flag4 = flag1 && flag2;
                    boolean flag5 = k < j + 3;
                    
                    int offsetX = x + i1;
                    int offsetY = y + k;
                    int offsetZ = z + j1;
                    
                    if (this.isReplaceable(world, offsetX, offsetY, offsetZ))
                    {
                        if (flag5)
                        {
                            if (!flag3)
                            {
                                this.placeLeafDropBlock(world, random, offsetX, offsetY, offsetZ, flag);
                            }
                        }
                        
                        else if (flag3)
                        {
                            this.placeLeafBlock(world, random, offsetX, offsetY, offsetZ, 0.1F, 0.2F, flag ? 0.1F : 0.0F);
                        }
                        
                        else if (flag4)
                        {
                            this.placeLeafBlock(world, random, offsetX, offsetY, offsetZ, 0.01F, 0.7F, flag ? 0.083F : 0.0F);
                        }
                        
                        else
                        {
                            this.placeLeafBlock(world, random, offsetX, offsetY, offsetZ, 5.0E-4F, 0.98F, flag ? 0.07F : 0.0F);
                        }
                    }
                }
            }
        }
    }
    
    private void placeLeafBlock(World world, Random random, int x, int y, int z, float p_225054_, float p_225055_, float p_225056_)
    {
        if (random.nextFloat() < p_225054_)
        {
            world.setBlock(x, y, z, RegistryBlocks.shroomlight, 0, 3);
        }
        
        else if (random.nextFloat() < p_225055_)
        {
            this.setBlockAndNotifyAdequately(world, x, y, z, this.leaf, this.leafMetadata);
        }
    }
    
    private void placeLeafDropBlock(World world, Random random, int x, int y, int z, boolean p_225069_)
    {
        if (world.getBlock(x, y - 1, z) == this.leaf && world.getBlockMetadata(x, y - 1, z) == this.leafMetadata)
        {
            this.setBlockAndNotifyAdequately(world, x, y, z, this.leaf, this.leafMetadata);
        }
        
        else if ((double) random.nextFloat() < 0.15D)
        {
            this.setBlockAndNotifyAdequately(world, x, y, z, this.leaf, this.leafMetadata);
        }
    }
}