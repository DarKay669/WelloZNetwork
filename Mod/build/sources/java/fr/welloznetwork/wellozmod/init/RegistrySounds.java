package fr.welloznetwork.wellozmod.init;

import java.util.Random;

import net.minecraft.block.Block.SoundType;

public class RegistrySounds
{
    private static final Random RANDOM = new Random();
    
    /*
     * Items
     */
    public static final String SOUND_TYPE_GOAT_HORN_PONDER = "wellozmod:item.goat_horn.ponder";
    public static final String SOUND_TYPE_GOAT_HORN_SING = "wellozmod:item.goat_horn.sing";
    public static final String SOUND_TYPE_GOAT_HORN_SEEK = "wellozmod:item.goat_horn.seek";
    public static final String SOUND_TYPE_GOAT_HORN_FEEL = "wellozmod:item.goat_horn.feel";
    public static final String SOUND_TYPE_GOAT_HORN_ADMIRE = "wellozmod:item.goat_horn.admire";
    public static final String SOUND_TYPE_GOAT_HORN_CALL = "wellozmod:item.goat_horn.call";
    public static final String SOUND_TYPE_GOAT_HORN_YEARN = "wellozmod:item.goat_horn.yearn";
    public static final String SOUND_TYPE_GOAT_HORN_DREAM = "wellozmod:item.goat_horn.dream";
    public static final String SOUND_TYPE_TOTEM = "wellozmod:item.totem.use";
    public static final String[] ITEM_SHIELD_BLOCK = {"wellozmod:item.shield.block1", "wellozmod:item.shield.block2", "wellozmod:item.shield.block3", "wellozmod:item.shield.block4", "wellozmod:item.shield.block5"};
    public static final String SOUND_TYPE_SPYGLASS_USE = "wellozmod:item.spyglass.use";
    public static final String SOUND_TYPE_SPYGLASS_STOP = "wellozmod:item.spyglass.stop";
    public static final String SOUND_TYPE_TRIDENT_THROW = "wellozmod:item.trident.throw";
    public static final String SOUND_TYPE_TRIDENT_THUNDER = "wellozmod:item.trident.thunder";
    public static final String[] ITEM_ARMOR_EQUIP_NETHERITE = {"wellozmod:item.armor.equip_netherite1", "wellozmod:item.armor.equip_netherite2", "wellozmod:item.armor.equip_netherite3", "wellozmod:item.armor.equip_netherite4"}; 
    public static final String[] SOUND_TYPE_BOTTLE_FILL = {"wellozmod:item.bottle.fill_dragonbreath1", "wellozmod:item.bottle.fill_dragonbreath2"};
    public static final String SOUND_TYPE_ELYTRA_FLYING = "wellozmod:item.elytra.elytra_loop";
    public static final String[] ITEM_ARMOR_EQUIP_CHAIN = {"wellozmod:item.armor.equip_chain1", "wellozmod:item.armor.equip_chain2", "wellozmod:item.armor.equip_chain3", "wellozmod:item.armor.equip_chain4", "wellozmod:item.armor.equip_chain5", "wellozmod:item.armor.equip_chain6"};
    public static final String[] ITEM_ARMOR_EQUIP_DIAMOND = {"wellozmod:item.armor.equip_diamond1", "wellozmod:item.armor.equip_diamond2", "wellozmod:item.armor.equip_diamond3", "wellozmod:item.armor.equip_diamond4", "wellozmod:item.armor.equip_diamond5", "wellozmod:item.armor.equip_diamond6"};
    public static final String[] ITEM_ARMOR_EQUIP_GENERIC = {"wellozmod:item.armor.equip_generic1", "wellozmod:item.armor.equip_generic2", "wellozmod:item.armor.equip_generic3", "wellozmod:item.armor.equip_generic4", "wellozmod:item.armor.equip_generic5", "wellozmod:item.armor.equip_generic6"};
    public static final String[] ITEM_ARMOR_EQUIP_GOLD = {"wellozmod:item.armor.equip_gold1", "wellozmod:item.armor.equip_gold2", "wellozmod:item.armor.equip_gold3", "wellozmod:item.armor.equip_gold4", "wellozmod:item.armor.equip_gold5", "wellozmod:item.armor.equip_gold6"};
    public static final String[] ITEM_ARMOR_EQUIP_IRON = {"wellozmod:item.armor.equip_iron1", "wellozmod:item.armor.equip_iron2", "wellozmod:item.armor.equip_iron3", "wellozmod:item.armor.equip_iron4", "wellozmod:item.armor.equip_iron5", "wellozmod:item.armor.equip_iron6"};
    public static final String[] ITEM_ARMOR_EQUIP_LEATHER = {"wellozmod:item.armor.equip_leather1", "wellozmod:item.armor.equip_leather2", "wellozmod:item.armor.equip_leather3", "wellozmod:item.armor.equip_leather4", "wellozmod:item.armor.equip_leather5", "wellozmod:item.armor.equip_leather6"};
    
