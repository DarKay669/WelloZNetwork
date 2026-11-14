package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockStar extends BlockBush
{
    public static int[] starX = new int[] { 888, 888, 888, 888, 888, 888, 888, 888, 888, 888 };
    public static int[] starY = new int[] { 888, 888, 888, 888, 888, 888, 888, 888, 888, 888 };
    public static int[] starZ = new int[] { 888, 888, 888, 888, 888, 888, 888, 888, 888, 888 };
    public static boolean presentTimer = false;
    Random generator = new Random();
    
    public BlockStar()
    {
        super(Material.glass);
        setHardness(0.8F);
        setLightLevel(0.9375F);
        setStepSound(soundTypeGlass);
        setBlockName("star");
        setBlockTextureName("wellozmod:star");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public void onBlockAdded(World par1World, int par2, int par3, int par4) 
    {
        super.onBlockAdded(par1World, par2, par3, par4);
                
        if ((((par1World.getBlock(par2, par3 - 1, par4) == Blocks.leaves || par1World.getBlock(par2, par3 - 1, par4) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2, par3 - 2, par4) == Blocks.log) ? 1 : 0) & ((par1World.getBlock(par2 - 1, par3 - 2, par4) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2 + 1, par3 - 2, par4) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2, par3 - 2, par4 - 1) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2, par3 - 2, par4 + 1) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2, par3 - 3, par4) == Blocks.log) ? 1 : 0) & ((par1World.getBlock(par2 - 1, par3 - 3, par4) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2 + 1, par3 - 3, par4) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2, par3 - 3, par4 - 1) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2, par3 - 3, par4 + 1) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2 + 1, par3 - 3, par4 - 1) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2 + 1, par3 - 3, par4 + 1) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2 - 1, par3 - 3, par4 - 1) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2 - 1, par3 - 3, par4 + 1) == RegistryBlocks.lights) ? 1 : 0) & ((par1World.getBlock(par2, par3 - 5, par4) == Blocks.log) ? 1 : 0) & ((par1World.getBlock(par2, par3 - 6, par4) == Blocks.wool) ? 1 : 0) & ((par1World.getBlock(par2 - 1, par3 - 6, par4) == Blocks.wool) ? 1 : 0) & ((par1World.getBlock(par2 + 1, par3 - 6, par4) == Blocks.wool) ? 1 : 0) & ((par1World.getBlock(par2, par3 - 6, par4 - 1) == Blocks.wool) ? 1 : 0) & ((par1World.getBlock(par2, par3 - 6, par4 + 1) == Blocks.wool) ? 1 : 0) & ((par1World.getBlock(par2 + 1, par3 - 6, par4 - 1) == Blocks.wool) ? 1 : 0) & ((par1World.getBlock(par2 + 1, par3 - 6, par4 + 1) == Blocks.wool) ? 1 : 0) & ((par1World.getBlock(par2 - 1, par3 - 6, par4 - 1) == Blocks.wool) ? 1 : 0) & ((par1World.getBlock(par2 - 1, par3 - 6, par4 + 1) == Blocks.wool) ? 1 : 0)) != 0)
        {    
            if (!par1World.isRemote)
            {        
                this.presentTimer = true;
                
                for (int i = 0; i < this.starY.length; i++)
                {
                    if (this.starY[i] == 888 && !checkStarSpecial(par2, par3, par4).booleanValue())
                    {
                        this.starX[i] = par2;
                        this.starY[i] = par3;
                        this.starZ[i] = par4;
                        par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D, "wellozmod:star", 1.0F, par1World.rand.nextFloat() * 0.1F + 0.9F);
                        break;
                    } 
                } 
            } 
        }
    }
    
    public void breakBlock(World world, int x, int y, int z, Block b, int i1)
    {
        if (!world.isRemote)
        {
            for (int i = 0; i < this.starY.length; i++)
            { 
                if (this.starY[i] != 888 && this.starX[i] == x && this.starY[i] == y && this.starZ[i] == z)
                {
                    this.starX[i] = 888;
                    this.starY[i] = 888;
                    this.starZ[i] = 888;
                }
            }
        }
    }
    
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i1)
    {
        if (!world.isRemote);
    }
    
    public Boolean checkStarSpecial(int x, int y, int z)
    {
        for (int i = 0; i < this.starY.length; i++)
        {
            if (this.starY[i] != 888 && this.starX[i] == x && this.starY[i] == y && this.starZ[i] == z)
            {
                return Boolean.valueOf(true);
            }
        }
        
        return Boolean.valueOf(false);
    }
    
    public void randomDisplayTick(World world, int x, int y, int z, Random r)
    {
        double d0 = x + 0.5D, d1 = y + 0.6D, d2 = z + 0.25D, d3 = 0.2199999988079071D, d4 = 0.27000001072883606D;
        super.randomDisplayTick(world, x, y, z, r);
        
        if (this.presentTimer == true && checkStarSpecial(x, y, z).booleanValue())
        {
            for (int l = x - 2; l <= x + 2; l++)
            {
                for (int i1 = z - 2; i1 <= z + 2; i1++)
                {
                    if (l > x - 2 && l < x + 2 && i1 == z - 1)
                    {
                        i1 = z + 2;
                    }
                    
                    if (r.nextInt(5) == 0)
                    {
                        for (int j1 = y; j1 <= y + 2; j1++)
                        {
                            world.spawnParticle("mobSpellAmbient", d0, d1 + d3, d2 + d4, 0.0D, 0.5D, 0.0D);
                        }
                    }
                }
            }
        }
    }
    
    protected boolean canPlaceBlockOn(Block block)
    {
        return (block == Blocks.leaves || block == RegistryBlocks.lights || block == RegistryBlocks.mangrove_leaves || block == RegistryBlocks.azalea_leaves || block == RegistryBlocks.flowering_azaela_leaves || block == RegistryBlocks.winter_leaves);
    }
    
    public Item getItemDropped(int par1, Random random, int par3)
    {
        return Items.glowstone_dust;
    }
    
    public int quantityDropped(Random random)
    {
        return random.nextInt(4) + 1;
    }
    
    protected boolean canSilkHarvest()
    {
        return true;
    }
}