package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import javax.swing.Icon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.tileentity.TileEntityLights;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLights extends BlockContainer
{
    String mobName;
    
    @SideOnly(Side.CLIENT)
    private IIcon iconPresentTop, iconPresentBottom;
    
    @SideOnly(Side.CLIENT)
    private Icon iconGrassTop, iconGrassBottom;
    
    @SideOnly(Side.CLIENT)
    private static Icon iconGrassSideOverlay;
    
    public BlockLights()
    {
        super(Material.leaves);
        setHardness(0.6F);
        setLightLevel(0.7F);
        setStepSound(soundTypeGlass);
        setBlockName("lights");
        setBlockTextureName("wellozmod:lights_side");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public void randomDisplayTick(World world, int x, int y, int z, Random r)
    {
        if (world.canLightningStrikeAt(x, y + 1, z))
            if (!World.doesBlockHaveSolidTopSurface((IBlockAccess) world, x, y - 1, z) && r.nextInt(15) == 1)
            {
                double d0 = (x + r.nextFloat()), d1 = y - 0.05D, d2 = (z + r.nextFloat());
                world.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
    }
    
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        TileEntityLights tile = (TileEntityLights) world.getTileEntity(x, y, z);
        BlockLeaves blockLeaves = Blocks.leaves;
        
        if (!world.isRemote)
        {
            world.setBlock(x, y, z, Block.getBlockById(tile.leaf), tile.type, 2);
            dropBlockAsItem(world, x, y, z, 1, 0);
        }
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (player.getHeldItem() == null)
        {
            TileEntityLights tile = (TileEntityLights) world.getTileEntity(x, y, z);
            BlockLeaves blockLeaves = Blocks.leaves;
            
            if (!world.isRemote && tile != null)
            {
                world.setBlock(x, y, z, Block.getBlockById(tile.leaf), tile.type, 2);
                dropBlockAsItem(world, x, y, z, 1, 0);
            }
            
            return true;
        }
        
        return false;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return (side == 1) ? this.iconPresentTop : ((side == 0) ? this.iconPresentBottom : this.blockIcon);
    }
    
    public IIcon getBlockTexture(IBlockAccess access, int x, int y, int z, int i)
    {
        if (i == 1)
        {
            return this.iconPresentTop;
        }
        
        if (i == 0)
        {
            return this.iconPresentBottom;
        }
        
        return this.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:" + "lights_side");
        this.iconPresentTop = iiconRegister.registerIcon("wellozmod:" + "lights_top");
        this.iconPresentBottom = iiconRegister.registerIcon("wellozmod:" + "lights_top");
    }
    
    public boolean isLeaves(IBlockAccess access, int x, int y, int z)
    {
        return (getMaterial() == Material.leaves);
    }
    
    public TileEntity createNewTileEntity(World world, int i)
    {
        return (TileEntity) new TileEntityLights();
    }
}