package fr.welloznetwork.wellozmod.client.cosmetic.cape.capes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.ACape;
import fr.welloznetwork.wellozmod.client.cosmetic.cape.Capes;

@SideOnly(Side.CLIENT)
public class CapeMojiraModerator extends ACape
{
    public CapeMojiraModerator()
    {
        super("mojira_moderator.png", Capes.MOJIRA_MODERATOR);
    }
}