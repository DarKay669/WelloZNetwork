package fr.welloznetwork.wellozmod.tileentity;

import java.util.List;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.inventory.ContainerEmeraldChest;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityEmeraldChest extends TileEntity implements IInventory
{
    private ItemStack[] chestContents = new ItemStack[108];
    private boolean openning, closing = false;
    public float prevLidAngle, lidAngle;
    private int facing, numUsingPlayers, ticksSinceSync = -1;
    private String customName;
    
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        
        if (nbtTagCompound.hasKey("CustomName", 8))
        {
            this.customName = nbtTagCompound.getString("CustomName");
        }
    }
    
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        
        if (this.hasCustomInventoryName())
        {
            nbtTagCompound.setString("CustomName", this.customName);
        }
    }
    
    public void updateEntity()
    {
        super.updateEntity();
        
        if (this.worldObj != null && !this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
        {
            this.numUsingPlayers = 0;
            
            List<EntityPlayer> list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((this.xCoord - 5.0F), (this.yCoord - 5.0F), (this.zCoord - 5.0F), ((this.xCoord + 1) + 0.5F), ((this.yCoord + 1) + 5.0F), ((this.zCoord + 1) + 5.0F)));
            
            for (EntityPlayer entityPlayer : list)
            {
                if (entityPlayer.openContainer instanceof ContainerEmeraldChest)
                {
                    this.numUsingPlayers++;
                }
            }
        }
        
        if (this.worldObj != null && !this.worldObj.isRemote && this.ticksSinceSync < 0)
        {
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, (Block) RegistryBlocks.emerald_chest, 3, this.numUsingPlayers << 3 & 0xF8 | this.facing & 0x7);
        }
        
        if (!this.worldObj.isRemote && this.openning)
        {
            this.openning = false;
        }
        
        this.ticksSinceSync++;
        this.prevLidAngle = this.lidAngle;
        
        if (this.numUsingPlayers > 0 & this.lidAngle == 0.0F)
        {
            double d = this.xCoord + 0.5D, d1 = this.zCoord + 0.5D;
            this.worldObj.playSoundEffect(d, this.yCoord + 0.5D, d1, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
        
        if ((this.numUsingPlayers == 0 && this.lidAngle > 0.0F) || (this.numUsingPlayers > 0 && this.lidAngle < 1.0F))
        {
            float f = this.lidAngle;
            
            if (this.numUsingPlayers > 0)
            {
                this.lidAngle += 0.1F;
            }
            
            else
            {
                this.lidAngle -= 0.1F;
            }
            
            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }
            
            if (this.lidAngle < 0.5F && f >= 0.5F)
            {
                double d2 = this.xCoord + 0.5D, d3 = this.zCoord + 0.5D;
                this.worldObj.playSoundEffect(d2, this.yCoord + 0.5D, d3, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
            
            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }
    
    public boolean receiveClientEvent(int id, int value)
    {
        if (id == 1)
        {
            this.numUsingPlayers = value;
        }
        
        else if (id == 2)
        {
            this.facing = (byte) value;
        }
        
        else if (id == 3)
        {
            this.facing = (byte) (value & 0x7);
            this.numUsingPlayers = (value & 0xF8) >> 3;
        }
        
        return true;
    }
    
    public int getSizeInventory()
    {
        return 108;
    }
    
    public ItemStack getStackInSlot(int slot)
    {
        return this.chestContents[slot];
    }
    
    public ItemStack decrStackSize(int index, int slot)
    {
        if (this.chestContents[index] != null)
        {
            ItemStack itemStack;
            
            if (this.chestContents[index].stackSize <= slot)
            {
                itemStack = this.chestContents[index];
                this.chestContents[index] = null;
                this.markDirty();
                
                return itemStack;
            }
            
            else
            {
                itemStack = this.chestContents[index].splitStack(slot);
                
                if (this.chestContents[index].stackSize == 0)
                {
                    this.chestContents[index] =  null;
                }
                
                this.markDirty();
                
                return itemStack;
            }
        }
        
        else
        {
            return null;
        }
    }
    
    public ItemStack getStackInSlotOnClosing(int index)
    {
        if (this.chestContents[index] != null)
        {
            ItemStack itemStack = this.chestContents[index];
            
            this.chestContents[index] = null;
            
            return itemStack;
        }
        
        else
        {
            return null;
        }
    }
    
    public void setInventorySlotContents(int index, ItemStack itemStack)
    {
        this.chestContents[index] = itemStack;
        
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        
        this.markDirty();
    }
    
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : "TileEntity.EmeraldChest";
    }
    
    public boolean hasCustomInventoryName()
    {   
        return this.customName != null && this.customName.length() > 0;
    }
    
    public int getInventoryStackLimit()
    {
        return 64;
    }
    
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityPlayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64;
    }
    
    public void openInventory() 
    {
        if (this.numUsingPlayers < 0)
        {
            this.numUsingPlayers = 0;
        }
        
        this.openning = true;
        this.numUsingPlayers++;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, RegistryBlocks.emerald_chest, 1, this.numUsingPlayers);
    }
    
    public void closeInventory() 
    {
        this.closing = true;
        this.numUsingPlayers--;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, RegistryBlocks.emerald_chest, 1, this.numUsingPlayers);
    }
    
    public boolean isItemValidForSlot(int index, ItemStack itemStack)
    {
        return true;
    }
}