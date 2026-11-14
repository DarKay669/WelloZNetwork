package fr.welloznetwork.wellozmod.client.notification;

import java.util.concurrent.LinkedBlockingDeque;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NotificationManager
{
    private static LinkedBlockingDeque<Notification> pendingNotifications = new LinkedBlockingDeque<>();
    private static Notification notification = null;
    
    public static void addNotification(Notification notification)
    {
        pendingNotifications.add(notification);
    }
    
    public static void renderNotification()
    {
        if (notification != null && !notification.isShown())
        {
            notification = null;
        }
        
        if (notification == null && !pendingNotifications.isEmpty())
        {
            notification = pendingNotifications.poll();
            notification.show();
        }
        
        if (notification != null)
        {
            notification.render();
        }
    }
}