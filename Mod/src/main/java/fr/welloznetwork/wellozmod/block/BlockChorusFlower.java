package fr.welloznetwork.wellozmod.block;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockChorusFlower extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon deadIcon;
	
	public static int chorusFlowerModel = -1;
	
	public BlockChorusFlower()
	{
		super(Material.wood);
		setResistance(0.4F);
		setHardness(0.4F);
		setHarvestLevel("axe", 0);
		setTickRandomly(true);
		setBlockName("chorus_flower");
		setBlockTextureName("wellozmod:chorus_flower");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setStepSound(RegistrySounds.SOUND_TYPE_CHORUS_FLOWER);
		
		this.chorusFlowerModel = RenderingRegistry.getNextAvailableRenderId();
	}
	
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
	    return !(entity instanceof EntityDragon);
	}
	
	public int getRenderType()
	{
	    return this.chorusFlowerModel;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
	    return (meta >= 5) ? this.deadIcon : this.blockIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
	    super.registerBlockIcons(reg);
	    this.deadIcon = reg.registerIcon("wellozmod:chorus_flower_dead");
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
	    if (world.isRemote)
	        return;
	    
	    int meta = world.getBlockMetadata(x, y, z);
	    
	    if (meta >= 5)
	    {
	        return;
	    }
	    
	    if (!canBlockStay(world, x, y, z))
	    {
	        world.func_147480_a(x, y, z, true);
	    }
	    
	    else if (world.isAirBlock(x, y + 1, z))
	    {
	        boolean canGrowUp = false, isSegmentOnEndstone = false;
	        Block lowerBlock = world.getBlock(x, y - 1, z);
	        
	        if (lowerBlock == Blocks.end_stone)
	        {
	            canGrowUp = true;
	        }
	        
	        else if (lowerBlock == RegistryBlocks.chorus_plant)
	        {
	            int par8 = 1, height;
	            
	            for (height = 0; height < 4; height++)
	            {
	                Block b = world.getBlock(x, y - par8 + 1, z);
	                
	                if (b != RegistryBlocks.chorus_plant)
	                {
	                    if (b == Blocks.end_stone)
	                        isSegmentOnEndstone = true;
	                    break;
	                }
	                
	                par8++;
	            }
	            
	            height = 4;
	            
	            if (isSegmentOnEndstone)
	            {
	                height++;
	            }
	            
	            if (par8 < 2 || rand.nextInt(height) >= par8)
	                canGrowUp = true;
	            
	            else if(lowerBlock.isAir((IBlockAccess) world, x, y -1, z))
	            {
	                canGrowUp = true;
	            }
	            
	            if (canGrowUp && isSpaceAroundFree(world, x, y + 1, z, ForgeDirection.DOWN) && world.isAirBlock(x, y + 2, meta))
	            {
	                world.setBlock(x, y, z, RegistryBlocks.chorus_plant);
	                world.setBlock(x, y + 1, z, (Block) this, meta, 3);
	                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, RegistrySounds.soundTypeChorusFlowerGrow[world.rand.nextInt(RegistrySounds.soundTypeChorusFlowerGrow.length)], 1.0F, 1.0F);
	            }
	            
	            else if (meta < 4)
	            {
	                int tries = rand.nextInt(4);
	                boolean grew = false;
	                
	                if (isSegmentOnEndstone)
	                    tries++;
	                
	                for (int i = 0; i < tries; i++)
	                {
	                    ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[rand.nextInt(ForgeDirection.VALID_DIRECTIONS.length)];
	                    int xx = x + dir.offsetX;
	                    int yy = y + dir.offsetY;
	                    int zz = z + dir.offsetZ;
	                    
	                    if (world.isAirBlock(xx, yy, zz) && isSpaceAroundFree(world, xx, yy, zz, dir.getOpposite()))
	                    {
	                        world.setBlock(xx, yy, zz, (Block) this, meta + 1, 3);
	                        grew = true;
	                    }
	                }
	                
	                if (grew)
	                {
	                    world.setBlock(x, y, z, RegistryBlocks.chorus_plant, 0, 3);
	                }
	                else
	                {
	                    world.setBlock(x, y, z, (Block) this, 5, 3);
	                }
	            }
	            
	            else if (meta == 4)
	            {
	                world.setBlock(x, y, z, (Block) this, 5, 3);
	            }
	        }
	    }
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour)
	{
	    if (!canBlockStay(world, x, y, z))
	    {
	        world.func_147480_a(x, y, z, false);
	    }
	}
	
	public boolean canBlockStay(World world, int x, int y, int z)
	{
	    return canPlantStay(world, x, y, z);
	}
	
	public static boolean canPlantStay(World world, int x, int y, int z)
	{
	    Block block = world.getBlock(x, y - 1, z);
	    
	    if (block != RegistryBlocks.chorus_plant && block != Blocks.end_stone)
	    {
	        if (block.isAir(world, x, y - 1, z))
	        {
	            int adjecentCount = 0;
	            
	            for (ForgeDirection forgeDirection : ForgeDirection.VALID_DIRECTIONS)
	            {
	                Block adjecentBlock = world.getBlock(x + forgeDirection.offsetX, y + forgeDirection.offsetY, z + forgeDirection.offsetZ);
	                
	                if (adjecentBlock == RegistryBlocks.chorus_plant)
	                {
	                    adjecentCount++;
	                }
	                
	                else if (!adjecentBlock.isAir(world, x + forgeDirection.offsetX, y + forgeDirection.offsetY, z + forgeDirection.offsetZ))
	                {
	                    return false;
	                }
	            }
	            
	            return adjecentCount == 1;
	        }
	        
	        else
	        {
	            return false;
	        }
	    }
	    
	    else
	    {
	        return true;
	    }
	}
	
	public static boolean isSpaceAroundFree(World world, int x, int y, int z, ForgeDirection forgeDirection)
	{
	    ForgeDirection forgeDirection1;
	    Iterator<ForgeDirection> iterator = Arrays.<ForgeDirection> asList(ForgeDirection.VALID_DIRECTIONS).iterator();
	    
	    do
	    {
	        if (!iterator.hasNext())
	        {
	            return true;
	        }
	            
	        forgeDirection1 = iterator.next();
	    } 
	    
	    while (forgeDirection1 == forgeDirection || world.isAirBlock(x + forgeDirection1.offsetX, y + forgeDirection1.offsetY, z + forgeDirection1.offsetZ));
	    {    
	        return false;
	    }
	}
	
	public boolean renderAsNormalBlock()
	{
	    return false;
	}
	
	public boolean isOpaqueCube()
	{
	    return false;
	}
	
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
	    return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
	}
	
	public static void generateChorusPlant(World world, int x, int y, int z, int pass)
    {
        int height;
        
        for (height = 0; height < 4; height++)
        {
            if (!BlockChorusFlower.canPlantStay(world, x, y + height, z))
            {
                world.setBlock(x, y + height, z, RegistryBlocks.chorus_plant, 5, 2);
                break;
            }
            
            world.setBlock(x, y + height, z, RegistryBlocks.chorus_plant);
        }
        
        if (height > 1)
        {
            world.setBlock(x, y + height, z, RegistryBlocks.chorus_plant);
            boolean grew = false;
            
            if (pass < 5)
            {
                ForgeDirection[] FORGE_DIRECTIONS = new ForgeDirection[] {ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.SOUTH};
                
                for (int j = 0; j < world.rand.nextInt(4); j++)
                {
                    ForgeDirection forgeDirection = FORGE_DIRECTIONS[world.rand.nextInt(FORGE_DIRECTIONS.length)];
                    int xx = x + forgeDirection.offsetX;
                    int yy = y + height + forgeDirection.offsetY;
                    int zz = z + forgeDirection.offsetZ;
                    
                    if (world.isAirBlock(xx, yy, zz) && BlockChorusFlower.isSpaceAroundFree(world, xx, yy, zz, forgeDirection.getOpposite()))
                    {
                        generateChorusPlant(world, xx, yy, zz, pass + 1);
                        grew = true;
                    }
                }
            }
            
            if (!grew)
            {
                world.setBlock(x, y + height, z, RegistryBlocks.chorus_flower, 5, 2);
            }
        }
    }
}