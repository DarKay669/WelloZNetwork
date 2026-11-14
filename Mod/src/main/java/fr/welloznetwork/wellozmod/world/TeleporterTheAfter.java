package fr.welloznetwork.wellozmod.world;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterTheAfter extends Teleporter
{
    private final WorldServer worldServer;
    
    public TeleporterTheAfter(WorldServer worldServer)
    {
        super(worldServer);
        this.worldServer = worldServer;
    }
    
    public void placeInPortal(Entity entity, double x, double y, double z, float rotationYaw)
    {
        ChunkCoordinates chunkCoordinates = this.createSpawnPlatform();
        
        entity.setLocationAndAngles(chunkCoordinates.posX + 0.5, chunkCoordinates.posY + 1, chunkCoordinates.posZ + 0.5, rotationYaw, entity.rotationPitch);
    }
    
    public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float rotationYaw)
    {
        return false;
    }
    
    public boolean makePortal(Entity entity)
    {
        return true;
    }
    
    private ChunkCoordinates getSafeSpawnPoint()
    {
        int x = this.worldServer.getSpawnPoint().posX;
        int z = this.worldServer.getSpawnPoint().posZ;
        
        for (int y = 64; y < 128; y++)
        {
            if (this.worldServer.isAirBlock(x, y, z) && this.worldServer.isAirBlock(x, y + 1, z) && this.worldServer.getBlock(x, y - 1, z).getMaterial().isSolid())
            {
                return new ChunkCoordinates(x, y, z);
            }
        }
        
        return this.worldServer.getSpawnPoint();
    }
    
    private ChunkCoordinates createSpawnPlatform()
    {
        ChunkCoordinates chunkCoordinates = this.worldServer.getSpawnPoint();
        
        int x = chunkCoordinates.posX, y = 80, z = chunkCoordinates.posZ;
        
        for (int x1 = -2; x1 <= 2; x1++)
        {
            for (int z1 = -2; z1 <= 2; z1++)
            {
                int x2 = x + x1, z2 = z + z1;
                
                this.worldServer.setBlock(x2, y, z2, Blocks.stone);
                this.worldServer.setBlockToAir(x2, y + 1, z2);
                this.worldServer.setBlockToAir(x2, y + 2, z2);
            }
        }
        
        return new ChunkCoordinates(x, y, z);
    }
}