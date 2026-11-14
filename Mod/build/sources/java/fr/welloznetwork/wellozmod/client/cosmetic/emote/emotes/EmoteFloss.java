package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteFloss extends AEmote
{
    public EmoteFloss(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
       return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 20, 400.0F).target(-0.9F)).push(Tween.to(modelBiped, 18, 400.0F).target(-0.4F)).push(Tween.to(modelBiped, 14, 400.0F).target(-0.9F)).push(Tween.to(modelBiped, 12, 400.0F).target(-0.4F)).push(Tween.to(modelBiped, 8, 400.0F).target(0.08F)).push(Tween.to(modelBiped, 32, 400.0F).target(-0.2F)).push(Tween.to(modelBiped, 26, 400.0F).target(-0.1F)).end().beginParallel().push(Tween.to(modelBiped, 20, 400.0F).target(0.9F)).push(Tween.to(modelBiped, 18, 400.0F).target(-0.4F)).push(Tween.to(modelBiped, 14, 400.0F).target(0.9F)).push(Tween.to(modelBiped, 12, 400.0F).target(-0.4F)).push(Tween.to(modelBiped, 8, 400.0F).target(-0.08F)).push(Tween.to(modelBiped, 32, 400.0F).target(0.1F)).push(Tween.to(modelBiped, 26, 400.0F).target(0.2F)).end().beginParallel().push(Tween.to(modelBiped, 20, 400.0F).target(-0.9F)).push(Tween.to(modelBiped, 18, 400.0F).target(0.2F)).push(Tween.to(modelBiped, 14, 400.0F).target(-0.9F)).push(Tween.to(modelBiped, 12, 400.0F).target(0.4F)).push(Tween.to(modelBiped, 8, 400.0F).target(0.08F)).push(Tween.to(modelBiped, 32, 400.0F).target(-0.2F)).push(Tween.to(modelBiped, 26, 400.0F).target(-0.1F)).end().beginParallel().push(Tween.to(modelBiped, 20, 400.0F).target(0.9F)).push(Tween.to(modelBiped, 18, 400.0F).target(0.4F)).push(Tween.to(modelBiped, 14, 400.0F).target(0.9F)).push(Tween.to(modelBiped, 12, 400.0F).target(0.2F)).push(Tween.to(modelBiped, 8, 400.0F).target(-0.08F)).push(Tween.to(modelBiped, 32, 400.0F).target(0.1F)).push(Tween.to(modelBiped, 26, 400.0F).target(0.2F)).end().repeatYoyo(1, 0.0F);
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 6 || part == 18 || part == 12 || part == 30 || part == 24);
    }
}