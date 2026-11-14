package fr.welloznetwork.wellozmod.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBamboo extends Block
{
    private IIcon bambooSide, bambooTop;
    public static int bambooModel = -1;
    
    public BlockBamboo()
    {
        super(Material.plants);
        setHarvestLevel("axe", 0);
        setHardness(0.2F);
        setTickRandomly(true);
        setBlockName("bamboo");
        setBlockTextureName("wellozmod:bamboo");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_BAMBOO);
        
        bambooModel = RenderingRegistry.getNextAvailableRenderId();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.bambooSide = iiconRegister.registerIcon("wellozmod:bamboo");
        this.bambooTop = iiconRegister.registerIcon("wellozmod:bamboo_top");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side > 1)
        {
            return bambooSide;
        }
        
        else
        {
            return bambooTop;
        }
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (world.isAirBlock(x, y + 1, z))
        {
            int var6;
            
            for (var6 = 1; world.getBlock(x, y - var6, z) == this; var6++) {}
            
            if (var6 < 3)
            {
                int metadata = world.getBlockMetadata(x, y, z);
                
                if (metadata == 15)
                {
                    world.setBlock(x, y + 1, z, this, 0, 2);
                    world.setBlockMetadataWithNotify(x, y, z, 0, 2);
                }
                
                else
                {
                    world.setBlockMetadataWithNotify(x, y, z, metadata + 1, 2);
                }
            }
        }
    }
    
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox((double) x + (1.0F - (0.0625F * 4)), (double) y, (double) z + (1.0F - (0.0625F * 4)), (double) x + (0.0625F * 4), (double) y + 1.0F, (double) z + (0.0625F * 4));
    }
    
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity)
    {
        this.setBlockBounds((0.0625F * 4), 0.0F, (0.0625F * 4), 1.0F - (0.0625F * 4), 1.0F, 1.0F - (0.0625F * 4));
        super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        this.setBlockBoundsForItemRender();
    }
    
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y - 1, z);
        
        if (block == this)
        {
            return true;
        }
        
        else if (block == Blocks.grass)
        {
            return true;
        }
        
        else
        {
            return false;
        }
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        this.checkBlockCoordValid(world, x, y, z);
    }
    
    public final void checkBlockCoordValid(World world, int x, int y, int z)
    {
        if (!this.canBlockStay(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }
    
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return this.canPlaceBlockAt(world, x, y, z);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return this.bambooModel;
    }
    
    public boolean canBeReplacedByLeaves(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        return false;
    }
    
    public boolean canSustainLeaves(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        return true;
    }
}