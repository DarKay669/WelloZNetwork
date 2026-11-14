package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import org.apache.commons.lang3.mutable.MutableInt;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockObserver extends Block implements IBlockObserver
{
    @SideOnly(Side.CLIENT)
    private IIcon observerFront, observerBack, observerTop, observerBackLit;
    
    public static int observerModel = RenderingRegistry.getNextAvailableRenderId();
    
    private static final ThreadLocal<MutableInt> TIMES_DISABLED = ThreadLocal.withInitial(MutableInt::new);
    
    public BlockObserver()
    {
        super(Material.rock);
        setResistance(3.0F);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 0);
        setBlockName("observer");
        setBlockTextureName("wellozmod:observer_front");
        setStepSound(soundTypeStone);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack)
    {
        int l = BlockPistonBase.determineOrientation(world, x, y, z, entityLivingBase);
        world.setBlockMetadataWithNotify(x, y, z, Facing.oppositeSide[l], 2);
        
        ((EntityPlayer) entityLivingBase).triggerAchievement(RegistryAchievements.SOMEONE_WATCHING_YOU);
    }
    
    public void observedNeighborChange(World world, int x, int y, int z, Block block, int x1, int y1, int z1)
    {
        if (world.isRemote)
        {
            return;
        }
        
        int metadata = world.getBlockMetadata(x, y, z);
        int facing = BlockPistonBase.getPistonOrientation(metadata);
        int observedX = x + Facing.offsetsXForSide[facing];
        int observedY = y + Facing.offsetsYForSide[facing];
        int observedZ = z + Facing.offsetsZForSide[facing];
        
        if (observedX == x1 && observedY == y1 && observedZ == z1)
        {
            if ((metadata & 8) == 0)
            {
                world.scheduleBlockUpdate(x, y, z, this, 2);
            }
        }
    }
    
    private void updateNeighborsInFront(World world, int x, int y, int z)
    {
        int facing = BlockPistonBase.getPistonOrientation(world.getBlockMetadata(x, y, z));
        int opposite = Facing.oppositeSide[facing];
        int newX = x + Facing.offsetsXForSide[opposite];
        int newY = y + Facing.offsetsYForSide[opposite];
        int newZ = z + Facing.offsetsZForSide[opposite];
        world.notifyBlockOfNeighborChange(newX, newY, newZ, this);
        world.notifyBlocksOfNeighborChange(newX, newY, newZ, this, facing);
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        
        if ((metadata & 8) != 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, metadata & 7, 2);
        }
        
        else
        {
            world.setBlockMetadataWithNotify(x, y, z, metadata | 8, 2);
            world.scheduleBlockUpdate(x, y, z, this, 2);
        }
        
        this.updateNeighborsInFront(world, x, y, z);
    }
    
    public boolean canProvidePower()
    {
        return true;
    }
    
    public int isProvidingStrongPower(IBlockAccess iBlockAccess, int x, int y, int z, int metadata)
    {
        return this.isProvidingWeakPower(iBlockAccess, x, y, z, metadata);
    }
    
    public int isProvidingWeakPower(IBlockAccess iBlockAccess, int x, int y, int z, int metadata)
    {
        int meta = iBlockAccess.getBlockMetadata(x, y, z);
        
        if ((meta & 8) == 0)
        {
            return 0;
        }
        
        return (BlockPistonBase.getPistonOrientation(meta) == metadata) ? 15 : 0;
    }
    
    public boolean isNormalCube(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.observerTop = iiconRegister.registerIcon("wellozmod:observer_top");
        this.observerFront = iiconRegister.registerIcon("wellozmod:observer_front");
        this.blockIcon = iiconRegister.registerIcon("wellozmod:observer_side");
        this.observerBack = iiconRegister.registerIcon("wellozmod:observer_back");
        this.observerBackLit = iiconRegister.registerIcon("wellozmod:observer_back_on");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {        
        int k = BlockPistonBase.getPistonOrientation(metadata);
        boolean powered = (metadata & 0x8) != 0;
        
        if (side == k)
        {
            return this.observerFront;
        }
        
        else if (side == Facing.oppositeSide[k])
        {
            return powered ? this.observerBackLit : this.observerBack;
        }
        
        else
        {
            int topSide;
            
            switch (k)
            {
                case 2:
                case 3:
                case 4:
                case 5:
                    topSide = 1;
                    break;
                    
                default:
                case 0:
                case 1:
                    topSide = 2;
                    break;
            }
            
            return (side == topSide || side == Facing.oppositeSide[topSide]) ? this.observerTop : this.blockIcon;
        }
    }
    
    public int getRenderType()
    {
        return observerModel;
    }
    
    public static void disableNotifications()
    {
        TIMES_DISABLED.get().increment();
    }
    
    public static void enableNotifications()
    {
        MutableInt timesDisabledMutable = TIMES_DISABLED.get();
        
        int timesDisabledInt = timesDisabledMutable.intValue();
        
        timesDisabledInt = Math.max(0, timesDisabledInt - 1);
        timesDisabledMutable.setValue(timesDisabledInt);
    }
    
    public static boolean areNotificationsEnabled()
    {
        return TIMES_DISABLED.get().intValue() == 0;
    }
}