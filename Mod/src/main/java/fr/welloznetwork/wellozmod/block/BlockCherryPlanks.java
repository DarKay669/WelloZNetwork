package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCherryPlanks extends Block
{
    public BlockCherryPlanks()
    {
        super(Material.wood);
        setHardness(2.0F);
        setResistance(5.0F);
        setStepSound(RegistrySounds.SOUND_TYPE_CHERRY_WOOD);
        setBlockName("cherry_planks");
        setBlockTextureName("wellozmod:cherry_planks");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}