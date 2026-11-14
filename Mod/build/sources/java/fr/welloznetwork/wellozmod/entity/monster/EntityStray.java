package fr.welloznetwork.wellozmod.entity.monster;

import java.util.Calendar;

import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityStray extends EntitySkeleton
{
    private final EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
    private final EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
    
    public EntityStray(final World world)
    {
        super(world);
        
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        
        if ((world != null) && (!world.isRemote))
        {
            this.setCombatTask();
        }
    }
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData iEntityLivingData)
    {
        iEntityLivingData = super.onSpawnWithEgg(iEntityLivingData);
        this.tasks.addTask(4, this.aiArrowAttack);
        this.addRandomArmor();
        this.enchantEquipment();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ));
        
        if (this.getEquipmentInSlot(4) == null)
        {
            Calendar calendar = this.worldObj.getCurrentDate();
            
            if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0F;
            }
        }
        
        return iEntityLivingData;
    }
    
    protected void addRandomArmor()
    {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
    }
    
    public boolean attackEntityAsMob(final Entity entity)
    {
        final boolean flag = super.attackEntityAsMob(entity);
        
        if (flag)
        {
            final int i = this.worldObj.difficultySetting.getDifficultyId();
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 140 * i, 0));
        }
        
        return flag;
    }
    
    public void attackEntityWithRangedAttack(EntityLivingBase entityLivingBase, float range)
    {
        EntityArrow entityArrow = new EntityArrow(this.worldObj, this, entityLivingBase, 1.6F, 14.0F - this.worldObj.difficultySetting.getDifficultyId() * 4);
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        entityArrow.setDamage(range * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting.getDifficultyId() * 0.11F);
        
        if (i > 0)
        {
            entityArrow.setDamage(entityArrow.getDamage() + i * 0.5D + 0.5D);
        }
        
        if (j > 0)
        {
            entityArrow.setKnockbackStrength(j);
        }
        
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1)
        {
            entityArrow.setFire(100);
        }
        
        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(entityArrow);
    }
    
    protected void dropFewItems(boolean chance, int fortune)
    {
        int j, k;
        
        j = this.rand.nextInt(3 + fortune);
        
        for (k = 0; k < j; k++)
        {
            this.dropItem(Items.arrow, 1);
        }
        
        for (k = 0; k < j; k++)
        {
            this.dropItem(Items.bone, 1);
        }
    }
    
    protected String getLivingSound()
    {
        return RegistrySounds.SOUND_TYPE_STRAY_IDLE[this.rand.nextInt(RegistrySounds.SOUND_TYPE_STRAY_IDLE.length)];
    }
    
    protected String getHurtSound()
    {
        return RegistrySounds.SOUND_TYPE_STRAY_HURT[this.rand.nextInt(RegistrySounds.SOUND_TYPE_STRAY_HURT.length)];
    }
    
    protected String getDeathSound()
    {
        return RegistrySounds.SOUND_TYPE_STRAY_DEATH[this.rand.nextInt(RegistrySounds.SOUND_TYPE_STRAY_DEATH.length)];
    }
    
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound(RegistrySounds.SOUND_TYPE_STRAY_STEP[this.rand.nextInt(RegistrySounds.SOUND_TYPE_STRAY_STEP.length)], 0.15F, 1.0F);
    }
}