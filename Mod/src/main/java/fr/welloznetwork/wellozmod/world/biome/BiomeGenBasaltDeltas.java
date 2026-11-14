package fr.welloznetwork.wellozmod.world.biome;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;

public class BiomeGenBasaltDeltas extends CustomBiomeGenHell
{
    public BiomeGenBasaltDeltas()
    {
        super(64, new BiomeHellBasaltDeltasDecorator());
        
        this.setBiomeName("Basalt Deltas");
        this.fogSkyColor = 0x534868;
        this.topBlock = RegistryBlocks.black_stone;
        this.fillerBlock = RegistryBlocks.basalt;
        
        this.spawnableMonsterList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 40, 1, 1));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 100, 2, 5));
    }
}