package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenDeadTree;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenWasteland extends BiomeGenBase
{
    public BiomeGenWasteland()
    {
        super(59);
        setBiomeName("Wasteland");
        setColor(5919808);
        setHeight(new Height(0.1F, 0.1F));
        setTemperatureRainfall(2.0F, 0.05F);
        setDisableRain();
        waterColorMultiplier = 15073024;
        theBiomeDecorator.treesPerChunk = 0;
        theBiomeDecorator.grassPerChunk = 20;
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
    }
    
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        return new WorldGenDeadTree();
    }
    
    public int getBiomeGrassColor(int x, int y, int z)
    {
        return 10330232;
    }

    public int getBiomeFoliageColor(int x, int y, int z)
    {
        return 10067541;
    }
}