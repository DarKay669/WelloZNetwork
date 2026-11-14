package fr.welloznetwork.wellozmod.world.biome;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenRedRockMountain extends BiomeGenBase
{
    public BiomeGenRedRockMountain()
    {
        super(57);
        setBiomeName("Red Rock Mountain");
        setColor(0xC4722F);
        setHeight(new Height(1.7F, 1.7F));
        setTemperatureRainfall(2.0F, 0.0F);
        setDisableRain();
        topBlock = RegistryBlocks.block_red_sandstone;
        fillerBlock = Blocks.stone;
        theBiomeDecorator.treesPerChunk = 0;
        theBiomeDecorator.grassPerChunk = 12;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 3, 1, 3));
    }
}