package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteNo extends AEmote
{
    public EmoteNo(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 0, 300.0F).target(0.0F)).push(Tween.to(modelBiped, 1, 300.0F).target(0.2F)).push(Tween.to(modelBiped, 2, 300.0F).target(0.0F)).end().push(Tween.to(modelBiped, 1, 300.0F).target(-0.2F).repeatYoyo(4, 0.0F)).push(Tween.to(modelBiped, 1, 300.0F).target(0.0F));
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 0);
    }
}