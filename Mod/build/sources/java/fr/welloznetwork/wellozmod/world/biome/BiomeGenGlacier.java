package fr.welloznetwork.wellozmod.world.biome;

import fr.welloznetwork.wellozmod.entity.monster.EntityStray;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenGlacier extends BiomeGenBase
{
    public BiomeGenGlacier()
    {
        super(51);
        setBiomeName("Glacier");
        setColor(0x77A696);
        setHeight(new Height(1.4F, 2.1F));
        setTemperatureRainfall(0.0F, 0.0F);
        setEnableSnow();
        topBlock = Blocks.snow;
        fillerBlock = Blocks.ice;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.add(new SpawnListEntry(EntityStray.class, 1, 1, 1));
    }
}