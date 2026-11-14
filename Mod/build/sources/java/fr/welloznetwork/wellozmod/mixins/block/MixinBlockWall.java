package fr.welloznetwork.wellozmod.mixins.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@Mixin(BlockWall.class)
public class MixinBlockWall
{
    @Overwrite
    public boolean canConnectWallTo(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        Block block = iBlockAccess.getBlock(x, y, z);
        
        return block instanceof BlockWall && block.getMaterial() == ((BlockWall) (Object) this).getMaterial() || block instanceof BlockFenceGate || (block.getMaterial().isOpaque() && block.renderAsNormalBlock() && block.getMaterial() != Material.gourd);
    }
    
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
    {
        return true;
    }
}