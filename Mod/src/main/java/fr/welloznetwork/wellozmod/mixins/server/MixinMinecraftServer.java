package fr.welloznetwork.wellozmod.mixins.server;

import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import fr.welloznetwork.wellozmod.util.ModUtil.RandomXoshiro256StarStar;
import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer
{
    @Final
    private Random field_147146_q = new RandomXoshiro256StarStar();
    
    @Overwrite
    public void initialWorldChunkLoad() {}
}