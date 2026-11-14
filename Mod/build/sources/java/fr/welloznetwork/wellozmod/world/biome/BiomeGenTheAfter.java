package fr.welloznetwork.wellozmod.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenTheAfter extends BiomeGenBase
{
    public BiomeGenTheAfter()
    {
        super(60);
        
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        
        this.topBlock = RegistryBlocks.warped_nylium;
        this.fillerBlock = Blocks.dirt;
        
        this.setBiomeName("The After");
        this.setDisableRain();
        this.setColor(0);
    }
    
    public int getWaterColorMultiplier()
    {
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float temperature)
    {
        return 0xBCBCFA;
    }
    
    public int getBiomeGrassColor(int x, int y, int z)
    {
        return 0xB1FFCB;
    }
    
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        return 0xB1FFCB;
    }
    
    public BiomeDecorator createBiomeDecorator()
    {
        return new BiomeTheAfterDecorator();
    }
}