package fr.welloznetwork.wellozmod.world.biome;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;

public class BiomeGenWarpedForest extends CustomBiomeGenHell
{
    public BiomeGenWarpedForest()
    {
        super(62, BiomeHellForestDecorator.getWarpedForestDecorator());
        
        this.setBiomeName("Warped Forest");
        this.setColor(0x49907B);
        this.fogSkyColor = 0x5700AF;
        this.topBlock = RegistryBlocks.warped_nylium;
        this.field_150604_aj = 1;
        this.fillerBlock = Blocks.netherrack;
        
        this.spawnableMonsterList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
    }
    
    public float getSpawningChance()
    {
        return 0.035F;
    }
}