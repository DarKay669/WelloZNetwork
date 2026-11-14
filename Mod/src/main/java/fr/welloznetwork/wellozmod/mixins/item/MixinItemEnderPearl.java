package fr.welloznetwork.wellozmod.mixins.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ItemEnderPearl.class)
public class MixinItemEnderPearl extends Item
{
    @Overwrite
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (!entityPlayer.capabilities.isCreativeMode)
        {
            itemStack.stackSize--;
        }
        
        world.playSoundAtEntity(entityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        
        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new EntityEnderPearl(world, entityPlayer));
        }
        
        return itemStack;
    }
}