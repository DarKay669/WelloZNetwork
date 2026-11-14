package fr.welloznetwork.wellozmod.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.EntityRabbit;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenDesertMountain extends BiomeGenBase
{    
    public BiomeGenDesertMountain()
    {
        super(47);
        setBiomeName("Desert Mountain");
        setColor(0xFA9418);
        setHeight(new Height(0.4F, 1.4F));
        setTemperatureRainfall(2.0F, 0.0F);
        setDisableRain();
        topBlock = Blocks.sand;
        fillerBlock = Blocks.sand;
        theBiomeDecorator.treesPerChunk = -999;
        theBiomeDecorator.deadBushPerChunk = 2;
        theBiomeDecorator.reedsPerChunk = 50;
        theBiomeDecorator.cactiPerChunk = 10;
        spawnableCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityRabbit.class, 6, 2, 6));
    }
    
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float temperature)
    {
        return 14522460;
    }
}