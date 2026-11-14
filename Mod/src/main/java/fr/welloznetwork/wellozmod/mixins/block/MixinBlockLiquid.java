package fr.welloznetwork.wellozmod.mixins.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

@Mixin(BlockLiquid.class)
public class MixinBlockLiquid
{
    @Overwrite
    protected void func_149799_m(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        
        if (block == Blocks.cobblestone | block == Blocks.obsidian | block == Blocks.stone)
        {
            world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
            
            for (int i = 0; i < 8; i++)
            {
                world.spawnParticle("largesmoke", (double) x + Math.random(), (double) y + 1.2D, (double) z + Math.random(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
}