package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockMagma extends Block
{
    public BlockMagma()
    {
        super(Material.rock);
        setLightLevel(3.0F);
        setResistance(0.5F);
        setHardness(0.5F);
        setHarvestLevel("pickaxe", 0);
        setBlockName("magma");
        setBlockTextureName("wellozmod:magma");
        setStepSound(soundTypeStone);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1 - 0.125F, z + 1);
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        super.onEntityCollidedWithBlock(world, x, y, z, entity);
        
        if (!(entity instanceof EntityLivingBase))
        {
            return;
        }
        
        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
        entityLivingBase.setFire(20);
        
        ((EntityPlayer) entity).triggerAchievement(RegistryAchievements.OUCH_THAT_BURN);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        Block block = world.getBlock(x, y + 1, z);
        Block block1 = world.getBlock(x, y + 2, z);
        
        if ((block == Blocks.water || block == Blocks.flowing_water) && block1.isAir(world, x, y + 2, z)) 
        {
            world.spawnParticle("explode", x + 0.5D, y + 1.0D, z + 0.5D, 0.0D, 0.0D, 0.0D);
        }
        
        else if ((block == Blocks.water || block == Blocks.flowing_water) && (block1 == Blocks.water || block1 == Blocks.flowing_water))
        {
            world.spawnParticle("bubble", x + 0.5D, y + 1.1D, z + 0.5D, 0.0D, 1.0D, 0.0D);
        }
    }
}