package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenWoodland extends BiomeGenBase
{
    public BiomeGenWoodland()
    {
        super(45);
        setBiomeName("Woodland");
        setColor(0x85B53E);
        setHeight(new Height(0.2F, 0.4F));
        setTemperatureRainfall(1.7F, 0.2F);
        theBiomeDecorator.treesPerChunk = 9;
        theBiomeDecorator.grassPerChunk = 7;
        theBiomeDecorator.mushroomsPerChunk = 4;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
    }
    
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        return random.nextInt(10) == 0 ? worldGeneratorBigTree : worldGeneratorTrees;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        return ColorizerFoliage.getFoliageColor(1.0F, 1.0F);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor(int x, int y, int z)
    {
        return ColorizerGrass.getGrassColor(1.0F, 1.0F);
    }
}