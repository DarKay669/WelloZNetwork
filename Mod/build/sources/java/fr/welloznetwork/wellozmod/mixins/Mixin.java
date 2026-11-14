package fr.welloznetwork.wellozmod.mixins;

import java.util.Map;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import net.minecraft.launchwrapper.Launch;

@MCVersion("1.7.10")
@TransformerExclusions("fr.welloznetwork.wellozmod.mixins")
@SortingIndex(1001)
public class Mixin implements IFMLLoadingPlugin
{
    static
    {
        try
        {
            Launch.classLoader.addTransformerExclusion("org.spongepowered.asm.mixin.");
            Launch.classLoader.addTransformerExclusion("org.spongepowered.asm");
        }
        
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
    
    public Mixin()
    {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.forge.json");
        Mixins.addConfiguration("mixins.hud.json");
        Mixins.addConfiguration("mixins.wellozmod.json");
    }
    
    public String[] getASMTransformerClass()
    {
        return new String[] {"org.spongepowered.asm.mixin.transformer.MixinTransformer"};
    }
    
    public String getModContainerClass()
    {
        return null;
    }
    
    public String getSetupClass()
    {
        return null;
    }
    
    public void injectData(Map<String, Object> data)  {}
    
    public String getAccessTransformerClass()
    {
        return null;
    }
}