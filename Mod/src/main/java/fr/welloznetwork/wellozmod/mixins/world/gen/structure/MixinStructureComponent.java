package fr.welloznetwork.wellozmod.mixins.world.gen.structure;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.IPlantable;

@Mixin(StructureComponent.class)
public abstract class MixinStructureComponent
{
    @Shadow
    protected abstract int getXWithOffset(int x, int z);
    
    @Shadow
    protected abstract int getYWithOffset(int y);
    
    @Shadow
    protected abstract int getZWithOffset(int x, int z);
    
    @Overwrite
    protected void func_151554_b(World world, Block block, int metadata, int x, int y, int z, StructureBoundingBox structureBoundingBox)
    {
        final int x1 = this.getXWithOffset(x, z);
        int y1 = this.getYWithOffset(y);
        final int z1 = this.getZWithOffset(x, z);
        
        if (structureBoundingBox.isVecInside(x1, y1, z1))
        {
            Block block1;
            Material material;
            
            do
            {
                block1 = world.getBlock(x1, y1, z1);
                material = block1.getMaterial();
                
                if (y1 > 1 && (material.isReplaceable() || material.isLiquid() || block1 instanceof IPlantable || world.isAirBlock(x1, y1, z1)))
                {
                    world.setBlock(x1, y1, z1, block, metadata, 2);
                    y1--;
                }
                
                else
                {
                    break;
                }
            }
            
            while (true);
        }
    }
    
    @Overwrite
    protected void clearCurrentPositionBlocksUpwards(World world, int x, int y, int z, StructureBoundingBox structureBoundingBox)
    {
        final int x1 = this.getXWithOffset(x, z);
        int y1 = this.getYWithOffset(y);
        final int z1 = this.getZWithOffset(x, z);
        
        if (!structureBoundingBox.isVecInside(x1, y1, z1))
        {
            return;
        }
        
        while (y1 < 255)
        {
            Block block = world.getBlock(x1, y1, z1);
            
            if (block.isAir(world, x1, y1, z1) || block.isLeaves(world, x1, y1, z1) || block instanceof BlockLog || block.getMaterial() == Material.vine)
            {
                return;
            }
            
            world.setBlock(x1, y1, z1, Blocks.air, 0, 2);
            y1++;
        }
    }
}