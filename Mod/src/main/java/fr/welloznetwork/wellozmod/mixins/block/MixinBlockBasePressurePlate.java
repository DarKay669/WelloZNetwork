package fr.welloznetwork.wellozmod.mixins.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

@Mixin(BlockBasePressurePlate.class)
public class MixinBlockBasePressurePlate extends Block
{
    MixinBlockBasePressurePlate(Material material)
    {
        super(material);
    }
    
    @Overwrite
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y - 1, z);
        
        return World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) || block instanceof BlockFence || block instanceof BlockWall;
    }
    
    @Overwrite
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (!this.canPlaceBlockAt(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }
}