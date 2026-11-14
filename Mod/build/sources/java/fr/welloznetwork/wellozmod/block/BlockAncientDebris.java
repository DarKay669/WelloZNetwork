package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAncientDebris extends Block
{
    public BlockAncientDebris()
    {
        super(Material.rock);
        setResistance(1200.0F);
        setHardness(30.0F);
        setHarvestLevel("pickaxe", 3);
        setBlockName("ancient_debris");
        setBlockTextureName("wellozmod:ancient_debris");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_ANCIENT_DEBRIS);
    }
}