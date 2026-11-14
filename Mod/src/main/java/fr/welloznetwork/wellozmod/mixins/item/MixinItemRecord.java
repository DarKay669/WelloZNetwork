package fr.welloznetwork.wellozmod.mixins.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.BlockJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ItemRecord.class)
public class MixinItemRecord extends Item
{
    @Overwrite
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (world.getBlock(x, y, z) instanceof BlockJukebox && world.getBlockMetadata(x, y, z) == 0)
        {
            if (world.isRemote)
            {
                return true;
            }
            
            ((BlockJukebox) world.getBlock(x, y, z)).func_149926_b(world, x, y, z, itemStack);
            world.playAuxSFXAtEntity(null, 1005, x, y, z, Item.getIdFromItem(this));
            itemStack.stackSize--;
            
            return true;
        }
        
        return false;
    }
}