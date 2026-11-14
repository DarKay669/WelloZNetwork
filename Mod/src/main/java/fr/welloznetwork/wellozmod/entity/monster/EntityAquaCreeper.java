package fr.welloznetwork.wellozmod.entity.monster;

import java.util.Iterator;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import fr.welloznetwork.wellozmod.network.play.client.C01PacketParticle;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityAquaCreeper extends EntityWaterMob
{
    private ChunkCoordinates chunkCoordinates;
    public boolean move;
    
    public EntityAquaCreeper(World world)
    {
        super(world);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        
        this.dataWatcher.addObject(20, 0);
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.8D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    public boolean getCanSpawnHere()
    {
        return (this.isUnderWater() && this.posY < 60.0D);
    }
    
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }
    
    protected void func_145780_a(int x, int y, int z, Block block)
    {
        if (this.rand.nextInt(5) == 0)
        {
            this.playSound(RegistrySounds.SOUND_TYPE_AQUA_CREEPER_SWIM, 0.5F, 1.0F);
        }
    }
    
    protected String getHurtSound()
    {
        return RegistrySounds.SOUND_TYPE_AQUA_CREEPER_HURT;
    }
    
    protected void fall(float distance) {}
    
    protected void updateFallState(double y, boolean onGround) {}
    
    protected Item getDropItem()
    {
        return Items.gunpowder;
    }
    
    protected Entity findPlayerToAttack()
    {
        return (Entity) this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
    }
    
    public boolean attackEntityFrom(DamageSource damageSource, float damage)
    {
        if (damageSource.equals(DamageSource.inWall) || damageSource.equals(DamageSource.drown))
        {
            return false;
        }
        
        return super.attackEntityFrom(damageSource, damage);
    }
    
    public void onUpdate()
    {
        super.onUpdate();
        
        if (!this.worldObj.isRemote)
        {
            if (!this.isUnderWater())
            {
                this.motionY -= 0.10000000149011612D;
                this.setInflateSize(this.getInflateSize() + 2);
                
                if (this.onGround)
                {
                    this.motionX += (this.rand.nextFloat() - this.rand.nextFloat());
                    this.motionY += 0.800000011920929D;
                    this.motionZ += (this.rand.nextFloat() - this.rand.nextFloat());
                }
            }
            
            if (this.getInflateSize() <= 0)
            {
                this.setInflateSize(0);
                this.setMove(true);
            }
            
            if (this.getInflateSize() > 0)
            {
                this.setMove(false);
            }
            
            if (this.getInflateSize() >= 100)
            {
                this.waterExplosion();
            }
            
            if (this.getEntityToAttack() == null && this.isUnderWater())
            {
                this.setInflateSize(this.getInflateSize() - 2);
            }
            
            if (this.getEntityToAttack() != null && this.isUnderWater())
            {
                float distance = (float) this.getDistance((this.getEntityToAttack()).posX, (this.getEntityToAttack()).boundingBox.minY, (this.getEntityToAttack()).posZ);
                
                if (this.getInflateSize() < 100 && distance <= 4.0F)
                {
                    this.setInflateSize(this.getInflateSize() + 2);
                    this.setMove(false);
                }
                
                if (this.getInflateSize() < 100 && distance > 4.0F)
                {
                    this.setInflateSize(this.getInflateSize() - 2);
                }
                
                if (this.getInflateSize() == 2 && distance < 4.0F)
                {
                    this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, RegistrySounds.SOUND_TYPE_AQUA_CREEPER_FUSE, 0.5F, 1.0F);
                }
            }
            
            if (!this.move)
            {
                int y = MathHelper.floor_double(this.posY - this.getYOffset());
                
                this.posY += -this.motionY;
                
                if (this.worldObj.getBlock((int) this.posX, y, (int) this.posZ).getMaterial() == Material.water && this.motionY < 0.0D && this.boundingBox.minY > y)
                {
                    this.motionY -= 0.014D;
                    this.fallDistance = 0.0F;
                    this.onGround = true;
                }
            }
        }
        
        if (this.worldObj.isRemote && this.rand.nextInt(10) == 0)
        {
            this.trailParticles(this.worldObj, this.posX + -Math.sin(Math.toRadians(this.renderYawOffset)) * 1.0D, this.posY + 0.75D, this.posZ + Math.cos(Math.toRadians(this.renderYawOffset)) * 1.0D, this.rand);
        }
    }
    
    public void onLivingUpdate()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.getEntityToAttack() != null && this.move)
            {
                this.onGround = false;
                
                double targetX = (this.getEntityToAttack()).posX + 0.5D - this.posX;
                double targetY = (this.getEntityToAttack()).posY + 1.0D - this.posY;
                double targetZ = (this.getEntityToAttack()).posZ + 0.5D - this.posZ;
                
                this.motionX += (Math.signum(targetX) * 0.3D - this.motionX) * 0.10000000149011612D;
                
                if (this.isUnderWater())
                {
                    this.motionY += (Math.signum(targetZ) * 0.899999988079071D - this.motionY) * 0.10000000149011612D;
                }
                
                this.motionZ += (Math.signum(targetZ) * 0.3D - this.motionZ) * 0.10000000149011612D;
                
                this.moveForward = 0.5F;
                this.rotationYaw += MathHelper.wrapAngleTo180_float((float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F - this.rotationYaw);
            }
            
            if (this.getEntityToAttack() == null && this.isUnderWater())
            {
                this.swimAbout();
            }
        }
        
        super.onLivingUpdate();
    }
    
    private void swimAbout()
    {
        if (this.chunkCoordinates != null && (this.worldObj.getBlock(this.chunkCoordinates.posX, this.chunkCoordinates.posY, this.chunkCoordinates.posZ) != Blocks.water || this.chunkCoordinates.posY < 1))
        {
            this.chunkCoordinates = null;
        }
        
        if (this.chunkCoordinates == null || this.rand.nextInt(30) == 0 || this.chunkCoordinates.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 10.0F)
        {
            this.chunkCoordinates = new ChunkCoordinates((int) this.posX + this.rand.nextInt(10) - this.rand.nextInt(10), (int) this.posY - this.rand.nextInt(2) + 1, (int) this.posZ + this.rand.nextInt(10) - this.rand.nextInt(10));
        }
        
        double targetX = this.chunkCoordinates.posX + 0.5D - this.posX;
        double targetY = this.chunkCoordinates.posY + 0.5D - this.posY;
        double targetZ = this.chunkCoordinates.posZ + 0.5D - this.posZ;
        
        this.motionX += (Math.signum(targetX) * 0.3D - this.motionX) * 0.10000000149011612D;
        this.motionY += (Math.signum(targetY) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
        this.motionZ += (Math.signum(targetZ) * 0.3D - this.motionZ) * 0.10000000149011612D;
        
        this.moveForward = 0.5F;
        this.rotationYaw += MathHelper.wrapAngleTo180_float((float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F - this.rotationYaw);
    }
    
    private void waterExplosion()
    {
        this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, RegistrySounds.SOUND_TYPE_AQUA_CREEPER_EXPLOSION, 1.0F, 1.0F);
        
        Explosion explosion = new Explosion(this.worldObj, this, this.posX, this.posY, this.posZ, 3.0F);
        explosion.doExplosionA();
        
        this.waterExplosionParticles((float) this.posX + 0.5F, (float) this.posY + 0.5F, (float) this.posZ + 0.5F);
        this.setDead();;
    }
    
    private void waterExplosionParticles(float x, float y, float z)
    {
        Iterator<EntityPlayer> entityPlayers = this.worldObj.playerEntities.iterator();
        
        while (entityPlayers.hasNext())
        {
            EntityPlayer entityPlayer = entityPlayers.next();
            
            if (entityPlayer.getDistanceSqToEntity(this) < 256.0D)
            {
                WelloZMod.proxy.getNetworkManager().sendToAll(new C01PacketParticle(x, y, z));
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void trailParticles(World world, double x, double y, double z, Random random)
    {
        for (int count = 0; count < 5; count++)
        {
            double velocityX = 0.0D;
            double velocityY = 0.0D;
            double velocityZ = 0.0D;
            
            velocityX = (random.nextFloat() * 1.0F * random.nextInt(2) * 2);
            velocityY = (random.nextFloat() - 0.5D) * 0.125D;
            velocityZ = (random.nextFloat() * 1.0F * random.nextInt(2) * 2);
            
            C01PacketParticle.spawnParticles("bubbles", this.worldObj, x, y, z, velocityX, velocityY, velocityZ);
        }
    }
    
    private void setMove(boolean result)
    {
        this.move = result;
    }
    
    private boolean isUnderWater()
    {
        return this.worldObj.isAnyLiquid(this.boundingBox);
    }
    
    private void setInflateSize(int size)
    {
        this.dataWatcher.updateObject(20, Integer.valueOf(size));
    }
    
    public int getInflateSize()
    {
        return this.dataWatcher.getWatchableObjectInt(20);
    }
}