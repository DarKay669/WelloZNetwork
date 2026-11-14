package fr.welloznetwork.wellozmod.mixins.entity.passive;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;

@Mixin(EntitySheep.class)
public class MixinEntitySheep
{
    @Inject(method = "dropFewItems", at = @At("HEAD"))
    public void dropNewItems(boolean p_70628_1_, int p_70628_2_, CallbackInfo callbackInfo)
    {
        EntitySheep entitySheep = (EntitySheep) (Object) this;
        
        ItemStack itemStack;
        
        if (entitySheep.isChild())
        {
            return;
        }
        
        if (entitySheep.isBurning())
        {
            itemStack = new ItemStack(RegistryItems.cooked_mutton, 1 + entitySheep.worldObj.rand.nextInt(2 + p_70628_2_));
        }
        
        else
        {
            itemStack = new ItemStack(RegistryItems.mutton, 1 + entitySheep.worldObj.rand.nextInt(2 + p_70628_2_));
        }
        
        entitySheep.worldObj.spawnEntityInWorld(new EntityItem(entitySheep.worldObj, entitySheep.posX, entitySheep.posY, entitySheep.posZ, itemStack));
    }
}