package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteStand extends AEmote
{
    public EmoteStand(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 6, 400.0F).target(-0.3F)).push(Tween.to(modelBiped, 7, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 8, 400.0F).target(-1.1780972F)).push(Tween.to(modelBiped, 9, 400.0F).target(0.3F)).push(Tween.to(modelBiped, 10, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 11, 400.0F).target(0.7853982F)).end().repeatYoyo(1, 2000.0F);
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 9 || part == 6);
    }
}