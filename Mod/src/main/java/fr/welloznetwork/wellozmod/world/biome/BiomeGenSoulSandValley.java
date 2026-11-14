package fr.welloznetwork.wellozmod.world.biome;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;

public class BiomeGenSoulSandValley extends CustomBiomeGenHell
{
    public BiomeGenSoulSandValley()
    {
        super(63, new BiomeHellSoulSandValleyDecorator());
        
        this.setBiomeName("Soul Sand Valley");
        this.fogSkyColor = 0x00BACD;
        this.topBlock = Blocks.soul_sand;
        this.fillerBlock = Blocks.soul_sand;
        
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 20, 1, 5));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
    }
    
    public float getSpawningChance()
    {
        return 0.075F;
    }
}