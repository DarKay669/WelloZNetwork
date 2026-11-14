package fr.welloznetwork.wellozmod.mixins.entity.item;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

@Mixin(EntityFallingBlock.class)
public abstract class MixinEntityFallingBlock extends Entity
{
    public MixinEntityFallingBlock(World world)
    {
        super(world);
    }
}