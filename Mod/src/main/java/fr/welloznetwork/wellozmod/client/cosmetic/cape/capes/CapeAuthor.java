package fr.welloznetwork.wellozmod.client.cosmetic.cape.capes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.ACape;

@SideOnly(Side.CLIENT)
public class CapeAuthor extends ACape
{
    public CapeAuthor()
    {
        super("author.png", null);
    }
}