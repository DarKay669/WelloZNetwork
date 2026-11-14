package fr.welloznetwork.wellozmod.mixins.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.world.World;

@Mixin(BlockTallGrass.class)
public class MixinBlockTallGrass extends BlockBush
{
    @Overwrite
    public void harvestBlock(World world, EntityPlayer entityPlayer, int x, int y, int z, int metadata)
    {
        if (!(entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() instanceof ItemShears))
        {
            super.harvestBlock(world, entityPlayer, x, y, z, metadata);
        }
    }
}