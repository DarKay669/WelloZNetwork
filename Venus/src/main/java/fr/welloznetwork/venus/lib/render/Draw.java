package fr.welloznetwork.venus.lib.render;

public class Draw
{
    public static float easingOut(float t)
    {
        return (float) (1 - Math.pow(1 - t, 3));
    }
}