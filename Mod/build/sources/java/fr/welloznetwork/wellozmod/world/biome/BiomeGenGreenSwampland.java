package fr.welloznetwork.wellozmod.world.biome;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenGreenSwampland extends BiomeGenBase
{
    public BiomeGenGreenSwampland()
    {
        super(49);
        setBiomeName("Green Swampland");
        setColor(0x68C474);
        setHeight(new Height(-0.2F, 0.1F));
        setTemperatureRainfall(0.8F, 0.9F);
        theBiomeDecorator.waterlilyPerChunk = 4;
        theBiomeDecorator.treesPerChunk = 4;
        theBiomeDecorator.deadBushPerChunk = 0;
        theBiomeDecorator.mushroomsPerChunk = 8;
        theBiomeDecorator.reedsPerChunk = 10;
        theBiomeDecorator.sandPerChunk = 0;
        theBiomeDecorator.sandPerChunk2 = 0;
        theBiomeDecorator.clayPerChunk = 1;
        theBiomeDecorator.flowersPerChunk = 6;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
    }
}