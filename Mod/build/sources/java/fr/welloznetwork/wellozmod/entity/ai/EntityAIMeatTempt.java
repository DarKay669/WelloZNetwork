package fr.welloznetwork.wellozmod.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class EntityAIMeatTempt extends EntityAIBase
{
    private EntityCreature entityCreature;
    private EntityPlayer entityPlayer;
    private double speed, targetX, targetY, targetZ, rotationPitch, rotationYaw;
    private int delay;
    private boolean isRunning, scaredByPlayerMovement, avoidsWater;
    
    public EntityAIMeatTempt(EntityCreature entityCreature, double speed, boolean scaredByPlayerMovement)
    {
        this.entityCreature = entityCreature;
        this.speed = speed;
        this.scaredByPlayerMovement = scaredByPlayerMovement;
    }
    
    public boolean shouldExecute()
    {
        if (this.delay > 0)
        {
            this.delay--;
            return false;
        }
        
        this.entityPlayer = this.entityCreature.worldObj.getClosestPlayerToEntity(this.entityCreature, 10.0D);
        
        if (this.entityPlayer == null)
        {
            return false;
        }
        
        ItemStack itemStack = this.entityPlayer.getCurrentEquippedItem();
        
        return (itemStack == null) ? false : (!(itemStack.getItem() instanceof ItemFood) ? false : ((ItemFood) itemStack.getItem()).isWolfsFavoriteMeat());
    }
    
    public boolean continueExecuting()
    {
        if (this.scaredByPlayerMovement)
        {
            if (this.entityCreature.getDistanceSqToEntity(this.entityPlayer) < 36.0D)
            {
                if (this.entityPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D)
                {
                    return false;
                }
                
                if (Math.abs(this.entityPlayer.rotationPitch - this.rotationPitch) > 5.0D || Math.abs(this.entityPlayer.rotationYaw - this.rotationYaw) > 5.0D)
                {
                    return false;
                }
            }
            
            else
            {
                this.targetX = this.entityPlayer.posX;
                this.targetY = this.entityPlayer.posY;
                this.targetZ = this.entityPlayer.posZ;
            }
            
            this.rotationPitch = this.entityPlayer.rotationPitch;
            this.rotationYaw = this.entityPlayer.rotationYaw;
        }
        
        return this.shouldExecute();
    }
    
    public void startExecuting()
    {
        this.targetX = this.entityPlayer.posX;
        this.targetY = this.entityPlayer.posY;
        this.targetZ = this.entityPlayer.posZ;
        this.isRunning = true;
        this.avoidsWater = this.entityCreature.getNavigator().getAvoidsWater();
        this.entityCreature.getNavigator().setAvoidsWater(false);
    }
    
    public void resetTask()
    {
        this.entityPlayer = null;
        this.entityCreature.getNavigator().clearPathEntity();
        this.delay = 100;
        this.isRunning = false;
        this.entityCreature.getNavigator().setAvoidsWater(this.avoidsWater);
    }
    
    public void updateTask()
    {
        this.entityCreature.getLookHelper().setLookPositionWithEntity(this.entityPlayer, 30.0F, this.entityCreature.getVerticalFaceSpeed());
        
        if (this.entityCreature.getDistanceSqToEntity(this.entityPlayer) < 6.25D)
        {
            this.entityCreature.getNavigator().clearPathEntity();
        }
        
        else
        {
            this.entityCreature.getNavigator().tryMoveToEntityLiving(this.entityPlayer, this.speed);
        }
    }
    
    public boolean isRunning()
    {
        return this.isRunning;
    }
}