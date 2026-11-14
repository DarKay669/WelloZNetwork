package fr.welloznetwork.wellozmod.mixins.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.world.World;

@Mixin(BlockPumpkin.class)
public class MixinBlockPumpkin
{
    @Overwrite
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.getBlock(x, y, z).isReplaceable(world, x, y, z);
    }
}