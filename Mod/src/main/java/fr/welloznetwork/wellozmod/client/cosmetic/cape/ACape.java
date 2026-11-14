package fr.welloznetwork.wellozmod.client.cosmetic.cape;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public abstract class ACape
{
    private ResourceLocation resourceLocation;
    private Capes capes;
    
    public ACape(String capeName, Capes capes)
    {
        this.resourceLocation = new ResourceLocation("wellozmod:textures/gui/cosmetics/capes/" + capeName);
        this.capes = capes;
    }
    
    public ResourceLocation getLocationCape()
    {
        return this.resourceLocation;
    }
}