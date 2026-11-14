package fr.welloznetwork.wellozmod.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenObsidian extends BiomeGenBase
{
	public BiomeGenObsidian()
	{
		super(40);
		setBiomeName("Obsidian");
		topBlock = Blocks.obsidian;
		fillerBlock = Blocks.obsidian;
		spawnableCaveCreatureList.clear();
		spawnableCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableWaterCreatureList.clear();
	}
}