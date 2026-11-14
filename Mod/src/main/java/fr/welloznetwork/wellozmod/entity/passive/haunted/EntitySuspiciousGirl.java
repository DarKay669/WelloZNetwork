package fr.welloznetwork.wellozmod.entity.passive.haunted;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.INpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntitySuspiciousGirl extends EntityAgeable implements INpc
{
    public EntitySuspiciousGirl(World world)
    {
        super(world);
        setSize(0.6F, 1.8F);
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
    }
    
    public boolean isAIEnabled()
    {
        return true;
    }
    
    public boolean interact(EntityPlayer entityPlayer)
    {
        if (entityPlayer.worldObj.isRemote)
        {
            entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("entity.SuspiciousGirl.interact")));
        }
        
        return true;
    }
    
    protected String getLivingSound()
    {
        return "mob.ghast.moan";
    }
    
    protected String getHurtSound()
    {
        return "mob.ghast.scream";
    }
    
    protected String getDeathSound()
    {
        return "mob.ghast.death";
    }
    
    public EntitySuspiciousGirl createChild(EntityAgeable entityAgeable)
    {
        return null;
    }
}