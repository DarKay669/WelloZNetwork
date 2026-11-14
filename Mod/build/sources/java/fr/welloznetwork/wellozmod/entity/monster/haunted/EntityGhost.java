package fr.welloznetwork.wellozmod.entity.monster.haunted;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGhost extends EntityMob
{    
    public EntityGhost(World world)
    {
        super(world);
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.isImmuneToFire = true;
        this.experienceValue = 10;
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
    }
    
    protected boolean isAIEnabled()
    {
        return true;
    }
    
    protected String getLivingSound()
    {
        return "mob.blaze.breathe";
    }
    
    protected String getHurtSound()
    {
        return "mob.blaze.hit";
    }
    
    protected String getDeathSound()
    {
        return "mob.blaze.death";
    }
    
    protected float getSoundVolume()
    {
        return 0.4F;
    }
    
    public boolean attackEntityAsMob(Entity entity)
    {
        boolean flag = super.attackEntityAsMob(entity);
        
        if (flag)
        {
            int i = this.worldObj.difficultySetting.getDifficultyId();
            
            if (this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < (float) i * 0.3F)
            {
                entity.setFire(2 * i);
            }
        }
        
        return flag;
    }
    
    public void onLivingUpdate()
    {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote)
        {
            float brightness = this.getBrightness(1.0F);
            
            if (brightness > 0.5F && this.rand.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
            {
                boolean flag = true;
                
                if (flag)
                {
                    this.kill();
                }
            }
        }
        
        super.onLivingUpdate();
    }
    
    public boolean attackEntityFrom(DamageSource damageSource, float damage)
    {
        if (!super.attackEntityFrom(damageSource, damage))
        {
            return false;
        }
        
        else
        {
            EntityLivingBase entityLivingBase = this.getAttackTarget();
            
            if (entityLivingBase == null && this.getEntityToAttack() instanceof EntityLivingBase)
            {
                entityLivingBase = (EntityLivingBase) this.getEntityToAttack();
            }
            
            if (entityLivingBase == null && damageSource.getEntity() instanceof EntityLivingBase)
            {
                entityLivingBase = (EntityLivingBase) damageSource.getEntity();
            }            
        }
        
        return true;
    }
}