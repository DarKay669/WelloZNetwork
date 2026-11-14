package fr.welloznetwork.wellozmod.init;

import static net.minecraftforge.common.util.EnumHelper.addArmorMaterial;

import net.minecraft.item.ItemArmor.ArmorMaterial;

public class RegistryArmorMaterials
{
    public static final ArmorMaterial EMERALD = addArmorMaterial("EMERALD", 160, new int[] {4, 9, 7, 4}, 10);
    public static final ArmorMaterial ZARKONIUM = addArmorMaterial("ZARKONIUM", 320, new int[] {4, 7, 5, 3}, 10);
    public static final ArmorMaterial ZARUX = addArmorMaterial("ZARUX", 370, new int[] {4, 8, 6, 3}, 10);
    public static final ArmorMaterial NETHERITE = addArmorMaterial("NETHERITE", 370, new int[] {3, 8, 6, 3}, 15);
    public static final ArmorMaterial TURTLE = addArmorMaterial("TURTLE", 100, new int[] {1, 1, 1, 1}, 0);
    public static final ArmorMaterial ICE_SKATES = addArmorMaterial("ICE_SKATES", 10, new int[] {2, 4, 2, 1}, 3);
    public static final ArmorMaterial SANTA = addArmorMaterial("SANTA", 7, new int[] {1, 4, 2, 1}, 16);
    public static final ArmorMaterial CAT_PAW = addArmorMaterial("CAT_PAW", 10, new int[] {1, 1, 1, 1}, 3);
    public static final ArmorMaterial COSTUME = addArmorMaterial("COSTUME", 5, new int[] {1, 3, 2, 1}, 15);
    public static final ArmorMaterial OBSIDIAN = addArmorMaterial("OBSIDIAN", 30, new int[] {3, 8, 6, 3}, 10);
    public static final ArmorMaterial SLIME = addArmorMaterial("SLIME", 12, new int[] {2, 6, 5, 2}, 9);
}