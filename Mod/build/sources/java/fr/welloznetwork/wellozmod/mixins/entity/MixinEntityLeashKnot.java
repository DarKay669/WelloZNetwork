package fr.welloznetwork.wellozmod.mixins.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.world.World;

@Mixin(EntityLeashKnot.class)
public abstract class MixinEntityLeashKnot extends EntityHanging
{
    public MixinEntityLeashKnot(World world)
    {
        super(world);
    }
    
    @Overwrite
    public boolean onValidSurface()
    {
        Block block = this.worldObj.getBlock(this.field_146063_b, this.field_146064_c, this.field_146062_d);
        
        return block.getRenderType() == 11 || block instanceof BlockFence;
    }
}