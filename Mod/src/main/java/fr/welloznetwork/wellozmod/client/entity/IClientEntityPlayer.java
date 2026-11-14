package fr.welloznetwork.wellozmod.client.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IClientEntityPlayer
{
    float getRotateElytraX();
    
    float getRotateElytraY();
    
    float getRotateElytraZ();
    
    void setRotateElytraX(float rotateElytraX);
    
    void setRotateElytraY(float rotateElytraY);
    
    void setRotateElytraZ(float rotateElytraZ);
}