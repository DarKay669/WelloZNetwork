package fr.welloznetwork.wellozmod.init;

import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class RegistryAchievements
{
    public static final Achievement NEW_ADVENTURE = (new Achievement("achievement.newAdventure", "newAdventure", 0, 0, Items.map, (Achievement) null)).initIndependentStat().registerStat();
    public static final Achievement HERE_A_DJ = (new Achievement("achievement.hereADJ", "hereADJ", -2, 1, RegistryItems.music_disc_pigstep, NEW_ADVENTURE)).setSpecial().registerStat();
    public static final Achievement SHIELD = (new Achievement("achievement.shield", "shield", 1, 0, RegistryItems.shield, NEW_ADVENTURE)).registerStat();
    public static final Achievement NEW_ORE = (new Achievement("achievement.newOre", "newOre", 2, 0, RegistryBlocks.zarkonium_ore, SHIELD)).registerStat();
    public static final Achievement BAD_SWIM = (new Achievement("achievement.badSwim", "badSwim", 2, -2, RegistryItems.poison_water_bucket, NEW_ORE)).registerStat();
    public static final Achievement GOOD_SWIM = (new Achievement("achievement.goodSwim", "goodSwim", 3, -2, RegistryItems.turtle_helmet, NEW_ORE)).registerStat();
    public static final Achievement MORE_STORAGE = (new Achievement("achievement.moreStorage", "moreStorage", 3, 0, RegistryBlocks.emerald_chest, NEW_ORE)).registerStat();
    public static final Achievement HIDE_IN_THE_DEPTH = (new Achievement("achievement.hideInTheDeep", "hideInTheDeep", 5, 0, RegistryBlocks.ancient_debris, MORE_STORAGE)).registerStat();
    public static final Achievement WHAT_TO_DO_WITH_THIS = (new Achievement("achievement.whatToDoWithThis", "whatToDoWithThis", 6, 0, RegistryBlocks.smithing_table, HIDE_IN_THE_DEPTH)).registerStat();
    public static final Achievement COVER_ME_WITH_DEBRIS = (new Achievement("achievement.coverMeWithDebris", "coverMeWithDebris", 7, 0, RegistryItems.netherite_chestplate, WHAT_TO_DO_WITH_THIS)).setSpecial().registerStat();
    public static final Achievement LIGHT_AS_A_FEATHER = (new Achievement("achievement.lightAsAFeather", "lightAsAFeather", 7, 4, Items.feather, COVER_ME_WITH_DEBRIS)).registerStat();
    public static final Achievement TOMATO = (new Achievement("achievement.tomato", "tomato", 7, 2, RegistryItems.tomato, COVER_ME_WITH_DEBRIS)).registerStat();
    public static final Achievement SOMEONE_WATCHING_YOU = (new Achievement("achievement.someoneWatchingYou", "someoneWatchingYou", 7, -2, RegistryBlocks.observer, COVER_ME_WITH_DEBRIS)).registerStat();
    public static final Achievement BOUNCE_BOUNCE = (new Achievement("achievement.bounceBounce", "bounceBounce", 7, -4, RegistryBlocks.slime, COVER_ME_WITH_DEBRIS)).registerStat();
    public static final Achievement IT_SLIPS = (new Achievement("achievement.itSlips", "itSlips", 8, 4, RegistryItems.ice_skates, LIGHT_AS_A_FEATHER)).registerStat();
    public static final Achievement RECYCLING = (new Achievement("achievement.recycling", "recycling", 8, 2, RegistryBlocks.composter, TOMATO)).registerStat();
    public static final Achievement SOMEONE_REALLY_WATCHING_YOU = (new Achievement("achievement.someoneReallyWatchingYou", "someoneReallyWatchingYou", 8, -2, RegistryBlocks.online_detector, SOMEONE_WATCHING_YOU)).registerStat();
    public static final Achievement OUCH_THAT_BURN = (new Achievement("achievement.ouchThatBurn", "ouchThatBurn", 8, -4, RegistryBlocks.magma, BOUNCE_BOUNCE)).registerStat();
    public static final Achievement INVINCIBLE = (new Achievement("achievement.invincible", "invincible", 9, 4, RegistryItems.totem_of_undying, IT_SLIPS)).registerStat();
    public static final Achievement LITERALLY_DEAD = (new Achievement("achievement.literallyDead", "literallyDead", 9, -4, RegistryBlocks.slide_box, OUCH_THAT_BURN)).registerStat();
    public static final Achievement FLYING_PETER_PAN = (new Achievement("achievement.flyingPeterPan", "flyingPeterPan", 10, 1, RegistryItems.elytra, INVINCIBLE)).setSpecial().registerStat();
    public static final Achievement GREAT_VIEW_FROM_UP_HERE = (new Achievement("achievement.greatViewFromUpHere", "greatViewFromUpHere", 12, 0, RegistryItems.potionLevitation, FLYING_PETER_PAN)).registerStat();
        
    public static void init()
    {
        AchievementPage.registerAchievementPage(new AchievementPage("WelloZMod", NEW_ADVENTURE, HERE_A_DJ, SHIELD, BAD_SWIM, NEW_ORE, GOOD_SWIM, MORE_STORAGE, HIDE_IN_THE_DEPTH, WHAT_TO_DO_WITH_THIS, LIGHT_AS_A_FEATHER, TOMATO, COVER_ME_WITH_DEBRIS, SOMEONE_WATCHING_YOU, BOUNCE_BOUNCE, IT_SLIPS, RECYCLING, SOMEONE_REALLY_WATCHING_YOU, OUCH_THAT_BURN, INVINCIBLE, LITERALLY_DEAD, FLYING_PETER_PAN, GREAT_VIEW_FROM_UP_HERE));
    }
}