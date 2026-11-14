package fr.welloznetwork.wellozmod.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenGreenHills extends BiomeGenBase
{
    public BiomeGenGreenHills()
    {
        super(50);
        setBiomeName("Green Hills");
        setColor(0x68C474);
        setHeight(new Height(0.6F, 1.2F));
        setTemperatureRainfall(0.6F, 0.7F);
        theBiomeDecorator.treesPerChunk = 1;
        theBiomeDecorator.flowersPerChunk = 5;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        return ColorizerFoliage.getFoliageColor(0.7F, 0.8F);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor(int x, int y, int z)
    {
        return ColorizerGrass.getGrassColor(0.7F, 0.8F);
    }
}