package fr.welloznetwork.wellozmod.mixins.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

@Mixin(BlockSnow.class)
public class MixinBlockSnow extends Block
{
    public MixinBlockSnow(Material material)
    {
        super(material);
    }
    
    @Overwrite
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side)
    {
        Block block = iBlockAccess.getBlock(x, y, z);
        
        if (block == Blocks.snow_layer)
        {
            int metadata = iBlockAccess.getBlockMetadata(x, y, z);
            
            switch (side)
            {
                case 0:
                    return false;
                    
                case 1:
                    
                    if (iBlockAccess.getBlockMetadata(x, y - 1, z) == 7)
                    {
                        return false;
                    }
                    
                    break;
                    
                case 2:
                    
                    if (iBlockAccess.getBlockMetadata(x, y, z + 1) <= metadata)
                    {
                        return false;
                    }
                    
                    break;
                    
                case 3:
                    
                    if (iBlockAccess.getBlockMetadata(x, y, z - 1) <= metadata)
                    {
                        return false;
                    }
                    
                    break;
                    
                case 4:
                    
                    if (iBlockAccess.getBlockMetadata(x + 1, y, z) <= metadata)
                    {
                        return false;
                    }
                    
                    break;
                    
                case 5:
                    
                    if (iBlockAccess.getBlockMetadata(x - 1, y, z) <= metadata)
                    {
                        return false;
                    }
            }
            
            boolean opaque = block.isOpaqueCube();
            
            if (opaque && (side != 1 || iBlockAccess.getBlockMetadata(x, y, z) == 7))
            {
                return false;
            }
        }
        
        return true;
    }
}