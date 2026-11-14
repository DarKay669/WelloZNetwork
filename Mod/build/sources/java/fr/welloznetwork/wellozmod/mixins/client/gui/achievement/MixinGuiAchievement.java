package fr.welloznetwork.wellozmod.mixins.client.gui.achievement;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import fr.welloznetwork.wellozmod.client.notification.Notification;
import fr.welloznetwork.wellozmod.client.notification.NotificationManager;
import fr.welloznetwork.wellozmod.client.notification.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumChatFormatting;

@Mixin(GuiAchievement.class)
public class MixinGuiAchievement
{
    @Shadow
    private Achievement field_146266_k;
    
    @Shadow
    private long field_146263_l;
    
    @Shadow
    private boolean field_146262_n;
    
    @Overwrite
    public void func_146254_a()
    {
        if (this.field_146266_k != null && this.field_146263_l != 0L && Minecraft.getMinecraft().thePlayer != null)
        {
            if (this.field_146262_n) {}
            
            else
            {
                NotificationManager.addNotification(new Notification(NotificationType.ACHIEVEMENT, EnumChatFormatting.YELLOW + I18n.format("achievement.get"), this.field_146266_k.getDescription(), 5));
                this.field_146262_n = true;
            }
        }
    }
}