package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteRun extends AEmote
{
    public EmoteRun(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 9, 100.0F).target(-0.7853982F)).push(Tween.to(modelBiped, 6, 100.0F).target(0.7853982F)).push(Tween.to(modelBiped, 18, 100.0F).target(0.7853982F)).push(Tween.to(modelBiped, 15, 100.0F).target(-0.7853982F)).end().beginParallel().push(Tween.to(modelBiped, 9, 200.0F).target(0.7853982F).repeatYoyo(10, 0.0F)).push(Tween.to(modelBiped, 6, 200.0F).target(-0.7853982F).repeatYoyo(10, 0.0F)).push(Tween.to(modelBiped, 18, 200.0F).target(-0.7853982F).repeatYoyo(10, 0.0F)).push(Tween.to(modelBiped, 15, 200.0F).target(0.7853982F).repeatYoyo(10, 0.0F)).end().beginParallel().push(Tween.to(modelBiped, 9, 100.0F).target(0.0F)).push(Tween.to(modelBiped, 6, 100.0F).target(0.0F)).push(Tween.to(modelBiped, 18, 100.0F).target(0.0F)).push(Tween.to(modelBiped, 15, 100.0F).target(0.0F)).end();
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 9 || part == 6 || part == 18 || part == 15);
    }
}