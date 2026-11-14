package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWreath extends Block
{
    public BlockWreath()
    {
        super(Material.leaves);
        setHardness(0.3F);
        setStepSound(soundTypeGrass);
        setBlockName("wreath");
        setBlockTextureName("wellozmod:wreath");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState((IBlockAccess) world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
    {
        updateLadderBounds(access.getBlockMetadata(x, y, z));
    }
    
    public void updateLadderBounds(int par1)
    {        
        if (par1 == 2)
        {
            setBlockBounds(0.0F, 0.0F, 1.0F - 0.125F, 1.0F, 1.0F, 1.0F);
        }
        
        if (par1 == 3)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
        }
        
        if (par1 == 4)
        {
            setBlockBounds(1.0F - 0.125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        
        if (par1 == 5)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
        }
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
        return 8;
    }
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return(world.isSideSolid(x - 2, y, z, ForgeDirection.EAST) || world.isSideSolid(x + 1, y, z, ForgeDirection.WEST) || world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) || world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH));
    }
    
    public int onBlockPlaced(World world, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int j1 = par9;
        
        if ((j1 == 0 || par5 == 2) && world.isSideSolid(par2, par3, par4 + 1, ForgeDirection.NORTH))
        {
            j1 = 2;
        }
        
        if ((j1 == 0 || par5 == 3) && world.isSideSolid(par2, par3, par4 - 1, ForgeDirection.SOUTH))
        {
            j1 = 3;
        }
        
        if ((j1 == 0 || par5 == 4) && world.isSideSolid(par2 + 1, par3, par4, ForgeDirection.WEST))
        {
            j1 = 4;
        }
        
        if ((j1 == 0 || par5 == 5) && world.isSideSolid(par2 - 1, par3, par4, ForgeDirection.EAST))
        {
            j1 = 5;
        }
        
        return j1;
    }
    
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5) 
    {
        int i1 = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = false;
        
        if (i1 == 2 && par1World.isSideSolid(par2, par3, par4 + 1, ForgeDirection.NORTH))
        {
            flag = true;
        }
        
        if (i1 == 3 && par1World.isSideSolid(par2, par3, par4 - 1, ForgeDirection.SOUTH))
        {
            flag = true;
        }
        
        if (i1 == 4 && par1World.isSideSolid(par2 + 1, par3, par4, ForgeDirection.WEST))
        {
            flag = true;
        }
        
        if (i1 == 5 && par1World.isSideSolid(par2 - 1, par3, par4, ForgeDirection.EAST))
        {
            flag = true;
        }
        
        if (!flag)
        {
            dropBlockAsItem(par1World, par2, par3, par4, i1, 0);
            par1World.setBlockToAir(par2, par3, par4);
        } 
        
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
    }
    
    public int quantityDropped(Random r)
    {
        return 1;
    }
}