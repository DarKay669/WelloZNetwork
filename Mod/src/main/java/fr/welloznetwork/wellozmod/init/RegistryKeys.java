package fr.welloznetwork.wellozmod.init;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import fr.welloznetwork.venus.lib.dev.Debug;
import fr.welloznetwork.venus.lib.dev.NTSC_DEBUG;
import fr.welloznetwork.wellozmod.Constants;
import net.minecraft.client.settings.KeyBinding;

public class RegistryKeys
{
    @NTSC_DEBUG
    public static final KeyBinding KEY_BIND_TIMER = new KeyBinding("key.timer", Keyboard.KEY_F, "key.categories.welloznetwork");
    
    @NTSC_DEBUG
    public static final KeyBinding KEY_BIND_ZOOM = new KeyBinding("key.zoom", Keyboard.KEY_LCONTROL, "key.categories.welloznetwork");
    
    public static final KeyBinding KEY_BIND_ADVANCEMENTS = new KeyBinding("key.advancements", Keyboard.KEY_L, "key.categories.welloznetwork");
    public static final KeyBinding KEY_BIND_JOB_OPEN_UI = new KeyBinding("key.job.openUi", Keyboard.KEY_M, "key.categories.welloznetwork");
    
    public static void init()
    {
        if (Debug.isDebugMode())
        {
            ClientRegistry.registerKeyBinding(KEY_BIND_TIMER);
        }
        
        if (Constants.V2)
        {
            ClientRegistry.registerKeyBinding(KEY_BIND_JOB_OPEN_UI);
        }
        
        for (KeyBinding keyBindings : new KeyBinding[] {KEY_BIND_ZOOM, KEY_BIND_ADVANCEMENTS})
        {
            ClientRegistry.registerKeyBinding(keyBindings);
        }
    }
}