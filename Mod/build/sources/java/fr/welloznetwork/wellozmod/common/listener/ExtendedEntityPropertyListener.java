package fr.welloznetwork.wellozmod.common.listener;

import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedData;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedEntityProperties;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedProperty;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;

public class ExtendedEntityPropertyListener
{
    @SubscribeEvent
    public void onStartTrackingEvent(StartTracking event)
    {
        if (!(event.entityPlayer instanceof EntityPlayerMP))
        {
            return;
        }
        
        for (Map.Entry<String, ExtendedData> extended : (Iterable<Map.Entry<String, ExtendedData>>) ExtendedUtils.getExtended().entrySet())
        {
            String propName = extended.getKey();
            ExtendedData extendedData = extended.getValue();
            
            if (extendedData.getProperties().contains(ExtendedProperty.SYNCHRONIZED_TRACKER))
            {
                IExtendedEntityProperties iExtendedEntityProperties = event.target.getExtendedProperties(propName);
                
                if (!(iExtendedEntityProperties instanceof ExtendedEntityProperties))
                {
                    continue;
                }
                
                ((ExtendedEntityProperties) iExtendedEntityProperties).sync((EntityPlayerMP) event.entityPlayer);
            }
        }
    }
    
    @SubscribeEvent
    public void onCloneEvent(Clone event)
    {
        if (event.wasDeath)
        {
            for (Map.Entry<String, ExtendedData> extended : (Iterable<Map.Entry<String, ExtendedData>>) ExtendedUtils.getExtended().entrySet())
            {
                String propName = extended.getKey();
                ExtendedData extendedData = extended.getValue();
                
                if (extendedData.getProperties().contains(ExtendedProperty.PERSISTANT) && extendedData.getEntityType().isAssignableFrom(event.entity.getClass()))
                {
                    NBTTagCompound nbtTagCompound = new NBTTagCompound();
                    
                    event.original.getExtendedProperties(propName).saveNBTData(nbtTagCompound);
                    event.entityPlayer.getExtendedProperties(propName).loadNBTData(nbtTagCompound);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinWorldEvent(EntityJoinWorldEvent event)
    {
        for (Map.Entry<String, ExtendedData> extended : (Iterable<Map.Entry<String, ExtendedData>>) ExtendedUtils.getExtended().entrySet())
        {
            String propName = extended.getKey();
            ExtendedData extendedData = extended.getValue();
            
            if (extendedData.getEntityType().isAssignableFrom(event.entity.getClass()))
            {
                ((ExtendedEntityProperties) event.entity.getExtendedProperties(propName)).sync();
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityConstructingEvent(EntityConstructing event)
    {
        for (Map.Entry<String, ExtendedData> extended : (Iterable<Map.Entry<String, ExtendedData>>) ExtendedUtils.getExtended().entrySet())
        {
            String propName = extended.getKey();
            ExtendedData extendedData = extended.getValue();
            
            if (extendedData.getProperties().contains(ExtendedProperty.SELF_CONSTRUCT) && extendedData.getEntityType().isAssignableFrom(event.entity.getClass()) && event.entity.getExtendedProperties(propName) == null)
            {
                try
                {
                    ExtendedEntityProperties extendedEntityProperties = extendedData.getExtended().newInstance();
                    extendedEntityProperties.setPropName(propName);
                    
                    event.entity.registerExtendedProperties(propName, extendedEntityProperties);
                }
                
                catch (InstantiationException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}