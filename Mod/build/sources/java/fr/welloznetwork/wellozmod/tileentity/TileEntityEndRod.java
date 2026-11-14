package fr.welloznetwork.wellozmod.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityEndRod extends TileEntity
{
    public boolean canUpdate()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, (this.xCoord + 1), (this.yCoord + 1), (this.zCoord + 1));
    }
    
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return Double.MAX_VALUE;
    }
}