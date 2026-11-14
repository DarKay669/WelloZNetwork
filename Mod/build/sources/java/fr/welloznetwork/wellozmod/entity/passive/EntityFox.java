package fr.welloznetwork.wellozmod.entity.passive;

import fr.welloznetwork.wellozmod.entity.ai.EntityAIMeatTempt;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityFox extends EntityAnimal
{
    public EntityFox(World world)
    {
        super(world);
        this.setSize(0.51F, 0.68F);
        this.getNavigator().setAvoidsWater(true);
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(4, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMeatTempt(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityChicken.class, 250, true));
    }
    
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }
    
    public boolean isAIEnabled()
    {
        return true;
    }
    
    public void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
    }
    
    public boolean attackEntityFromPart(Entity entity)
    {
        return entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) this), 3.0F);
    }
    
    public boolean attackEntityFrom(DamageSource damageSource, float amount)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        
        return super.attackEntityFrom(damageSource, amount);
    }
    
    protected void func_145780_a(int x, int y, int z, Block block)
    {
        this.playSound("mob.wolf.step", 0.15F, 1.0F);
    }
    
    protected String getHurtSound()
    {
        return "mob.wolf.hurt";
    }
    
    protected String getDeathSound()
    {
        return "mob.wolf.death";
    }
    
    protected float getSoundVolume()
    {
        return 0.4F;
    }
    
    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }
    
    public boolean isBreedingItem(ItemStack itemStack)
    {
        return (itemStack != null && itemStack.getItem() instanceof ItemFood && ((ItemFood) itemStack.getItem()).isWolfsFavoriteMeat());
    }
    
    protected Item getDropItem()
    {
        return null;
    }
    
    protected void dropFewItems(boolean kill, int looting)
    {
        int i = this.rand.nextInt(2) + 1 + this.rand.nextInt(1 + looting);
        
        for (int j = 0; j < i; j++)
        {
            this.dropItem(null, 1);
        }
    }
    
    public EntityAgeable createChild(EntityAgeable entityAgeable)
    {
        return new EntityFox(this.worldObj);
    }
}