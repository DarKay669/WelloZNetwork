package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.BlockBush;

public class BlockDeadCoral extends BlockBush
{
    public BlockDeadCoral(String unlocalizedName)
    {
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_CORAL);
    }
}