package fr.welloznetwork.wellozmod.world;

import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

@Custom(Teleporter.class)
public class CustomTeleporter extends Teleporter
{
    public CustomTeleporter(WorldServer worldServer)
    {
        super(worldServer);
    }
    
    public void placeInPortal(Entity entity, double x, double y, double z, float rotationYaw) {}
    
    public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float rotationYaw)
    {
        return false;
    }    
}