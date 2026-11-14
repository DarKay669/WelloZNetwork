package fr.welloznetwork.wellozmod.mixins.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@Mixin(ItemStack.class)
public abstract class MixinItemStack
{
    @Shadow
    public int stackSize;
    
    @Shadow
    public int itemDamage;
    
    @Shadow
    public NBTTagCompound stackTagCompound;
    
    @Shadow
    public abstract void func_150996_a(Item item);
    
    @Overwrite
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        byte b = nbtTagCompound.func_150299_b("id");
        
        if (b == 2 || b == 3 || b == 4 || b == 5 || b == 6)
        {
            this.func_150996_a(Item.getItemById(nbtTagCompound.getShort("id")));
        }
        
        else if (b == 8)
        {
            this.func_150996_a(GameData.getItemRegistry().getObject(nbtTagCompound.getString("id")));
        }
        
        else
        {
            this.func_150996_a(Items.stick);
        }
        
        this.stackSize = nbtTagCompound.getByte("Count");
        this.itemDamage = nbtTagCompound.getShort("Damage");
        
        if (this.itemDamage < 0)
        {
            this.itemDamage = 0;
        }
        
        if (nbtTagCompound.hasKey("tag", 10))
        {
            this.stackTagCompound = nbtTagCompound.getCompoundTag("tag");
        }
    }
}