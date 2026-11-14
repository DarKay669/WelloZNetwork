package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMossCarpet extends Block
{
    public BlockMossCarpet()
    {
        super(Material.carpet);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        setTickRandomly(true);
        setResistance(0.1F);
        setHardness(0.1F);
        setBlockName("moss_carpet");
        setBlockTextureName("wellozmod:moss_carpet");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_MOSS);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) 
    {
        return RegistryBlocks.moss_block.getIcon(side, metadata);
    }
    
    public AxisAlignedBB getCollisionBoudingBoxFromPool(World world, int x, int y, int z)
    {
        byte b0 = 0;
        float f = 0.0625F;
        return AxisAlignedBB.getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) ((float) y + (float) b0 * f), (double) z + this.maxZ);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
    {
        this.func_150089_b(access.getBlockMetadata(x, y, z));
    }
    
    protected void func_150089_b(int i)
    {
        byte b0 = 0;
        float f = (float) (1 * (1 + b0)) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        this.func_150090_e(world, x, y, z);
    }
    
    private boolean func_150090_e(World world, int x, int y, int z)
    {
        if (!this.canBlockStay(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
            return false;
        } else {
            return true;
        }
    }
    
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return !world.isAirBlock(x, y -1, z);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side)
    {
        return side == 1 ? true : super.shouldSideBeRendered(access, x, y, z, side);
    }
    
    public int damageDropped(int metadata)
    {
        return metadata;
    }
    
    /*@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < 16; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }*/
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister) {}
}