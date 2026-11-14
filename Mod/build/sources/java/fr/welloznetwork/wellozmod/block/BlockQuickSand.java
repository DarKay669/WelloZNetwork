package fr.welloznetwork.wellozmod.block;

import java.util.List;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockQuickSand extends BlockFalling
{    
    public BlockQuickSand()
    {
        setHardness(0.5F);
        setStepSound(soundTypeSand);
        setBlockName("quick_sand");
        setBlockTextureName("sand");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity)
    {
        AxisAlignedBB axisAlignedBB2 = getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        entity.motionX *= 0.025D;
        entity.motionZ *= 0.025D;
        entity.attackEntityFrom(DamageSource.inWall, 1.0F);
    }   
}