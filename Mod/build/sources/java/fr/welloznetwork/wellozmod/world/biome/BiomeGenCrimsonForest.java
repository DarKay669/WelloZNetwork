package fr.welloznetwork.wellozmod.world.biome;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;

public class BiomeGenCrimsonForest extends CustomBiomeGenHell
{
    public BiomeGenCrimsonForest()
    {
        super(61, BiomeHellForestDecorator.getCrimsonForestDeorator());
        
        this.setBiomeName("Crimson Forest");
        this.setColor(0xFA9418);
        this.fogSkyColor = 0xAD0808;
        this.topBlock = RegistryBlocks.crimson_nylium;
        this.fillerBlock = Blocks.netherrack;
        
        this.spawnableMonsterList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 1, 2, 4));
    }
}