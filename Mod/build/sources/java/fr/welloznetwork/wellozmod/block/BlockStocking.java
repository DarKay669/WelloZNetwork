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

public class BlockStocking extends Block
{
    public BlockStocking()
    {
        super(Material.circuits);
        setHardness(0.2F);
        setStepSound(soundTypeCloth);
        setBlockName("stocking");
        setBlockTextureName("wellozmod:stocking");
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
        return (world.isSideSolid(x - 1, y, z, ForgeDirection.EAST) || world.isSideSolid(x + 1, y, z, ForgeDirection.WEST) || world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) || world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH));
    }
    
    public int onBlockPlaced(World world, int x, int y, int z, int par5, float par6, float par7, float par8, int par9)
    {
        int j1 = par9;
        
        if ((j1 == 0 || par5 == 2) && world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH))
        {
            j1 = 2;
        }
        
        if ((j1 == 0 || par5 == 3) && world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH))
        {
            j1 = 3;
        }
        
        if ((j1 == 0 || par5 == 4) && world.isSideSolid(x + 1, y, z, ForgeDirection.WEST))
        {
            j1 = 4;
        }
        
        if ((j1 == 0 || par5 == 5) && world.isSideSolid(x - 1, y, z, ForgeDirection.EAST))
        {
            j1 = 5;
        }
            
        return j1;
    }
    
    public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block b)
    {
        int l = p_149695_1_.getBlockMetadata(x, y, z);
        boolean flag = false;
        
        if (l == 2 && p_149695_1_.isSideSolid(x, y, z + 1, ForgeDirection.NORTH))
        {
            flag = true;
        }
        
        if (l == 3 && p_149695_1_.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH))
        {
            flag = true;
        }
        
        if (l == 4 && p_149695_1_.isSideSolid(x + 1, y, z, ForgeDirection.WEST))
        {
            flag = true;
        }
        
        if (l == 5 && p_149695_1_.isSideSolid(x - 1, y, z, ForgeDirection.EAST))
        {
            flag = true;
        }
        
        if (!flag)
        {
            dropBlockAsItem(p_149695_1_, x, y, z, l, 0);
            p_149695_1_.setBlockToAir(x, y, z);
        } 
        
        super.onNeighborBlockChange(p_149695_1_, x, y, z, b);
    }
    
    public int getDroppedItem(Random r)
    {
        return 1;
    }
}