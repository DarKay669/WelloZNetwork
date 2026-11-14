package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;

@Custom(BlockTrapDoor.class)
public class CustomBlockTrapDoor extends BlockTrapDoor
{
    private static final int WOOD_TRAP_DOOR = 0, METAL_TRAP_DOOR = 1;
    int type;
    
    public CustomBlockTrapDoor(Material material, String unlocalizedName, int type, SoundType soundType)
    {
        super(material);
        
        switch (this.type)
        {            
            case WOOD_TRAP_DOOR:
                setHardness(2.0F);
                setResistance(5.0F);
                setHarvestLevel("axe", 0);
                break;
            
            case METAL_TRAP_DOOR:
                setHardness(5.0F);
                setResistance(10.0F);
                setHarvestLevel("pickaxe", 0);
                break;
        }
        
        setStepSound(soundType);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        this.type = type;
    }
}