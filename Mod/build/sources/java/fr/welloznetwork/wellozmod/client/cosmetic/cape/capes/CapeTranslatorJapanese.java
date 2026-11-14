package fr.welloznetwork.wellozmod.client.cosmetic.cape.capes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.ACape;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.Capes;

@SideOnly(Side.CLIENT)
public class CapeTranslatorJapanese extends ACape
{
    public CapeTranslatorJapanese()
    {
        super("translator_japanese.png", Capes.TRANSLATOR_JAPANESE);
    }
}