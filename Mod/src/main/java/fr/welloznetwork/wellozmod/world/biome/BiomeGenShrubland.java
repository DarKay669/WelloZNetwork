package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenShrub;

public class BiomeGenShrubland extends BiomeGenBase
{
    public BiomeGenShrubland()
    {
        super(46);
        setBiomeName("Shrubland");
        setColor(0x51B57D);
        setHeight(new Height(0.1F, 0.3F));
        setTemperatureRainfall(0.4F, 0.6F);
        theBiomeDecorator.treesPerChunk = 1;
        theBiomeDecorator.flowersPerChunk = 0;
        theBiomeDecorator.grassPerChunk = 5;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 6, 1, 5));
    }
    
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        return new WorldGenShrub(0, 0);
    }
}