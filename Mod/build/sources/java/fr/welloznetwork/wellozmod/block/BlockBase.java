package fr.welloznetwork.wellozmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block
{
    private String blockName;
    
    public BlockBase(String blockName, Material material)
    {
        super(material);
        this.blockName = blockName;
    }
}