package fr.welloznetwork.wellozmod.entity.passive.snow;

import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityPenguin extends EntityAnimal
{    
    public float flapLength = 0.0F, flapIntensity = 0.0F, lastFlapIntensity, lastFlapLength;
    private float flapSpeed = 1.0F;
    
	public EntityPenguin(World world)
	{
		super(world);
		setSize(0.5F, 0.9F);
		this.tasks.addTask(0, (EntityAIBase) new EntityAISwimming((EntityLiving) this));
		this.tasks.addTask(1, (EntityAIBase) new EntityAIPanic((EntityCreature) this, 1.75D));
		this.tasks.addTask(2, (EntityAIBase) new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, (EntityAIBase) new EntityAITempt((EntityCreature) this, 0.75D, Items.fish, false));
		this.tasks.addTask(4, (EntityAIBase) new EntityAIFollowParent(this, 1.149999976158142D));
		this.tasks.addTask(5, (EntityAIBase) new EntityAIWander((EntityCreature) this, 1.0D));
		this.tasks.addTask(6, (EntityAIBase) new EntityAIWatchClosest((EntityLiving) this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, (EntityAIBase) new EntityAIWatchClosest2((EntityLiving) this, EntityPenguin.class, 5.0F, 0.02F));
		this.tasks.addTask(8, (EntityAIBase) new EntityAILookIdle((EntityLiving) this));
	}
	
	public boolean isAIEnabled()
	{
	    return true;
	}
	
	protected boolean canTriggerWalking()
	{
	    return false;
	}
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
    }
	
	public void onLivingUpdate()
	{
	    super.onLivingUpdate();
	    
	    this.lastFlapLength = this.flapLength;
	    this.lastFlapIntensity = this.flapIntensity;
	    this.flapIntensity = (float) (this.flapIntensity + (this.onGround ? -1 : 4) * 0.3D);
	    
	    if (this.flapIntensity < 0.0F)
	    {
	        this.flapIntensity = 0.0F;
	    }
	    
	    if (this.flapIntensity > 1.0F)
	    {
	        this.flapIntensity = 1.0F;
	    }
	    
	    if (!this.onGround && this.flapSpeed < 1.0F)
	    {
	        this.flapSpeed = 1.0F;
	    }
	    
	    this.flapSpeed = (float) (this.flapSpeed * 0.9D);
	    
	    if (!this.onGround && this.motionY < 0.0D)
	    {
	        this.motionY *= 0.6D;
	    }
	    
	    this.flapLength += this.flapSpeed * 2.0F;
	}
	
	protected void fall(float fallDistance) {}
	
	public boolean isBreedingItem(ItemStack itemStack)
	{
		return (itemStack != null && itemStack.getItem() == Items.fish || itemStack.getItem() == RegistryItems.cat_fish);
	}
	
	protected String getLivingSound()
	{
	    return RegistrySounds.SOUND_TYPE_PENGUIN_SAY;
	}
	
    protected String getHurtSound()
    {
        return RegistrySounds.SOUND_TYPE_PENGUIN_HURT;
    }
    
    protected String getDeathSound()
    {
        return RegistrySounds.SOUND_TYPE_PENGUIN_DEATH;
    }
    
	protected Item getDropItem()
    {
        return RegistryItems.raw_cod;
    }
	
	public EntityAnimal createChild(EntityAgeable entityAnimal)
    {
        return new EntityPenguin(this.worldObj);
    }
}