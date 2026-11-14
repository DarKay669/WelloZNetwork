package fr.welloznetwork.wellozmod.entity.item;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.network.play.server.S01PacketBoatMoving;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Custom(EntityBoat.class)
public class CustomEntityBoat extends Entity
{
    private float momentum, outOfControlTicks, deltaRotation, boatGlide, boatSpeed = 1.0F;
    private int lerpSteps;
    private double boatPitch, lerpY, lerpZ, boatYaw, lerpXRot, waterLevel, lastYd;
    private boolean leftInputDown, rightInputDown, forwardInputDown, backInputDown;
    private Status status, previousStatus;
    private String entityName;
    
    public CustomEntityBoat(World world)
    {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(1.375F, 0.5625F);
    }
    
    public CustomEntityBoat(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }
    
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    protected void entityInit()
    {
        this.dataWatcher.addObject(17, 0);
        this.dataWatcher.addObject(18, 1);
        this.dataWatcher.addObject(19, 0.0F);
        this.dataWatcher.addObject(20, 0);
    }
    
    public AxisAlignedBB getCollisionBox(Entity entity)
    {
        return entity.boundingBox;
    }
    
    public AxisAlignedBB getBoundingBox()
    {
        return this.boundingBox;
    }
    
    public boolean canBePushed()
    {
        return true;
    }
    
    public double getMountedYOffset()
    {
        return 0.0D;
    }
    
    public double getYOffset()
    {
        return 0.0D;
    }
    
    public boolean isPassenger(Entity entity)
    {
        return this.getPassengers().contains(entity);
    }
    
    public void removePassengers()
    {
        if (this.getPassengers().isEmpty())
        {
            return;
        }
        
        for (EntityLivingBase entityLivingBase : this.getPassengers())
        {
            entityLivingBase.mountEntity(null);
        }
    }
    
    public List<EntityLivingBase> getPassengers()
    {
        List<EntityLivingBase> list = new ArrayList<EntityLivingBase>();
        
        if (this.riddenByEntity instanceof EntityLivingBase) 
        {
            list.add((EntityLivingBase) this.riddenByEntity);
        }
        
        return list;
    }
    
    public void sitEntity(Entity entity)
    {
        entity.mountEntity(this);
    }
    
    public void addToBoat(Entity entity)
    {
        if (!(entity instanceof EntityLivingBase))
        {
            return;
        }
       
        EntityLivingBase entityLivingBase = (EntityLivingBase) getControllingPassenger();
        
        if (this.getPassengers().isEmpty())
        {
            this.sitEntity(entity);
        }
        
        else if (this.getPassengers().size() == 1)
        {
            if (entity instanceof EntityPlayer && !(entityLivingBase instanceof EntityPlayer))
            {
                entity.mountEntity(this);
            }
        }
        
        entity.prevRotationYaw = this.rotationYaw;
        entity.rotationYaw = this.rotationYaw;
    }
    
    public boolean interactFirst(EntityPlayer entityPlayer)
    {
        if (!entityPlayer.isSneaking() && this.outOfControlTicks < 60.0F)
        {
            this.addToBoat(entityPlayer);
        }
        
        return true;
    }
    
    public boolean isBeingRidden()
    {
        return !this.getPassengers().isEmpty();
    }
    
    public boolean canPassengerSteer()
    {
        return !this.worldObj.isRemote || this.getControllingPassenger() instanceof EntityPlayer && this.riddenByEntity == this.getControllingPassenger();
    }
    
    public boolean canFitPassenger(Entity entity)
    {
        return this.getPassengers().size() < 2;
    }
    
    public Entity getControllingPassenger()
    {
        List<EntityLivingBase> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }
    
