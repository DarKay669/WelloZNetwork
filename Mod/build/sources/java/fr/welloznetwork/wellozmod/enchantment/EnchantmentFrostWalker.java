package fr.welloznetwork.wellozmod.enchantment;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryEnchantments;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantmentFrostWalker extends Enchantment
{
    public EnchantmentFrostWalker()
    {
        super(65, 2, EnumEnchantmentType.armor_feet);
        setName("frost_walker");
    }
    
    public int getMaxLevel()
    {
        return 2;
    }
    
    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 10;
    }
    
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }
    
    public static void freezeNearby(EntityLivingBase entityLivingBase, World world, int x, int y, int z, int level)
    {
        if (world.isRemote)
        {
            return;
        }
        
        if (entityLivingBase.onGround)
        {
            int radius = 1 + level;
            
            for (int i = -radius; i <= radius; i++)
            {
                for (int j = -radius; j <= radius; j++)
                {
                    Block block = world.getBlock(x + i, y - 1, z + j);
                    
                    if (block == Blocks.water || block == Blocks.flowing_water)
                    {
                        world.setBlock(x + i, y - 1, z + j, RegistryBlocks.frosted_ice);
                    }
                }
            }
        }
    }
    
    public boolean canApplyTogether(Enchantment enchantment)
    {
        return super.canApplyTogether(enchantment) && enchantment != RegistryEnchantments.DEPTH_STRIDER;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack itemStack)
    {
        return itemStack != null && itemStack.getItem() == Items.book;
    }
}