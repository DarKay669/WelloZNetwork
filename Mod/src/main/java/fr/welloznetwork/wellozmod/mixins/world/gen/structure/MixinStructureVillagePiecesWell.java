package fr.welloznetwork.wellozmod.mixins.world.gen.structure;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraft.world.gen.structure.StructureVillagePieces.Well;

@Mixin(Well.class)
public abstract class MixinStructureVillagePiecesWell extends Village
{
    @Overwrite
    public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox)
    {
        if (this.field_143015_k < 0)
        {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            
            if (this.field_143015_k < 0)
            {
                return true;
            }
            
            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
            
            BiomeGenBase biomeGenBase = world.getBiomeGenForCoords(this.getXWithOffset(0, 0), this.getZWithOffset(0, 0));
            
            boolean desert = (biomeGenBase == BiomeGenBase.desert | biomeGenBase == BiomeGenBase.desertHills);
            
            Block block = desert ? Blocks.sandstone : Blocks.cobblestone;
            
            this.fillWithBlocks(world, this.boundingBox, 1, 0, 1, 4, 12, 4, block, Blocks.water, false);
            
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 2, 12, 2, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 3, 12, 2, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 2, 12, 3, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 3, 12, 3, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 13, 1, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 14, 1, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 13, 1, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 14, 1, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 13, 4, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 14, 4, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 13, 4, this.boundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 14, 4, this.boundingBox);
            
            this.fillWithBlocks(world, this.boundingBox, 1, 15, 1, 4, 15, 4, block, block, false);
            
            block = desert ? Blocks.sandstone : Blocks.gravel;
            
            for (int z = 0; z <= 5; z++)
            {
                for (int x = 0; x <= 5; x++)
                {
                    if (x == 0 | x == 5 | z == 0 | z == 5)
                    {
                        this.placeBlockAtCurrentPosition(world, block, 0, x, 11, z, this.boundingBox);
                        this.clearCurrentPositionBlocksUpwards(world, x, 12, z, this.boundingBox);
                    }
                }
            }
        }
        
        return true;
    }
}