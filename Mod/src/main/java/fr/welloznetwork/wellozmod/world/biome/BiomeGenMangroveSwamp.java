package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenBulbTree;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenMangroveSwamp extends BiomeGenBase
{    
    public BiomeGenMangroveSwamp()
    {
        super(52);
        setBiomeName("Mangrove Swamp");
        setColor(6454333);
        theBiomeDecorator.treesPerChunk = 8;
        theBiomeDecorator.generateLakes = true;
        spawnableCaveCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
    }
    
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float temperature)
    {
        return 0;
    }
    
    public WorldGenAbstractTree func_150567_a(Random random) 
    {
        return new WorldGenBulbTree(RegistryBlocks.mangrove_log, RegistryBlocks.mangrove_leaves, 0, 1, false, 5, 8, true);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        return 6388580;
    }
}