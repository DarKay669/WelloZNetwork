package fr.welloznetwork.wellozmod.entity.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTNTPresent extends Entity
{
    public int fuse;
    
    public EntityTNTPresent(World world)
    {
        super(world);
        this.fuse = 10;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
    }
    
    public EntityTNTPresent(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x + 0.5D, y, z + 0.5D);
        this.motionY = 0.20000000298023224D;
        this.fuse = 10;
    }
    
    protected void entityInit() {}
    
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionY *= 0.9800000190734863D;
        
        if (this.onGround)
        {
            this.motionY *= - 0.5D;
        }
        
        if (this.fuse-- <= 0)
        {
            if (!this.worldObj.isRemote)
            {
                this.setDead();
                
                this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0.4F, true);
            }
        }
        
        else
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }
    
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }
    
    protected void readEntityFromNBT(NBTTagCompound nbtTagCompound)
    {
        this.fuse = nbtTagCompound.getInteger("fuse");
    }
    
    protected void writeEntityToNBT(NBTTagCompound nbtTagCompound)
    {
        nbtTagCompound.setInteger("fuse", this.fuse);
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }
}