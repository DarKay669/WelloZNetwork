package fr.welloznetwork.wellozmod.init;

import static net.minecraftforge.common.BiomeManager.addBiome;
import static net.minecraftforge.common.BiomeManager.BiomeType.DESERT;
import static net.minecraftforge.common.BiomeManager.BiomeType.ICY;
import static net.minecraftforge.common.BiomeManager.BiomeType.WARM;

import fr.welloznetwork.wellozmod.world.biome.BiomeGenAutumnWood;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenBambooForest;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenBasaltDeltas;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenCrimsonForest;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenDesertMountain;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenExtremeJungle;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenForestedHills;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenGlacier;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenGreenHills;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenGreenSwampland;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenHaunted;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenHugeIsland;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenIce;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenMangroveSwamp;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenMeadow;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenObsidian;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenRedRockMountain;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenShrubland;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenSoulSandValley;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenTemporateRainforest;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenTheAfter;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenWarpedForest;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenWasteland;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenWinter;
import fr.welloznetwork.wellozmod.world.biome.BiomeGenWoodland;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager.BiomeEntry;

public class RegistryBiomes
{
    public static final BiomeGenObsidian OBSIDIAN = new BiomeGenObsidian();
    public static final BiomeGenIce ICE = new BiomeGenIce();
    public static final BiomeGenWinter WINTER = new BiomeGenWinter();
    public static final BiomeGenHugeIsland HUGE_ISLAND = new BiomeGenHugeIsland();
    public static final BiomeGenHaunted HAUNTED = new BiomeGenHaunted();
    public static final BiomeGenWoodland WOODLAND = new BiomeGenWoodland();
    public static final BiomeGenShrubland SHRUBLAND = new BiomeGenShrubland();
    public static final BiomeGenDesertMountain DESERT_MOUNTAIN = new BiomeGenDesertMountain();
    public static final BiomeGenMeadow MEADOW = new BiomeGenMeadow();
    public static final BiomeGenGreenSwampland GREEN_SWAMPLAND = new BiomeGenGreenSwampland();
    public static final BiomeGenGreenHills GREEN_HILLS = new BiomeGenGreenHills();
    public static final BiomeGenGlacier GLACIER = new BiomeGenGlacier();
    public static final BiomeGenMangroveSwamp MANGROVE_SWAMP = new BiomeGenMangroveSwamp();
    public static final BiomeGenBambooForest BAMBOO_FOREST = new BiomeGenBambooForest();
    public static final BiomeGenExtremeJungle EXTREME_JUNGLE = new BiomeGenExtremeJungle();
    public static final BiomeGenAutumnWood AUTUMN_WOOD = new BiomeGenAutumnWood();
    public static final BiomeGenForestedHills FORESTED_HILLS = new BiomeGenForestedHills();
    public static final BiomeGenRedRockMountain RED_ROCK_MOUNTAIN = new BiomeGenRedRockMountain();
    public static final BiomeGenTemporateRainforest TEMPORATE_RAINFOREST = new BiomeGenTemporateRainforest();
    public static final BiomeGenWasteland WASTELAND = new BiomeGenWasteland();
    public static final BiomeGenBase THE_AFTER = new BiomeGenTheAfter();
    public static final BiomeGenBase CRIMSON_FOREST = new BiomeGenCrimsonForest();
    public static final BiomeGenBase WARPED_FOREST = new BiomeGenWarpedForest();
    public static final BiomeGenBase SOUL_SAND_VALLEY = new BiomeGenSoulSandValley();
    public static final BiomeGenBase BASALT_DELTAS = new BiomeGenBasaltDeltas();
    
    public static void init()
    {
        addBiome(WARM, new BiomeEntry(OBSIDIAN, 5));
        addBiome(ICY, new BiomeEntry(ICE, 10));
        addBiome(ICY, new BiomeEntry(WINTER, 10));
        addBiome(WARM, new BiomeEntry(HUGE_ISLAND, 20));
        addBiome(WARM, new BiomeEntry(HAUNTED, 10));
        addBiome(WARM, new BiomeEntry(WOODLAND, 10));
        addBiome(WARM, new BiomeEntry(SHRUBLAND, 10));
        addBiome(DESERT, new BiomeEntry(DESERT_MOUNTAIN, 10));
        addBiome(WARM, new BiomeEntry(MEADOW, 10));
        addBiome(WARM, new BiomeEntry(GREEN_SWAMPLAND, 10));
        addBiome(WARM, new BiomeEntry(GREEN_HILLS, 10));
        addBiome(ICY, new BiomeEntry(GLACIER, 5));
        addBiome(WARM, new BiomeEntry(MANGROVE_SWAMP, 10));
        addBiome(WARM, new BiomeEntry(BAMBOO_FOREST, 20));
        addBiome(WARM, new BiomeEntry(EXTREME_JUNGLE, 10));
        addBiome(WARM, new BiomeEntry(AUTUMN_WOOD, 10));
        addBiome(WARM, new BiomeEntry(FORESTED_HILLS, 10));
        addBiome(DESERT, new BiomeEntry(RED_ROCK_MOUNTAIN, 10));
        addBiome(WARM, new BiomeEntry(TEMPORATE_RAINFOREST, 10));
        addBiome(WARM, new BiomeEntry(WASTELAND, 10));
    }
}