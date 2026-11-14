package fr.welloznetwork.wellozmod.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerData.ServerResourceMode;

@Mixin(ServerData.class)
public class MixinServerData
{
    @Overwrite
    public ServerResourceMode func_152586_b()
    {
        return ServerResourceMode.ENABLED;
    }
}