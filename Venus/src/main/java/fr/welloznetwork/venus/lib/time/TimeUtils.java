package fr.welloznetwork.venus.lib.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtils
{
    public static long now()
    {
        return ZonedDateTime.now(ZoneId.of("Europe/Paris")).toEpochSecond();
    }
    
    public static ZonedDateTime nowZoned()
    {
        return ZonedDateTime.now(ZoneId.of("Europe/Paris"));
    }
    
    public static ZonedDateTime fromLong(long l)
    {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(l), ZoneId.of("Europe/Paris"));
    }
}