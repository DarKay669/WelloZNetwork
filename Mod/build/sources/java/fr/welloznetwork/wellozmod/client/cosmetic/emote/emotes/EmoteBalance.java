package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteBalance extends AEmote
{
    public EmoteBalance(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 11, 2000.0F).target(-2.9415927F)).push(Tween.to(modelBiped, 8, 2000.0F).target(2.9415927F)).push(Tween.to(modelBiped, 17, 2000.0F).target(2.6179938F)).end().pushPause(2000.0F).beginParallel().push(Tween.to(modelBiped, 11, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 8, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 17, 500.0F).target(0.0F)).end();
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 0 || part == 9 || part == 6 || part == 18 || part == 15);
    }
}