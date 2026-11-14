package fr.welloznetwork.wellozmod.modules.jobs.util;

public class JobData
{
    private JobType jobType;
    private int level, xp, maxLevel = 20, maxXp = 160000;
    private boolean doubleXp = false;
    
    public JobData(JobType jobType)
    {
        this.jobType = jobType;
        this.level = 1;
        this.xp = 0;
    }
    
    public JobType getJobType()
    {
        return this.jobType;
    }
    
    public int getLevel()
    {
        return this.level;
    }
    
    public int getXp()
    {
        return this.xp;
    }
    
    public int getMaxXp()
    {
        return this.maxXp;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public void setXp(int xp)
    {
        this.xp = xp;
        this.updateLevelFromXp();
    }
    
    public void setMaxXp(int maxXp)
    {
        this.maxXp = maxXp;
    }
    
    public boolean isDoubleXp()
    {
        return this.doubleXp;
    }
    
    public void setDoubleXp(boolean doubleXp)
    {
        this.doubleXp = doubleXp;
    }
    
    public void addXp(int amount)
    {
        if (this.doubleXp)
        {
            amount *= 2;
        }
        
        this.xp += amount;
        
        if (this.xp > this.maxXp)
        {
            this.xp = this.maxXp;
        }
        
        this.updateLevelFromXp();
    }
    
    private void updateLevelFromXp()
    {
        if (this.xp <= 0)
        {
            this.level = 1;
            return;
        }
        
        double accumulatedXp = 0, xpForNextLevel = 140.0D;
        int newLevel = 1;
        
        while (newLevel < this.maxLevel && this.xp >= accumulatedXp + xpForNextLevel)
        {
            accumulatedXp += xpForNextLevel;
            xpForNextLevel *= Math.pow((double) this.maxXp / 140.0D, 1.0D / (this.maxLevel - 1));
            newLevel++;
        }
        
        this.level = newLevel;
    }
    
    public int getXpInCurrentLevel()
    {
        if (this.xp <= 0)
        {
            return this.xp;
        }
        
        double accumulatedXp = 0, xpForNextLevel = 140.0D;
        int currentLevel = 1;
        
        while (currentLevel < this.level)
        {
            accumulatedXp += xpForNextLevel;
            xpForNextLevel *= Math.pow((double) this.maxXp / 140.0D, 1.0D / (this.maxLevel - 1));
            currentLevel++;
        }
        
        return (int) (this.xp - accumulatedXp);
    }
    
    public int getXpForNextLevel()
    {
        if (this.level >= this.maxLevel)
        {
            return 0;
        }
        
        double xpForNextLevel = 140.0D;
        
        for (int i = 1; i < this.level; i++)
        {
            xpForNextLevel *= Math.pow((double) this.maxXp / 140.0D, 1.0D / (this.maxLevel - 1));
        }
        
        return (int) xpForNextLevel;
    }
}