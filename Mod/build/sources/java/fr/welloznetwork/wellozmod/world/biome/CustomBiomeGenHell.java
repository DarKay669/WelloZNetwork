package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenHell;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

@Custom(BiomeGenHell.class)
public class CustomBiomeGenHell extends BiomeGenBase
{
    private BiomeHellDecorator biomeHellDecorator;
    public int fogSkyColor;
    
    public CustomBiomeGenHell(int id, BiomeHellDecorator biomeHellDecorator)
    {
        super(id);
        this.biomeHellDecorator = biomeHellDecorator;
        this.setDisableRain();
        this.setTemperatureRainfall(2.0F, 0.0F);
        this.field_150604_aj = this.field_76754_C = 0;
        
        BiomeDictionary.registerBiomeType(this, Type.NETHER);
        
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.addAll(BiomeGenBase.hell.getSpawnableList(EnumCreatureType.monster));
    }
    
    public void populate(World world, Random random, int chunkX, int chunkZ)
    {
        this.biomeHellDecorator.populate(world, random, chunkX, chunkZ);
    }
    
    public void decorate(World world, Random random, int chunkX, int chunkZ)
    {
        this.biomeHellDecorator.decorate(world, random, chunkX, chunkZ);
    }
    
    public int getSkyColorByTemp(float temperature)
    {
        return this.fogSkyColor;
    }
}