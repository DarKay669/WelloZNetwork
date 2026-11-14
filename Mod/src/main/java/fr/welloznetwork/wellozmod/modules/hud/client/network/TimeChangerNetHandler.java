package fr.welloznetwork.wellozmod.modules.hud.client.network;

import java.lang.reflect.Field;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.modules.hud.MHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S40PacketDisconnect;

@SideOnly(Side.CLIENT)
public class TimeChangerNetHandler extends NetHandlerPlayClient
{
private NetHandlerPlayClient netHandlerPlayClient;
    
    public TimeChangerNetHandler(NetHandlerPlayClient netHandlerPlayClient)
    {
        super(Minecraft.getMinecraft(), getGuiScreen(netHandlerPlayClient), netHandlerPlayClient.getNetworkManager());
        this.netHandlerPlayClient = netHandlerPlayClient;
    }
    
    private static GuiScreen getGuiScreen(NetHandlerPlayClient netHandlerPlayClient)
    {
        for (Field field : netHandlerPlayClient.getClass().getDeclaredFields())
        {
            if (field.getType().equals(GuiScreen.class))
            {
                field.setAccessible(true);
                
                try
                {
                    return (GuiScreen) field.get(netHandlerPlayClient);
                }
                
                catch (Exception e)
                {
                    return null;
                }
            }
        }
        
        return null;
    }
    
    public void handleJoinGame(S01PacketJoinGame packetIn)
    {
        this.netHandlerPlayClient.handleJoinGame(packetIn);
    }
    
    public void handleSpawnObject(S0EPacketSpawnObject packetIn)
    {
        this.netHandlerPlayClient.handleSpawnObject(packetIn);
    }
    
