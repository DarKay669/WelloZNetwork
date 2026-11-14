package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGrassPath extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;
    
    public BlockGrassPath()
    {
        super(Material.ground);
        setHardness(0.6F);
        setHarvestLevel("shovel", 0);
        setStepSound(soundTypeGrass);
        setBlockName("grass_path");
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        
        this.useNeighborBrightness = true;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }
    
    public Item getItemDropped(int metadata, Random random, int fortune)
    {
        return Blocks.dirt.getItemDropped(metadata, random, fortune);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (world.getBlock(x, y + 1, z).getMaterial().isSolid())
        {
            world.setBlock(x, y, z, Blocks.dirt);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side)
    {        
        if (iBlockAccess.getBlock(x, y, z) instanceof BlockGrassPath || iBlockAccess.getBlock(x, y, z) instanceof BlockFarmland)
        {
            return false;
        }
        
        return super.shouldSideBeRendered(iBlockAccess, x, y, z, side);
    }
    
    public boolean canSilkHarvest()
    {
        return false;
    }
    
    public boolean isSideSolid(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection forgeDirection)
    {
        return forgeDirection != ForgeDirection.UP;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return side == 0 ? Blocks.dirt.getIcon(side, 0) : side == 1 ? this.blockIcon : this.sideIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:grass_path_top");
        this.sideIcon = iiconRegister.registerIcon("wellozmod:grass_path_side");
    }
}