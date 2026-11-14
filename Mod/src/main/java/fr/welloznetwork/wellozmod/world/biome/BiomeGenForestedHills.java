package fr.welloznetwork.wellozmod.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenForestedHills extends BiomeGenBase
{
    public BiomeGenForestedHills()
    {
        super(56);
        setBiomeName("Forested Hills");
        setHeight(new Height(0.2F, 1.8F));
        setTemperatureRainfall(BiomeGenBase.forest.temperature - 0.1F, BiomeGenBase.forest.rainfall);
        theBiomeDecorator.treesPerChunk = 7;
        theBiomeDecorator.flowersPerChunk = 5;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        return ColorizerFoliage.getFoliageColor(0.8F, 1.0F);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor(int x, int y, int z)
    {
        return ColorizerGrass.getGrassColor(0.8F, 1.0F);
    }
}