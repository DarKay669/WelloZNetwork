package fr.welloznetwork.wellozmod.mixins.entity;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityTrackerEntry;

@Mixin(EntityTrackerEntry.class)
public class MixinEntityTrackerEntry {} /*implements IEntityTrackerEntry
{
    /*private boolean sendVelocityUpdates;
    
    public boolean getWasSendingVelocityUpdates()
    {
        return this.sendVelocityUpdates;
    }
    
    public void setWasSendingVelocityUpdates(boolean sendVelocityUpdates)
    {
        this.sendVelocityUpdates = sendVelocityUpdates;
    }
}*/