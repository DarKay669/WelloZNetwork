package fr.welloznetwork.wellozmod.client.entity;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

@Custom(AbstractClientPlayer.class)
@SideOnly(Side.CLIENT)
public class CustomAbstractClientPlayer extends AbstractClientPlayer
{    
    public CustomAbstractClientPlayer(World world)
    {
        super(world, new GameProfile(null, "CustomPlayer"));
    }
    
    public boolean canCommandSenderUseCommand(int permLevel, String commandName)
    {
        return false;
    }
    
    public ChunkCoordinates getPlayerCoordinates()
    {
        return new ChunkCoordinates(0, 255, 0);
    }
    
    public int getBrightnessForRender(float brightness)
    {
        return 15728880;
    }
    
    public boolean isInvisible()
    {
        return true;
    }
    
    public void addChatMessage(IChatComponent iChatComponent) {}
}