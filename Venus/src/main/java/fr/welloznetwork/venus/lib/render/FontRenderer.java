package fr.welloznetwork.venus.lib.render;

import java.awt.Color;

public class FontRenderer
{
    public static Color rainbowEffect(float f, float fade)
    {
        float hue = ((float) (System.nanoTime() + f) / 4.0E9F % 1.0F);
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
        Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
    }
    
    public static Color rainbowHueEffect(long offset, float brightness, int speed)
    {
        float hue = (float) (System.nanoTime() + offset * speed) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, brightness, 1.0F)).intValue()), 16);
        Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
    }
}