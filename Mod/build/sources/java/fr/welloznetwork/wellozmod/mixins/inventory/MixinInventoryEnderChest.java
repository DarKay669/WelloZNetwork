package fr.welloznetwork.wellozmod.mixins.inventory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

@Mixin(InventoryEnderChest.class)
public class MixinInventoryEnderChest extends InventoryBasic
{
    private boolean compiled;
    
    public MixinInventoryEnderChest(CallbackInfo callbackInfo)
    {
        super("container.enderchest", false, 9);
    }
    
    @Overwrite
    public void loadInventoryFromNBT(NBTTagList nbtTagList)
    {
        if (!this.compiled)
        {
            try
            {
                ReflectionHelper.setPrivateValue(InventoryBasic.class, this, Integer.valueOf(9), new String[] {"slotsCount", "field_70481_b"});
                ReflectionHelper.setPrivateValue(InventoryBasic.class, this, new ItemStack[9], new String[] {"inventoryContents", "field_70482_c"});
            }
            
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            this.compiled = true;
        }
        
        int i;
        
        for (i = 0; i < 9; i++)
        {
            setInventorySlotContents(i, null);
        }
        
        for (i = 0; i < nbtTagList.tagCount(); i++)
        {
            NBTTagCompound nbtTagCompound = nbtTagList.getCompoundTagAt(i);
            
            int j = nbtTagCompound.getByte("Slot") & 0xFF;
            
            if (j >= 0 && j < getSizeInventory())
            {
                setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbtTagCompound));
            }
        }
    }
    
    @Overwrite
    public NBTTagList saveInventoryToNBT()
    {
        if (!this.compiled)
        {
            try
            {
                ReflectionHelper.setPrivateValue(InventoryBasic.class, this, Integer.valueOf(9), new String[] {"slotsCount", "field_70481_b"});
                ReflectionHelper.setPrivateValue(InventoryBasic.class, this, new ItemStack[9], new String[] {"inventoryContents", "field_70482_c"});
            }
            
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            this.compiled = true;
        }
        
        NBTTagList nbtTagList = new NBTTagList();
        
        for (int i = 0; i < 9; i++)
        {
            ItemStack itemStack = getStackInSlot(i);
            
            if (itemStack != null)
            {
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                
                nbtTagCompound.setByte("Slot", (byte) i);
                itemStack.writeToNBT(nbtTagCompound);
                nbtTagList.appendTag(nbtTagCompound);
            }
        }
        
        return nbtTagList;
    }
}