package fr.welloznetwork.wellozmod.block;

import net.minecraft.item.ItemBlock;

public interface ISubBlocksBlock
{
    Class<? extends ItemBlock> getItemBlockClass();
}