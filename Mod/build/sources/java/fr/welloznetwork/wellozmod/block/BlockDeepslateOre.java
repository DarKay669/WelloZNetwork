package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockDeepslateOre extends Block
{
    private static final int COAL = 0, COPPER = 1, DIAMOND = 2, EMERALD = 3, GOLD = 4, IRON = 5, LAPIS = 6, REDSTONE = 7;
    int type;
    
    public BlockDeepslateOre(String unlocalizedName, int type)
    {
        super(Material.rock);
        setHarvestLevel("pickaxe", 0);
        setResistance(3.0F);
        setHardness(4.5F);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_DEEPSLATE);
        this.type = type;
        
        switch (this.type)
        {
            case 7:
                setLightLevel(9.0F);
        }
    }
    
    public Item getItemDropped(int dropped, Random random, int chance)
    {        
        switch (this.type)
        {
            case 0:
                return Items.coal;
                
            case 1:
                return RegistryItems.raw_copper;
                
            case 2:
                return Items.diamond;
                
            case 3:
                return Items.emerald;
                
            case 4:
                return RegistryItems.raw_gold;
                
            case 5:
                return RegistryItems.raw_iron;
                
            case 6:
                return Items.dye;
                
            case 7:
                return Items.redstone;
        }
        
        return Item.getItemFromBlock(this);
    }
    
    public int quantityDropped(Random random)
    {
        return this == RegistryBlocks.deepslate_lapis_ore ? 4 + random.nextInt(5) : 1;
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
    
    public int getExpDrop(IBlockAccess iBlockAccess, int dropped, int chance)
    {
        Random random = new Random();
        
        if (this.getItemDropped(dropped, random, chance) != Item.getItemFromBlock(this))
        {
            int j1 = 0;
            
            if (this == RegistryBlocks.deepslate_coal_ore)
            {
                j1 = MathHelper.getRandomIntegerInRange(random, 0, 2);
            }
            
            else if (this == RegistryBlocks.deepslate_diamond_ore)
            {
                j1 = MathHelper.getRandomIntegerInRange(random, 3, 7);
            }
            
            else if (this == RegistryBlocks.deepslate_emerald_ore)
            {
                j1 = MathHelper.getRandomIntegerInRange(random, 3, 7);
            }
            
            else if (this == RegistryBlocks.deepslate_lapis_ore)
            {
                j1 = MathHelper.getRandomIntegerInRange(random, 2, 5);
            }
            
            return j1;
        }
        
        return 0;
    }
    
    public int damageDropped(int damage)
    {
        return this == RegistryBlocks.deepslate_lapis_ore ? 4 : 0;
    }
}