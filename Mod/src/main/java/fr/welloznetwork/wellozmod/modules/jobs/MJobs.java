package fr.welloznetwork.wellozmod.modules.jobs;

import fr.welloznetwork.wellozmod.modules.IModule;
import fr.welloznetwork.wellozmod.modules.Module;
import fr.welloznetwork.wellozmod.modules.ModuleHelper;
import fr.welloznetwork.wellozmod.modules.jobs.eep.JobsEEP;
import fr.welloznetwork.wellozmod.modules.jobs.listener.JobsListener;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedProperty;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedUtils;
import net.minecraft.entity.player.EntityPlayer;

@Module(id = "jobs", description = "Jobs logic")
public class MJobs implements IModule
{
    public void init() 
    {
        ExtendedUtils.init(EntityPlayer.class, JobsEEP.class, "Jobs", new ExtendedProperty[] {ExtendedProperty.SELF_CONSTRUCT, ExtendedProperty.PERSISTANT, ExtendedProperty.SYNCHRONIZED});
        
        ModuleHelper.addListener(new Class[] {JobsListener.class});
    }
}