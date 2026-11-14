package fr.welloznetwork.wellozmod.modules.jobs.eep;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.welloznetwork.venus.lib.time.TimeUtils;
import fr.welloznetwork.wellozmod.modules.jobs.util.JobData;
import fr.welloznetwork.wellozmod.modules.jobs.util.JobType;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedEntityProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class JobsEEP extends ExtendedEntityProperties
{
    private final Map<JobType, JobData> jobs = new EnumMap<>(JobType.class);
    private int dailyXp = 0, maxDailyXp = 5000;
    private long lastReset = 0L;
    
    public JobsEEP()
    {
        for (JobType jobType : JobType.values())
        {
            this.jobs.put(jobType, new JobData(jobType));
        }
    }
    
    public void saveNBTData(NBTTagCompound nbtTagCompound)
    {
        NBTTagCompound nbtTagCompound1 = new NBTTagCompound();
        
        for (Entry<JobType, JobData> entry : this.jobs.entrySet())
        {
            NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            JobData jobData = entry.getValue();
            
            nbtTagCompound2.setInteger("level", jobData.getLevel());
            nbtTagCompound2.setInteger("xp", jobData.getXp());
            nbtTagCompound2.setInteger("maxXp", jobData.getMaxXp());
            
            nbtTagCompound1.setTag(entry.getKey().name(), nbtTagCompound2);
        }
        
        nbtTagCompound.setTag("Jobs", nbtTagCompound1);
        nbtTagCompound.setInteger("dailyXp", this.dailyXp);
        nbtTagCompound.setLong("lastReset", this.lastReset);
    }
    
    public void loadNBTData(NBTTagCompound nbtTagCompound)
    {
        if (!nbtTagCompound.hasKey("Jobs"))
        {
            return;
        }
        
        NBTTagCompound nbtTagCompound1 = nbtTagCompound.getCompoundTag("Jobs");
        
        for (JobType jobType : JobType.values())
        {
            if (nbtTagCompound1.hasKey(jobType.name()))
            {
                NBTTagCompound nbtTagCompound2 = nbtTagCompound1.getCompoundTag(jobType.name());
                JobData jobData = this.jobs.get(jobType);
                
                if (jobData != null)
                {
                    jobData.setLevel(nbtTagCompound2.getInteger("level"));
                    jobData.setXp(nbtTagCompound2.getInteger("xp"));
                    jobData.setMaxXp(nbtTagCompound2.getInteger("maxXp"));
                }
            }
        }
        
        this.dailyXp = nbtTagCompound.getInteger("dailyXp");
        this.lastReset = nbtTagCompound.getLong("lastReset");
    }
    
    public static JobsEEP get(EntityPlayer entityPlayer)
    {
        return (JobsEEP) entityPlayer.getExtendedProperties("Jobs");
    }
    
    public JobData getJob(JobType jobType)
    {
        return this.jobs.get(jobType);
    }
    
    public boolean canEarnXp(int amount)
    {
        this.checkDailyXpReset();
        
        return (this.dailyXp + amount) <= this.maxDailyXp;
    }
    
    public boolean addXp(JobType jobType, int amount)
    {
        this.checkDailyXpReset();
        
        if (!this.canEarnXp(amount))
        {
            return false;
        }
        
        JobData jobData = this.jobs.get(jobType);
        
        if (jobData != null)
        {
            jobData.addXp(amount);
        }
        
        this.dailyXp += amount;
        return true;
    }
    
    private void checkDailyXpReset()
    {
        if (TimeUtils.nowZoned().getHour() == 0 && TimeUtils.nowZoned().getMinute() == 0 && TimeUtils.nowZoned().getSecond() == 0 && TimeUtils.nowZoned().getNano() == 0)
        {
            this.dailyXp = 0;
            this.lastReset = System.currentTimeMillis();
        }
        
        /*Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        if (this.lastReset < calendar.getTimeInMillis())
        {
            this.dailyXp = 0;
            this.lastReset = System.currentTimeMillis();
        }*/
    }
    
    public int getDailyXp()
    {
        this.checkDailyXpReset();
        return this.dailyXp;
    }
    
    public int getMaxDailyXp()
    {
        return this.maxDailyXp;
    }
}