package fr.welloznetwork.wellozmod.init;

import static cpw.mods.fml.common.registry.GameRegistry.registerTileEntityWithAlternatives;

import fr.welloznetwork.wellozmod.tileentity.TileEntityBarrel;
import fr.welloznetwork.wellozmod.tileentity.TileEntityEmeraldChest;
import fr.welloznetwork.wellozmod.tileentity.TileEntityEndRod;
import fr.welloznetwork.wellozmod.tileentity.TileEntityLights;
import fr.welloznetwork.wellozmod.tileentity.TileEntityObserver;
import fr.welloznetwork.wellozmod.tileentity.TileEntityOnlineDetector;
import fr.welloznetwork.wellozmod.tileentity.TileEntitySnowGlobe;

public class RegistryTileEntity
{
    public static void init()
    {
        registerTileEntityWithAlternatives(TileEntityBarrel.class, "wellozmod:barrel");
        registerTileEntityWithAlternatives(TileEntityEmeraldChest.class, "wellozmod:emeraldChest");
        registerTileEntityWithAlternatives(TileEntityEndRod.class, "wellozmod:endRod");
        registerTileEntityWithAlternatives(TileEntityLights.class, "wellozmod:lights");
        registerTileEntityWithAlternatives(TileEntitySnowGlobe.class, "wellozmod:snowGlobe");
        registerTileEntityWithAlternatives(TileEntityOnlineDetector.class, "wellozmod:onlineDetector");
        registerTileEntityWithAlternatives(TileEntityObserver.class, "wellozmod:observer");
    }
}