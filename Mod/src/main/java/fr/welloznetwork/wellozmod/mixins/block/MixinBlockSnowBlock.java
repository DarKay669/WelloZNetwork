package fr.welloznetwork.wellozmod.mixins.block;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.BlockSnowBlock;
import net.minecraft.world.World;

@Mixin(BlockSnowBlock.class)
public class MixinBlockSnowBlock
{
    @Overwrite
    public void updateTick(World world, int x, int y, int z, Random random) {}
}