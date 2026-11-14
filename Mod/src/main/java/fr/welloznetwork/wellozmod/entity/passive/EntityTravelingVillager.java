package fr.welloznetwork.wellozmod.entity.passive;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryUI;
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

public class EntityTravelingVillager extends EntityAgeable implements INpc
{
    private float offset = 0.0F, angle = 0.0F;
    
    public EntityTravelingVillager(World world)
    {
        super(world);
        this.setSize(0.6F, 1.8F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }
    
    public boolean interact(EntityPlayer entityPlayer)
    {
        if (!this.worldObj.isRemote)
        {
            entityPlayer.openGui(WelloZMod.instance, RegistryUI.TRAVELING_VILLAGER, this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ);
        }
        
        return true;
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
    
    public EntityAgeable createChild(EntityAgeable entityAgeable)
    {
        return null;
    }
    
    public boolean allowLeashing()
    {
        return false;
    }
}