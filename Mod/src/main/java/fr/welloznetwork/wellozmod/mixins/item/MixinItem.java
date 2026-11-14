package fr.welloznetwork.wellozmod.mixins.item;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.util.ModUtil.RandomXoshiro256StarStar;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

@Mixin(value = Item.class, remap = false)
public class MixinItem
{
    private static final Block[] VANILLA_BLOCKS = {Blocks.stone, Blocks.grass, Blocks.dirt, Blocks.cobblestone, Blocks.planks, Blocks.bedrock, Blocks.log, Blocks.log2, Blocks.sponge, Blocks.glass, Blocks.wool, Blocks.gold_block, Blocks.iron_block, Blocks.brick_block, Blocks.bookshelf, Blocks.diamond_block, Blocks.ice, Blocks.snow, Blocks.soul_sand, Blocks.glowstone, Blocks.lit_pumpkin, Blocks.stained_glass, Blocks.melon_block, Blocks.end_stone, Blocks.emerald_block, Blocks.hay_block, Blocks.packed_ice, Blocks.chest, Blocks.crafting_table, Blocks.furnace, Blocks.lit_furnace, Blocks.cactus, Blocks.jukebox, Blocks.ender_chest, Blocks.trapped_chest, Blocks.leaves, Blocks.leaves2, Blocks.dispenser, Blocks.noteblock, Blocks.sticky_piston, Blocks.piston, Blocks.tnt, Blocks.redstone_lamp, Blocks.lit_redstone_lamp, Blocks.redstone_block, Blocks.dropper, Blocks.beacon, RegistryBlocks.emerald_chest, RegistryBlocks.sea_lantern, RegistryBlocks.observer, RegistryBlocks.slime, RegistryBlocks.shroomlight, RegistryBlocks.magma, RegistryBlocks.barrier, RegistryBlocks.azalea_leaves, RegistryBlocks.bamboo_leaves, RegistryBlocks.brown_autumn_leaves, RegistryBlocks.cherry_leaves, RegistryBlocks.christmas_leaves, RegistryBlocks.flowering_azaela_leaves, RegistryBlocks.mangrove_leaves, RegistryBlocks.orange_autumn_leaves, RegistryBlocks.red_autumn_leaves, RegistryBlocks.winter_leaves, RegistryBlocks.yellow_autumn_leaves, RegistryBlocks.spectre_glass, RegistryBlocks.cloud, RegistryBlocks.pumpkin_face_1_off, RegistryBlocks.pumpkin_face_1_on, RegistryBlocks.pumpkin_face_2_off, RegistryBlocks.pumpkin_face_2_on, RegistryBlocks.pumpkin_face_3_off, RegistryBlocks.pumpkin_face_3_on, RegistryBlocks.pumpkin_face_4_off, RegistryBlocks.pumpkin_face_4_on, RegistryBlocks.pumpkin_face_5_off, RegistryBlocks.pumpkin_face_5_on, RegistryBlocks.iced_stone, RegistryBlocks.iced_cobblestone, RegistryBlocks.lights};
    
    private static Random itemRand = new RandomXoshiro256StarStar();
    
    @Overwrite
    public boolean isValidArmor(ItemStack itemStack, int armorType, Entity entity)
    {
        if ((Item) (Object) this instanceof ItemArmor)
        {
            return ((ItemArmor) (Item) (Object) this).armorType == armorType;
        }
        
        if (armorType == 0)
        {
            if (Constants.V2)
            {
                for (Block block : VANILLA_BLOCKS)
                {
                    if ((Item) (Object) this == Item.getItemFromBlock(block))
                    {
                        return true;
                    }
                }
                
                for (Block block : RegistryBlocks.MODDED_BLOCKS)
                {
                    if ((Item) (Object) this == Item.getItemFromBlock(block))
                    {
                        return true;
                    }
                }
            }
            
            return ((Item) (Object) this == Item.getItemFromBlock(Blocks.pumpkin)) || ((Item) (Object) this == Items.skull);
        }
        
        return false;
    }
}