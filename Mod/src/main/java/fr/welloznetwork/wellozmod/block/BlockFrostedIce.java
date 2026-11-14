package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFrostedIce extends BlockIce
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    
    public BlockFrostedIce()
    {
        setHardness(0.5F);
        setLightOpacity(3);
        setStepSound(soundTypeGlass);
        setBlockName("frosted_ice");
        setBlockTextureName("wellozmod:frosted_ice");
        setCreativeTab(null);
    }
    
    public void onBlockAdded(World world, int x, int y, int z)
    {
        if (world.isRemote)
        {
            return;
        }
        
        world.scheduleBlockUpdate(x, y, z, this, 40 + world.rand.nextInt(40));
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (world.isRemote)
        {
            return;
        }
        
        int surroundingBlockCount = 0;
        
        for (ForgeDirection forgeDirection : ForgeDirection.VALID_DIRECTIONS)
        {
            Block block = world.getBlock(x + forgeDirection.offsetX, y + forgeDirection.offsetY, z + forgeDirection.offsetZ);
            
            if (block == this || block == Blocks.ice || block == Blocks.packed_ice)
            {
                if (surroundingBlockCount++ >= 4)
                {
                    break;
                }
            }
        }
        
        if (surroundingBlockCount < 4 || random.nextInt(100) <= 33)
        {
            int metadata = world.getBlockMetadata(x, y, z);
            
            if (metadata < 3)
            {
                world.setBlockMetadataWithNotify(x, y, z, metadata + 1, 2);
            }
            
            else
            {
                world.setBlock(x, y, z, Blocks.water);
            }
        }
        
        world.scheduleBlockUpdate(x, y, z, this, 40 + random.nextInt(40));
    }
    
    protected boolean canSilkHarvest()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (metadata < 0 || metadata >= this.icons.length)
        {
            metadata = 0;
        }
        
        return this.icons[metadata];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.icons = new IIcon[4];
        
        for (int i = 0; i < this.icons.length; i++)
        {
            this.icons[i] = iiconRegister.registerIcon(this.getTextureName() + "_" + i);
        }
    }
}