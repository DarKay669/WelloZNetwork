package fr.welloznetwork.wellozmod.init;

import static cpw.mods.fml.common.registry.EntityRegistry.findGlobalUniqueEntityId;
import static cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID;
import static cpw.mods.fml.common.registry.EntityRegistry.registerModEntity;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.registry.EntityRegistry;
import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.entity.block.EntityTNTPresent;
import fr.welloznetwork.wellozmod.entity.boss.EntityWhiter;
import fr.welloznetwork.wellozmod.entity.item.CustomEntityBoat;
import fr.welloznetwork.wellozmod.entity.monster.EntityAquaCreeper;
import fr.welloznetwork.wellozmod.entity.monster.EntityEndermite;
import fr.welloznetwork.wellozmod.entity.monster.EntityHerobrine;
import fr.welloznetwork.wellozmod.entity.monster.EntityStray;
import fr.welloznetwork.wellozmod.entity.monster.haunted.EntityGhost;
import fr.welloznetwork.wellozmod.entity.monster.huge.EntityHugeCreeper;
import fr.welloznetwork.wellozmod.entity.monster.huge.EntityHugeSkeleton;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowCreeper;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowEnderman;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowSlime;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowSpider;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowWitch;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowZombie;
import fr.welloznetwork.wellozmod.entity.passive.EntityElf;
import fr.welloznetwork.wellozmod.entity.passive.EntityFox;
import fr.welloznetwork.wellozmod.entity.passive.EntityRabbit;
import fr.welloznetwork.wellozmod.entity.passive.EntityTravelingVillager;
import fr.welloznetwork.wellozmod.entity.passive.haunted.EntitySuspiciousGirl;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeBat;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeChicken;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeCow;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeHorse;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeMooshroom;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeOcelot;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugePig;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeRabbit;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeSheep;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeWolf;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntityPenguin;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowBat;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowChicken;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowCow;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowHorse;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowOcelot;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowPig;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowRabbit;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowVillager;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowWolf;
import fr.welloznetwork.wellozmod.entity.projectile.EntityArrowTipped;
import fr.welloznetwork.wellozmod.entity.projectile.EntitySplashPotion;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class RegistryEntity
{
    public static void init()
    {
        registerGlobalEntityID(EntityHerobrine.class, "Herobrine", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityPenguin.class, "Penguin", findGlobalUniqueEntityId());
        registerModEntity(EntitySplashPotion.class, "EntitySplashPotion", findGlobalUniqueEntityId(), WelloZMod.instance, 80, 3, true);
        registerGlobalEntityID(EntitySnowChicken.class, "SnowChicken", findGlobalUniqueEntityId(), 10592673, 14021365);
        registerGlobalEntityID(EntitySnowCow.class, "SnowCow", findGlobalUniqueEntityId(), 4470310, 14021365);
        registerGlobalEntityID(EntitySnowCreeper.class, "SnowCreeper", findGlobalUniqueEntityId(), 894731, 14021365);
        registerGlobalEntityID(EntitySnowHorse.class, "SnowHorse", findGlobalUniqueEntityId(), 12623485, 14021365);
        registerGlobalEntityID(EntitySnowOcelot.class, "SnowOcelot", findGlobalUniqueEntityId(), 15720061, 14021365);
        registerGlobalEntityID(EntitySnowPig.class, "SnowPig", findGlobalUniqueEntityId(), 15771042, 14021365);
        registerGlobalEntityID(EntitySnowWolf.class, "SnowWolf", findGlobalUniqueEntityId(), 14144467, 14021365);
        registerGlobalEntityID(EntitySnowZombie.class, "SnowZombie", findGlobalUniqueEntityId(), 44975, 14021365);
        registerGlobalEntityID(EntitySnowRabbit.class, "SnowRabbit", findGlobalUniqueEntityId(), 10051392, 14021365);
        registerGlobalEntityID(EntityElf.class, "Elf", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeBat.class, "HugeBat", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeChicken.class, "HugeChicken", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeCow.class, "HugeCow", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeHorse.class, "HugeHorse", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeMooshroom.class, "HugeMooshroom", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeOcelot.class, "HugeOcelot", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugePig.class, "HugePig", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeSheep.class, "HugeSheep", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeWolf.class, "HugeWolf", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeCreeper.class, "HugeCreeper", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeSkeleton.class, "HugeSkeleton", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityHugeRabbit.class, "HugeRabbit", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntitySnowVillager.class, "SnowVillager", findGlobalUniqueEntityId(), 5651507, 14021365);
        registerGlobalEntityID(EntitySuspiciousGirl.class, "SuspiciousGirl", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityGhost.class, "Ghost", findGlobalUniqueEntityId());
        registerGlobalEntityID(EntityRabbit.class, "Rabbit", findGlobalUniqueEntityId(), 10051392, 7555121);
        registerModEntity(CustomEntityBoat.class, "CustomBoat", findGlobalUniqueEntityId(), WelloZMod.instance, 128, 5, true);
        registerGlobalEntityID(EntityEndermite.class, "Endermite", findGlobalUniqueEntityId(), 0x161616, 0x6E6E6E);
        registerGlobalEntityID(EntityStray.class, "Stray", findGlobalUniqueEntityId(), 0x617778, 0xE6EAEA);
        registerModEntity(EntityArrowTipped.class, "ArrowTipped", findGlobalUniqueEntityId(), WelloZMod.instance, 64, 20, true);
        registerGlobalEntityID(EntitySnowEnderman.class, "SnowEnderman", findGlobalUniqueEntityId(), 1447446, 14021365);
        registerGlobalEntityID(EntitySnowSlime.class, "SnowSlime", findGlobalUniqueEntityId(), 5349438, 14021365);
        registerGlobalEntityID(EntitySnowSpider.class, "SnowSpider", findGlobalUniqueEntityId(), 3419431, 14021365);
        registerGlobalEntityID(EntitySnowWitch.class, "SnowWitch", findGlobalUniqueEntityId(), 3407872, 14021365);
        registerGlobalEntityID(EntitySnowBat.class, "SnowBat", findGlobalUniqueEntityId(), 4996656, 14021365);
        registerGlobalEntityID(EntityTravelingVillager.class, "TravelingVillager", findGlobalUniqueEntityId(), 5651507, 14021365);
        registerModEntity(EntityTNTPresent.class, "TNTPresent", findGlobalUniqueEntityId(), WelloZMod.instance, 160, 20, true);
        
        if (Constants.V2)
        {
            registerGlobalEntityID(EntityAquaCreeper.class, "AquaCreeper", findGlobalUniqueEntityId(), 4489885, 10475728);
            registerGlobalEntityID(EntityWhiter.class, "Whiter", findGlobalUniqueEntityId());
            registerGlobalEntityID(EntityFox.class, "Fox", findGlobalUniqueEntityId(), 14383139, 3026478);
            
            BiomeDictionary.registerAllBiomes();
            BiomeGenBase[] biomes = BiomeDictionary.getBiomesForType(Type.OCEAN);
            
            for (int i = 0; i < biomes.length; i++)
            {
                biomes[i].getSpawnableList(EnumCreatureType.waterCreature).add(new SpawnListEntry(EntityAquaCreeper.class, 5, 1, 1));
            }
        }
        
        EntityRegistry.addSpawn(EntityRabbit.class, 5, 3, 3, EnumCreatureType.creature, BiomeDictionary.getBiomesForType(Type.SANDY));
        ArrayUtils.addAll(BiomeDictionary.getBiomesForType(Type.SNOWY), BiomeDictionary.getBiomesForType(Type.PLAINS));
        ArrayUtils.addAll(BiomeDictionary.getBiomesForType(Type.SNOWY), BiomeDictionary.getBiomesForType(Type.FOREST));
        EntityRegistry.addSpawn(EntityRabbit.class, 10, 3, 3, EnumCreatureType.creature, BiomeDictionary.getBiomesForType(Type.SNOWY));
        EntityRegistry.addSpawn(EntityStray.class, 80, 4, 4, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.SNOWY));
    }
}