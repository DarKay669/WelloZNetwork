package fr.welloznetwork.wellozmod.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IFakeTileEntity
{
    TileEntity getTileEntity(World world, int x, int y, int z);
}