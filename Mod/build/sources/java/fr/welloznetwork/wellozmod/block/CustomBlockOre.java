package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

@Custom(BlockOre.class)
public class CustomBlockOre extends Block
{
    private static final int ZARKONIUM_ORE = 0, ZARUX_ORE = 1, RUBY_ORE = 2, XP_ORE = 3, RANDOM_ORE = 4, ZARKONIUM_BLOCK = 5, ZARUX_BLOCK = 6;
    int type;
    
    public CustomBlockOre(String unlocalizedName, int type)
    {
        super(Material.rock);
        
        switch (this.type)
        {
            case ZARKONIUM_ORE:
                setHardness(3.0F);
                setResistance(5.0F);
                setHarvestLevel("pickaxe", 2);
                break;
                
            case ZARUX_ORE:
                setHardness(3.0F);
                setResistance(5.0F);
                setHarvestLevel("pickaxe", 2);
                break;
                
            case ZARKONIUM_BLOCK:
                setHardness(4.0F);
                setResistance(5.0F);
                break;
                
            case ZARUX_BLOCK:
                setHardness(4.0F);
                setResistance(5.0F);
                break;
        }
        
        setStepSound(soundTypeStone);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        this.type = type;
    }
    
    public Item getItemDropped(int dropped, Random random, int chance)
    {   
        int drop = random.nextInt(10);
        
        switch (this.type)
        {
            case RANDOM_ORE:
                
                if (drop == 0)
                {
                    return Item.getItemFromBlock(Blocks.gold_ore);
                }
                
                else if (drop == 1)
                {
                    return Item.getItemFromBlock(Blocks.iron_ore);
                }
                
                else if (drop == 2)
                {
                    return Item.getItemFromBlock(Blocks.coal_ore);
                }
                
                else if (drop == 3)
                {
                    return Item.getItemFromBlock(Blocks.lapis_ore);
                }
                
                else if (drop == 4)
                {
                    return Item.getItemFromBlock(Blocks.diamond_ore);
                }
                
                else if (drop == 5)
                {
                    return Item.getItemFromBlock(Blocks.redstone_ore);
                }
                
                else if (drop == 6)
                {
                    return Item.getItemFromBlock(Blocks.emerald_ore);
                }
                
                else if (drop == 7)
                {
                    return Item.getItemFromBlock(RegistryBlocks.zarkonium_ore);
                }
                
                else if (drop == 8)
                {
                    return Item.getItemFromBlock(RegistryBlocks.zarux_ore);
                }
                
                else if (drop == 9)
                {
                    return Item.getItemFromBlock(RegistryBlocks.ruby_ore);
                }
                
                break;
        }
        
        return this == RegistryBlocks.xp_ore ? Items.experience_bottle : Item.getItemFromBlock(this);
    }
    
    public int quantityDroppedWithBonus(int quantity, Random random)
    {
        if (quantity > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, random, quantity))
        {
            int j = random.nextInt(quantity + 2) - 1;
            
            if (j < 0)
            {
                j = 0;
            }
            
            return this.quantityDropped(random) * (j + 1);
        }
        
        else
        {
            return this.quantityDropped(random);
        }
    }
    
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float dropChance, int fortune)
    {
        super.dropBlockAsItemWithChance(world, x, y, z, metadata, dropChance, fortune);
    }
    
    public int quantityDropped(Random random)
    {
        switch (this.type)
        {
            case RANDOM_ORE:
                return 1;
        }
        
        return 1;
    }
}