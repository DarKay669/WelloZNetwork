package fr.welloznetwork.wellozmod.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityElf extends EntityAnimal
{
    public EntityElf(World world)
    {
        super(world);
        setSize(0.6F, 1.8F);
        getNavigator().setBreakDoors(true);
        getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, (EntityAIBase) new EntityAISwimming((EntityLiving) this));
        this.tasks.addTask(2, (EntityAIBase) new EntityAIMoveIndoors((EntityCreature) this));
        this.tasks.addTask(3, (EntityAIBase) new EntityAIRestrictOpenDoor((EntityCreature) this));
        this.tasks.addTask(4, (EntityAIBase) new EntityAIOpenDoor((EntityLiving) this, true));
        this.tasks.addTask(5, (EntityAIBase) new EntityAIMoveTowardsRestriction((EntityCreature) this, 0.6D));
        this.tasks.addTask(9, (EntityAIBase) new EntityAIWatchClosest2((EntityLiving) this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(9, (EntityAIBase) new EntityAIWatchClosest2((EntityLiving) this, EntityVillager.class, 5.0F, 0.02F));
        this.tasks.addTask(9, (EntityAIBase) new EntityAIWander((EntityCreature) this, 0.6D));
        this.tasks.addTask(10, (EntityAIBase) new EntityAIWatchClosest((EntityCreature) this, EntityLiving.class, 8.0F));
    }
    
    public boolean isAIEnabled()
    {
        return true;
    }
    
    public boolean interact(EntityPlayer entityPlayer)
    {
        if (entityPlayer.worldObj.isRemote)
        {
            entityPlayer.addChatMessage((IChatComponent) new ChatComponentText(StatCollector.translateToLocal("entity.Elf.interact")));
        }
        
        return true;
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    protected String getLivingSound()
    {
        return null;
    }
    
    protected String getHurtSound()
    {
        return null;
    }
    
    protected String getDeathSound() 
    {
        return null;
    }
    
    public EntityAgeable createChild(EntityAgeable entityAgeable)
    {
        return null;
    }
}