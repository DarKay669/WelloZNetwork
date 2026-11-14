package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSoulFire extends BlockFire
{
    public BlockSoulFire()
    {
        setTickRandomly(true);
        setLightLevel(1.0F);
        setBlockName("soul_fire");
        setBlockTextureName("wellozmod:soul_fire");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (!world.isRemote && entity instanceof EntityPlayer)
        {
            entity.attackEntityFrom(DamageSource.inFire, 2.0F);
            entity.setFire(4);
        }
    }
    
    public int getFireSpreadSpeed(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection forgeDirection)
    {
        return super.getFireSpreadSpeed(iBlockAccess, x, y, z, forgeDirection);
    }
    
    public int getFlammability(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection forgeDirection)
    {
        return super.getFlammability(iBlockAccess, x, y, z, forgeDirection);
    }
}