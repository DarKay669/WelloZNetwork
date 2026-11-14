package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockRaw extends Block
{
    private static final int COPPER = 0, IRON = 1, GOLD = 2;
    int type;
    
    public BlockRaw(String unlocalizedName, int type)
    {
        super(Material.rock);
        
        switch (this.type)
        {
            case GOLD:
                setHarvestLevel("pickaxe", 2);
                break;
        }
        
        setHarvestLevel("pickaxe", 1);
        setHardness(5.0F);
        setResistance(6.0F);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setStepSound(soundTypeStone);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        this.type = type;
    }
}