package fr.welloznetwork.wellozmod.mixins.client.renderer;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import fr.welloznetwork.wellozmod.client.notification.Notification;
import fr.welloznetwork.wellozmod.client.notification.NotificationManager;
import fr.welloznetwork.wellozmod.client.notification.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal
{
    @Shadow
    private Minecraft mc;
    
    @Shadow
    private Map mapSoundPositions;
    
    @Overwrite
    public void playRecord(String recordName, int x, int y, int z)
    {
        ChunkCoordinates chunkCoordinates = new ChunkCoordinates(x, y, z);
        ISound iSound = (ISound) this.mapSoundPositions.get(chunkCoordinates);
        
        if (iSound != null)
        {
            this.mc.getSoundHandler().stopSound(iSound);
            this.mapSoundPositions.remove(chunkCoordinates);
        }
        
        if (recordName != null)
        {
            ItemRecord itemRecord = ItemRecord.getRecord(recordName);
            ResourceLocation resourceLocation = null;
            
            if (itemRecord != null)
            {
                NotificationManager.addNotification(new Notification(NotificationType.MUSIC, I18n.format("record.nowPlayingNotification"), itemRecord.getRecordNameLocal(), 4));
                resourceLocation = itemRecord.getRecordResource(recordName);
            }
            
            if (resourceLocation == null)
            {
                resourceLocation = new ResourceLocation(recordName);
            }
            
            PositionedSoundRecord positionedSoundRecord = PositionedSoundRecord.func_147675_a(resourceLocation, (float) x, (float) y, (float) z);
            
            this.mapSoundPositions.put(chunkCoordinates, positionedSoundRecord);
            this.mc.getSoundHandler().playSound(positionedSoundRecord);
        }
    }
}