    public boolean attackEntityFrom(DamageSource damageSource, float amount)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        
        else if (!this.worldObj.isRemote && !this.isDead)
        {
            if (damageSource instanceof EntityDamageSourceIndirect && damageSource.getEntity() != null && this.isPassenger(damageSource.getEntity()))
            {
                return false;
            }
            
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + amount - 10.0F);
            this.setBeenAttacked();
            boolean flag = damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer) damageSource.getEntity()).capabilities.isCreativeMode;
            
            if (flag || this.getDamageTaken() > 40.0F)
            {
                if (!flag && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
                {
                    ItemStack itemStack = new ItemStack(this.getItemBoat());
                    
                    if (this.entityName != null)
                    {
                        itemStack.setStackDisplayName(this.entityName);
                    }
                    
                    this.entityDropItem(itemStack, 0);
                }
                
                this.setDead();
            }
            
            return true;
        }
        
        else
        {
            return true;
        }
    }
    
    public void setDead()
    {
        if (this.riddenByEntity instanceof EntityLivingBase)
        {
            ((EntityLivingBase) riddenByEntity).dismountEntity(this);
            this.riddenByEntity.ridingEntity = null;
        }
        
        super.setDead();
    }
    
    public void applyEntityCollision(Entity entity)
    {
        if (entity instanceof CustomEntityBoat)
        {
            if (entity.boundingBox.minY < this.boundingBox.maxY)
            {
                super.applyEntityCollision(entity);
            }
        }
        
        if (this.getPassengers().contains(entity) || entity.ridingEntity instanceof CustomEntityBoat) {}
        
        else if (entity.boundingBox.minY <= this.boundingBox.minY)
        {
            super.applyEntityCollision(entity);
        }
    }
    
    public Item getItemBoat()
    {
        if (this.getBoatType() == BoatType.OAK)
        {
            return RegistryItems.oak_boat;
        }
        
        return RegistryItems.CUSTOM_BOAT[this.getBoatType().ordinal()];
    }
    
    public void performHurtAnimation()
    {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }
    
    public boolean canBeCollidedWith()
    {
        return !isDead;
    }
    
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements)
    {
        this.boatPitch = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpXRot = pitch;
        this.lerpSteps = 5;
        this.boatYaw = yaw;
    }
    
    public EnumFacing getAdjustedHorizontalFacing()
    {
        return this.getAdjustedHorizontalFacing();
    }
    
    private void collideWithSurfaceBlocks()
    {
        AxisAlignedBB axisAlignedBB = this.boundingBox;
        int minX = MathHelper.floor_double(axisAlignedBB.minX - 0.2D);
        int minY = MathHelper.floor_double(axisAlignedBB.minY - 0.2D);
        int minZ = MathHelper.floor_double(axisAlignedBB.minZ - 0.2D);
        int maxX = MathHelper.floor_double(axisAlignedBB.maxX + 0.2D);
        int maxY = MathHelper.floor_double(axisAlignedBB.maxY + 0.2D);
        int maxZ = MathHelper.floor_double(axisAlignedBB.maxZ + 0.2D);
        
        for (int x = minX; x <= maxX; x++)
        {
            for (int y = minY; y <= maxY; y++)
            {
                for (int z = minZ; z <= maxZ; z++)
                {
                    Block block = this.worldObj.getBlock(x, y, z);
                    
                    if (block == Blocks.snow_layer)
                    {
                        this.worldObj.setBlockToAir(x, y, z);
                        this.isCollidedHorizontally = false;
                    }
                    
                    else if (block == Blocks.waterlily)
                    {
                        this.worldObj.func_147480_a(x, y, z, true);
                        this.isCollidedHorizontally = false;
                    }
                }
            }
        }
    }
    
    public void onUpdate()
    {
        this.previousStatus = this.status;
        this.status = this.getBoatStatus();
        
        if (this.status != Status.UNDER_WATER && this.status != Status.UNDER_FLOWING_WATER)
        {
            this.outOfControlTicks = 0.0F;
        }
        
        else
        {
            this.outOfControlTicks++;
        }
        
        if (!this.worldObj.isRemote && this.outOfControlTicks >= 60.0F)
        {
            this.removePassengers();
        }
        
        if (this.getTimeSinceHit() > 0)
        {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        
        if (this.getDamageTaken() > 0.0F)
        {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        super.onUpdate();
        this.tickLerp();
        
        if (this.canPassengerSteer())
        {
            this.collideWithSurfaceBlocks();
            this.updateMotion();
            
            if (this.getPassengers().size() > 0 && this.getControllingPassenger() instanceof EntityPlayer)
            {
                EntityPlayer entityPlayer = (EntityPlayer) this.getControllingPassenger();
                boolean left = entityPlayer.moveStrafing > 0;
                boolean right = entityPlayer.moveStrafing < 0;
                boolean forward = entityPlayer.moveForward > 0;
                boolean back = entityPlayer.moveForward < 0;
                this.updateInputs(left, right, forward, back);
            }
            
            else
            {
                this.updateInputs(false, false, false, false);
            }
            
            this.controlBoat();
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
        }
        
        else
        {
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
            this.deltaRotation = 0.0F;
        }
        
        this.func_145775_I();
        
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D));
        
        if (!list.isEmpty()) 
        {
            boolean flag = !this.worldObj.isRemote && !(this.getControllingPassenger() instanceof EntityPlayer);
            
            for (int j = 0; j < list.size(); j++)
            {
                Entity entity = list.get(j);
                
                if (entity.ridingEntity != this)
                {
                    if (flag && this.getPassengers().size() < 2 && !entity.isRiding() && entity.width < this.width && entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer))
                    {
                        this.addToBoat(entity);
                    }
                    
                    else
                    {
                        this.applyEntityCollision(entity);
                    }
                }
            }
        }
        
        if (this.worldObj.isRemote && this.canPassengerSteer() && this.getControllingPassenger() instanceof EntityClientPlayerMP)
        {
            WelloZMod.proxy.getNetworkManager().sendToServer(new S01PacketBoatMoving(this));
        }
    }
    
    private void tickLerp()
    {
        if (this.worldObj.isRemote && this.lerpSteps > 0)
        {
            if (!(this.getControllingPassenger() instanceof EntityClientPlayerMP))
            {
                double d0 = this.posX + (this.boatPitch - this.posX) / (double) this.lerpSteps;
                double d1 = this.posY + (this.lerpY - this.posY) / (double) this.lerpSteps;
                double d2 = this.posZ + (this.lerpZ - this.posZ) / (double) this.lerpSteps;
                double d3 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double) this.rotationYaw);
                this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
                this.rotationPitch = (float) ((double) this.rotationPitch + (this.lerpXRot - (double) this.rotationPitch) / (double) this.lerpSteps);
                this.lerpSteps--;
                this.setPosition(d0, d1, d2);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
        }
    }
    
    private Status getBoatStatus()
    {
        Status status = this.getUnderwaterStatus();
        
        if (status != null)
        { 
            this.waterLevel = this.boundingBox.maxY;
            return status;
        }
        
        else if (this.checkInWater())
        {
            return Status.IN_WATER;
        }
        
        else
        {
            float f = Math.min(this.getBoatGlide(), 0.986F);
            
            if (f > 0.0F) 
            {
                return Status.ON_LAND;
            }
            
            return Status.IN_AIR;
        }
    }
    
    public float getWaterLevelAbove()
    {
        AxisAlignedBB axisAlignedBB = this.boundingBox;
        int i = MathHelper.floor_double(axisAlignedBB.minX);
        int j = MathHelper.ceiling_double_int(axisAlignedBB.maxX);
        int k = MathHelper.floor_double(axisAlignedBB.maxY);
        int l = MathHelper.ceiling_double_int(axisAlignedBB.maxY - this.lastYd);
        int i1 = MathHelper.floor_double(axisAlignedBB.minZ);
        int j1 = MathHelper.ceiling_double_int(axisAlignedBB.maxZ);
        
        label78:
            
            for (int k1 = k; k1 < l; k1++)
            {
                 float f = 0.0F;
                 int l1 = i;
                 
                 while (true)
                 {
                     if (l1 >= j)
                     {
                         if (f < 1.0F)
                         {
                             float f2 = (float) k1 + f;
                             return f2;
                         }
                         
                         break;
                     }
                     
                     for (int i2 = i1; i2 < j1; i2++) 
                     {
                         Block block = this.worldObj.getBlock(l1, k1, i2);
                         
                         if (block.getMaterial() == Material.water)
                         {
                             f = Math.max(f, getBlockLiquidHeight(this.worldObj, l1, k1, i2));
                         }
                         
                         if (f >= 1.0F)
                         {
                             continue label78;
                         }
                     }
                     
                     l1++;
                 }
            }
        
        float f1 = (float) (l + 1);
        return f1;
    }
    
    public float getBoatGlide()
    {
        AxisAlignedBB axisAlignedBB = this.boundingBox;
        AxisAlignedBB axisAlignedBB1 = AxisAlignedBB.getBoundingBox(axisAlignedBB.minX, axisAlignedBB.minY - 0.001D, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        int i = MathHelper.floor_double(axisAlignedBB1.minX) - 1;
        int j = MathHelper.ceiling_double_int(axisAlignedBB1.maxX) + 1;
        int k = MathHelper.floor_double(axisAlignedBB1.minY) - 1;
        int l = MathHelper.ceiling_double_int(axisAlignedBB1.maxY) + 1;
        int i1 = MathHelper.floor_double(axisAlignedBB1.minZ) - 1;
        int j1 = MathHelper.ceiling_double_int(axisAlignedBB1.maxZ) + 1;
        List<AxisAlignedBB> list = Lists.newArrayList();
        float f = 0.0F;
        int k1 = 0;
        
        for (int l1 = i; l1 < j; l1++)
        {
            for (int i2 = i1; i2 < j1; i2++)
            {
                int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
                
                if (j2 != 2)
                {
                    for (int k2 = k; k2 < l; k2++)
                    {
                        if (j2 <= 0 || k2 != k && k2 != l - 1)
                        {
                            Block block = this.worldObj.getBlock(l1, k2, i2);
                            
                            block.addCollisionBoxesToList(this.worldObj, l1, k2, i2, axisAlignedBB1, list, this);
                            
                            if (!list.isEmpty())
                            {
                                f += block.slipperiness;
                                k1++;
                            }
                            
                            list.clear();
                        }
                    }
                }
            }
        }
        
        return f == 0 || k1 == 0 ? 0 : f / (float) k1;
    }
    
    private boolean checkInWater()
    {
        AxisAlignedBB axisAlignedBB = this.boundingBox;
        int i = MathHelper.floor_double(axisAlignedBB.minX);
        int j = MathHelper.ceiling_double_int(axisAlignedBB.maxX);
        int k = MathHelper.floor_double(axisAlignedBB.minY);
        int l = MathHelper.ceiling_double_int(axisAlignedBB.minY + 0.001D);
        int i1 = MathHelper.floor_double(axisAlignedBB.minZ);
        int j1 = MathHelper.ceiling_double_int(axisAlignedBB.maxZ);
        boolean flag = false;
        this.waterLevel = Double.MIN_VALUE;
        
        for (int k1 = i; k1 < k; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    Block block = this.worldObj.getBlock(k1, l1, i2);
                    
                    if (block.getMaterial() == Material.water)
                    {
                        float f = getLiquidHeight(this.worldObj, k1, l1, i2);
                        this.waterLevel = Math.max(f, this.waterLevel);
                        flag |= axisAlignedBB.minY < (double) f;
                        
                        if (flag)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        
        return flag;
    }
    
    private Status getUnderwaterStatus()
    {
        AxisAlignedBB axisAlignedBB = this.boundingBox;
        double d0 = axisAlignedBB.maxY + 0.001D;
        int i = MathHelper.floor_double(axisAlignedBB.minX);
        int j = MathHelper.ceiling_double_int(axisAlignedBB.maxX);
        int k = MathHelper.floor_double(axisAlignedBB.maxY);
        int l = MathHelper.ceiling_double_int(d0);
        int i1 = MathHelper.floor_double(axisAlignedBB.minZ);
        int j1 = MathHelper.ceiling_double_int(axisAlignedBB.maxZ);
        boolean flag = false;
        
        for (int k1 = i; k1 < j; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    Block block = this.worldObj.getBlock(k1, l1, i2);
                    
                    if (block.getMaterial() == Material.water && d0 < (double) getLiquidHeight(this.worldObj, k1, l1, i2))
                    {
                        if (this.worldObj.getBlockMetadata(k1, l1, i2) != 0)
                        {
                            Status status = Status.UNDER_FLOWING_WATER;
                            return status;
                        }
                        
                        flag = true;
                    }
                }
            }
        }
        
        return flag ? Status.UNDER_WATER : null;
    }
    
    public static float getBlockLiquidHeight(World world, int x, int y, int z)
    {
        int i = world.getBlockMetadata(x, y, z);
        return (i % 8) == 0 && world.getBlock(x, y + 1, i).getMaterial() == Material.water ? 1.0F : 1.0F - BlockLiquid.getLiquidHeightPercent(i);
    }
    
    public static float getLiquidHeight(World world, int x, int y, int z)
    {
        return (float) y + getBlockLiquidHeight(world, x, y, z);
    }
    
    private void updateMotion()
    {
        double d0 = -0.03999999910593033D;
        double d1 = d0;
        double d2 = 0.0D;
        this.momentum = 0.05F;
        
        if (this.previousStatus == Status.IN_AIR && this.status != Status.IN_AIR && this.status != Status.ON_LAND)
        {
            this.waterLevel = this.getBoundingBox().minY + (double) this.height;
            this.setPosition(this.posX, (double) (this.getWaterLevelAbove() - this.height) + 0.101D, this.posZ);
            this.motionY = 0.0D;
            this.lastYd = 0.0D;
            this.status = Status.IN_WATER;
        }
        
        else
        {
            if (this.status == Status.IN_WATER)
            {
                d2 = (this.waterLevel - this.getBoundingBox().minY) / (double) this.height;
                this.momentum = 0.9F;
            }
            
            else if (this.status == Status.UNDER_FLOWING_WATER)
            {
                d1 = -7.0E-4D;
                this.momentum = 0.9F;
            }
            
            else if (this.status == Status.UNDER_WATER)
            {
                d2 = 0.009999999776482582D;
                this.momentum = 0.45F;
            }
            
            else if (this.status == Status.IN_AIR)
            {
                this.momentum = 0.9F;
            }
            
            else if (this.status == Status.ON_LAND)
            {
                this.momentum = this.boatGlide;
                
                if (this.getControllingPassenger() instanceof EntityPlayer)
                {
                    this.boatGlide /= 2.0F;
                }
            }
            
            this.motionX *= this.momentum;
            this.motionZ *= this.momentum;
            this.deltaRotation *= this.momentum;
            this.motionY += d1;
            
            if (d2 > 0.0D)
            {
                double d3 = 0.65D;
                this.motionY += d2 * (-d0 / 0.65D);
                double d4 = 0.75D;
                this.motionY *= 0.75D;
            }
        }
    }
    
    private void controlBoat()
    {
        if (this.isBeingRidden())
        {
            float f = 0.0F;
            
            if (this.leftInputDown)
            {
                this.deltaRotation += -1.0F;
            }
            
            if (this.rightInputDown)
            {
                this.deltaRotation++;
            }
            
            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown)
            {
                f += 0.005F;
            }
            
            this.rotationYaw += this.deltaRotation;
            
            if (this.forwardInputDown)
            {
                f += 0.04F;
            }
            
            if (this.backInputDown)
            {
                f -= 0.005F;
            }
            
            f *= this.boatSpeed;
            
            this.motionX += MathHelper.sin(-this.rotationYaw * 0.017453292F) * f;
            this.motionZ += MathHelper.cos(this.rotationYaw * 0.017453292F) * f;
        }
    }
    
    public void updateRiderPosition()
    {
        if (this.riddenByEntity != null)
        {
            this.updatePassenger(this.riddenByEntity);
        }
    }
    
    protected float getDefaultRiderOffset()
    {
        return 0.0F;
    }
    
    public void updatePassenger(Entity entity)
    {
        float f = this.getDefaultRiderOffset();
        float f1 = (float) ((isDead ? 0.009999999776482582D : this.getMountedYOffset()) + entity.getYOffset());
        
        if (this.getPassengers().size() > 1)
        {
            int i = this.getPassengers().indexOf(entity);
            
            if (i == 0)
            {
                f = 0.2F;
            }
            
            else
            {
                f = -0.6F;
            }
            
            if (entity instanceof EntityAnimal)
            {
                f = (float) ((double) f + 0.2D);
            }
        }
        
        Vec3 vec3 = (Vec3.createVectorHelper(f, 0.0D, 0.0D));
        vec3.rotateAroundY(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2.0F));
        entity.setPosition(this.posX + vec3.xCoord, this.posY + (double) f1, this.posZ + vec3.zCoord);
        entity.rotationYaw += this.deltaRotation;
        this.applyYawToEntity(entity);
    }
    
    protected void applyYawToEntity(Entity entity)
    {
        int j = 0;
        
        if (entity instanceof EntityAnimal && this.getPassengers().size() > 1)
        {
            j = entity.getEntityId() % 2 == 0 ? 90 : 270;
        }
        
        float f = MathHelper.wrapAngleTo180_float(((EntityLivingBase) entity).rotationYawHead - this.rotationYaw);
        float f1 = MathHelper.clamp_float(f, -105.0F + j, 105.0F + j % 360);
        entity.prevRotationYaw += f1 - f;
        entity.rotationYaw += f1 - f;
        
        if (entity instanceof EntityLivingBase)
        {
            ((EntityLivingBase) entity).rotationYawHead += f1 - f;
            ((EntityLivingBase) entity).prevRotationYawHead += f1 - f;
            ((EntityLivingBase) entity).renderYawOffset = this.rotationYaw + j;
            ((EntityLivingBase) entity).prevRenderYawOffset = this.rotationYaw + j;
        }
    }
    
    protected void readEntityFromNBT(NBTTagCompound tagCompound)
    {
        if (tagCompound.hasKey("Type", 8))
        {
            this.setBoatType(BoatType.getTypeFromString(tagCompound.getString("Type")));
        }
        
        if (tagCompound.hasKey("CustomName", 9) && tagCompound.getString("CustomName").length() > 0)
        {
            this.entityName = tagCompound.getString("CustomName");
        }
    }
    
    protected void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setString("Type", this.getBoatType().getName());
        
        if (this.entityName != null && this.entityName.length() > 0)
        {
            tagCompound.setString("CustomName", this.entityName);
        }
    }
    
    protected void updateFallState(double y, boolean onGround)
    {
        this.lastYd = this.motionY;
        
        if (!this.isRiding())
        {
            if (onGround)
            {
                if (this.fallDistance > 3.0F)
                {
                    if (this.status != Status.ON_LAND && this.getControllingPassenger() instanceof EntityPlayer)
                    {
                        this.fallDistance = 0.0F;
                        return;
                    }
                    
                    this.fall(this.fallDistance);
                    
                    if (!this.worldObj.isRemote && !this.isDead)
                    {
                        this.setDead();
                        
                        if (this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
                        {
                            for (int i = 0; i < 3; i++)
                            {
                                this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.planks), 1, this.getBoatType().getMetadata()), 0.0F);
                            }
                            
                            for (int j = 0; j < 2; j++)
                            {
                                this.entityDropItem(new ItemStack(Items.stick), 0.0F);
                            }
                        }
                    }
                }
                
                this.fallDistance = 0.0F;
            }
            
            else if (this.worldObj.getBlock((int) this.posX, (int) (this.posY - 1.0D), (int) this.posZ).getMaterial() != Material.water && y < 0.0D)
            {
                this.fallDistance = (float) ((double) this.fallDistance - y);
            }
        }
    }
    
    public void setDamageTaken(float damage)
    {
        this.dataWatcher.updateObject(19, Float.valueOf(damage));
    }
    
    public float getDamageTaken()
    {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }
    
    public void setTimeSinceHit(int time)
    {
        this.dataWatcher.updateObject(17, Integer.valueOf(time));
    }
    
    public int getTimeSinceHit()
    {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setForwardDirection(int direction)
    {
        this.dataWatcher.updateObject(18, Integer.valueOf(direction));
    }
    
    public int getForwardDirection()
    {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public void setBoatType(BoatType boatType)
    {
        getDataWatcher().updateObject(20, Integer.valueOf(boatType.ordinal()));
    }
    
    public BoatType getBoatType()
    {
        return BoatType.byId(getDataWatcher().getWatchableObjectInt(20));
    }
    
    public void updateInputs(boolean left, boolean right, boolean forward, boolean back)
    {
        this.leftInputDown = left;
        this.rightInputDown = right;
        this.forwardInputDown = forward;
        this.backInputDown = back;
    }
    
    public String getBoatName()
    {
        return this.entityName;
    }
    
    public void setBoatName(String name)
    {
        this.entityName = name;
    }
    
    public enum Status
    {
        IN_WATER, UNDER_WATER, UNDER_FLOWING_WATER, ON_LAND, IN_AIR;
    }
    
    public enum BoatType
    {
        OAK(0, "oak"),
        SPRUCE(1, "spruce"),
        BIRCH(2, "birch"),
        JUNGLE(3, "jungle"),
        ACACIA(4, "acacia"),
        DARK_OAK(5, "dark_oak"),
        CHERRY(6, "cherry"),
        MANGROVE(7, "mangrove");
        
        private final String name;
        private final int metadata;
        
        BoatType(int metadata, String name)
        {
            this.name = name;
            this.metadata = metadata;
        }
        
        public String getName()
        {
            return this.name;
        }
        
        public int getMetadata()
        {
            return this.metadata;
        }
        
        public String toString()
        {
            return this.name;
        }
        
        public static BoatType byId(int id)
        {
            return values()[MathHelper.clamp_int(id, 0, values().length - 1)];
        }
        
        public static BoatType getTypeFromString(String name)
        {
            for (int i = 0; i < values().length; i++)
            {
                if (values()[i].getName().equals(name))
                {
                    return values()[i];
                }
            }
            
            return values()[0];
        }
    }
}