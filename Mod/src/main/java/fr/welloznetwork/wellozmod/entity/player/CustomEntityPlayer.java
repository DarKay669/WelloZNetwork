package fr.welloznetwork.wellozmod.entity.player;

import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

@Custom(EntityPlayer.class)
public class CustomEntityPlayer implements IExtendedEntityProperties
{
    public static final String ID = "EntityPlayer";
    private int money = 100;
    
    public static CustomEntityPlayer get(EntityPlayer entityPlayer)
    {
        return (CustomEntityPlayer) entityPlayer.getExtendedProperties(ID);
    }
    
    public void saveNBTData(NBTTagCompound nbtTagCompound)
    {
        nbtTagCompound.setInteger("money", this.money);
    }
    
    public void loadNBTData(NBTTagCompound nbtTagCompound)
    {
        this.money = nbtTagCompound.getInteger("money");
    }
    
    public void init(Entity entity, World world) {}
    
    public void addMoney(int amount)
    {
        this.money += amount;
    }
    
    public boolean decreaseMoney(int amount)
    {
        if (this.money >= amount)
        {
            this.money -= amount;
            
            return true;
        }
        
        return false;
    }
    
    public void resetMoney()
    {
        this.money = 0;
    }
    
    public int getMoney()
    {
        return this.money;
    }
    
    public void setMoney(int amount)
    {
        this.money = amount;
    }
}