package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.monster.haunted.EntityGhost;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenBulbTree;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenHaunted extends BiomeGenBase
{
    private WorldGenerator theWorldGenerator;
    
    public BiomeGenHaunted()
    {
        super(44);
        setBiomeName("Haunted");
        this.theWorldGenerator = new WorldGenMinable(Blocks.web, 4);
        theBiomeDecorator.treesPerChunk = 1;
        waterColorMultiplier = 0;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.add(new SpawnListEntry(EntityGhost.class, 1, 1, 1));     
    }
    
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        return new WorldGenBulbTree(Blocks.log2, Blocks.leaves2, 0, 1, false, 6, 9, false);
    }
    
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float temperature)
    {
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor(int x, int y, int z)
    {
        return plantNoise.func_151601_a((double) x * 0.0225D, (double) z * 0.0225D) < -0.1D ? 5011004 : 6975570;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        return 6975570;
    }
}