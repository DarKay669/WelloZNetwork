package fr.welloznetwork.wellozmod.common;

import java.lang.reflect.Field;
import java.util.HashMap;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

@Custom(ChestGenHooks.class)
public class CustomChestGenHooks
{
    public static void getChestGenHooksOverworld()
    {
        mineshaftCorridor();
        pyramidDesertChest();
        pyramidJungleChest();
        pyramidJungleDispenser();
        strongholdCorridor();
        strongholdLibrary();
        strongholdCrossing();
        villageBlacksmith();
        dungeonChest();
    }
    
    public static void getChestGenHooksNether()
    {
        netherFortress();
    }
    
    private static void netherFortress()
    {
        try
        {
            Field fortressWeightedField;
            Field chestInfo = ChestGenHooks.class.getDeclaredField("chestInfo");
            chestInfo.setAccessible(true);
            
            if (!((HashMap<String, ChestGenHooks>) chestInfo.get(null)).containsKey("netherFortress"))
            {
                fortressWeightedField = Class.forName("net.minecraft.world.gen.structure.StructureNetherBridgePieces$Piece").getDeclaredField("field_111019_a");
                fortressWeightedField.setAccessible(true);
                ((HashMap<String, ChestGenHooks>) chestInfo.get(null)).put("netherFortress", new ChestGenHooks("netherFortress", (WeightedRandomChestContent[]) fortressWeightedField.get(null), 2, 5));
            }
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        ChestGenHooks.addItem("netherFortress", new WeightedRandomChestContent(RegistryItems.music_disc_pigstep, 0, 1, 1, 5));
    }
    
    private static void mineshaftCorridor()
    {
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(RegistryItems.beetroot_seeds, 0, 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(RegistryItems.music_disc_calmos, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(RegistryItems.delta_stick, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.dye, 4, 4, 9, 5));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1));
    }
    
    private static void pyramidDesertChest()
    {
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(RegistryItems.beetroot_seeds, 0, 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(RegistryItems.totem_of_undying, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1));
    }
    
    private static void pyramidJungleChest()
    {
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(RegistryItems.beetroot_seeds, 0, 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(RegistryItems.music_disc_lava_ground, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1));
    }
    
    private static void pyramidJungleDispenser()
    {
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER, new WeightedRandomChestContent(Items.arrow, 0, 2, 7, 30));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER, new WeightedRandomChestContent(RegistryItems.music_disc_otherside, 0, 1, 1, 5));
    }
    
    private static void strongholdCorridor()
    {
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(RegistryItems.beetroot_seeds, 0, 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(RegistryItems.scute, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1));
    }
    
    private static void strongholdLibrary()
    {
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(RegistryItems.beetroot_seeds, 0, 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(Items.book, 0, 1, 3, 20));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(Items.map, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1));
    }
    
    private static void strongholdCrossing()
    {
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(RegistryItems.beetroot_seeds, 0, 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(RegistryItems.elytra, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1));
    }
    
    private static void villageBlacksmith()
    {        
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(RegistryItems.beetroot_seeds, 0, 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(RegistryItems.music_disc_relic, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Item.getItemFromBlock(RegistryBlocks.azalea_sapling), 0, 3, 7, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Item.getItemFromBlock(RegistryBlocks.cherry_sapling), 0, 3, 7, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Item.getItemFromBlock(RegistryBlocks.flowering_azalea_sapling), 0, 3, 7, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Item.getItemFromBlock(RegistryBlocks.mangrove_sapling), 0, 3, 7, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Item.getItemFromBlock(RegistryBlocks.winter_sapling), 0, 3, 7, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1));
    }
    
    private static void dungeonChest()
    {
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(RegistryItems.beetroot_seeds, 0, 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(RegistryItems.music_disc_mid_day, 0, 1, 1, 5));
        
        for (int i = 0; i < 1; i++)
        {
            ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(RegistryItems.ponder, 0, 1, 1, 1));
            ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(RegistryItems.sing, 0, 1, 1, 1));
            ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(RegistryItems.seek, 0, 1, 1, 1));
            ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(RegistryItems.feel, 0, 1, 1, 1));
            ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(RegistryItems.admire, 0, 1, 1, 1));
            ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(RegistryItems.call, 0, 1, 1, 1));
            ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(RegistryItems.yearn, 0, 1, 1, 1));
            ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(RegistryItems.dream, 0, 1, 1, 1));
        }
        
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.string, 0, 1, 4, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1));
    }
}