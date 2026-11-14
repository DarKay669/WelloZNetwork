package fr.welloznetwork.wellozmod.init;

import static net.minecraft.enchantment.Enchantment.addToBookList;

import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentBindingCurse;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentDeathBringer;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentDepthStrider;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentExplosive;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentFrostWalker;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentImplants;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentLastStand;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentMending;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentSoulSpeed;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentSweepingEdge;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentSwiftSneak;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentVanishingCurse;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentWitherHunter;
import net.minecraft.enchantment.Enchantment;

public class RegistryEnchantments
{
    public static final Enchantment DEATH_BRINGER = new EnchantmentDeathBringer();
    public static final Enchantment EXPLOSIVE = new EnchantmentExplosive();
    public static final Enchantment FROST_WALKER = new EnchantmentFrostWalker();
    public static final Enchantment IMPLANTS = new EnchantmentImplants();
    public static final Enchantment LAST_STAND = new EnchantmentLastStand();
    public static final Enchantment MENDING = new EnchantmentMending();
    public static final Enchantment WITHER_HUNTER = new EnchantmentWitherHunter();
    public static final Enchantment DEPTH_STRIDER = new EnchantmentDepthStrider();
    public static final Enchantment SWIFT_SNEAK = new EnchantmentSwiftSneak();
    public static final Enchantment SOUL_SPEED = new EnchantmentSoulSpeed();
    public static final Enchantment BINDING_CURSE = new EnchantmentBindingCurse();
    public static final Enchantment SWEEPING_EDGE = new EnchantmentSweepingEdge();
    public static final Enchantment VANISHING_CURSE = new EnchantmentVanishingCurse();
    
    public static void init()
    {
        addToBookList(DEATH_BRINGER);
        addToBookList(EXPLOSIVE);
        addToBookList(FROST_WALKER);
        addToBookList(IMPLANTS);
        addToBookList(LAST_STAND);
        addToBookList(MENDING);
        addToBookList(WITHER_HUNTER);
        
        if (Constants.V2)
        {
            addToBookList(DEPTH_STRIDER);
            addToBookList(SWIFT_SNEAK);
            addToBookList(SOUL_SPEED);
            addToBookList(BINDING_CURSE);
            addToBookList(SWEEPING_EDGE);
            addToBookList(VANISHING_CURSE);
        }
    }
}