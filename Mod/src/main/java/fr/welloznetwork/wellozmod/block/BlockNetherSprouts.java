package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.BlockBush;

public class BlockNetherSprouts extends BlockBush
{
    public BlockNetherSprouts()
    {
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName("nether_sprouts");
        setBlockTextureName("wellozmod:nether_sprouts");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_NETHER_SPROUTS);
    }
}