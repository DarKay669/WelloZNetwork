package fr.welloznetwork.wellozmod.modules.welcomekit.eep;

import fr.welloznetwork.wellozmod.client.notification.Notification;
import fr.welloznetwork.wellozmod.client.notification.NotificationManager;
import fr.welloznetwork.wellozmod.client.notification.NotificationType;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedEntityProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WelcomeKitEEP extends ExtendedEntityProperties
{
    private boolean welcomeKit = false;
    
    public static WelcomeKitEEP get(Entity entity)
    {
        return (WelcomeKitEEP) entity.getExtendedProperties("welcome_eep");
    }
    
    public void saveNBTData(NBTTagCompound nbtTagCompound)
    {
        nbtTagCompound.setBoolean("welcomeKit", this.welcomeKit);
    }
    
    public void loadNBTData(NBTTagCompound nbtTagCompound)
    {
        if (nbtTagCompound.hasKey("welcomeKit"))
        {
            this.welcomeKit = nbtTagCompound.getBoolean("welcomeKit");
        }
    }
    
    public void giveWelcomeKit(EntityPlayerMP entityPlayerMP)
    {
        ItemStack[] kit = {new ItemStack(RegistryItems.zarkonium_sword), new ItemStack(Items.iron_pickaxe), new ItemStack(Items.cooked_beef, 64), new ItemStack(Blocks.torch, 20), new ItemStack(Blocks.log, 32)};
        ItemStack[] armor = {new ItemStack(Items.iron_boots), new ItemStack(Items.iron_leggings), new ItemStack(Items.iron_chestplate), new ItemStack(RegistryItems.zarkonium_helmet)};
        
        for (ItemStack itemStack : kit)
        {
            this.addItem(entityPlayerMP, itemStack, itemStack.stackSize);
        }
        
        for (int i = 0; i < armor.length; i++)
        {
            entityPlayerMP.inventory.armorInventory[i] = armor[i];
        }
        
        this.welcomeKit = true;
        this.sync();
        
        NotificationManager.addNotification(new Notification(NotificationType.INFO, "Bienvenue sur WelloZ", "Voici un kit de départ, de quoi débuter ton aventure !", 4));
    }
    
    public boolean isWelcomeKit()
    {
        return this.welcomeKit;
    }
    
    private void addItem(EntityPlayer entityPlayer, ItemStack itemStack, int quantity)
    {
        InventoryPlayer inventoryPlayer = entityPlayer.inventory;
        ItemStack itemStack1 = itemStack.copy();
        
        int maxStackSize = itemStack1.getMaxStackSize();
        
        if (quantity > maxStackSize)
        {
            int leftOver = quantity;
            
            while (leftOver > 0)
            {
                int add = 0;
                
                if (leftOver <= maxStackSize)
                {
                    add += leftOver;
                }
                
                else
                {
                    add += maxStackSize;
                }
                
                itemStack1 = itemStack1.copy();
                itemStack1.stackSize += add;
                
                inventoryPlayer.addItemStackToInventory(itemStack1);
                
                leftOver -= add;
            }
        }
        
        else
        {
            itemStack1 = itemStack.copy();
            itemStack1.stackSize = quantity;
            
            inventoryPlayer.addItemStackToInventory(itemStack1);
        }
    }
}