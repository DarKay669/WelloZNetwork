package fr.welloznetwork.wellozmod.entity.passive.snow;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntitySnowVillager extends EntityAgeable implements INpc
{   
    public EntitySnowVillager(World world)
    {
        super(world);
        setSize(0.6F, 1.8F);
        getNavigator().setAvoidsWater(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 0.6D));
        tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    }
    
    public boolean isAIEnabled()
    {
        return true;
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    protected String getLivingSound()
    {
        return "mob.villager.idle";
    }
    
    protected String getHurtSound()
    {
        return "mob.villager.hit";
    }
    
    protected String getDeathSound()
    {
        return "mob.villager.death";
    }
    
    public EntitySnowVillager createChild(EntityAgeable entityAgeable)
    {
        return null;
    }
    
    public boolean allowLeashing()
    {
        return false;
    }
}