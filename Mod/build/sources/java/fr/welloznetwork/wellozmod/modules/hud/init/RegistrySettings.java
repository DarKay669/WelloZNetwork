package fr.welloznetwork.wellozmod.modules.hud.init;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fr.welloznetwork.venus.lib.render.ButtonTheme;
import fr.welloznetwork.venus.lib.render.ChatMode;
import net.minecraftforge.common.config.Configuration;

public class RegistrySettings
{
    private static Configuration configuration;
    public static int cpsX, cpsY, keystrokesX, keystrokesY, reachDisplayX, reachDisplayY, toggleSprintX, toggleSprintY, cpsColor, keystrokesColor, keystrokesColorPressed, keystrokesMode, reachDisplayColor, toggleSprintColor, timeType;
    public static boolean cpsEnabled, keystrokesEnabled, reachDisplayEnabled, blurEnabled, toggleSprintEnabled;
    private static final String COLOR = "[0 = BLACK, 1 = DARK BLUE, 2 = DARK GREEN, 3 = DARK AQUA, 4 = DARK RED, 5 = DARK PURPLE, 6 = GOLD, 7 = GRAY, 8 = DARK GRAY, 9 = BLUE, 10 = GREEN, 11 = AQUA, 12 = RED, 13 = LIGHT PURPLE, 14 = YELLOW, 15 = WHITE, 16 = RAINBOW, 17 = HUE RAINBOW]";
    private static String currentTheme, currentChatMode;
    
    public static ButtonTheme activeTheme;
    public static ChatMode chatMode;
    
    public static void preInit(FMLPreInitializationEvent event)
    {
        configuration = new Configuration(new File(event.getModConfigurationDirectory(), "hud.cfg"));
        
        cpsEnabled = configuration.getBoolean("cps", "modules", false, "Enable/Disable CPS");
        cpsX = configuration.getInt("cpsPosX", "modules", 2, 0, 0, "Change the horizontal CPS position");
        cpsY = configuration.getInt("cpsPosY", "modules", 80, 0, 0, "Change the vertical CPS position");
        cpsColor = configuration.getInt("cpsColor", "modules", 15, 0, 17, "Change the CPS text color " + COLOR);
        
        keystrokesEnabled = configuration.getBoolean("keystrokes", "modules", false, "Enable/Disable Keystrokes");
        keystrokesX = configuration.getInt("keystrokesPosX", "modules", 2, 0, 0, "Change the Keystrokes horizontal position");
        keystrokesY = configuration.getInt("keystrokesPosY", "modules", 40, 0, 0, "Change the Keystrokes vertical position");
        keystrokesColor = configuration.getInt("keystrokesColor", "modules", 15, 0, 17, "Change the Keystrokes keys color " + COLOR);
        keystrokesColorPressed = configuration.getInt("keystrokesColorPressed", "modules", 15, 0, 17, "Change the Keystrokes color when a key is pressed " + COLOR);
        keystrokesMode = configuration.getInt("keystrokesMode", "modules", 0, 0, 5, "Change the Keystrokes display [0 = ZQSD, 1 = ZQSD + CPS, 2 = ZQSD + JUMP + CPS, 3 = WASD, 4 = WASD + CPS, 5 = WASD + JUMP + CPS]");
        
        reachDisplayEnabled = configuration.getBoolean("reachDisplay", "modules", false, "Enable/Disable Reach Display");
        reachDisplayX = configuration.getInt("reachDisplayPosX", "modules", 2, 0, 0, "Change the Reach Display horizontal position");
        reachDisplayY = configuration.getInt("reachDisplayPosY", "modules", 16, 0, 0, "Change the Reach Display vertical position");
        reachDisplayColor = configuration.getInt("reachDisplayColor", "modules", 15, 0, 17, "Change the Reach Display text color " + COLOR);
        
        blurEnabled = configuration.getBoolean("blur", "modules", false, "Enable/Disable Blur");
        
        toggleSprintEnabled = configuration.getBoolean("toggleSprint", "modules", false, "Enable/Disable Toggle Sprint");
        toggleSprintX = configuration.getInt("toggleSprintPosX", "modules", 2, 0, 0, "Change the Toggle Sprint horizontal position");
        toggleSprintY = configuration.getInt("toggleSprintPosY", "modules", 2, 0, 0, "Change the Toggle Sprint vertical position");
        toggleSprintColor = configuration.getInt("toggleSprintColor", "modules", 15, 0, 17, "Change the Toggle Sprint text color " + COLOR);
        
        timeType = configuration.getInt("timeType", "modules", 0, 0, 4, "Change the time type of the world [0 = Vanilla, 1 = Day, 2 = Sunset, 3 = Night, 4 = Fast]");
        
        currentTheme = configuration.getString("theme", "modules", "OLD", "Switch between the OLD, NEW and LEGACY button theme");
        activeTheme = ButtonTheme.valueOf(currentTheme.toUpperCase());
        
        currentChatMode = configuration.getString("chatMode", "modules", "NORMAL", "Switch between the NORMAL, ADAPTIVE and CLEAR chat mode");
        chatMode = ChatMode.valueOf(currentChatMode.toUpperCase());
        
        configuration.save();
    }
    
    public static void updateConfiguration(String category, String key, int defaultValue)
    {
        configuration.get(category, key, defaultValue).set(defaultValue);
        configuration.save();
    }
    
    public static void updateConfiguration(String category, String key, boolean defaultValue)
    {
        configuration.get(category, key, defaultValue).set(defaultValue);
        configuration.save();
    }
    
    public static void updateConfiguration(String category, String key, String defaultValue)
    {
        configuration.get(category, key, defaultValue).set(defaultValue);
        configuration.save();
    }
}