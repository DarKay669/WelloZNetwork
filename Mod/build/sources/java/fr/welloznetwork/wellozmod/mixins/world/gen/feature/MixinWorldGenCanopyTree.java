package fr.welloznetwork.wellozmod.mixins.world.gen.feature;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;

@Mixin(WorldGenCanopyTree.class)
public abstract class MixinWorldGenCanopyTree extends WorldGenAbstractTree
{
    private MixinWorldGenCanopyTree(boolean doBlockNotify)
    {
        super(doBlockNotify);
    }
    
    @Redirect(method = "generate(Lnet/minecraft/world/World;Ljava/util/Random;III)Z", at = @At(value = "INVOKE", target = "Lnet/minecaft/world/gen/feature/WorldGenCanopyTree;setBlockAndNotifyAdequately(Lnet/minecraft/world/World;IIILnet/minecraft/block/Block;I)V", ordinal = 4))
    void ifReplaceableSetBlockAndNotifyAdequately(WorldGenCanopyTree worldGenCanopyTree, World world, int x, int y, int z, Block block, int metadata)
    {
        if (this.isReplaceable(world, x, y, z))
        {
            this.setBlockAndNotifyAdequately(world, x, y, z, block, metadata);
        }
    }
}