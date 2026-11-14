package fr.welloznetwork.wellozmod.modules;

import java.util.HashMap;
import java.util.Map;

import fr.welloznetwork.wellozmod.modules.hud.MHUD;
import fr.welloznetwork.wellozmod.modules.jobs.MJobs;
import fr.welloznetwork.wellozmod.modules.welcomekit.MWelcomeKit;

public class ModuleManager
{
    private final Map<String, IModule> modules = new HashMap<>();
    
    public void preInit()
    {
        this.register(MWelcomeKit.class);
        this.register(MJobs.class);
        this.register(MHUD.class);
    }
    
    public void init()
    {
        this.modules.values().forEach(IModule::init);
    }
    
    private void register(Class<? extends IModule> clazz)
    {
        if (clazz.isAnnotationPresent(Module.class) && IModule.class.isAssignableFrom(clazz))
        {
            try
            {
                IModule iModule = (IModule) clazz.newInstance();
                
                this.modules.put(clazz.getAnnotation(Module.class).id(), iModule);
                iModule.init();
            }
            
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}