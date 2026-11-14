package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteHeadbang extends AEmote
{
    public EmoteHeadbang(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 6, 400.0F).target(-3.1415927F)).push(Tween.to(modelBiped, 0, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 1, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 2, 400.0F).target(0.0F)).end().beginParallel().push(Tween.to(modelBiped, 6, 300.0F).target(-1.1415927F).repeatYoyo(11, 0.0F)).push(Tween.to(modelBiped, 0, 300.0F).target(1.1415927F).repeatYoyo(11, 0.0F)).end().push(Tween.to(modelBiped, 6, 600.0F).target(0.0F));
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 6 || part == 0);
    }
}