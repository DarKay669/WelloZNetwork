package fr.welloznetwork.wellozmod.world;

import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class SpawnSavedData extends WorldSavedData
{
    public NBTTagCompound nbtTagCompound = new NBTTagCompound();
    
    public SpawnSavedData()
    {
        super("spawnData");
    }
    
    public SpawnSavedData(String mapName)
    {
        super(mapName);
    }
    
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        if (nbtTagCompound != null)
        {
            this.nbtTagCompound = (NBTTagCompound) nbtTagCompound.copy();
        }
    }
    
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        if (nbtTagCompound != null)
        {
            Set<String> keys = this.nbtTagCompound.func_150296_c();
            
            for (String key : keys)
            {
                nbtTagCompound.setTag(key, this.nbtTagCompound.getTag(key));
            }
        }
    }
    
    public static SpawnSavedData getSpawn(World world)
    {
        MapStorage mapStorage = world.perWorldStorage;
        SpawnSavedData spawnSavedData = (SpawnSavedData) mapStorage.loadData(SpawnSavedData.class, "spawnData");
        
        if (spawnSavedData == null)
        {
            spawnSavedData = new SpawnSavedData();
            mapStorage.setData("spawnData", spawnSavedData);
        }
        
        return spawnSavedData;
    }
}