package fr.welloznetwork.wellozmod.entity.player;

public interface IEntityPlayer
{
    void setElytraFlying(boolean state);
    
    float getTicksElytraFlying();
    
    boolean lastElytraFlying();
    
    void setLastElytraFlying(boolean state);
    
    void tickElytra();
    
    boolean isElytraFlying();
}