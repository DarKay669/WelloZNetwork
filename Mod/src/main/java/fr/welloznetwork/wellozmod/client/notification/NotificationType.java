package fr.welloznetwork.wellozmod.client.notification;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum NotificationType
{
    INFO, MUSIC, ACHIEVEMENT, JOB, SERVER, WARNING, ERROR;
}