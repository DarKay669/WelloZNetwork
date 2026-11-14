package fr.welloznetwork.wellozmod.client.cosmetic.cape.capes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.ACape;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.Capes;

@SideOnly(Side.CLIENT)
public class CapeMojang extends ACape
{
    public CapeMojang()
    {
        super("mojang.png", Capes.MOJANG);
    }
}