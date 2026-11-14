package fr.welloznetwork.wellozmod.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;

import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.TweenAccessor;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.ModelAccessor;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteAirGuitar;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteBalance;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteCheer;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteClap;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteDab;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteExorcist;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteFacepalm;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteFloss;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteGangnamStyle;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteHeadbang;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteLevitate;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteNaruto;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteNo;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmotePoint;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteRun;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteSalute;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteShaftHeadTilt;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteShrug;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteStand;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteThink;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteWave;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteYes;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes.EmoteZombie;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class RegistryEmotes
{
    public static final Map<String, Class<? extends AEmote>> EMOTE = new TreeMap<>();
    private static WeakHashMap<EntityPlayer, AEmote> playerEmotes = new WeakHashMap<>();
    private static WeakHashMap<EntityPlayer, AEmote> localEmotes = new WeakHashMap<>();
    private static List<EntityPlayer> updatedPlayers = new ArrayList<>();
    
    public static void init()
    {
        Tween.registerAccessor(ModelBiped.class, (TweenAccessor) new ModelAccessor());
        
        EMOTE.put("airGuitar", EmoteAirGuitar.class);
        EMOTE.put("balance", EmoteBalance.class);
        EMOTE.put("cheer", EmoteCheer.class);
        EMOTE.put("clap", EmoteClap.class);
        EMOTE.put("dab", EmoteDab.class);
        EMOTE.put("exorcist", EmoteExorcist.class);
        EMOTE.put("facepalm", EmoteFacepalm.class);
        EMOTE.put("floss", EmoteFloss.class);
        EMOTE.put("gangnamStyle", EmoteGangnamStyle.class);
        EMOTE.put("headbang", EmoteHeadbang.class);
        EMOTE.put("levitate", EmoteLevitate.class);
        EMOTE.put("naruto", EmoteNaruto.class);
        EMOTE.put("no", EmoteNo.class);
        EMOTE.put("point", EmotePoint.class);
        EMOTE.put("run", EmoteRun.class);
        EMOTE.put("salute", EmoteSalute.class);
        EMOTE.put("shaftHeadTilt", EmoteShaftHeadTilt.class);
        EMOTE.put("shrug", EmoteShrug.class);
        EMOTE.put("stand", EmoteStand.class);
        EMOTE.put("think", EmoteThink.class);
        EMOTE.put("wave", EmoteWave.class);
        EMOTE.put("yes", EmoteYes.class);
        EMOTE.put("zombie", EmoteZombie.class);
    }
    
    public static void registerEmote(EntityPlayer entityPlayer, String emoteName)
    {
        if (EMOTE.containsKey(emoteName))
        {
            registerEmote(entityPlayer, EMOTE.get(emoteName));
        }
    }
    
    public static void registerEmote(EntityPlayer entityPlayer, Class<? extends AEmote> clazz)
    {
        if (entityPlayer instanceof AbstractClientPlayer)
        {
            ModelBiped modelBiped = getPlayerModel();
            ModelBiped armorModel = getPlayerArmorModel();
            ModelBiped armorLegModel = getPlayerArmorLegModel();
            
            if (modelBiped.bipedHead.rotateAngleY < 0.0F)
            {
                modelBiped.bipedHead.rotateAngleY = 6.2831855F - modelBiped.bipedHead.rotateAngleY;
            }
            
            try
            {
                if (playerEmotes.containsKey(entityPlayer) && !playerEmotes.get(entityPlayer).isDone())
                {
                    return;
                }
                
                else
                {
                    playerEmotes.put(entityPlayer, clazz.getConstructor(EntityPlayer.class, ModelBiped.class, ModelBiped.class, ModelBiped.class).newInstance(entityPlayer, modelBiped, armorModel, armorLegModel));
                }                
            }
            
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static void updateEmotes(Entity entity, ModelBase modelBase)
    {
        if (entity instanceof AbstractClientPlayer)
        {
            AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) entity;
            
            if (localEmotes.containsKey(abstractClientPlayer) || playerEmotes.containsKey(abstractClientPlayer))
            {
                if (localEmotes.containsKey(abstractClientPlayer))
                {
                    AEmote emoteLocal = localEmotes.get(abstractClientPlayer);
                    
                    if (emoteLocal.isDone())
                    {
                        localEmotes.remove(abstractClientPlayer);
                        updatedPlayers.remove(abstractClientPlayer);
                        
                        resetModel(getPlayerModel());
                        resetModel(getPlayerArmorModel());
                        resetModel(getPlayerArmorLegModel());
                    }
                    
                    else
                    {
                        emoteLocal.update(!updatedPlayers.contains(abstractClientPlayer));
                    }
                    
                    updatedPlayers.add(abstractClientPlayer);
                }
                
                if (playerEmotes.containsKey(abstractClientPlayer))
                {
                    AEmote emote = playerEmotes.get(abstractClientPlayer);
                    
                    if (emote.isDone())
                    {
                        playerEmotes.remove(abstractClientPlayer);
                        
                        resetModel(getPlayerModel());
                        resetModel(getPlayerArmorModel());
                        resetModel(getPlayerArmorLegModel());
                    }
                    
                    else
                    {
                        emote.update(!updatedPlayers.contains(abstractClientPlayer));
                    }
                    
                    updatedPlayers.add(abstractClientPlayer);
                }
            }
            
            else
            {
                resetPlayer(abstractClientPlayer);
            }
        }
    }
    
    public static void clearPlayerList()
    {
        updatedPlayers.clear();
    }
    
    private static ModelBiped getPlayerModel()
    {
        RenderPlayer renderPlayer = (RenderPlayer) RenderManager.instance.entityRenderMap.get(EntityPlayer.class);
        return renderPlayer.modelBipedMain;
    }
    
    private static ModelBiped getPlayerArmorModel()
    {
        RenderPlayer renderPlayer = (RenderPlayer) RenderManager.instance.entityRenderMap.get(EntityPlayer.class);
        return renderPlayer.modelArmorChestplate;
    }
    
    private static ModelBiped getPlayerArmorLegModel()
    {
        RenderPlayer renderPlayer = (RenderPlayer) RenderManager.instance.entityRenderMap.get(EntityPlayer.class);
        return renderPlayer.modelArmor;
    }
    
    private static void resetPlayer(EntityPlayer entityPlayer)
    {
        entityPlayer.setArrowCountInEntity(0);
        
        resetModel(getPlayerModel());
        resetModel(getPlayerArmorModel());
        resetModel(getPlayerArmorLegModel());
    }
    
    private static void resetModel(ModelBiped modelBiped)
    {
        modelBiped.bipedHead.rotateAngleZ = 0.0F;
        modelBiped.bipedHeadwear.rotateAngleZ = 0.0F;
        modelBiped.bipedBody.rotateAngleZ = 0.0F;
        modelBiped.bipedRightLeg.rotateAngleZ = 0.0F;
        modelBiped.bipedLeftLeg.rotateAngleZ = 0.0F;
    }
}