package fr.welloznetwork.wellozmod.mixins.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;

@Mixin(BlockTrapDoor.class)
public class MixinBlockTrapDoor extends Block
{
    protected MixinBlockTrapDoor(Material material)
    {
        super(material);
    }
    
    public boolean isLadder(IBlockAccess iBlockAccess, int x, int y, int z, EntityLivingBase entityLivingBase)
    {
        return iBlockAccess.getBlockMetadata(x, y, z) % 8 > 3 && iBlockAccess.getBlock(x, y - 1, z) instanceof BlockLadder;
    }
}