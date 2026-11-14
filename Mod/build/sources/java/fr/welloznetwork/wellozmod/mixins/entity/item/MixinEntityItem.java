package fr.welloznetwork.wellozmod.mixins.entity.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.item.CustomItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

@Mixin(EntityItem.class)
public abstract class MixinEntityItem extends Entity
{
    private CustomItem[] blacklistItems;
    private int[] blacklistRegistryItems;
    private String[] blacklistOreDictionary;
    
    @Shadow
    public abstract ItemStack getEntityItem();
    
    public MixinEntityItem(World world)
    {
        super(world);
    }
    
    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo callbackInfo)
    {
        boolean floating = false;
        
        if (!this.getEntityItem().getItem().onEntityItemUpdate(((EntityItem) (Object) this)) && this.getEntityItem() != null)
        {
            if (this.blacklistItems != null && this.blacklistItems.length >= 1)
            {
                for (CustomItem blacklistItems : this.blacklistItems)
                {
                    if (blacklistItems.getItem().equals(this.getEntityItem().getItem()))
                    {
                        if (blacklistItems.getMetadata() == this.getEntityItem().getItemDamage())
                        {
                            return;
                        }
                        
                        else
                        {
                            floating = true;
                        }
                    }
                }
            }
            
            if (!floating && this.blacklistRegistryItems != null && this.blacklistRegistryItems.length >= 1)
            {
                for (int blacklistRegistryItems : this.blacklistRegistryItems)
                {
                    if (blacklistRegistryItems == Item.getIdFromItem(this.getEntityItem().getItem()))
                    {
                        return;
                    }
                }
            }
            
            if (!floating && this.blacklistOreDictionary != null && this.blacklistOreDictionary.length >= 1)
            {
                for (String blacklistOreDictionary : this.blacklistOreDictionary)
                {
                    String blacklistOreDictionaryEntry = blacklistOreDictionary.replaceAll("[^A-Za-z0-9 ]", "");
                    
                    if (OreDictionary.getOres(blacklistOreDictionaryEntry).size() > 0)
                    {
                        for (ItemStack itemStack : OreDictionary.getOres(blacklistOreDictionaryEntry))
                        {
                            if (itemStack.getItem().equals(this.getEntityItem().getItem()))
                            {
                                if (itemStack.getItemDamage() == this.getEntityItem().getItemDamage())
                                {
                                    return;
                                }
                                
                                else
                                {
                                    floating = true;
                                }
                            }
                            
                            else
                            {
                                floating = true;
                            }
                        }
                    }
                    
                    else
                    {
                        floating = true;
                    }
                }
            }
            
            else
            {
                floating = true;
            }
            
            if (floating)
            {
                Block block = this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ);
                
                if ((block.getMaterial().isLiquid() && block.getMaterial() != Material.lava) && BlockLiquid.getLiquidHeightPercent(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) > this.getEyeHeight() - 0.11111111F)
                {
                    setUnderWaterMovement(this);
                    this.motionY += 0.03999999910593033D;
                }
            }
        }
    }
    
    @Unique
    private static void setUnderWaterMovement(Entity entity)
    {
        entity.motionX *= 0.95D;
        entity.motionY += entity.motionY < 0.06D ? 5.0E-4D : 0.0D;
        entity.motionZ *= 0.95D;
    }
    
    @Inject(method = "searchForOtherItemsNearby", at = @At("HEAD"), cancellable = true)
    private void searchForOtherItemsNearbyHead(CallbackInfo callbackInfo)
    {
        final ItemStack itemStack = this.getDataWatcher().getWatchableObjectItemStack(10);
        
        if (itemStack != null && itemStack.stackSize >= itemStack.getMaxStackSize())
        {
            callbackInfo.cancel();
        }
    }
}