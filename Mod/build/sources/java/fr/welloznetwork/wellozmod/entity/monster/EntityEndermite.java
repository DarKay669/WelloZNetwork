package fr.welloznetwork.wellozmod.entity.monster;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityEndermite extends EntityMob
{
    private final Sorter sorter;
    private int lifeTime = 0;
    private boolean playerSpawned = false;
    
    public EntityEndermite(World world)
    {
        super(world);
        
        this.sorter = new Sorter(this);
        this.experienceValue = 3;
        this.setSize(0.4F, 0.3F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 0.8F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true));
    }
    
    public float getEyeHeight()
    {
        return 0.1F;
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
    }
    
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    protected String getLivingSound()
    {
        return "mob.silverfish.say";
    }
    
    protected String getHurtSound()
    {
        return "mob.silverfish.hit";
    }
    
    protected String getDeathSound()
    {
        return "mob.silverfish.kill";
    }
    
    protected void func_145780_a(int x, int y, int z, Block block)
    {
        this.playSound("mob.silverfish.step", 0.15F, 1.0F);
    }
    
    protected Item getDropItem()
    {
        return null;
    }
    
    public void readEntityFromNBT(NBTTagCompound tagCompound)
    {
        super.readEntityFromNBT(tagCompound);
        this.lifeTime = tagCompound.getInteger("Lifetime");
        this.playerSpawned = tagCompound.getBoolean("PlayerSpawned");
    }
    
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Lifetime", this.lifeTime);
        tagCompound.setBoolean("PlayerSpawned", this.playerSpawned);
    }
    
    public void onUpdate()
    {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.worldObj.isRemote)
        {
            for (int i = 0; i < 2; i++)
            {
                this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
        
        else
        {
            if (!this.isNoDespawnRequired() && !this.hasCustomNameTag())
            {
                this.lifeTime++;
            }
            
            if (this.lifeTime >= 2400)
            {
                this.setDead();
            }
        }
        
        if (!this.isDead && this.isSpawnedByPlayer() && this.ticksExisted % 20 == 0)
        {
            this.aggroEndermen(64);
        }
    }
    
    protected boolean isValidLightLevel()
    {
        return true;
    }
    
    public boolean getCanSpawnHere()
    {
        if (super.getCanSpawnHere())
        {
            EntityPlayer entityPlayer = this.worldObj.getClosestPlayerToEntity(this, 0.5D);
            return entityPlayer == null;
        }
        
        return false;
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    private boolean isSpawnedByPlayer()
    {
        return this.playerSpawned;
    }
    
    public void setSpawnedByPlayer(boolean spawnedByPlayer)
    {
        this.playerSpawned = spawnedByPlayer;
    }
    
    public void aggroEndermen(int range)
    {
        double radius = range / 2.0D;
        int targetChance = 10;
        
        if (this.rand.nextInt(targetChance) != 0)
        {
            List<EntityEnderman> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX - radius, this.posY - 4.0D, this.posZ - radius, this.posX + radius, this.posY + 4.0D, this.posZ + radius), new IEntitySelector()
            {
                public boolean isEntityApplicable(Entity entity)
                {
                    return entity instanceof EntityEnderman && ((EntityEnderman) entity).getEntityToAttack() == null;
                }
            });
            
            list.sort(this.sorter);
            
            if (!list.isEmpty())
            {
                for (EntityEnderman enderman : list)
                {
                    enderman.setTarget(this);
                    enderman.setScreaming(true);
                }
            }
        }
    }
}