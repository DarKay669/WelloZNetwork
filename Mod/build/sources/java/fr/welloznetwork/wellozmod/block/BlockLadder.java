package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLadder extends BlockTrapDoor
{    
    public BlockLadder()
    {
        super(Material.wood);
        setHardness(3.0F);
        setStepSound(soundTypeWood);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.5F, 1.0F, 1.5F);
        setBlockName("ladder");
        setBlockTextureName("wellozmod:ladder");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState((IBlockAccess) world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState((IBlockAccess) world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }
    
    public void func_150117_b(int metadata)
    {
        boolean isOpen = ((metadata & 0x4) != 0);
        
        if (isOpen)
        {
            switch (metadata & 0x3)
            {
                case 0:
                    setBlockBounds(0.0F, 0.0F, 1.0F - 0.125F, 1.0F, 1.0F, 1.0F);
                    return;
                    
                case 1:
                    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
                    return;
                    
                case 2:
                    setBlockBounds(1.0F - 0.125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    return;
            }
        }
        
        else
        {
            boolean isOnTop = ((metadata & 0x8) != 0);
            
            if (isOnTop)
            {
                setBlockBounds(0.0F, 1.0F - 0.125F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            
            else
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
            }
        }
    }
    
    public boolean isLadder(IBlockAccess iBlockAccess, int x, int y, int z, EntityLivingBase entityLivingBase)
    {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        
        return ((metadata & 0x4) != 0);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return 0;
    }
}