package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.client.registry.RenderingRegistry;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.tileentity.TileEntityEndRod;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockEndRod extends BlockContainer
{
    public static int endRodModel = -1;
    
    public BlockEndRod()
    {
        super(Material.rock);
        setResistance(0.9375F);
        setHardness(0.0F);
        setLightLevel(1.0F);
        setBlockName("end_rod");
        setBlockTextureName("wellozmod:end_rod");
        setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
        setStepSound(soundTypeWood);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        
        this.endRodModel = RenderingRegistry.getNextAvailableRenderId();
    }
    
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
    {
        return !(entity instanceof EntityDragon);
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
        
        if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP)
        {
            setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
        }
        else if (dir == ForgeDirection.WEST || dir == ForgeDirection.EAST)
        {
            setBlockBounds(0.0F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F);
        }
        else if(dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH)
        {
            setBlockBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);
        }
    }
    
    public int getRenderType()
    {
        return this.endRodModel;
    }
    
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();
        
        if (world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) != this)
            dir = dir.getOpposite();
        
        return dir.ordinal();
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return (TileEntity) new TileEntityEndRod();
    }
}