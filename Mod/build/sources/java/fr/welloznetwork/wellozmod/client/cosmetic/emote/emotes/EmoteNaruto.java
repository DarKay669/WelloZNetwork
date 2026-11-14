package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteNaruto extends AEmote
{
    public EmoteNaruto(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 20, 1000.0F).target(-1.8471975F)).push(Tween.to(modelBiped, 14, 1000.0F).target(1.8471975F)).push(Tween.to(modelBiped, 19, 1000.0F).target(-0.24719757F)).push(Tween.to(modelBiped, 13, 1000.0F).target(0.24719757F)).push(Tween.to(modelBiped, 18, 1000.0F).target(0.9F)).push(Tween.to(modelBiped, 12, 1000.0F).target(0.9F)).push(Tween.to(modelBiped, 6, 1000.0F).target(0.5F)).push(Tween.to(modelBiped, 10, 1000.0F).target(0.2F)).push(Tween.to(modelBiped, 11, 1000.0F).target(-0.2F)).push(Tween.to(modelBiped, 16, 1000.0F).target(0.2F)).push(Tween.to(modelBiped, 17, 1000.0F).target(-0.2F)).push(Tween.to(modelBiped, 22, 1000.0F).target(0.2F)).push(Tween.to(modelBiped, 23, 1000.0F).target(-0.2F)).push(Tween.to(modelBiped, 4, 1000.0F).target(0.2F)).push(Tween.to(modelBiped, 5, 1000.0F).target(-0.2F)).push(Tween.to(modelBiped, 1, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 2, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 0, 500.0F).target(0.0F)).end().pushPause(10000.0F).beginParallel().push(Tween.to(modelBiped, 20, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 14, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 18, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 12, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 19, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 13, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 6, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 10, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 11, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 16, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 17, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 22, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 23, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 4, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 5, 500.0F).target(0.0F)).end();
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 18 || part == 12 || part == 6 || part == 0);
    }
}