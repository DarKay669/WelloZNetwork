package fr.welloznetwork.wellozmod.entity.projectile;

import java.awt.Color;
import java.io.IOException;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityArrowTipped extends EntityArrow implements IEntityAdditionalSpawnData
{
    private ItemStack arrow;
    private boolean inGround;
    private int ticksInGround;
    
    public EntityArrowTipped(World world)
    {
        super(world);
    }
    
    public EntityArrowTipped(World world, EntityLivingBase entityLivingBase, float charge)
    {
        super(world, entityLivingBase, charge);
    }
    
    public EntityArrowTipped(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }
    
    public EntityArrowTipped(World world, EntityLivingBase entityLivingBase, EntityLivingBase target, float f, float f1)
    {
        super(world, entityLivingBase, target, f, f1);
    }
    
    public void setArrow(ItemStack itemStack)
    {
        this.arrow = itemStack;
    }
    
    public ItemStack getArrow()
    {
        return this.arrow;
    }
    
    private boolean isEffectValid()
    {
        return this.arrow != null;
    }
    
    public void setThrowableHeading(double x, double y, double z, float yaw, float pitch)
    {
        super.setThrowableHeading(x, y, z, yaw, pitch);
        this.ticksInGround = 0;
    }
    
    @SideOnly(Side.CLIENT)
    public void setVelocity(double x, double y, double z)
    {
        super.setVelocity(x, y, z);
        
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            this.ticksInGround = 0;
        }
    }
    
    public void onUpdate()
    {
        super.onUpdate();
        
        if (this.worldObj.isRemote)
        {
            if (this.inGround)
            {
                if (this.ticksInGround % 5 == 0)
                {
                    this.spawnPotionParticles(1);
                }
            }
            
            else
            {
                this.spawnPotionParticles(2);
            }
        }
    }
    
    private void spawnPotionParticles(int particleCount)
    {
        if (this.isEffectValid() && particleCount > 0)
        {
            Color color = new Color(RegistryItems.tipped_arrow.getColorFromItemStack(this.arrow, 0));
            
            for (int j = 0; j < particleCount; j++)
            {
                this.worldObj.spawnParticle("mobSpell", this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height - (double) this.yOffset, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
            }
        }
    }
    
    public void setPotionDamage(int damage)
    {
        if (this.arrow == null)
        {
            this.arrow = new ItemStack(RegistryItems.tipped_arrow, 1, 0);
        }
        
        this.arrow.setItemDamage(damage);
    }
    
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        
        tagCompound.setShort("life", (short) this.ticksInGround);
        tagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        
        if (this.arrow != null)
        {
            tagCompound.setTag("Potion", this.arrow.writeToNBT(new NBTTagCompound()));
        }
    }
    
    public void readEntityFromNBT(NBTTagCompound tagCompound)
    {
        super.readEntityFromNBT(tagCompound);
        
        this.ticksInGround = tagCompound.getShort("life");
        this.inGround = tagCompound.getByte("inGround") == 1;
        
        if (tagCompound.hasKey("Potion", 10))
        {
            this.arrow = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("Potion"));
        }
        
        else
        {
            this.setPotionDamage(tagCompound.getInteger("potionValue"));
        }
        
        if (this.arrow == null)
        {
            this.setDead();
        }
    }
    
    public void onCollideWithPlayer(EntityPlayer entityPlayer)
    {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0 && this.isEffectValid())
        {
            boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && entityPlayer.capabilities.isCreativeMode;
            
            ItemStack itemStack = this.arrow.copy();
            itemStack.stackSize = 1;
            
            if (this.canBePickedUp == 1 && !entityPlayer.inventory.addItemStackToInventory(itemStack))
            {
                flag = false;
            }
            
            if (flag)
            {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                entityPlayer.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
    
    public void writeSpawnData(ByteBuf byteBuf)
    {
        byteBuf.writeFloat(this.rotationYaw);
        
        int id = this.shootingEntity == null ? this.getEntityId() : this.shootingEntity.getEntityId();
        
        byteBuf.writeInt(id);
        byteBuf.writeDouble(this.motionX);
        byteBuf.writeDouble(this.motionY);
        byteBuf.writeDouble(this.motionZ);
        
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        
        try
        {
            packetBuffer.writeItemStackToBuffer(this.arrow);
        }
        
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public void readSpawnData(ByteBuf additionalData)
    {
        this.rotationYaw = additionalData.readFloat();
        this.shootingEntity = this.worldObj.getEntityByID(additionalData.readInt());
        
        this.motionX = additionalData.readDouble();
        this.motionY = additionalData.readDouble();
        this.motionZ = additionalData.readDouble();
        
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        
        PacketBuffer packetBuffer = new PacketBuffer(additionalData);
        
        try
        {
            this.arrow = packetBuffer.readItemStackFromBuffer();
        }
        
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}