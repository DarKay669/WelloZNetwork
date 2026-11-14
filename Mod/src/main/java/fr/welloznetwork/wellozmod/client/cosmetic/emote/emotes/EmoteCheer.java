package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteCheer extends AEmote
{
    public EmoteCheer(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 8, 400.0F).target(2.6415927F)).push(Tween.to(modelBiped, 11, 400.0F).target(-2.6415927F)).end().beginParallel().push(Tween.to(modelBiped, 6, 150.0F).target(-0.6F).repeatYoyo(11, 0.0F)).push(Tween.to(modelBiped, 9, 150.0F).target(-0.6F).repeatYoyo(11, 0.0F)).end().beginParallel().push(Tween.to(modelBiped, 8, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 11, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 6, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 9, 400.0F).target(0.0F)).end();
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 6 || part == 9);
    }
}