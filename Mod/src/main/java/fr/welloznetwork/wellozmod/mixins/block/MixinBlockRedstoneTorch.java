package fr.welloznetwork.wellozmod.mixins.block;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.world.World;

@Mixin(BlockRedstoneTorch.class)
public class MixinBlockRedstoneTorch
{
    private static Map<World, List<?>> field_150112_b = new WeakHashMap<>();
}