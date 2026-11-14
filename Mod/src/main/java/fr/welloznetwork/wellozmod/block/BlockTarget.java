package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTarget extends Block
{    
    @SideOnly(Side.CLIENT)
    private IIcon top, side;
    
    public BlockTarget()
    {
        super(Material.wood);
        setHardness(0.5F);
        setResistance(0.5F);
        setHarvestLevel("hoe", 0);
        setBlockName("target");
        setBlockTextureName("wellozmod:target_side");
        setStepSound(soundTypeGrass);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (world.isRemote)
        {
            return;
        }
        
        int metadata = world.getBlockMetadata(x, y, z);
        
        if (metadata == 0 && entity instanceof IProjectile)
        {
            int newMetadata = this.determinePower(world, x, y, z, entity);
            world.setBlockMetadataWithNotify(x, y, z, newMetadata, 3);
            int delay = entity instanceof EntityArrow ? 20 : 8;
            world.scheduleBlockUpdate(x, y, z, this, delay);
        }
    }
    
    private double fractionalPos(double n)
    {
        return n - MathHelper.floor_double(n);
    }
    
    private int determinePower(World world, int x, int y, int z, Entity entity)
    {
        MovingObjectPosition movingObjectPosition = world.func_147447_a(Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ), Vec3.createVectorHelper(x + 0.5, y + 0.5, z + 0.5), true, false, false);
        
        if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectType.BLOCK)
        {
            double xDiff = Math.abs(this.fractionalPos(movingObjectPosition.hitVec.xCoord) - 0.5D);
            double yDiff = Math.abs(this.fractionalPos(movingObjectPosition.hitVec.yCoord) - 0.5D);
            double zDiff = Math.abs(this.fractionalPos(movingObjectPosition.hitVec.zCoord) - 0.5D);
            double finalDiff;
            
            switch (movingObjectPosition.sideHit)
            {
                default:
                case 0:
                case 1:
                    finalDiff = Math.max(xDiff, zDiff);
                    break;
                    
                case 2:
                case 3:
                    finalDiff = Math.max(xDiff, yDiff);
                    break;
                    
                case 4:
                case 5:
                    finalDiff = Math.max(yDiff, zDiff);
                    break;
            }
            
            return Math.max(1, MathHelper.ceiling_double_int(15.0D * MathHelper.clamp_double((0.5D - finalDiff) / 0.5D, 0.0D, 1.0D)));
        }
        
        return 0;
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        world.setBlockMetadataWithNotify(x, y, z, 0, 3);
    }
    
    public boolean canProvidePower()
    {
        return true;
    }
    
    public boolean shouldCheckWeakPower(IBlockAccess iBlockAccess, int x, int y, int z, int side)
    {
        return false;
    }
    
    public int isProvidingStrongPower(IBlockAccess iBlockAccess, int x, int y, int z, int metadata)
    {
        return this.isProvidingWeakPower(iBlockAccess, x, y, z, metadata);
    }
    
    public int isProvidingWeakPower(IBlockAccess iBlockAccess, int x, int y, int z, int metadata)
    {
        return iBlockAccess.getBlockMetadata(x, y, z);
    }
    
    public boolean isNormalCube(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        return true;
    }
    
    public boolean isNormalCube()
    {
        return true;
    }
    
    public boolean isOpaqueCube()
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 1)
        {
            return this.top;
        } else if (side == 2 || side == 3 || side == 4 || side == 5) {
            return this.side;
        }
        
        return this.blockIcon;
    }
    
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:" + "target_side");
        this.top = iiconRegister.registerIcon("wellozmod:" + "target_top");
        this.side = iiconRegister.registerIcon("wellozmod:" + "target_side");
    }
}