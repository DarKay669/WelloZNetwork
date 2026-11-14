package fr.welloznetwork.wellozmod.mixins.util;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.util.AxisAlignedBB;

@Mixin(AxisAlignedBB.class)
public class MixinAxisAlignedBB implements IAxisAlignedBB
{
    public AxisAlignedBB func_186662_g(double value)
    {
        AxisAlignedBB axisAlignedBB = (AxisAlignedBB) (Object) this;
        
        return axisAlignedBB.expand(value, value, value);
    }
}