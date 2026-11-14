package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteDab extends AEmote
{
    public EmoteDab(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 0, 400.0F).target(0.2F)).push(Tween.to(modelBiped, 1, 400.0F).target(0.3F)).push(Tween.to(modelBiped, 2, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 12, 400.0F).target(0.1F)).push(Tween.to(modelBiped, 14, 400.0F).target(2.0F)).push(Tween.to(modelBiped, 18, 400.0F).target(-1.9F)).push(Tween.to(modelBiped, 19, 400.0F).target(1.0F)).end().repeatYoyo(1, 1000.0F);
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 18 || part == 12 || part == 0);
    }
}