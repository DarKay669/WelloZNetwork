package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

@Custom(BlockStairs.class)
public class CustomBlockStairs extends BlockStairs
{
    private static final int GLASS_STAIRS = 0, END_BRICK_STAIRS = 1, PURPUR_STAIRS = 2, SANDSTONE_STAIRS = 3, MANGROVE_STAIRS = 4, CHERRY_STAIRS = 5;
    int type;
    
    public CustomBlockStairs(Block block, String unlocalizedName, int type, SoundType soundType)
    {
        super(block, 0);
        
        switch (this.type)
        {
            case GLASS_STAIRS:
                setHardness(0.8F);
                setLightOpacity(0);
                break;
                
            case END_BRICK_STAIRS:
                setHardness(3.0F);
                setLightLevel(0.0F);
                break;
                
            case PURPUR_STAIRS:
                setHardness(1.5F);
                setResistance(6.0F);
                setLightOpacity(0);
                setHarvestLevel("pickaxe", 0);
                break;
                
            case SANDSTONE_STAIRS:
                setHardness(0.8F);
                setResistance(0.8F);
                setLightLevel(0.0F);
                break;
                
            case MANGROVE_STAIRS:
                setHardness(2.0F);
                setResistance(5.0F);
                setLightLevel(0.0F);
                break;
                
            case CHERRY_STAIRS:
                setHardness(2.0F);
                setResistance(5.0F);
                setLightLevel(0.0F);
                break;
        }
        
        setStepSound(soundType);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        this.type = type;
    }
}