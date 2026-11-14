package fr.welloznetwork.wellozmod.tileentity;

import java.util.Iterator;
import java.util.List;

import fr.welloznetwork.wellozmod.block.BlockBarrel;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityBarrel extends TileEntity implements IInventory
{
    private ItemStack[] chestContents = new ItemStack[36];
    private String customName;
    private int numUsingPlayers, ticksSinceSync;
    private float soundTimer;
    
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
        
        this.ticksSinceSync++;
        
        if (!this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
        {
            this.numUsingPlayers = 0;
            
            List<EntityPlayer> list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord - 5.0F, this.yCoord - 5.0F, this.zCoord - 5.0F, this.xCoord + 1 + 5.0F, this.yCoord + 1 + 5.0F, this.zCoord + 1 + 5.0F));
            Iterator<EntityPlayer> iterator = list.iterator();
            
            while (iterator.hasNext())
            {
                EntityPlayer entityPlayer = iterator.next();
                
                if (entityPlayer.openContainer instanceof ContainerChest)
                {
                    this.numUsingPlayers++;
                }
            }
        }
        
        double d2;
        
        if (this.numUsingPlayers > 0 && this.soundTimer <= 0.0F)
        {
            double d1 = this.xCoord + 0.5D;
            d2 = this.zCoord + 0.5D;
            
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) % 8 + 8, 2);
            this.worldObj.playSoundEffect(d1, this.yCoord + 0.5D, d2, RegistrySounds.SOUND_TYPE_BARREL_OPEN[this.worldObj.rand.nextInt(RegistrySounds.SOUND_TYPE_BARREL_OPEN.length)], 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
        
        if (this.numUsingPlayers == 0 && this.soundTimer > 0.0F || this.numUsingPlayers > 0 && this.soundTimer < 1.0F)
        {
            if (this.numUsingPlayers > 0)
            {
                this.soundTimer += 0.5F;
            }
            
            else
            {
                this.soundTimer -= 0.5F;
            }
            
            if (this.soundTimer > 10.0F)
            {
                this.soundTimer = 10.0F;
            }
            
            if (this.soundTimer < 0.5F && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) > 7)
            {
                this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) % 8, 2);
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, RegistrySounds.SOUND_TYPE_BARREL_CLOSE, 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
            
            if (this.soundTimer < 0.0F)
            {
                this.soundTimer = 0.0F;
            }
        }
    }
    
    public void invalidate()
    {
        super.invalidate();
        this.updateContainingBlockInfo();
    }
    
    public boolean receiveClientEvent(int id, int value)
    {
        if (id == 1)
        {
            this.numUsingPlayers = value;
            return true;
        }
        
        return super.receiveClientEvent(id, value);
    }
    
    public int getSizeInventory()
    {
        return 27;
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
                    this.chestContents[index] = null;
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
        return this.hasCustomInventoryName() ? this.customName : "tile.barrel.name";
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
        
        this.numUsingPlayers++;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numUsingPlayers);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
    }
    
    public void closeInventory() 
    {
        if (this.getBlockType() instanceof BlockBarrel)
        {
            this.numUsingPlayers--;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numUsingPlayers);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
        }
    }
    
    public boolean isItemValidForSlot(int index, ItemStack itemStack)
    {
        return true;
    }
}