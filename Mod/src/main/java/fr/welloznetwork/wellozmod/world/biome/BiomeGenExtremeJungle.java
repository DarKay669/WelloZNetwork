package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenExtremeJungle extends BiomeGenBase
{
    public BiomeGenExtremeJungle()
    {
        super(54);
        setBiomeName("Extreme Jungle");
        setColor(0x2c4205);
        setHeight(new Height(2.1F, 2.3F));
        setTemperatureRainfall(0.95F, 0.9F);
        theBiomeDecorator.treesPerChunk = 50;
        theBiomeDecorator.grassPerChunk = 25;
        theBiomeDecorator.flowersPerChunk = 7;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, 2, 1, 1));
    }
    
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        return (WorldGenAbstractTree) (random.nextInt(10) == 0 ? this.worldGeneratorBigTree : (random.nextInt(2) == 0 ? new WorldGenShrub(3, 0) : new WorldGenTrees(false, 4 + random.nextInt(7), 3, 3, true)));
    }
    
    public WorldGenerator getRandomWorldGenForGrass(Random random)
    {
        return random.nextInt(4) == 0 ? new WorldGenTallGrass(Blocks.tallgrass, 2) : new WorldGenTallGrass(Blocks.tallgrass, 1);
    }
}