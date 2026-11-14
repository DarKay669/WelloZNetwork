package fr.welloznetwork.wellozmod.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenTemporateRainforest extends BiomeGenBase
{
    public BiomeGenTemporateRainforest()
    {
        super(58);
        setBiomeName("Temporate Rainforest");
        setColor(0x338235);
        setHeight(new Height(0.4F, 1.5F));
        setTemperatureRainfall(0.2F, 0.3F);
        setEnableSnow();
        theBiomeDecorator.treesPerChunk = 17;
        theBiomeDecorator.grassPerChunk = 16;
        theBiomeDecorator.mushroomsPerChunk = 2;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
    }
    
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float temperature)
    {
        return 10599892;
    }
}