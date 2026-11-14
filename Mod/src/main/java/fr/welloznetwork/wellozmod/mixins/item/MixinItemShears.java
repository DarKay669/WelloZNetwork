package fr.welloznetwork.wellozmod.mixins.item;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.IShearable;

@Mixin(ItemShears.class)
public class MixinItemShears extends Item
{
    @Overwrite(remap = false)
    public boolean onBlockStartBreak(ItemStack itemStack, int x, int y, int z, EntityPlayer entityPlayer)
    {
        if (entityPlayer.worldObj.isRemote || entityPlayer.capabilities.isCreativeMode)
        {
            return false;
        }
        
        Block block = entityPlayer.worldObj.getBlock(x, y, z);
        
        if (block instanceof IShearable)
        {
            IShearable iShearable = (IShearable) block;
            
            if (iShearable.isShearable(itemStack, entityPlayer.worldObj, x, y, z))
            {
                ArrayList<ItemStack> drops = iShearable.onSheared(itemStack, entityPlayer.worldObj, x, y, z, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemStack));
                
                for (ItemStack itemStack1 : drops)
                {
                    double x1 = (double) (this.itemRand.nextFloat() * 0.7F) + (double) (1.0F - 0.7F) * 0.5D;
                    double y1 = (double) (this.itemRand.nextFloat() * 0.7F) + (double) (1.0F - 0.7F) * 0.5D;
                    double z1 = (double) (this.itemRand.nextFloat() * 0.7F) + (double) (1.0F - 0.7F) * 0.5D;
                    
                    EntityItem entityItem = new EntityItem(entityPlayer.worldObj, (double) x + x1, (double) y + y1, (double) z + z1, itemStack1);
                    
                    entityItem.delayBeforeCanPickup = 10;
                    entityPlayer.worldObj.spawnEntityInWorld(entityPlayer);
                }
                
                entityPlayer.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(block)], 1);
            }
        }
        
        itemStack.damageItem(1, entityPlayer);
        
        return false;
    }
}