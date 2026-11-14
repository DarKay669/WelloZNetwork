package fr.welloznetwork.wellozmod.mixins.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block.SoundType;

@Mixin(targets = "net/minecraft/block/Block$3")
public class MixinBlockAnvil extends SoundType
{
    public String getStepResourcePath()
    {
        return "step.stone";
    }
    
    MixinBlockAnvil(String name, float volume, float frequency)
    {
        super(name, volume, frequency);
    }
}