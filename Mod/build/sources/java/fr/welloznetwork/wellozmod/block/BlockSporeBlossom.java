package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;

public class BlockSporeBlossom extends BlockBush
{
    public BlockSporeBlossom()
    {
        super(Material.plants);
        setTickRandomly(true);
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName("spore_blossom");
        setBlockTextureName("wellozmod:spore_blossom");
        setBlockBounds(0.5F - 0.2F, 0.0F, 0.5F - 0.2F, 0.5F + 0.2F, 0.2F * 3.0F, 0.5F + 0.2F);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_SPORE_BLOSSOM);
    }
}