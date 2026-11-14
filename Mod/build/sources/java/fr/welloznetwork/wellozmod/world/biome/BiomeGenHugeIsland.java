package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import fr.welloznetwork.wellozmod.entity.monster.huge.EntityHugeCreeper;
import fr.welloznetwork.wellozmod.entity.monster.huge.EntityHugeSkeleton;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeBat;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeChicken;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeCow;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeHorse;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeMooshroom;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeOcelot;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugePig;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeRabbit;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeSheep;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeWolf;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenHugeTree;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenHugeIsland extends BiomeGenBase
{    
    public BiomeGenHugeIsland()
    {
        super(43);
        setBiomeName("Huge Island");
        setHeight(new Height(0.2F, 0.3F));
        setTemperatureRainfall(0.9F, 1.0F);
        addDefaultFlowers();
        topBlock = Blocks.grass;
        fillerBlock = Blocks.dirt;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeChicken.class, 3, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeCow.class, 3, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeHorse.class, 3, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeMooshroom.class, 3, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeOcelot.class, 3, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugePig.class, 3, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeSheep.class, 3, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeWolf.class, 3, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeCreeper.class, 1, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeSkeleton.class, 1, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeBat.class, 3, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityHugeRabbit.class, 3, 1, 2));
    }
    
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        return new WorldGenHugeTree(true);
    }
}