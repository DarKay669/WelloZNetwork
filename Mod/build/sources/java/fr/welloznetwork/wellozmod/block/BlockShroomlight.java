package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockShroomlight extends Block
{
    public BlockShroomlight()
    {
        super(Material.rock);
        setResistance(1.0F);
        setHardness(1.0F);
        setLightLevel(1.0F);
        setBlockName("shroomlight");
        setBlockTextureName("wellozmod:shroomlight");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_SHROOMLIGHT);
    }
}