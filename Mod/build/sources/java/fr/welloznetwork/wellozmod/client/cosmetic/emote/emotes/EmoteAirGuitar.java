package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteAirGuitar extends AEmote
{
    public EmoteAirGuitar(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 0, 400.0F).target(0.4F)).push(Tween.to(modelBiped, 1, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 2, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 9, 400.0F).target(-2.8274333F)).push(Tween.to(modelBiped, 10, 400.0F).target(-1.5707964F)).push(Tween.to(modelBiped, 6, 400.0F).target(-0.77079636F)).push(Tween.to(modelBiped, 7, 400.0F).target(-1.0707964F)).end().beginParallel().push(Tween.to(modelBiped, 0, 100.0F).target(0.2F).repeatYoyo(18, 0.0F)).push(Tween.to(modelBiped, 6, 100.0F).target(-1.3707963F).repeatYoyo(18, 0.0F)).end().beginParallel().push(Tween.to(modelBiped, 0, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 9, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 10, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 6, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 7, 400.0F).target(0.0F)).end();
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 0 || part == 9 || part == 6);
    }
}