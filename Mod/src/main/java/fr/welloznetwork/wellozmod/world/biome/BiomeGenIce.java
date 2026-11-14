package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.monster.EntityStray;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntityPenguin;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowBat;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowVillager;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeGenIce extends BiomeGenBase
{
	public BiomeGenIce()
	{
		super(41);
		setBiomeName("Ice");
		setTemperatureRainfall(0.1F, 0.1F);
		setEnableSnow();
		topBlock = Blocks.packed_ice;
		fillerBlock = RegistryBlocks.iced_stone;
		spawnableCaveCreatureList.clear();
		spawnableCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCreatureList.add(new SpawnListEntry(EntityPenguin.class, 1, 2, 2));
		spawnableCreatureList.add(new SpawnListEntry(EntitySnowVillager.class, 1, 1, 1));
		spawnableCreatureList.add(new SpawnListEntry(EntitySnowBat.class, 1, 2, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityStray.class, 1, 1, 1));
	}
	
	public WorldGenAbstractTree func_150567_a(Random random)
	{
	    if (random.nextInt(3) == 0)
	    {
	        return new WorldGenTaiga1();
	    }
	    
	    return new WorldGenTaiga2(true);   
	}
	
	@SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float temperature)
    {
        return 10599892;
    }
	
	public boolean getEnableSnow()
	{
	    return true;
	}
}