    public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb packetIn)
    {
        this.netHandlerPlayClient.handleSpawnExperienceOrb(packetIn);
    }
    
    public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity packetIn)
    {
        this.netHandlerPlayClient.handleSpawnGlobalEntity(packetIn);
    }
    
    public void handleSpawnPainting(S10PacketSpawnPainting packetIn)
    {
        this.netHandlerPlayClient.handleSpawnPainting(packetIn);
    }
    
    public void handleEntityVelocity(S12PacketEntityVelocity packetIn)
    {
        this.netHandlerPlayClient.handleEntityVelocity(packetIn);
    }
    
    public void handleEntityMetadata(S1CPacketEntityMetadata packetIn)
    {
        this.netHandlerPlayClient.handleEntityMetadata(packetIn);
    }
    
    public void handleSpawnPlayer(S0CPacketSpawnPlayer packetIn)
    {
        this.netHandlerPlayClient.handleSpawnPlayer(packetIn);
    }
    
    public void handleEntityTeleport(S18PacketEntityTeleport packetIn)
    {
        this.netHandlerPlayClient.handleEntityTeleport(packetIn);
    }
    
    public void handleHeldItemChange(S09PacketHeldItemChange packetIn)
    {
        this.netHandlerPlayClient.handleHeldItemChange(packetIn);
    }
    
    public void handleEntityMovement(S14PacketEntity packetIn)
    {
        this.netHandlerPlayClient.handleEntityMovement(packetIn);
    }
    
    public void handleEntityHeadLook(S19PacketEntityHeadLook packetIn)
    {
        this.netHandlerPlayClient.handleEntityHeadLook(packetIn);
    }
    
    public void handleDestroyEntities(S13PacketDestroyEntities packetIn)
    {
        this.netHandlerPlayClient.handleDestroyEntities(packetIn);
    }

    public void handlePlayerPosLook(S08PacketPlayerPosLook packetIn)
    {
        this.netHandlerPlayClient.handlePlayerPosLook(packetIn);
    }

    public void handleMultiBlockChange(S22PacketMultiBlockChange packetIn)
    {
        this.netHandlerPlayClient.handleMultiBlockChange(packetIn);
    }

    public void handleChunkData(S21PacketChunkData packetIn)
    {
        this.netHandlerPlayClient.handleChunkData(packetIn);
    }

    public void handleBlockChange(S23PacketBlockChange packetIn)
    {
        this.netHandlerPlayClient.handleBlockChange(packetIn);
    }
    
    public void handleMapChunkBulk(S26PacketMapChunkBulk packetIn)
    {
        this.netHandlerPlayClient.handleMapChunkBulk(packetIn);
    }

    public void handleDisconnect(S40PacketDisconnect packetIn)
    {
        this.netHandlerPlayClient.handleDisconnect(packetIn);
    }
    
    public void handleCollectItem(S0DPacketCollectItem packetIn)
    {
        this.netHandlerPlayClient.handleCollectItem(packetIn);
    }

    public void handleChat(S02PacketChat packetIn)
    {
        this.netHandlerPlayClient.handleChat(packetIn);
    }

    public void handleAnimation(S0BPacketAnimation packetIn)
    {
        this.netHandlerPlayClient.handleAnimation(packetIn);
    }

    public void handleUseBed(S0APacketUseBed packetIn)
    {
        this.netHandlerPlayClient.handleUseBed(packetIn);
    }

    public void handleSpawnMob(S0FPacketSpawnMob packetIn)
    {
        this.netHandlerPlayClient.handleSpawnMob(packetIn);
    }
    
    public void handleTimeUpdate(S03PacketTimeUpdate packet)
    {
        switch (MHUD.CLIENT_LISTENER.timeType)
        {
            case VANILLA:
                this.netHandlerPlayClient.handleTimeUpdate(packet);
                return;
                
            case DAY:
                this.netHandlerPlayClient.handleTimeUpdate(new S03PacketTimeUpdate(packet.func_149365_d(), -6000L, true));
                return;
                
            case SUNSET:
                this.netHandlerPlayClient.handleTimeUpdate(new S03PacketTimeUpdate(packet.func_149365_d(), -22880L, true));
                return;
                
            case NIGHT:
                this.netHandlerPlayClient.handleTimeUpdate(new S03PacketTimeUpdate(packet.func_149365_d(), -18000L, true));
                return;
                
            case FAST:
                return;
        }
    }
    
    public void handleSpawnPosition(S05PacketSpawnPosition packetIn)
    {
        this.netHandlerPlayClient.handleSpawnPosition(packetIn);
    }
    
    public void handleEntityAttach(S1BPacketEntityAttach packetIn)
    {
        this.netHandlerPlayClient.handleEntityAttach(packetIn);
    }
    
    public void handleEntityStatus(S19PacketEntityStatus packetIn)
    {
        this.netHandlerPlayClient.handleEntityStatus(packetIn);
    }
    
    public void handleUpdateHealth(S06PacketUpdateHealth packetIn)
    {
        this.netHandlerPlayClient.handleUpdateHealth(packetIn);
    }

    public void handleSetExperience(S1FPacketSetExperience packetIn)
    {
        this.netHandlerPlayClient.handleSetExperience(packetIn);
    }

    public void handleRespawn(S07PacketRespawn packetIn)
    {
        this.netHandlerPlayClient.handleRespawn(packetIn);
    }

    public void handleExplosion(S27PacketExplosion packetIn)
    {
        this.netHandlerPlayClient.handleExplosion(packetIn);
    }

    public void handleOpenWindow(S2DPacketOpenWindow packetIn)
    {
        this.netHandlerPlayClient.handleOpenWindow(packetIn);
    }

    public void handleSetSlot(S2FPacketSetSlot packetIn)
    {
        this.netHandlerPlayClient.handleSetSlot(packetIn);
    }

    public void handleConfirmTransaction(S32PacketConfirmTransaction packetIn)
    {
        this.netHandlerPlayClient.handleConfirmTransaction(packetIn);
    }

    public void handleWindowItems(S30PacketWindowItems packetIn)
    {
        this.netHandlerPlayClient.handleWindowItems(packetIn);
    }

    public void handleSignEditorOpen(S36PacketSignEditorOpen packetIn)
    {
        this.netHandlerPlayClient.handleSignEditorOpen(packetIn);
    }
    
    public void handleUpdateTileEntity(S35PacketUpdateTileEntity packetIn)
    {
        this.netHandlerPlayClient.handleUpdateTileEntity(packetIn);
    }
    
    public void handleWindowProperty(S31PacketWindowProperty packetIn)
    {
        this.netHandlerPlayClient.handleWindowProperty(packetIn);
    }
    
    public void handleEntityEquipment(S04PacketEntityEquipment packetIn)
    {
        this.netHandlerPlayClient.handleEntityEquipment(packetIn);
    }
    
    public void handleCloseWindow(S2EPacketCloseWindow packetIn)
    {
        this.netHandlerPlayClient.handleCloseWindow(packetIn);
    }

    public void handleBlockAction(S24PacketBlockAction packetIn)
    {
        this.netHandlerPlayClient.handleBlockAction(packetIn);
    }
    
    public void handleBlockBreakAnim(S25PacketBlockBreakAnim packetIn)
    {
        this.netHandlerPlayClient.handleBlockBreakAnim(packetIn);
    }

    public void handleChangeGameState(S2BPacketChangeGameState packetIn)
    {
        this.netHandlerPlayClient.handleChangeGameState(packetIn);
    }

    public void handleMaps(S34PacketMaps packetIn)
    {
        this.netHandlerPlayClient.handleMaps(packetIn);
    }

    public void handleEffect(S28PacketEffect packetIn)
    {
        this.netHandlerPlayClient.handleEffect(packetIn);
    }

    public void handleStatistics(S37PacketStatistics packetIn)
    {
        this.netHandlerPlayClient.handleStatistics(packetIn);
    }
    
    public void handleEntityEffect(S1DPacketEntityEffect packetIn)
    {
        this.netHandlerPlayClient.handleEntityEffect(packetIn);
    }
    
    public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect packetIn)
    {
        this.netHandlerPlayClient.handleRemoveEntityEffect(packetIn);
    }
    
    public void handlePlayerListItem(S38PacketPlayerListItem packetIn)
    {
        this.netHandlerPlayClient.handlePlayerListItem(packetIn);
    }
    
    public void handleKeepAlive(S00PacketKeepAlive packetIn)
    {
        this.netHandlerPlayClient.handleKeepAlive(packetIn);
    }
    
    public void handlePlayerAbilities(S39PacketPlayerAbilities packetIn)
    {
        this.netHandlerPlayClient.handlePlayerAbilities(packetIn);
    }
    
    public void handleTabComplete(S3APacketTabComplete packetIn)
    {
        this.netHandlerPlayClient.handleTabComplete(packetIn);
    }
    
    public void handleSoundEffect(S29PacketSoundEffect packetIn)
    {
        this.netHandlerPlayClient.handleSoundEffect(packetIn);
    }
    
    public void handleCustomPayload(S3FPacketCustomPayload packetIn)
    {
        this.netHandlerPlayClient.handleCustomPayload(packetIn);
    }
    
    public void handleScoreboardObjective(S3BPacketScoreboardObjective packetIn)
    {
        this.netHandlerPlayClient.handleScoreboardObjective(packetIn);
    }
    
    public void handleUpdateScore(S3CPacketUpdateScore packetIn)
    {
        this.netHandlerPlayClient.handleUpdateScore(packetIn);
    }
    
    public void handleDisplayObjective(S3DPacketDisplayScoreboard packetIn)
    {
        this.netHandlerPlayClient.handleDisplayScoreboard(packetIn);
    }
    
    public void handleTeams(S3EPacketTeams packetIn)
    {
        this.netHandlerPlayClient.handleTeams(packetIn);
    }
    
    public void handleParticles(S2APacketParticles packetIn)
    {
        this.netHandlerPlayClient.handleParticles(packetIn);
    }
    
    public void handleEntityProperties(S20PacketEntityProperties packetIn)
    {
        if (!(Minecraft.getMinecraft().theWorld.getEntityByID(packetIn.func_149442_c()) instanceof EntityLivingBase))
        {
            return;
        }
        
        this.netHandlerPlayClient.handleEntityProperties(packetIn);
    }
}