package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.dev.DeveloperHelper;
import fr.welloznetwork.wellozmod.entity.projectile.EntityArrowTipped;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCloud extends Block
{
    public BlockCloud()
    {
        super(Material.ice);
        
        setHardness(0.2F);
        setHarvestLevel("shovel", 0);
        setBlockName("cloud");
        setBlockTextureName("wellozmod:cloud");
        setStepSound(soundTypeCloth);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    @DeveloperHelper("Maybe an achievement")
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        entity.fallDistance = 0.0F;
        
        if (world.getBlockMetadata(x, y, z) == 1)
        {
            if (entity instanceof EntityPlayer)
            {
                EntityPlayer entityPlayer = (EntityPlayer) entity;
                
                //Achievement
                
                if (entityPlayer.isSneaking())
                {
                    if (entity.motionY < 0.0D)
                    {
                        entity.motionY *= 0.005D;
                    }
                    
                    return;
                }
                
                entity.motionY = 2.0D;
            }
            
            else
            {
                if (entity instanceof EntityArrow || entity instanceof EntityArrowTipped)
                {
                    if (entity.ticksExisted >= 1200)
                    {
                        entity.setDead();
                    }
                }
                
                entity.motionY = 2.0D;
            }
            
            if (world.isRemote)
            {
                if (!(entity instanceof EntityFX))
                {
                    for (int count = 0; count < 50; count++)
                    {
                        world.spawnParticle("splash", x + world.rand.nextDouble(), y + world.rand.nextDouble(), z + world.rand.nextDouble(), 0, 0, 0);
                    }
                }
            }
        }
        
        else if (entity.motionY < 0.0D)
        {
            entity.motionY *= 0.005D;
        }
    }
    
    public boolean renderAsNormalBlock()
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public int damageDropped(int metadata)
    {
        return metadata;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int metadata)
    {
        if (metadata == 1)
        {
            return 0xCCFFFF;
        }
        
        else if (metadata == 2)
        {
            return 0xFFFF80;
        }
        
        return this.getBlockColor();
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        return this.getRenderColor(iBlockAccess.getBlockMetadata(x, y, z));
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side)
    {
        Block block = iBlockAccess.getBlock(x, y, z);
        
        if (iBlockAccess.getBlockMetadata(x, y, z) != iBlockAccess.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]))
        {
            return true;
        }
        
        if (block == this)
        {
            return true;
        }
        
        return super.shouldSideBeRendered(iBlockAccess, x, y, z, side);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) != 1 ? AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0D, y + 0.01D, z + 1.0D) : AxisAlignedBB.getBoundingBox(x, y, z, x, y, z);
    }
}