    /*
     * Blocks
     */
    private static final String[] soundTypeAncientDebris = {"wellozmod:block.ancient_debris.break1", "wellozmod:block.ancient_debris.break2", "wellozmod:block.ancient_debris.break3", "wellozmod:block.ancient_debris.break4", "wellozmod:block.ancient_debris.break5"};
    public static final SoundType SOUND_TYPE_ANCIENT_DEBRIS = new SoundType("stone", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeAncientDebris[RANDOM.nextInt(soundTypeAncientDebris.length)];
        }
    };
    
    private static final String[] soundTypeBambooBreak = {"wellozmod:block.bamboo.break1", "wellozmod:block.bamboo.break2", "wellozmod:block.bamboo.break3", "wellozmod:block.bamboo.break4", "wellozmod:block.bamboo.break5"};
    private static final String[] soundTypeBambooStep = {"wellozmod:block.bamboo.step1", "wellozmod:block.bamboo.step2", "wellozmod:block.bamboo.step3", "wellozmod:block.bamboo.step4", "wellozmod:block.bamboo.step5", "wellozmod:block.bamboo.step6"};
    private static final String[] soundTypeBambooPlace = {"wellozmod:block.bamboo.place1", "wellozmod:block.bamboo.place2", "wellozmod:block.bamboo.place3", "wellozmod:block.bamboo.place4", "wellozmod:block.bamboo.place5", "wellozmod:block.bamboo.place6"};
    public static final SoundType SOUND_TYPE_BAMBOO = new SoundType("grass", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeBambooBreak[RANDOM.nextInt(soundTypeBambooBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeBambooStep[RANDOM.nextInt(soundTypeBambooStep.length)];
        }
        
        public String func_150496_b()
        {
            return soundTypeBambooPlace[RANDOM.nextInt(soundTypeBambooPlace.length)];
        }
    };
    
    public static final String[] SOUND_TYPE_BARREL_OPEN = {"wellozmod:block.barrel.open1", "wellozmod:block.barrel.open2"};
    public static final String SOUND_TYPE_BARREL_CLOSE = "wellozmod:block.barrel.close";
    
    private static final String[] soundTypeBasaltBreak = {"wellozmod:block.basalt.break1", "wellozmod:block.basalt.break2", "wellozmod:block.basalt.break3", "wellozmod:block.basalt.break4", "wellozmod:block.basalt.break5"};
    private static final String[] soundTypeBasaltStep = {"wellozmod:block.basalt.step1", "wellozmod:block.basalt.step2", "wellozmod:block.basalt.step3", "wellozmod:block.basalt.step4", "wellozmod:block.basalt.step5", "wellozmod:block.basalt.step6"};
    public static final SoundType SOUND_TYPE_BASALT = new SoundType("stone", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeBasaltBreak[RANDOM.nextInt(soundTypeBasaltBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeBasaltStep[RANDOM.nextInt(soundTypeBasaltStep.length)];
        }
    };
    
    private static final String[] soundTypeCalciteBreak = {"wellozmod:block.calcite.break1", "wellozmod:block.calcite.break2", "wellozmod:block.calcite.break3", "wellozmod:block.calcite.break4"};
    private static final String[] soundTypeCalciteStep = {"wellozmod:block.calcite.step1", "wellozmod:block.calcite.step2", "wellozmod:block.calcite.step3", "wellozmod:block.calcite.step4", "wellozmod:block.calcite.step5", "wellozmod:block.calcite.step6"};
    private static final String[] soundTypeCalcitePlace = {"wellozmod:block.calcite.place1", "wellozmod:block.calcite.place2", "wellozmod:block.calcite.place3", "wellozmod:block.calcite.place4"};
    public static final SoundType SOUND_TYPE_CALCITE = new SoundType("stone", 1.0F, 1.0F) 
    {
        public String getBreakSound()
        {
            return soundTypeCalciteBreak[RANDOM.nextInt(soundTypeCalciteBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeCalciteStep[RANDOM.nextInt(soundTypeCalciteStep.length)];
        }
        
        public String func_150496_b()
        {
            return soundTypeCalcitePlace[RANDOM.nextInt(soundTypeCalcitePlace.length)];
        }
    };
    
    private static final String[] soundTypeCherryWoodBreak = {"wellozmod:block.cherry_wood.break1", "wellozmod:block.cherry_wood.break2", "wellozmod:block.cherry_wood.break3", "wellozmod:block.cherry_wood.break4", "wellozmod:block.cherry_wood.break5"};
    private static final String[] soundTypeCherryWoodStep = {"wellozmod:block.cherry_wood.step1", "wellozmod:block.cherry_wood.step2", "wellozmod:block.cherry_wood.step3", "wellozmod:block.cherry_wood.step4", "wellozmod:block.cherry_wood.step5", "wellozmod:block.cherry_wood.step6"};
    public static final SoundType SOUND_TYPE_CHERRY_WOOD = new SoundType("wood", 1.0F, 1.0F) 
    {
        public String getBreakSound()
        {
            return soundTypeCherryWoodBreak[RANDOM.nextInt(soundTypeCherryWoodBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeCherryWoodStep[RANDOM.nextInt(soundTypeCherryWoodStep.length)];
        }
    };
    
    private static final String[] soundTypeChorusFlowerDeath = {"wellozmod:block.chorus_flower.death1", "wellozmod:block.chorus_flower.death2", "wellozmod:block.chorus_flower.death3"};
    public static final String[] soundTypeChorusFlowerGrow = {"wellozmod:block.chorus_flower.grow1", "wellozmod:block.chorus_flower.grow2", "wellozmod:block.chorus_flower.grow3", "wellozmod:block.chorus_flower.grow4"};
    public static final SoundType SOUND_TYPE_CHORUS_FLOWER = new SoundType("wood", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeChorusFlowerDeath[RANDOM.nextInt(soundTypeChorusFlowerDeath.length)];
        }
    };
    
    private static final String[] soundTypeCobbledDeepslatePlace = {"wellozmod:block.deepslate_bricks.place1", "wellozmod:block.deepslate_bricks.place2", "wellozmod:block.deepslate_bricks.place3", "wellozmod:block.deepslate_bricks.place4", "wellozmod:block.deepslate_bricks.place5", "wellozmod:block.deepslate_bricks.place6"};
    private static final String[] soundTypeCobbledDeepslateStep = {"wellozmod:block.deepslate_bricks.step1", "wellozmod:block.deepslate_bricks.step2", "wellozmod:block.deepslate_bricks.step3", "wellozmod:block.deepslate_bricks.step4", "wellozmod:block.deepslate_bricks.step5"};
    public static final SoundType SOUND_TYPE_COBBLED_DEEPSLATE = new SoundType("stone", 1.0F, 1.0F) 
    {
        public String getBreakSound()
        {
            return soundTypeDeepslateBreak[RANDOM.nextInt(soundTypeDeepslateBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeCobbledDeepslateStep[RANDOM.nextInt(soundTypeCobbledDeepslateStep.length)];
        }
        
        public String func_150496_b()
        {
            return soundTypeCobbledDeepslatePlace[RANDOM.nextInt(soundTypeCobbledDeepslatePlace.length)];
        }
    };
    
    public static final String[] soundTypeComposterEmpty = {"wellozmod:block.composter.empty1", "wellozmod:block.composter.empty2", "wellozmod:block.composter.empty3"};
    public static final String[] soundTypeComposterFill = {"wellozmod:block.composter.fill1", "wellozmod:block.composter.fill2", "wellozmod:block.composter.fill3", "wellozmod:block.composter.fill4"};
    public static final String[] soundTypeComposterFillSuccess = {"wellozmod:block.composter.fill_success1", "wellozmod:block.composter.fill_success2", "wellozmod:block.composter.fill_success3", "wellozmod:block.composter.fill_success4"};
    public static final String[] soundTypeComposterReady = {"wellozmod:block.composter.ready1", "wellozmod:block.composter.ready2", "wellozmod:block.composter.ready3", "wellozmod:block.composter.ready4"};
    
    private static final String[] soundTypeCoralBreak = {"wellozmod:dig.coral1", "wellozmod:dig.coral2", "wellozmod:dig.coral3", "wellozmod:dig.coral4"};
    private static final String[] soundTypeCoralStep = {"wellozmod:step.coral1", "wellozmod:step.coral2", "wellozmod:step.coral3", "wellozmod:step.coral4", "wellozmod:step.coral5", "wellozmod:step.coral6"};
    public static final SoundType SOUND_TYPE_CORAL = new SoundType("stone", 1.0F, 1.0F) 
    {
        public String getBreakSound()
        {
            return soundTypeCoralBreak[RANDOM.nextInt(soundTypeCoralBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeCoralStep[RANDOM.nextInt(soundTypeCoralStep.length)];
        }
    };
    
    private static final String[] soundTypeFungusBreak = {"wellozmod:block.fungus.break1", "wellozmod:block.fungus.break2", "wellozmod:block.fungus.break3", "wellozmod:block.fungus.break4", "wellozmod:block.fungus.break5", "wellozmod:block.fungus.break6"};
    public static final SoundType SOUND_TYPE_FUNGUS = new SoundType("grass", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeFungusBreak[RANDOM.nextInt(soundTypeFungusBreak.length)];
        }
    };
    
    private static final String[] soundTypeNyliumBreak = {"wellozmod:block.nylium.break1", "wellozmod:block.nylium.break2", "wellozmod:block.nylium.break3", "wellozmod:block.nylium.break4", "wellozmod:block.nylium.break5", "wellozmod:block.nylium.break6"};
    private static final String[] soundTypeNyliumStep = {"wellozmod:block.nylium.step1", "wellozmod:block.nylium.step2", "wellozmod:block.nylium.step3", "wellozmod:block.nylium.step4", "wellozmod:block.nylium.step5", "wellozmod:block.nylium.step6"};
    public static final SoundType SOUND_TYPE_NYLIUM = new SoundType("grass", 1.0F, 1.0F) 
    {
        public String getBreakSound()
        {
            return soundTypeNyliumBreak[RANDOM.nextInt(soundTypeNyliumBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeNyliumStep[RANDOM.nextInt(soundTypeNyliumStep.length)];
        }
    };
    
    private static final String[] soundTypeDeepslateBreak = {"wellozmod:block.deepslate.break1", "wellozmod:block.deepslate.break2", "wellozmod:block.deepslate.break3", "wellozmod:block.deepslate.break4"};
    private static final String[] soundTypeDeepslateStep = {"wellozmod:block.deepslate.step1", "wellozmod:block.deepslate.step2", "wellozmod:block.deepslate.step3", "wellozmod:block.deepslate.step4", "wellozmod:block.deepslate.step5", "wellozmod:block.deepslate.step6"};
    private static final String[] soundTypeDeepslatePlace = {"wellozmod:block.deepslate.place1", "wellozmod:block.deepslate.place2", "wellozmod:block.deepslate.place3", "wellozmod:block.deepslate.place4", "wellozmod:block.deepslate.place5", "wellozmod:block.deepslate.place6"};
    public static final SoundType SOUND_TYPE_DEEPSLATE = new SoundType("stone", 1.0F, 1.0F) 
    {
        public String getBreakSound()
        {
            return soundTypeDeepslateBreak[RANDOM.nextInt(soundTypeDeepslateBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeDeepslateStep[RANDOM.nextInt(soundTypeDeepslateStep.length)];
        }
        
        public String func_150496_b()
        {
            return soundTypeDeepslatePlace[RANDOM.nextInt(soundTypeDeepslatePlace.length)];
        }
    };
    
    private static final String[] soundTypeDripstoneBreak = {"wellozmod:block.dripstone.break1", "wellozmod:block.dripstone.break2", "wellozmod:block.dripstone.break3", "wellozmod:block.dripstone.break4", "wellozmod:block.dripstone.break5"};
    private static final String[] soundTypeDripstoneStep = {"wellozmod:block.dripstone.step1", "wellozmod:block.dripstone.step2", "wellozmod:block.dripstone.step3", "wellozmod:block.dripstone.step4", "wellozmod:block.dripstone.step5", "wellozmod:block.dripstone.step6"};
    public static final SoundType SOUND_TYPE_DRIPSTONE = new SoundType("stone", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeDripstoneBreak[RANDOM.nextInt(soundTypeDripstoneBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeDripstoneStep[RANDOM.nextInt(soundTypeDripstoneStep.length)];
        }
    };
    
    private static final String[] soundTypeMangroveRootsBreak = {"wellozmod:block.mangrove_roots.break1", "wellozmod:block.mangrove_roots.break2", "wellozmod:block.mangrove_roots.break3", "wellozmod:block.mangrove_roots.break4", "wellozmod:block.mangrove_roots.break5", "wellozmod:block.mangrove_roots.break6"};
    private static final String[] soundTypeMangroveRootsStep = {"wellozmod:block.mangrove_roots.step1", "wellozmod:block.mangrove_roots.step2", "wellozmod:block.mangrove_roots.step3", "wellozmod:block.mangrove_roots.step4", "wellozmod:block.mangrove_roots.step5", "wellozmod:block.mangrove_roots.step6"};
    public static final SoundType SOUND_TYPE_MANGROVE_ROOTS = new SoundType("grass", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeMangroveRootsBreak[RANDOM.nextInt(soundTypeMangroveRootsBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeMangroveRootsStep[RANDOM.nextInt(soundTypeMangroveRootsStep.length)];
        }
    };
    
    private static final String[] soundTypeMossBreak = {"wellozmod:block.moss.break1", "wellozmod:block.moss.break2", "wellozmod:block.moss.break3", "wellozmod:block.moss.break4", "wellozmod:block.moss.break5"};
    private static final String[] soundTypeMossStep = {"wellozmod:block.moss.step1", "wellozmod:block.moss.step2", "wellozmod:block.moss.step3", "wellozmod:block.moss.step4", "wellozmod:block.moss.step5", "wellozmod:block.moss.step6"};
    public static final SoundType SOUND_TYPE_MOSS = new SoundType("grass", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeMossBreak[RANDOM.nextInt(soundTypeMossBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeMossStep[RANDOM.nextInt(soundTypeMossStep.length)];
        }
    };
    
    private static final String[] soundTypeMudBreak = {"wellozmod:block.mud.break1", "wellozmod:block.mud.break2", "wellozmod:block.mud.break3", "wellozmod:block.mud.break4", "wellozmod:block.mud.break5"};
    private static final String[] soundTypeMudStep = {"wellozmod:block.mud.step1", "wellozmod:block.mud.step2", "wellozmod:block.mud.step3", "wellozmod:block.mud.step4", "wellozmod:block.mud.step5", "wellozmod:block.mud.step6"};
    public static final SoundType SOUND_TYPE_MUD = new SoundType("grass", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeMudBreak[RANDOM.nextInt(soundTypeMudBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeMudStep[RANDOM.nextInt(soundTypeMudStep.length)];
        }
    };
    
    private static final String[] soundTypeMuddyMangroveRootsBreak = {"wellozmod:block.muddy_mangrove_roots.break1", "wellozmod:block.muddy_mangrove_roots.break2", "wellozmod:block.muddy_mangrove_roots.break3", "wellozmod:block.muddy_mangrove_roots.break4", "wellozmod:block.muddy_mangrove_roots.break5", "wellozmod:block.muddy_mangrove_roots.break6"};
    private static final String[] soundTypeMuddyMangroveRootsStep = {"wellozmod:block.muddy_mangrove_roots.step1", "wellozmod:block.muddy_mangrove_roots.step2", "wellozmod:block.muddy_mangrove_roots.step3", "wellozmod:block.muddy_mangrove_roots.step4", "wellozmod:block.muddy_mangrove_roots.step5", "wellozmod:block.muddy_mangrove_roots.step6"};
    public static final SoundType SOUND_TYPE_MUDDY_MANGROVE_ROOTS = new SoundType("grass", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeMuddyMangroveRootsBreak[RANDOM.nextInt(soundTypeMuddyMangroveRootsBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeMuddyMangroveRootsStep[RANDOM.nextInt(soundTypeMuddyMangroveRootsStep.length)];
        }
    };
    
    private static final String[] soundTypeNetheriteBreak = {"wellozmod:block.netherite.break1", "wellozmod:block.netherite.break2", "wellozmod:block.netherite.break3", "wellozmod:block.netherite.break4"};
    private static final String[] soundTypeNetheriteStep = {"wellozmod:block.netherite.step1", "wellozmod:block.netherite.step2", "wellozmod:block.netherite.step3", "wellozmod:block.netherite.step4", "wellozmod:block.netherite.step5", "wellozmod:block.netherite.step6"};
    public static final SoundType SOUND_TYPE_NETHERITE = new SoundType("stone", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeNetheriteBreak[RANDOM.nextInt(soundTypeNetheriteBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeNetheriteStep[RANDOM.nextInt(soundTypeNetheriteStep.length)];
        }
    };
    
    private static final String[] soundTypeNetherSproutsBreak = {"wellozmod:block.nether_sprout.break1", "wellozmod:block.nether_sprout.break2", "wellozmod:block.nether_sprout.break3", "wellozmod:block.nether_sprout.break4"};
    private static final String[] soundTypeNetherSproutsStep = {"wellozmod:block.nether_sprout.step1", "wellozmod:block.nether_sprout.step2", "wellozmod:block.nether_sprout.step3", "wellozmod:block.nether_sprout.step4", "wellozmod:block.nether_sprout.step5"};
    public static final SoundType SOUND_TYPE_NETHER_SPROUTS = new SoundType("stone", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeNetherSproutsBreak[RANDOM.nextInt(soundTypeNetherSproutsBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeNetherSproutsStep[RANDOM.nextInt(soundTypeNetherSproutsStep.length)];
        }
    };
    
    private static final String[] soundTypeFroglightBreak = {"wellozmod:block.froglight.break1", "wellozmod:block.froglight.break2", "wellozmod:block.froglight.break3", "wellozmod:block.froglight.break4"};
    private static final String[] soundTypeFroglightStep = {"wellozmod:block.froglight.step1", "wellozmod:block.froglight.step2", "wellozmod:block.froglight.step3", "wellozmod:block.froglight.step4", "wellozmod:block.froglight.step5", "wellozmod:block.froglight.step6"};
    public static final SoundType SOUND_TYPE_FROGLIGHT = new SoundType("stone", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeFroglightBreak[RANDOM.nextInt(soundTypeFroglightBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeFroglightStep[RANDOM.nextInt(soundTypeFroglightStep.length)];
        }
    };
    
    private static final String[] soundTypeShroomlightBreak = {"wellozmod:block.shroomlight.break1", "wellozmod:block.shroomlight.break2", "wellozmod:block.shroomlight.break3", "wellozmod:block.shroomlight.break4", "wellozmod:block.shroomlight.break5"};
    private static final String[] soundTypeShroomlightStep = {"wellozmod:block.shroomlight.step1", "wellozmod:block.shroomlight.step2", "wellozmod:block.shroomlight.step3", "wellozmod:block.shroomlight.step4", "wellozmod:block.shroomlight.step5", "wellozmod:block.shroomlight.step6"};
    public static final SoundType SOUND_TYPE_SHROOMLIGHT = new SoundType("stone", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeShroomlightBreak[RANDOM.nextInt(soundTypeShroomlightBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeShroomlightStep[RANDOM.nextInt(soundTypeShroomlightStep.length)];
        }
    };
    
    public static final String[] SOUND_TYPE_SMITHING_TABLE_USE = {"wellozmod:block.smithing_table.smithing_table1", "wellozmod:block.smithing_table.smithing_table2", "wellozmod:block.smithing_table.smithing_table3"};
    
    private static final String[] soundTypeSoulSoilBreak = {"wellozmod:block.soul_soil.break1", "wellozmod:block.soul_soil.break2", "wellozmod:block.soul_soil.break3", "wellozmod:block.soul_soil.break4", "wellozmod:block.soul_soil.break5", "wellozmod:block.soul_soil.break6"};
    private static final String[] soundTypeSoulSoilStep = {"wellozmod:block.soul_soil.step1", "wellozmod:block.soul_soil.step2", "wellozmod:block.soul_soil.step3", "wellozmod:block.soul_soil.step4", "wellozmod:block.soul_soil.step5"};
    public static final SoundType SOUND_TYPE_SOUL_SOIL = new SoundType("stone", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeSoulSoilBreak[RANDOM.nextInt(soundTypeSoulSoilBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeSoulSoilStep[RANDOM.nextInt(soundTypeSoulSoilStep.length)];
        }
    };
    
    private static final String[] soundTypeSporeBlossomBreak = {"wellozmod:block.spore_blossom.break1", "wellozmod:block.spore_blossom.break2", "wellozmod:block.spore_blossom.break3", "wellozmod:block.spore_blossom.break4", "wellozmod:block.spore_blossom.break5"};
    private static final String[] soundTypeSporeBlossomStep = {"wellozmod:block.spore_blossom.step1", "wellozmod:block.spore_blossom.step2", "wellozmod:block.spore_blossom.step3", "wellozmod:block.spore_blossom.step4", "wellozmod:block.spore_blossom.step5", "wellozmod:block.spore_blossom.step6"};
    public static final SoundType SOUND_TYPE_SPORE_BLOSSOM = new SoundType("grass", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeSporeBlossomBreak[RANDOM.nextInt(soundTypeSporeBlossomBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeSporeBlossomStep[RANDOM.nextInt(soundTypeSporeBlossomStep.length)];
        }
    };
    
    private static final String[] soundTypeSweetBerryBushBreak = {"wellozmod:block.sweet_berry_bush.break1", "wellozmod:block.sweet_berry_bush.break2", "wellozmod:block.sweet_berry_bush.break3", "wellozmod:block.sweet_berry_bush.break4"};
    private static final String[] soundTypSweetBerryBushPlace = {"wellozmod:block.sweet_berry_bush.place1", "wellozmod:block.sweet_berry_bush.place2", "wellozmod:block.sweet_berry_bush.place3", "wellozmod:block.sweet_berry_bush.place4", "wellozmod:block.sweet_berry_bush.place5", "wellozmod:block.sweet_berry_bush.place6"};
    public static final SoundType SOUND_TYPE_SWEET_BERRY_BUSH = new SoundType("grass", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeSweetBerryBushBreak[RANDOM.nextInt(soundTypeSweetBerryBushBreak.length)];
        }
        
        public String func_150496_b()
        {
            return soundTypSweetBerryBushPlace[RANDOM.nextInt(soundTypSweetBerryBushPlace.length)];
        }
    };
    
    private static final String[] soundTypeTuffBreak = {"wellozmod:block.tuff.break1", "wellozmod:block.tuff.break2", "wellozmod:block.tuff.break3", "wellozmod:block.tuff.break4", "wellozmod:block.tuff.break5"};
    private static final String[] soundTypeTuffStep = {"wellozmod:block.tuff.step1", "wellozmod:block.tuff.step2", "wellozmod:block.tuff.step3", "wellozmod:block.tuff.step4", "wellozmod:block.tuff.step5", "wellozmod:block.tuff.step6"};
    public static final SoundType SOUND_TYPE_TUFF = new SoundType("stone", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeTuffBreak[RANDOM.nextInt(soundTypeTuffBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeTuffStep[RANDOM.nextInt(soundTypeTuffStep.length)];
        }
    };
    
    private static final String[] soundTypeAzaleaLeavesBreak = {"wellozmod:block.azalea_leaves.break1", "wellozmod:block.azalea_leaves.break2", "wellozmod:block.azalea_leaves.break3", "wellozmod:block.azalea_leaves.break4", "wellozmod:block.azalea_leaves.break5", "wellozmod:block.azalea_leaves.break6", "wellozmod:block.azalea_leaves.break7"};
    private static final String[] soundTypeAzaleaLeavesStep = {"wellozmod:block.azalea_leaves.step1", "wellozmod:block.azalea_leaves.step2", "wellozmod:block.azalea_leaves.step3", "wellozmod:block.azalea_leaves.step4", "wellozmod:block.azalea_leaves.step5"};
    public static final SoundType SOUND_TYPE_AZALEA_LEAVES = new SoundType("grass", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeAzaleaLeavesBreak[RANDOM.nextInt(soundTypeAzaleaLeavesBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeAzaleaLeavesStep[RANDOM.nextInt(soundTypeAzaleaLeavesStep.length)];
        }
    };
    
    private static final String[] soundTypeCherryLeavesBreak = {"wellozmod:block.cherry_leaves.break1", "wellozmod:block.cherry_leaves.break2", "wellozmod:block.cherry_leaves.break3", "wellozmod:block.cherry_leaves.break4", "wellozmod:block.cherry_leaves.break5"};
    private static final String[] soundTypeCherryLeavesStep = {"wellozmod:block.cherry_leaves.step1", "wellozmod:block.cherry_leaves.step2", "wellozmod:block.cherry_leaves.step3", "wellozmod:block.cherry_leaves.step4", "wellozmod:block.cherry_leaves.step5"};
    public static final SoundType SOUND_TYPE_CHERRY_LEAVES = new SoundType("grass", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return soundTypeCherryLeavesBreak[RANDOM.nextInt(soundTypeCherryLeavesBreak.length)];
        }
        
        public String getStepResourcePath()
        {
            return soundTypeCherryLeavesStep[RANDOM.nextInt(soundTypeCherryLeavesStep.length)];
        }
    };
    
    public static final String[] soundTypeCherryWoodTrapdoor = {"wellozmod:block.cherrywood_trapdoor.toggle1", "wellozmod:block.cherrywood_trapdoor.toggle2", "wellozmod:block.cherrywood_trapdoor.toggle3"};
    
    /*
     * Entities
     */
    public static final String SOUND_TYPE_PENGUIN_SAY = "wellozmod:mob.penguin.say";
    public static final String SOUND_TYPE_PENGUIN_HURT = "wellozmod:mob.penguin.hurt";
    public static final String SOUND_TYPE_PENGUIN_DEATH = "wellozmod:mob.penguin.death";
    public static final String SOUND_TYPE_RABBIT_BUNNYMURDER = "wellozmod:mob.rabbit.bunnymurder";
    public static final String[] SOUND_TYPE_RABBIT_HOP = {"wellozmod:mob.rabbit.hop1", "wellozmod:mob.rabbit.hop2", "wellozmod:mob.rabbit.hop3", "wellozmod:mob.rabbit.hop4"};
    public static final String[] SOUND_TYPE_RABBIT_HURT = {"wellozmod:mob.rabbit.hurt1", "wellozmod:mob.rabbit.hurt2", "wellozmod:mob.rabbit.hurt3", "wellozmod:mob.rabbit.hurt4"};
    public static final String[] SOUND_TYPE_RABBIT_IDLE = {"wellozmod:mob.rabbit.idle1", "wellozmod:mob.rabbit.idle2", "wellozmod:mob.rabbit.idle3", "wellozmod:mob.rabbit.idle4"};
    public static final String[] SOUND_TYPE_STRAY_DEATH = {"wellozmod:mob.stray.death1", "wellozmod:mob.stray.death2"};
    public static final String[] SOUND_TYPE_STRAY_HURT = {"wellozmod:mob.stray.hurt1", "wellozmod:mob.stray.hurt2", "wellozmod:mob.stray.hurt3", "wellozmod:mob.stray.hurt4"};
    public static final String[] SOUND_TYPE_STRAY_IDLE = {"wellozmod:mob.stray.idle1", "wellozmod:mob.stray.idle2", "wellozmod:mob.stray.idle3", "wellozmod:mob.stray.idle4"};
    public static final String[] SOUND_TYPE_STRAY_STEP = {"wellozmod:mob.stray.step1", "wellozmod:mob.stray.step2", "wellozmod:mob.stray.step3", "wellozmod:mob.stray.step4"};
    public static final String SOUND_TYPE_AQUA_CREEPER_SWIM = "wellozmod:mob.creeper.swim";
    public static final String SOUND_TYPE_AQUA_CREEPER_HURT = "wellozmod:mob.creeper.hurt";
    public static final String SOUND_TYPE_AQUA_CREEPER_EXPLOSION = "wellozmod:mob.creeper.explosion";
    public static final String SOUND_TYPE_AQUA_CREEPER_FUSE = "wellozmod:mob.creeper.fuse";
    
    /*
     * Musics
     */
    public static final String SOUND_TYPE_CHRISTMAS = "wellozmod:music.christmas";
    public static final String SOUND_TYPE_HALLOWEEN = "wellozmod:music.halloween";
    public static final String SOUND_TYPE_SERVER_LOADING = "wellozmod:music.server_loading";
    public static final String SOUND_TYPE_TITLE_THEME = "wellozmod:music.title_theme";
    public static final String SOUND_TYPE_STAFF_ROLL = "wellozmod:music.staff_roll";
    public static final String SOUND_TYPE_OTHERSIDE = "wellozmod:otherside";
    public static final String SOUND_TYPE_FIVE = "wellozmod:five";
    public static final String SOUND_TYPE_PIGSTEP = "wellozmod:pigstep";
    public static final String SOUND_TYPE_RELIC = "wellozmod:relic";
    public static final String SOUND_TYPE_JINGLE_BELLS = "wellozmod:jingle_bells";
    public static final String SOUND_TYPE_MID_DAY = "wellozmod:mid_day";
    public static final String SOUND_TYPE_CALMOS = "wellozmod:calmos";
    public static final String SOUND_TYPE_LAVA_GROUND = "wellozmod:lava_ground";
    public static final String SOUND_TYPE_JUNGLE_BELLE = "wellozmod:jungle_belle";
}