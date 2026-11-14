package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockSlime extends BlockBreakable
{
    private boolean bounce = false;
    private double howHigh = 0.0D;
    
    public BlockSlime()
    {
        super("wellozmod:slime_block", Material.clay, false);
        slipperiness = 0.8F;
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName("slime");
        setBlockTextureName("wellozmod:slime_block");
        setStepSound(soundTypeCloth);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ); 
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    protected boolean canSilkHarvest()
    {
        return true;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox(x, y, z, (x + 1), ((y + 1) - 0.0625F), (z + 1));
    }
    
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance)
    {
        if (entity.isSneaking())
        {
            super.onFallenUpon(world, x, y, z, entity, fallDistance);
        }
        
        else 
        {
            ((EntityPlayer) entity).triggerAchievement(RegistryAchievements.BOUNCE_BOUNCE);
            
            entity.fallDistance = 0.0F;
            this.bounce = true;
            this.howHigh =- entity.motionY;
        }
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (!entity.isSneaking())
        {
            if (this.bounce)
            {
                entity.motionY = this.howHigh;
                this.bounce = false;
            } 
            
            else if (Math.abs(entity.motionY) < 0.1D)
            {
                double d0 = 0.4D + Math.abs(entity.motionY) * 0.2D;
                entity.motionX *= d0;
                entity.motionZ *= d0;
            }
        }
        
        super.onEntityCollidedWithBlock(world, x, y, z, entity);
    }
    
    public static class SlimeSoundType extends SoundType
    {
        public final String soundName;
        public final float volume, frequency;
        
        public SlimeSoundType(String soundName, float volume, float frequency)
        {
            super(soundName, volume, frequency);
            this.soundName = soundName;
            this.volume = volume;
            this.frequency = frequency;
        }
        
        public String getBreakSound()
        {
            return "mob.slime.big";
        }
        
        public String getStepResourcePath()
        {
            return "mob.slime.big";
        }
        
        public String func_150496_b()
        {
            return getBreakSound();
        }
    }
}