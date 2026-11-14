package fr.welloznetwork.wellozmod.init;

import fr.welloznetwork.wellozmod.potion.PotionFire;
import fr.welloznetwork.wellozmod.potion.PotionGlowing;
import fr.welloznetwork.wellozmod.potion.PotionLevitation;
import fr.welloznetwork.wellozmod.potion.PotionPoison;
import fr.welloznetwork.wellozmod.potion.PotionStep;
import fr.welloznetwork.wellozmod.potion.PotionWaterJump;
import fr.welloznetwork.wellozmod.potion.PotionWither;
import net.minecraft.potion.Potion;

public class RegistryPotions
{
    public static final Potion WITHER = new PotionWither();
    public static final Potion FIRE = new PotionFire();
    public static final Potion POISON = new PotionPoison();
    public static final Potion GLOWING = new PotionGlowing();
    public static final Potion LEVITATION = new PotionLevitation();
    public static final Potion WATER_JUMP = new PotionWaterJump();
    public static final Potion STEP = new PotionStep();
}