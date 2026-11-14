package fr.welloznetwork.venus.lib.packet;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cpw.mods.fml.relauncher.Side;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface Packet
{
    Side side();
}