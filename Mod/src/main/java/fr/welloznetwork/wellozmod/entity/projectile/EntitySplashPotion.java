package fr.welloznetwork.wellozmod.entity.projectile;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntitySplashPotion extends EntityThrowable
{
    private int damageValue;
    private static final int NAUSEA = 0, WEB = 1, EXPLOSIVE = 2, DARKNESS = 3;
  
    public EntitySplashPotion(World world)
    {
        super(world);
    }
    
    public EntitySplashPotion(World world, EntityPlayer entityPlayer, int damageValue)
    {
        super(world, (EntityLivingBase) entityPlayer);
        setThrowableHeading(this.motionX, this.motionY, this.motionZ, 0.4F, 0.3F);
        this.setDamageValue(damageValue);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(2, Integer.valueOf(0));
    }
    
    public void setDamageValue(int damageValue)
    {
        this.damageValue = damageValue;
        
        if (!this.worldObj.isRemote)
        {
            this.dataWatcher.updateObject(2, Integer.valueOf(damageValue));
        }
    }
    
    public int getDamageValue()
    {
        return this.dataWatcher.getWatchableObjectInt(2);
    }
    
    protected void onImpact(MovingObjectPosition movingObjectPosition)
    {
        if (!this.worldObj.isRemote)
        {
            switch (this.damageValue)
            {
                case 0:
                    this.effect(movingObjectPosition);
                    break;
                    
                case 1:
                    this.effect(movingObjectPosition);
                    break;
                    
                case 2:
                    this.effect(movingObjectPosition);
                    break;
                    
                case 3:
                    this.effect(movingObjectPosition);
                    break;
            }
        }
        
        this.setDead();
    }
    
    private void effect(MovingObjectPosition movingObjectPosition)
    {
        switch (this.damageValue)
        {
            case 1:
                switch (movingObjectPosition.typeOfHit)
                {
                    case ENTITY:
                        
                        this.worldObj.setBlock((int) movingObjectPosition.entityHit.posX, (int) movingObjectPosition.entityHit.posY, (int) movingObjectPosition.entityHit.posZ, Blocks.web);
                        break;
                        
                    case BLOCK:
                        
                        if (this.worldObj.getBlock(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ) == Blocks.snow)
                        {
                            movingObjectPosition.blockY--;
                            movingObjectPosition.sideHit = 1;
                        }
                        
                        switch (movingObjectPosition.sideHit)
                        {
                            case 0:
                                this.setBlockIfNotSolid(this.worldObj, movingObjectPosition.blockX, movingObjectPosition.blockY - 1, movingObjectPosition.blockZ, (Block) Blocks.web);
                                break;
                                
                            case 1:
                                this.setBlockIfNotSolid(this.worldObj, movingObjectPosition.blockX, movingObjectPosition.blockY + 1, movingObjectPosition.blockZ, (Block) Blocks.web);
                                break;
                                
                            case 2:
                                this.setBlockIfNotSolid(this.worldObj, movingObjectPosition.blockX - 1, movingObjectPosition.blockY, movingObjectPosition.blockZ, (Block) Blocks.web);
                                break;
                                
                            case 3:
                                this.setBlockIfNotSolid(this.worldObj, movingObjectPosition.blockX + 1, movingObjectPosition.blockY - 1, movingObjectPosition.blockZ, (Block) Blocks.web);
                                break;
                                
                            case 4:
                                this.setBlockIfNotSolid(this.worldObj, movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ - 1, (Block) Blocks.web);
                                break;
                                
                            case 5:
                                this.setBlockIfNotSolid(this.worldObj, movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ + 1, (Block) Blocks.web);
                                break;
                        }
                        
                        break;
                        
                    default:
                        break;
                }
                break;
                
            case 2:
                if (!this.worldObj.isRemote)
                {
                    this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.0F, true);
                    this.worldObj.spawnParticle("smoke", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
                }
                break;
        }
        
        AxisAlignedBB axisAlignedBB = this.boundingBox.expand(4.0D, 2.0D, 4.0D);
        List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, axisAlignedBB);
        
        if (list != null && !list.isEmpty())
        {
            Iterator<EntityPlayer> iterator = list.iterator();
            
            while (iterator.hasNext())
            {
                EntityPlayer entityPlayer = iterator.next();
                double d0 = getDistanceSqToEntity((Entity) entityPlayer);
                
                if (d0 < 16.0D)
                {
                    double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
                    
                    if (entityPlayer == movingObjectPosition.entityHit)
                    {
                        d1 = 1.0D;
                    }
                    
                    switch (this.damageValue)
                    {
                        case 0:
                            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 400, 100));
                            break;
                            
                        case 3:
                            entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.id, 400, 100));
                            break;
                    }
                }
            }
        }
        
        this.worldObj.playAuxSFX(2002, (int) Math.round(this.posX), (int) Math.round(this.posY), (int) Math.round(this.posZ), 4);
    }
    
    private boolean setBlockIfNotSolid(World world, int x, int y, int z, Block block)
    {
        return setBlockIfNotSolid(world, x, y, z, block, 0);
    }
    
    private boolean setBlockIfNotSolid(World world, int x, int y, int z, Block block, int metadata)
    {
        if (world.getBlock(x, y, z).isAir((IBlockAccess) world, x, y, z) || world .getBlock(x, y, z) == Blocks.web)
        {
            world.setBlock(x, y, z, block, metadata, 3);
            return true;
        }
        
        return false;
    }
}