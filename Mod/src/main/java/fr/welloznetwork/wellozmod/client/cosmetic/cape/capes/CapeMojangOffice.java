package fr.welloznetwork.wellozmod.client.cosmetic.cape.capes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.ACape;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.Capes;

@SideOnly(Side.CLIENT)
public class CapeMojangOffice extends ACape
{
    public CapeMojangOffice()
    {
        super("mojang_office.png", Capes.MOJANG_OFFICE);
    }
}