package fr.welloznetwork.wellozmod.potion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.util.ModUtil.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class PotionLevitation extends Potion
{	
	public PotionLevitation()
    {
        super(36, true, 13565951);
        setIconIndex(0, 0);
        setPotionName("potion.imbueLevitation");
    }
    
    public void performEffect(EntityLivingBase entityLivingBase, int level)
    {
        if (entityLivingBase instanceof EntityPlayer)
        {
            return;
        }
        
        entityLivingBase.fallDistance = 0.0F;
        entityLivingBase.addVelocity(0.0D, (0.05D * (double) (level + 1) - entityLivingBase.motionY) * 0.2D, 0.0D);
        
        if (!entityLivingBase.isInWater() && !entityLivingBase.handleLavaMovement())
        {
            entityLivingBase.motionY += 0.08D;
        }
    }
    
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        return false;
    }
    
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBase, BaseAttributeMap baseAttributeMap, int potency)
    {
        super.removeAttributesModifiersFromEntity(entityLivingBase, baseAttributeMap, potency);
        
        if (this.shouldSync(entityLivingBase))
        {
            MinecraftServer.getServer().getConfigurationManager().sendToAllNear(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, 80.0D, entityLivingBase.dimension, new S1EPacketRemoveEntityEffect(entityLivingBase.getEntityId(), new PotionEffect(this.id, 0)));
        }
    }
    
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBase, BaseAttributeMap baseAttributeMap, int potency)
    {
        super.applyAttributesModifiersToEntity(entityLivingBase, baseAttributeMap, potency);
        
        if (this.shouldSync(entityLivingBase))
        {
            PotionEffect potionEffect = entityLivingBase.getActivePotionEffect(this);
            
            if (potionEffect != null)
            {
                MinecraftServer.getServer().getConfigurationManager().sendToAllNear(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, 80.0D, entityLivingBase.dimension, new S1DPacketEntityEffect(entityLivingBase.getEntityId(), potionEffect));
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect potionEffect, Minecraft mc)
    {
        mc.renderEngine.bindTexture(new ResourceLocation("wellozmod:textures/gui/effects/levitation.png"));
        RenderUtils.drawTexturedQuadFit((x + 5), (y + 5), 20.0D, 20.0D, 1.0D);
    }
    
    private boolean hasPacket()
    {
        return false;
    }
    
    private boolean shouldSync(EntityLivingBase entityLivingBase)
    {
        return this.hasPacket() && !(entityLivingBase instanceof EntityPlayer);
    }
}