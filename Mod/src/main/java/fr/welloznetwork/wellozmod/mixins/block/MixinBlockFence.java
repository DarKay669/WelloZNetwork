package fr.welloznetwork.wellozmod.mixins.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@Mixin(BlockFence.class)
public class MixinBlockFence
{
    @Overwrite
    public boolean canConnectFenceTo(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        Block block = iBlockAccess.getBlock(x, y, z);
        
        return block instanceof BlockFence && block.getMaterial() == ((BlockFence) (Object) this).getMaterial() || block instanceof BlockFenceGate || (block.getMaterial().isOpaque() && block.renderAsNormalBlock() && block.getMaterial() != Material.gourd);
    }
    
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
    {
        return true;
    }
}