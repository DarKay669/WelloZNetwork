package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenShrub;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenTaiga2;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenMeadow extends BiomeGenBase
{
    public BiomeGenMeadow()
    {
        super(48);
        setBiomeName("Meadow");
        setHeight(new Height(0.0F, 0.0F));
        setTemperatureRainfall(0.7F, 0.7F);
        theBiomeDecorator.treesPerChunk = 2;
        theBiomeDecorator.grassPerChunk = 10;
        theBiomeDecorator.sandPerChunk = -999;
        theBiomeDecorator.sandPerChunk2 = -999;
        theBiomeDecorator.mushroomsPerChunk = 2;
        theBiomeDecorator.flowersPerChunk = 10;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 6, 2, 6));
    }
    
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        return random.nextInt(5) == 0 ? new WorldGenTaiga2(Blocks.log, Blocks.leaves, 1, 1, false, 6, 4, 0) : new WorldGenShrub(Blocks.log, Blocks.leaves, 0, 1, Blocks.dirt, Blocks.grass);
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