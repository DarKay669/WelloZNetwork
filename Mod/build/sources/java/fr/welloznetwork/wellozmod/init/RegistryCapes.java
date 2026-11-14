package fr.welloznetwork.wellozmod.init;

import java.util.HashMap;
import java.util.Map;

import fr.welloznetwork.wellozmod.client.cosmetic.cape.ACape;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.Cape15thAnniversary;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeBirthday;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeCherryBlossom;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeCobalt;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeCommon;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeFollower;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeFounder;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeHome;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMCC15Th;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMenace;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMigrator;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMillionthCustomer;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMinecon2011;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMinecon2012;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMinecon2013;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMinecon2015;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMinecon2016;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMinecraftExperience;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMissing;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMojang;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMojangClassic;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMojangOffice;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMojangStudios;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeMojiraModerator;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeOxeye;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapePan;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapePrismarine;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapePurpleHeart;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeRealmsMapmaker;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeScrolls;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeSnowman;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeSpade;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeTest;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeTranslator;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeTranslatorChinese;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeTranslatorJapanese;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeTurtle;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeValentine;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeVanilla;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapeYearn;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.capes.CapedB;

public class RegistryCapes
{
    private static final Map<String, ACape> CAPES = new HashMap<>();
    public static final Map<String, Class<? extends ACape>> CAPE = new HashMap<>();
    
    public static void init() 
    {
        CAPE.put("15th_anniversary", Cape15thAnniversary.class);
        CAPE.put("birthday", CapeBirthday.class);
        CAPE.put("cherry_blossom", CapeCherryBlossom.class);
        CAPE.put("cobalt", CapeCobalt.class);
        CAPE.put("common", CapeCommon.class);
        CAPE.put("dB", CapedB.class);
        CAPE.put("follower", CapeFollower.class);
        CAPE.put("founder", CapeFounder.class);
        CAPE.put("home", CapeHome.class);
        CAPE.put("mcc_15th", CapeMCC15Th.class);
        CAPE.put("menace", CapeMenace.class);
        CAPE.put("migrator", CapeMigrator.class);
        CAPE.put("millionth_customer", CapeMillionthCustomer.class);
        CAPE.put("minecon_2011", CapeMinecon2011.class);
        CAPE.put("minecon_2012", CapeMinecon2012.class);
        CAPE.put("minecon_2013", CapeMinecon2013.class);
        CAPE.put("minecon_2015", CapeMinecon2015.class);
        CAPE.put("minecon_2016", CapeMinecon2016.class);
        CAPE.put("minecraft_experience", CapeMinecraftExperience.class);
        CAPE.put("missing", CapeMissing.class);
        CAPE.put("mojira_moderator", CapeMojiraModerator.class);
        CAPE.put("mojang", CapeMojang.class);
        CAPE.put("mojang_classic", CapeMojangClassic.class);
        CAPE.put("mojang_office", CapeMojangOffice.class);
        CAPE.put("mojang_studios", CapeMojangStudios.class);
        CAPE.put("oxeye", CapeOxeye.class);
        CAPE.put("pan", CapePan.class);
        CAPE.put("prismarine", CapePrismarine.class);
        CAPE.put("purple_heart", CapePurpleHeart.class);
        CAPE.put("realms_mapmaker", CapeRealmsMapmaker.class);
        CAPE.put("scrolls", CapeScrolls.class);
        CAPE.put("snowman", CapeSnowman.class);
        CAPE.put("spade", CapeSpade.class);
        CAPE.put("test", CapeTest.class);
        CAPE.put("translator", CapeTranslator.class);
        CAPE.put("translator_chinese", CapeTranslatorChinese.class);
        CAPE.put("translator_japanese", CapeTranslatorJapanese.class);
        CAPE.put("turtle", CapeTurtle.class);
        CAPE.put("valentine", CapeValentine.class);
        CAPE.put("vanilla", CapeVanilla.class);
        CAPE.put("yearn", CapeYearn.class);
    }
    
    public static ACape setCape(String capeName)
    {
        if (capeName == null || capeName.isEmpty())
        {
            return null;
        }
        
        Class<? extends ACape> clazz = CAPE.get(capeName.toLowerCase());
        
        if (clazz != null)
        {
            try
            {
                return clazz.getConstructor().newInstance();
            }
            
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    public static void setCape(String playerName, ACape cape)
    {
        if (playerName == null)
        {
            return;
        }
        
        if (cape == null)
        {
            CAPES.remove(playerName.toLowerCase());
        }
        
        else
        {
            CAPES.put(playerName.toLowerCase(), cape);
        }
    }
    
    public static ACape getCape(String playerName)
    {
        if (playerName == null)
        {
            return null;
        }
        
        return CAPES.get(playerName.toLowerCase());
    }
}