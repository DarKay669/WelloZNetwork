package fr.welloznetwork.wellozmod.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

@Mixin(Minecraft.class)
public interface IMixinMinecraftAccessor
{
    @Accessor("timer")
    Timer getTimer();
}