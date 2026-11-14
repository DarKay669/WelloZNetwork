package fr.welloznetwork.wellozmod.mixins.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.world.World;

@Mixin(ItemBucket.class)
public class MixinItemBucket extends Item
{
    @Shadow
    private Block isFull;
    
    @Redirect(method = "tryPlaceContainedLiquid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlock(IIILnet/minecraft/block/Block;II)Z"))
    private boolean newSetBlock(World world, int x, int y, int z, Block block, int m, int n)
    {
        if (y >= 255)
        {
            return false;
        }
        
        return world.setBlock(x, y, z, this.isFull, 0, 3);
    }
}