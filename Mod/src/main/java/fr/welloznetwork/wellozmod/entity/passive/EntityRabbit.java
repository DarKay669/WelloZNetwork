package fr.welloznetwork.wellozmod.entity.passive;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRabbit extends EntityAnimal
{
    private int field_175540_bm = 0, field_175535_bn = 0, currentMoveTypeDuration = 0, carrotTicks;
    private boolean field_175536_bo = false, field_175537_bp = false;
    private EnumMoveType moveType;

    public EntityRabbit(World world)
    {
        super(world);
        this.moveType = EnumMoveType.HOP;
        this.carrotTicks = 0;
        this.setSize(0.4F, 0.5F);
        ReflectionHelper.setPrivateValue(EntityLiving.class, this, new RabbitJumpHelper(this), "jumpHelper", "field_70767_i");
        ReflectionHelper.setPrivateValue(EntityLiving.class, this, new RabbitMoveHelper(), "moveHelper", "field_70765_h");
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(1, new AIPanic(1.33D));
        this.tasks.addTask(2, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.carrot, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityWolf.class, 16.0F, 1.33D, 1.33D));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.8F, 1.33D));
        this.setMovementSpeed(0.0D);
    }
    
    public RabbitMoveHelper getMoveHelper()
    {
        return (RabbitMoveHelper) super.getMoveHelper();
    }
    
    public RabbitJumpHelper getJumpHelper()
    {
        return (RabbitJumpHelper) super.getJumpHelper();
    }
    
    public void setMoveType(EnumMoveType moveType)
    {
        this.moveType = moveType;
    }
    
    @SideOnly(Side.CLIENT)
    public float func_175521_o(float p_175521_1_)
    {
        return this.field_175535_bn == 0 ? 0.0F : (this.field_175540_bm + p_175521_1_) / this.field_175535_bn;
    }
    
    public void setMovementSpeed(double speed)
    {
        this.getNavigator().setSpeed(speed);
        this.getMoveHelper().setMoveTo(this.getMoveHelper().getX(), this.getMoveHelper().getY(), this.getMoveHelper().getZ(), speed);
    }
    
    public void setJumping(boolean jumping, EnumMoveType moveType)
    {
        super.setJumping(jumping);
        
        if (!jumping)
        {
            if (moveType == EnumMoveType.ATTACK)
            {
                moveType = EnumMoveType.HOP;
            }
            
            else
            {
                this.setMovementSpeed(1.5D * moveType.getSpeed());
                this.playSound(this.getJumpingSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            }
        }
        
        this.field_175536_bo = jumping;
    }
    
    public void doMovementAction(EnumMoveType moveType)
    {
        this.setJumping(true, moveType);
        this.field_175535_bn = moveType.func_180073_d();
        this.field_175540_bm = 0;
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(18, (byte) 0);
    }
    
    protected boolean isAIEnabled()
    {
        return true;
    }
    
    public void updateAITasks()
    {
        super.updateAITasks();
        
        if (this.getMoveHelper().getSpeed() > 0.8D)
        {
            this.setMoveType(EnumMoveType.SPRINT);
        }
        
        else if (this.moveType != EnumMoveType.ATTACK)
        {
            this.setMoveType(EnumMoveType.HOP);
        }
        
        if (this.currentMoveTypeDuration > 0)
        {
            this.currentMoveTypeDuration--;
        }
        
        if (this.carrotTicks > 0)
        {
            this.carrotTicks -= this.rand.nextInt(3);
            
            if (this.carrotTicks < 0)
            {
                this.carrotTicks = 0;
            }
        }
        
        if (this.onGround)
        {
            if (!this.field_175537_bp)
            {
                this.setJumping(false, EnumMoveType.NONE);
                this.func_175517_cu();
            }
            
            RabbitJumpHelper rabbitJumpHelper = this.getJumpHelper();
            
            if (!rabbitJumpHelper.getIsJumping())
            {
                if (!this.getNavigator().noPath() && this.currentMoveTypeDuration == 0)
                {
                    PathEntity pathEntity = this.getNavigator().getPath();
                    Vec3 vec3 = Vec3.createVectorHelper(this.getMoveHelper().getX(), this.getMoveHelper().getY(), this.getMoveHelper().getZ());
                    
                    if (pathEntity != null && pathEntity.getCurrentPathIndex() < pathEntity.getCurrentPathIndex())
                    {
                        vec3 = pathEntity.getPosition(this);
                    }
                    
                    this.calculateRotationYaw(vec3.xCoord, vec3.zCoord);
                    this.doMovementAction(moveType);
                }
            }
            
            else if (!rabbitJumpHelper.func_180065_d())
            {
                this.func_175518_cr();
            }
        }
        
        this.field_175537_bp = this.onGround;
    }
    
    private void calculateRotationYaw(double x, double z)
    {
        this.rotationYaw = (float) (Math.atan2(z - this.posZ, x - this.lastTickPosX) * 180.0D / Math.PI) - 90.0F;
    }
    
    private void func_175518_cr()
    {
        this.getJumpHelper().func_180066_a(true);
    }
    
    private void func_175520_cs()
    {
        this.getJumpHelper().func_180066_a(false);
    }
    
    private void updateMoveTypeDuration()
    {
        this.currentMoveTypeDuration = this.getMoveTypeDuration();
    }
    
    private void func_175517_cu()
    {
        this.updateMoveTypeDuration();
        this.func_175520_cs();
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.field_175540_bm != this.field_175535_bn)
        {
            if (this.field_175540_bm == 0 && !this.worldObj.isRemote)
            {
                this.worldObj.setEntityState(this, (byte) 1);
            }
            
            this.field_175540_bm++;
        }
        
        else if (this.field_175535_bn != 0)
        {
            this.field_175540_bm = 0;
            this.field_175535_bn = 0;
        }
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }
    
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("RabbitType", this.getRabbitType());
        tagCompound.setInteger("MoreCarrotTicks", this.carrotTicks);
    }
    
    public void readEntityFromNBT(NBTTagCompound tagCompound)
    {
        super.readEntityFromNBT(tagCompound);
        this.setRabbitType(tagCompound.getInteger("RabbitType"));
        this.carrotTicks = tagCompound.getInteger("MoreCarrotTicks");
    }
    
    protected String getJumpingSound()
    {
        return RegistrySounds.SOUND_TYPE_RABBIT_HOP[this.rand.nextInt(RegistrySounds.SOUND_TYPE_RABBIT_HOP.length)];
    }
    
    protected String getLivingSound()
    {
        return RegistrySounds.SOUND_TYPE_RABBIT_IDLE[this.rand.nextInt(RegistrySounds.SOUND_TYPE_RABBIT_IDLE.length)];
    }
    
    protected String getHurtSound()
    {
        return RegistrySounds.SOUND_TYPE_RABBIT_HURT[this.rand.nextInt(RegistrySounds.SOUND_TYPE_RABBIT_HURT.length)];
    }
    
    protected String getDeathSound()
    {
        return RegistrySounds.SOUND_TYPE_RABBIT_BUNNYMURDER;
    }
    
    protected void dropFewItems(boolean hitRecently, int fortune)
    {
        int j = this.rand.nextInt(2) + this.rand.nextInt(1 + fortune);
        
        for (int i = 0; i < j; i++)
        {
            this.dropItem(RegistryItems.rabbit_hide, 1);
        }
        
        j = this.rand.nextInt(2);
        
        for (int i = 0; i < j; i++)
        { 
            if (this.isBurning())
            {
                this.dropItem(RegistryItems.cooked_rabbit, 1);
            }
            
            else
            {
                this.dropItem(RegistryItems.rabbit, 1);
            }
        }
        
        if (this.rand.nextInt(100) <= 10 + fortune)
        {
            this.entityDropItem(new ItemStack(RegistryItems.rabbit_foot), 0.0F);
        }
    }
    
    private boolean isRabbitBreedingItem(Item item)
    {
        return item == Items.carrot || item == Items.golden_carrot || item == Item.getItemFromBlock(Blocks.yellow_flower);
    }
    
    public boolean isBreedingItem(ItemStack itemStack)
    {
        return itemStack != null && this.isRabbitBreedingItem(itemStack.getItem());
    }
    
    public byte getRabbitType()
    {
        return this.dataWatcher.getWatchableObjectByte(18);
    }
    
    public void setRabbitType(int type)
    {
        this.dataWatcher.updateObject(18, (byte) type);
    }
    
    public EntityAgeable createChild(EntityAgeable entityAgeable)
    {
        EntityRabbit entityRabbit = new EntityRabbit(this.worldObj);
        
        if (entityAgeable instanceof EntityRabbit)
        {
            entityRabbit.setRabbitType(this.rand.nextBoolean() ? this.getRabbitType() : ((EntityRabbit) entityAgeable).getRabbitType());
        }
        
        return entityRabbit;
    }
    
    private boolean isCarrotEaten()
    {
        return this.carrotTicks == 0;
    }
    
    protected int getMoveTypeDuration()
    {
        return this.moveType.getDuration();
    }
    
    protected void createRunningParticles() {}
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte id)
    {
        if (id == 1)
        {
            this.createRunningParticles();
            this.field_175535_bn = 10;
            this.field_175540_bm = 0;
        }
        
        else
        {
            super.handleHealthUpdate(id);
        }
    }
    
    class AIEvilAttack extends EntityAIAttackOnCollide
    {
        public AIEvilAttack()
        {
            super(EntityRabbit.this, EntityLivingBase.class, 1.4D, true);
        }
        
        protected double func_179512_a(EntityLivingBase entityLivingBase)
        {
            return 4.0F + entityLivingBase.width;
        }
    }
    
    class AIPanic extends EntityAIPanic
    {
        private double speed;
        private EntityRabbit entityRabbit = EntityRabbit.this;
        
        public AIPanic(double speed)
        {
            super(EntityRabbit.this, speed);
            this.speed = speed;
        }
        
        public void updateTask()
        {
            super.updateTask();
            this.entityRabbit.setMovementSpeed(speed);
        }
    }
    
    static enum EnumMoveType
    {
        NONE(0.0F, 0.0F, 30, 1),
        HOP(8.0F, 0.2F, 20, 10),
        STEP(1.0F, 0.45F, 14, 14),
        SPRINT(1.75F, 0.4F, 1, 8),
        ATTACK(2.0F, 0.7F, 7, 8);
        
        private final float speed, field_180077_g;
        private final int duration, field_180085_i;
        
        private EnumMoveType(float speed, float p_i45866_4_, int duration, int p_i45866_6_)
        {
            this.speed = speed;
            this.field_180077_g = p_i45866_4_;
            this.duration = duration;
            this.field_180085_i = p_i45866_6_;
        }
        
        public float getSpeed()
        {
            return this.speed;
        }
        
        public float func_180074_b()
        {
            return this.field_180077_g;
        }
        
        public int getDuration()
        {
            return this.duration;
        }
        
        public int func_180073_d()
        {
            return this.field_180085_i;
        }
    }
    
    public class RabbitJumpHelper extends EntityJumpHelper
    {
        private EntityRabbit entityRabbit;
        private boolean field_180068_d = false;
        
        public RabbitJumpHelper(EntityRabbit entityRabbit)
        {
            super(entityRabbit);
            this.entityRabbit = entityRabbit;
        }
        
        public boolean getIsJumping()
        {
            return isJumping;
        }
        
        public boolean func_180065_d()
        {
            return this.field_180068_d;
        }
        
        public void func_180066_a(boolean p_180066_1_)
        {
            this.field_180068_d = p_180066_1_;
        }
        
        public void doJump()
        {
            if (isJumping)
            {
                entityRabbit.doMovementAction(EnumMoveType.STEP);
                isJumping = false;
            }
        }
    }
    
    class RabbitMoveHelper extends EntityMoveHelper
    {
        private EntityRabbit entityRabbit = EntityRabbit.this;
        private double posX, posY, posZ;
        
        public RabbitMoveHelper()
        {
            super(EntityRabbit.this);
        }
        
        public void setMoveTo(double posX, double posY, double posZ, double d)
        {
            super.setMoveTo(posX, posY, posZ, d);
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
        }
        
        public double getX()
        {
            return this.posX;
        }
        
        public double getY()
        {
            return this.posY;
        }
        
        public double getZ()
        {
            return this.posZ;
        }
        
        public void onUpdateMoveHelper()
        {
            if (this.entityRabbit.onGround && !field_175536_bo)
            {
                this.entityRabbit.setMovementSpeed(0.0D);
            }
            
            super.onUpdateMoveHelper();
        }
    }
}