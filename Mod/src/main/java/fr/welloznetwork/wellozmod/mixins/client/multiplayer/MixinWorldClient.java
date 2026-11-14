package fr.welloznetwork.wellozmod.mixins.client.multiplayer;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import fr.welloznetwork.wellozmod.util.ModUtil.RandomXoshiro256StarStar;
import net.minecraft.client.multiplayer.WorldClient;

@Mixin(WorldClient.class)
public class MixinWorldClient
{
    @Redirect(method = "doVoidFogParticles", at = @At(value = "NEW", target = "java/util/Random", ordinal = 0))
    private Random redirectDoVoidFogParticlesRandom()
    {
        return new RandomXoshiro256StarStar();
    }
}