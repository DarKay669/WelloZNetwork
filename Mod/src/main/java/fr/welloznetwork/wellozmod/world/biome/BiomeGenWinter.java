package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowCreeper;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowEnderman;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowSlime;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowSpider;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowWitch;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowZombie;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowChicken;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowCow;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowHorse;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowOcelot;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowPig;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowRabbit;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowWolf;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenBulbTree;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenWinter extends BiomeGenBase
{    
    public BiomeGenWinter()
    {
        super(42);
        setBiomeName("Winter");
        setHeight(new Height(0.2F, 0.2F));
        setTemperatureRainfall(-0.5F, 0.4F);
        setEnableSnow();
        topBlock = Blocks.snow_layer;
        fillerBlock = Blocks.snow;
        theBiomeDecorator.treesPerChunk = 9;
        theBiomeDecorator.grassPerChunk = 5;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowChicken.class, 3, 2, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowCow.class, 3, 2, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowHorse.class, 3, 2, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowOcelot.class, 3, 2, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowPig.class, 3, 2, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowWolf.class, 3, 2, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowCreeper.class, 1, 1, 1));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowZombie.class, 1, 1, 1));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowRabbit.class, 3, 2, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 3, 2, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowEnderman.class, 1, 1, 1));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowSlime.class, 1, 1, 1));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowSpider.class, 1, 1, 1));
        spawnableCreatureList.add(new SpawnListEntry(EntitySnowWitch.class, 1, 1, 1));
    }
    
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        return new WorldGenBulbTree(Blocks.log, RegistryBlocks.winter_leaves, 0, 1, false, 3, 6, false);
    }
    
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float temperature)
    {
        return 16056062;
    }
}