package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenAutumnTree;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenAutumnTree.AutumnTreeType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenAutumnWood extends BiomeGenBase
{    
    public BiomeGenAutumnWood()
    {
        super(55);
        setBiomeName("Autumn Wood");
        setColor(0xF29C11);
        setHeight(new Height(0.2F, 0.8F));
        setTemperatureRainfall(BiomeGenBase.forest.temperature, BiomeGenBase.forest.rainfall);
        theBiomeDecorator.treesPerChunk = 12;
        theBiomeDecorator.grassPerChunk = 6;
        theBiomeDecorator.mushroomsPerChunk = 3;
        theBiomeDecorator.flowersPerChunk = 5;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
    }
    
    public WorldGenAbstractTree func_150567_a(Random random) 
    {
        return random.nextInt(5) == 0 ? new WorldGenAutumnTree(false, AutumnTreeType.BROWN) : random.nextInt(5) == 0 ? new WorldGenAutumnTree(false, AutumnTreeType.ORANGE) : random.nextInt(5) == 0 ? new WorldGenAutumnTree(false, AutumnTreeType.RED) : random.nextInt(5) == 0 ? new WorldGenAutumnTree(false, AutumnTreeType.CHERRY) : new WorldGenAutumnTree(false, AutumnTreeType.YELLOW);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        return ColorizerFoliage.getFoliageColor(1.0F, 0.1F);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor(int x, int y, int z)
    {
        return ColorizerGrass.getGrassColor(1.0F, 0.1F);
    }
}