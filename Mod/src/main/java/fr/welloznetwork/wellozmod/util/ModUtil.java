package fr.welloznetwork.wellozmod.util;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.render.FontRenderer;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.command.CommandNickname;
import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.network.play.client.C07PacketExtendedEntityProperties;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IExtendedEntityProperties;
import scala.actors.threadpool.Arrays;

public class ModUtil
{
	//TODO HomeUtils.java
	public static class HomeUtils
	{   
	    private static final File SAVES_DIRECTORY;
	    
	    static
	    {
	        if (FMLCommonHandler.instance().getSide().isClient())
	        {
	            SAVES_DIRECTORY = new File(Minecraft.getMinecraft().mcDataDir, "saves");
	        }
	        
	        else
	        {
	            if (MinecraftServer.getServer() != null)
	            {
	                SAVES_DIRECTORY = MinecraftServer.getServer().getFile("");
	            }
	            
	            else
	            {
	                SAVES_DIRECTORY = new File(".");
	            }
	        }
	    }
	    
		public static int isValidName(String name)
		{
			if (name.length() < 2)
			{
				return 1;
			}
			
			if (Character.isDigit(name.charAt(0)))
			{
				return 2;	
			}
			
			return 0;
		}
		
		public static NBTTagCompound loadTeleportLocations(EntityPlayer entityPlayer)
		{
			try 
			{
				File filePlayerData = new File(SAVES_DIRECTORY, entityPlayer.worldObj.getSaveHandler().getWorldDirectoryName() + "/playerdata/" + entityPlayer.getCommandSenderName() + ".dat");
				
				if (filePlayerData.exists())
				{
					return CompressedStreamTools.readCompressed(new FileInputStream(filePlayerData));
				}
				
				return new NBTTagCompound();
				
			}
			
			catch (Throwable e)
			{
				e.printStackTrace();
				return null;
			}
		}
		
		public static void saveTeleportLocations(EntityPlayer entityPlayer, NBTTagCompound nbtTagCompound)
		{
			try 
			{
				if (nbtTagCompound == null)
				{
					return;
				}
				
				File file1 = new File(SAVES_DIRECTORY, entityPlayer.worldObj.getSaveHandler().getWorldDirectoryName() + "/playerdata/" + entityPlayer.getCommandSenderName() + ".dat.tmp");
				File file2 = new File(SAVES_DIRECTORY, entityPlayer.worldObj.getSaveHandler().getWorldDirectoryName() + "/playerdata/" + entityPlayer.getCommandSenderName() + ".dat");
				
				CompressedStreamTools.writeCompressed(nbtTagCompound, new FileOutputStream(file1));
				
				if (file2.exists())
				{
					file2.delete();
				}
				
				file1.renameTo(file2);
			}
			
			catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
		
		private static void transferEntityToWorld(Entity entity, int dimensionID, WorldServer worldServer, WorldServer worldServer1)
		{
			WorldProvider worldProviderOld = worldServer.provider, worldProviderNew = worldServer1.provider;
			double moveFactor = worldProviderOld.getMovementFactor() / worldProviderNew.getMovementFactor();
			double d0 = entity.posX * moveFactor;
			double d1 = entity.posZ * moveFactor;
			double d3 = entity.posX;
			double d4 = entity.posY;
			double d5 = entity.posZ;
			float f = entity.rotationYaw;
			
			worldServer.theProfiler.startSection("moving");
			worldServer.theProfiler.endSection();
			worldServer.theProfiler.startSection("placing");
			
			d0 = MathHelper.clamp_int((int) d0, -29999872, 29999872);
			d1 = MathHelper.clamp_int((int) d1, -29999872, 29999872);
			
			if (entity.isEntityAlive())
			{
			    worldServer1.spawnEntityInWorld(entity);
				entity.setLocationAndAngles(d0, entity.posY, d1, entity.rotationYaw, entity.rotationPitch);
				worldServer1.updateEntityWithOptionalForce(entity, false);
			}
			
			worldServer.theProfiler.endSection();
			entity.setWorld((World) worldServer1);
		}
		
		public static void transferPlayerToDimension(EntityPlayerMP entityPlayerMP, int x, int y, int z, int yaw, int dimensionID)
		{
			ServerConfigurationManager serverConfigurationManager = entityPlayerMP.mcServer.getConfigurationManager();
			
			int dimension = entityPlayerMP.dimension;
			WorldServer worldServer = entityPlayerMP.mcServer.worldServerForDimension(entityPlayerMP.dimension);
			entityPlayerMP.dimension = dimensionID;
			WorldServer worldServer1 = entityPlayerMP.mcServer.worldServerForDimension(entityPlayerMP.dimension);
			entityPlayerMP.playerNetServerHandler.sendPacket(new S07PacketRespawn(entityPlayerMP.dimension, entityPlayerMP.worldObj.difficultySetting, worldServer1.getWorldInfo().getTerrainType(), entityPlayerMP.theItemInWorldManager.getGameType()));
			worldServer.removePlayerEntityDangerously((Entity) entityPlayerMP);
			entityPlayerMP.isDead = false;
			transferEntityToWorld((Entity) entityPlayerMP, dimension, worldServer, worldServer1);
			serverConfigurationManager.func_72375_a(entityPlayerMP, worldServer);
			entityPlayerMP.playerNetServerHandler.setPlayerLocation(x + 0.5D, y, z + 0.5D, yaw, 0.0F);
			entityPlayerMP.theItemInWorldManager.setWorld(worldServer1);
			serverConfigurationManager.updateTimeAndWeatherForPlayer(entityPlayerMP, worldServer1);
			serverConfigurationManager.syncPlayerInventory(entityPlayerMP);
			Iterator<PotionEffect> iterator = entityPlayerMP.getActivePotionEffects().iterator();
			
			while (iterator.hasNext())
			{
				PotionEffect potionEffect = iterator.next();
				entityPlayerMP.playerNetServerHandler.sendPacket((Packet) new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), potionEffect));
			}
			
			FMLCommonHandler.instance().firePlayerChangedDimensionEvent((EntityPlayer) entityPlayerMP, dimension, dimensionID);
		}
		
		private static void travelToDimension(EntityPlayerMP entityPlayerMP, int x, int y, int z, int yaw, int dimensionID)
		{
			if (entityPlayerMP.worldObj.isRemote && !entityPlayerMP.isDead)
			{
				ServerConfigurationManager serverConfigurationManager = entityPlayerMP.mcServer.getConfigurationManager();
				entityPlayerMP.worldObj.theProfiler.startSection("changeDimension");
				MinecraftServer minecraftServer = MinecraftServer.getServer();
				int dimension = entityPlayerMP.dimension;
				WorldServer worldServer = minecraftServer.worldServerForDimension(dimension);
				WorldServer worldServer1 = minecraftServer.worldServerForDimension(dimensionID);
				entityPlayerMP.dimension = dimensionID;
				
				entityPlayerMP.worldObj.removeEntity((Entity) entityPlayerMP);
				entityPlayerMP.isDead = false;
				entityPlayerMP.worldObj.theProfiler.startSection("reposition");
				minecraftServer.getConfigurationManager().transferEntityToWorld((Entity) entityPlayerMP, dimension, worldServer, worldServer1);
				entityPlayerMP.worldObj.theProfiler.endStartSection("reloading");
				Entity e = EntityList.createEntityByName(EntityList.getEntityString((Entity) entityPlayerMP), (World) worldServer1);
				
				if (e != null)
				{
					e.copyDataFrom((Entity) entityPlayerMP, true);
					e.setLocationAndAngles(x + 0.5D, y, z + 0.5D, yaw, 0.0F);
					worldServer1.spawnEntityInWorld(e);
				}
				
				entityPlayerMP.isDead = true;
				entityPlayerMP.worldObj.theProfiler.endSection();
				worldServer.resetUpdateEntityTick();
				worldServer1.resetUpdateEntityTick();
				entityPlayerMP.worldObj.theProfiler.endSection();
			}
		}		
	}
	
	//TODO HealthBarRenderer.java
	@SideOnly(Side.CLIENT)
	public static class HealthBarRenderer
	{
	    public static void renderHealthBar(EntityLivingBase entityLivingBase, float partialTicks, Entity viewPoint)
		{
			if (entityLivingBase.riddenByEntity != null)
			{
				return;
			}
			
			EntityLivingBase entity = entityLivingBase;
			
			while (entity.ridingEntity != null && entity.ridingEntity instanceof EntityLivingBase)
			{
				entity = (EntityLivingBase) entity.ridingEntity;
			}
			
			Minecraft mc = Minecraft.getMinecraft();
			
			float pastTranslate = 0.0F;
			
			while (entity != null)
			{			    
			    if (entityLivingBase.getDistanceToEntity(viewPoint) <= 24 && entityLivingBase.canEntityBeSeen(viewPoint))
			    {
			        if (!(entity instanceof IBossDisplayData) && !CommandSpectator.isSpectator())
			        {
			            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
                        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
                        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
                        
                        double yOffset = entity.getEyeHeight() + 0.6D;
                        
                        if (entity == mc.thePlayer)
                        {
                            yOffset = entity.getEyeHeight() + 0.5D;
                        }
                        
                        else
                        {
                            yOffset = entity.getEyeHeight() + 0.5D;
                        }
                        
                        float scale = 0.026666673F;
                        float maxHealth = entity.getMaxHealth();
                        float health = Math.min(maxHealth, entity.getHealth());
                        
                        if (maxHealth > 0.0F)
                        {
                            float percent = (int) (health / maxHealth * 100.0F);
                            
                            GL11.glPushMatrix();
                            GL11.glTranslatef((float) (x - RenderManager.renderPosX), (float) (y - RenderManager.renderPosY + yOffset) , (float) (z - RenderManager.renderPosZ));
                            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                            GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                            GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
                            GL11.glScalef(-scale, -scale, scale);
                            GL11.glDisable(2896);
                            GL11.glDepthMask(false);
                            GL11.glDisable(2929);
                            GL11.glDisable(3553);
                            GL11.glEnable(3042);
                            GL11.glBlendFunc(770, 771);
                            
                            Tessellator t = Tessellator.instance;
                                
                            float padding = 2.0F;
                            int backgroundHeight = 6;
                            int barHeight = 4;
                            float size = 25.0F;
                                
                            int r = 0, g = 255, b = 0;
                                
                            ItemStack stack = null;
                                
                            if (entity instanceof IBossDisplayData)
                            {
                                stack = new ItemStack(Items.skull);
                                size = 50.0F;
                                r = 128;
                                g = 0;
                                b = 128;
                            }
                                
                            int armor = entity.getTotalArmorValue();
                            
                            float hue = (Math.max(0.0F, health / maxHealth / 3.0F - 0.07F));
                            Color color = Color.getHSBColor(hue, 1.0F, 1.0F);
                            r = color.getRed();
                            g = color.getGreen();
                            b = color.getBlue();
                            
                            GL11.glTranslatef(0.0F, pastTranslate, 0.0F);
                                
                            float s = 0.5F;
                                
                            String name = I18n.format("entity." + EntityList.getEntityString((Entity) entity) + ".name");
                            
                            if (entity instanceof EntityLiving && ((EntityLiving) entity).hasCustomNameTag())
                            {
                                name = EnumChatFormatting.ITALIC + ((EntityLiving) entity).getCustomNameTag();
                            }
                            
                            if (entity instanceof EntityPlayer)
                            {
                                name = CommandNickname.getNickname((EntityPlayer) entity);
                                
                                GradeType gradeType = RegistryGrades.getGrade(((EntityPlayer) entity).getUniqueID());
                                
                                if (gradeType != null && gradeType != GradeType.NOT_SET)
                                {
                                    name = gradeType.getColor() + name;
                                }
                            }
                            
                            float namel = mc.fontRenderer.getStringWidth(name) * s;
                                
                            if (namel + 20.0F > size * 2.0F)
                            {
                                size = namel / 2.0F + 10.0F;
                            }
                                
                            float healthSize = size * health / maxHealth;
                            
                            t.startDrawingQuads();
                            t.setColorRGBA(0, 0, 0, 64);
                            t.addVertex((-size - padding), -backgroundHeight, 0.0D);
                            t.addVertex((-size - padding), (barHeight + padding), 0.0D);
                            t.addVertex((size + padding), (barHeight + padding), 0.0D);
                            t.addVertex((size + padding), -backgroundHeight, 0.0D);
                            t.draw();
                                
                            t.startDrawingQuads();
                            t.setColorRGBA(127, 127, 127, 127);
                            t.addVertex(-size, 0.0D, 0.0D);
                            t.addVertex(-size, barHeight, 0.0D);
                            t.addVertex(size, barHeight, 0.0D);
                            t.addVertex(size, 0.0D, 0.0D);
                            t.draw();
                                
                            t.startDrawingQuads();
                            t.setColorRGBA(r, g, b, 127);
                            t.addVertex(-size, 0.0D, 0.0D);
                            t.addVertex(-size, barHeight, 0.0D);
                            t.addVertex((healthSize * 2.0F - size), barHeight, 0.0D);
                            t.addVertex((healthSize * 2.0F - size), 0.0D, 0.0D);
                            t.draw();
                                
                            GL11.glEnable(3553);
                            GL11.glPushMatrix();
                            GL11.glTranslatef(-size, -4.5F, 0.0F);
                            GL11.glScalef(s, s, s);
                            
                            mc.fontRenderer.drawString(name, 0, 0, 16777215);
                                
                            GL11.glPushMatrix();
                            float s1 = 0.75F;
                            GL11.glScalef(s1, s1, s1);
                            
                            int h = 14;
                                
                            String maxHpStr = EnumChatFormatting.BOLD + "" + (Math.round(maxHealth * 100.0D) / 100.0D);
                            String hpStr = "" + (Math.round(health * 100.0) / 100.0D);
                            String percStr = (int) percent + "%";
                                
                            if (maxHpStr.endsWith(".0"))
                            {
                                maxHpStr = maxHpStr.substring(0, maxHpStr.length() - 2);
                            }
                                    
                            if (hpStr.endsWith(".0"))
                            {
                                hpStr = hpStr.substring(0, hpStr.length() - 2);
                            }
                            
                            mc.fontRenderer.drawString(hpStr, 2, h, Color.WHITE.getRGB());
                            mc.fontRenderer.drawString(maxHpStr, (int) (size / s * s1 * 2.0F) - 2 - mc.fontRenderer.getStringWidth(maxHpStr), h, Color.WHITE.getRGB());
                            mc.fontRenderer.drawString(percStr, (int) (size / s * s1) - mc.fontRenderer.getStringWidth(percStr) / 2, h, Color.WHITE.getRGB());
                            
                           Combo combo = Combo.TARGET.get(entityLivingBase.getEntityId());
                            
                            if (combo != null && combo.render())
                            {
                                float rotation;
                                int color1;
                                
                                switch (combo.getCombo())
                                {
                                    case 1:
                                        rotation = -10.0F;
                                        color1 = 16777215;
                                        break;
                                        
                                    case 2:
                                        rotation = -20.0F;
                                        color1 = 16440904;
                                        break;
                                        
                                    case 3:
                                        rotation = 15.0F;
                                        color1 = 15104809;
                                        break;
                                        
                                    case 4:
                                        rotation = -15.0F;
                                        color1 = 15098779;
                                        break;
                                        
                                    case 5:
                                        rotation = 10.0F;
                                        color1 = 4678323;
                                        break;
                                        
                                    case 6:
                                        rotation = 0.0F;
                                        color1 = 2680795;
                                        break;
                                        
                                    default:
                                        rotation = 15.0F;
                                        color1 = FontRenderer.rainbowEffect(0 + (float) 0L * 2000.0F, 1.0F).getRGB();
                                        break;
                                }
                                
                                GL11.glPushMatrix();
                                GL11.glTranslatef((size + padding) * 3.7F, -backgroundHeight -17.0F, 0.0F);
                                GL11.glRotatef(rotation, 0.0F, 0.0F, 1.0F);
                                GL11.glScalef(2.0F, 2.0F, 2.0F);
                                
                                String message = combo.getMessage();
                                
                                long systemTime = mc.getSystemTime();
                                int xOffset = 0;
                                
                                for (int i = 0; i < message.length(); i++)
                                {
                                    char c = message.charAt(i);
                                    
                                    float progress = ((systemTime + i * 60L) % 1000L) / 1000.0F;
                                    float bounce = (float) Math.sin(progress * 1.0F * Math.PI) * 5.0F;
                                    
                                    GL11.glPushMatrix();
                                    GL11.glTranslatef(xOffset, bounce, 0.0F);
                                    
                                    mc.fontRenderer.drawStringWithShadow(String.valueOf(c), 0, 0, color1);
                                    
                                    GL11.glPopMatrix();
                                    
                                    xOffset += mc.fontRenderer.getCharWidth(c);
                                }
                                
                                GL11.glPopMatrix();
                            }
                                
                            GL11.glPopMatrix();
                            
                            GL11.glPopMatrix();
                            GL11.glEnable(2929);
                            GL11.glDepthMask(true);
                            GL11.glEnable(2896);
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                            GL11.glPopMatrix();
                            
                            pastTranslate = -((backgroundHeight + barHeight) + padding);
                        }
			        }
			    }
    				
			    Entity riddenByEntity = entity.riddenByEntity;
    				
			    if (riddenByEntity instanceof EntityLivingBase)
			    {
			        entity = (EntityLivingBase) riddenByEntity;
			        continue;
			    }
    				
			    return;
			}
		}
	}
	
	//TODO RenderUtils.java
	@SideOnly(Side.CLIENT)
	public static class RenderUtils
	{	    
	    public static void drawTexturedQuadFit(double x, double y, double width, double height, double zLevel)
        {
            Tessellator t = Tessellator.instance;
            t.startDrawingQuads();
            t.addVertexWithUV(x + 0.0D, y + height, zLevel, 0.0D, 1.0D);
            t.addVertexWithUV(x + width, y + height, zLevel, 1.0D, 1.0D);
            t.addVertexWithUV(x + width, y + 0.0D, zLevel, 1.0D, 0.0D);
            t.addVertexWithUV(x + 0.0D, y + 0.0D, zLevel, 0.0D, 0.0D);
            t.draw();
        }
	    
	    public static void drawStringWithHueRainbowEffect(String string, int x, int y, float brightness, boolean shadow)
	    {
	        int xPos = x;
	        
	        for (int i = 0; i < string.length(); i++)
	        {
	            String s = string.charAt(i) + "";
	            
	            if (shadow)
	            {
	                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(s, xPos, y, FontRenderer.rainbowHueEffect(i * 3500000L, brightness, 100).getRGB());
	            }
	            
	            Minecraft.getMinecraft().fontRenderer.drawString(s, xPos, y, FontRenderer.rainbowHueEffect(i * 3500000L, brightness, 100).getRGB());
	            xPos += Minecraft.getMinecraft().fontRenderer.getStringWidth(s);
	        }
	    }
	    
	    public static void drawCenteredStringWithScaledEffect(String string, float scale, int color)
	    {
	        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
	        
	        int screenWidth = scaledResolution.getScaledWidth();
	        int screenHeight = scaledResolution.getScaledHeight();
	        
	        GL11.glPushMatrix();
	        GL11.glScalef(scale, scale, scale);
	        
	        int x = (int) ((screenWidth / scale) / scale);
	        int y = (int) ((screenHeight / scale) / scale);
	        
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(string, x - Minecraft.getMinecraft().fontRenderer.getStringWidth(string) / 2, y - 10, color);
	        
	        GL11.glPopMatrix();
	    }
	    
	    public static void drawBottomCenteredString(String string, int y, int color)
	    {
	        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
	        
	        int screenWidth = scaledResolution.getScaledWidth();
            int screenHeight = scaledResolution.getScaledHeight();
            
            GL11.glPushMatrix();
            GL11.glTranslatef(screenWidth / 1.0F, screenHeight - 10, 0.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(string, - Minecraft.getMinecraft().fontRenderer.getStringWidth(string) / 1, y, color);
            
            GL11.glPopMatrix();
	    }
	    
	    public static void drawSizedTexture(int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight)
	    {
	        Tessellator tessellator = Tessellator.instance;
	        tessellator.startDrawingQuads();
	        
	        tessellator.addVertexWithUV(x, y + height, 0, u * 1.0F / textureWidth, (v + height) * 1.0F / textureHeight);
	        tessellator.addVertexWithUV(x + width, y + height, 0, (u + width) * 1.0F / textureWidth, (v + height) * 1.0F / textureHeight);
	        tessellator.addVertexWithUV(x + width, y, 0, (u + width) * 1.0F / textureWidth, v * 1.0F / textureHeight);
	        tessellator.addVertexWithUV(x, y, 0, u * 1.0F / textureWidth, v * 1.0F / textureHeight);
	        
	        tessellator.draw();
	    }
	    
	    public static void drawOutlinedBlock(float partialTicks, double xCoord, double yCoord, double zCoord, int color)
	    {
	        float posX = (float) Minecraft.getMinecraft().thePlayer.posX;
	        float posY = (float) Minecraft.getMinecraft().thePlayer.posY;
	        float posZ = (float) Minecraft.getMinecraft().thePlayer.posZ;
	        float prevPosX = (float) Minecraft.getMinecraft().thePlayer.prevPosX;
	        float prevPosY = (float) Minecraft.getMinecraft().thePlayer.prevPosY;
	        float prevPosZ = (float) Minecraft.getMinecraft().thePlayer.prevPosZ;
	        float prevPosX1 = prevPosX + (posX - prevPosX) * partialTicks;
	        float prevPosY1 = prevPosY + (posY - prevPosY) * partialTicks;
	        float prevPosZ1  = prevPosZ + (posZ - prevPosZ) * partialTicks;
	        
	        Tessellator tessellator = Tessellator.instance;
	        
	        tessellator.startDrawing(1);
	        tessellator.setColorRGBA_I(color, 255);
	        tessellator.setBrightness(200);
	        
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
	        
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
	        
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 1.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 1.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 0.0F, zCoord - prevPosZ1 + 0.0F);
	        tessellator.addVertex(xCoord - prevPosX1 + 0.0F, yCoord - prevPosY1 + 1.0F, zCoord - prevPosZ1 + 0.0F);
	        
	        tessellator.draw();
	    }
	    
	    public static void drawFullSizedImage(int width, int height)
	    {
	        Tessellator tessellator = Tessellator.instance;
	        
	        tessellator.startDrawingQuads();
	        
	        tessellator.addVertexWithUV(0.0D, height, 0.0D, 0.0D, 1.0D);
	        tessellator.addVertexWithUV(width, height, 0.0D, 1.0D, 1.0D);
	        tessellator.addVertexWithUV(width, 0.0D, 0.0D, 1.0D, 0.0D);
	        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	        
	        tessellator.draw();
	    }
	}
	
	//TODO ItemStackMap.java
	public static class ItemStackMap<T> extends AbstractMap<ItemStack, T>
	{
	    public static final NBTTagCompound WILDCARD_TAG;
	    
	    static
	    {
	        WILDCARD_TAG = new NBTTagCompound();
	        WILDCARD_TAG.setBoolean("*", true);
	    }
	    
	    private final HashMap<Item, DetailMap> itemMap = new HashMap<>();
	    private int size;
	    private final boolean NBTSensitive;
	    
	    static ItemStack newItemStack(Item item, int size, int damage, NBTTagCompound tag)
	    {
	        ItemStackSet set = null;
	        ItemStack stack = new ItemStack(item, size, damage);
	        stack.stackTagCompound = tag;
	        return stack;
	    }

	    static int actualDamage(ItemStack stack)
	    {
	        return Items.diamond.getDamage(stack);
	    }

	    static ItemStack wildcard(Item item)
	    {
	        return wildcard(item, false);
	    }

	    static ItemStack wildcard(Item item, boolean nbtsensitive)
	    {
	        return newItemStack(item, 1, WILDCARD_VALUE, nbtsensitive ? WILDCARD_TAG : null);
	    }

	    static boolean isWildcard(int damage)
	    {
	        return damage == WILDCARD_VALUE;
	    }

	    static boolean isWildcard(NBTTagCompound tag)
	    {
	        return tag != null && tag.getBoolean("*");
	    }

	    public ItemStackMap()
	    {
	        this.NBTSensitive = false;
	    }

	    public ItemStackMap(boolean NBTSensitive)
	    {
	        this.NBTSensitive = NBTSensitive;
	    }
	    
	    public T get(Object key) 
	    {
	        if (!(key instanceof ItemStack))
	        {
	            return null;
	        }
	        
	        ItemStack stack = ((ItemStack) key);
	        
	        if (stack.getItem() == null)
	        {
	            return null;
	        }
	        
	        DetailMap map = this.itemMap.get(stack.getItem());
	        
	        return map == null ? null : map.get(stack);
	    }
	    
	    public T put(ItemStack key, T value)
	    {
	        if (key == null || key.getItem() == null || value == null)
	        {
	            return null;
	        }
	        
	        return this.itemMap.computeIfAbsent(key.getItem(), k -> new DetailMap(this.NBTSensitive)).put(key, value);
	    }
	    
	    public T remove(Object key)
	    {
	        if (!(key instanceof ItemStack))
	        {
	            return null;
	        }
	        
	        ItemStack stack = ((ItemStack) key);
	        
	        if (stack.getItem() == null)
	        {
	            return null;
	        }
	        
	        DetailMap map = this.itemMap.get(stack.getItem());
	        
	        return map == null ? null : map.remove(stack);
	    }
	    
	    public boolean containsKey(Object key)
	    {
	        if (!(key instanceof ItemStack))
	        {
	            return false;
	        }
	        
	        ItemStack stack = ((ItemStack) key);
	        
	        if (stack.getItem() == null)
	        {
	            return false;
	        }
	        
	        DetailMap map = this.itemMap.get(stack.getItem());
	        
	        return map != null && map.get(stack) != null;
	    }
	    
	    public T merge(ItemStack key, T value, BiFunction<? super T, ? super T, ? extends T> remappingFunction)
	    {
	        if (key == null || key.getItem() == null || value == null || remappingFunction == null)
	        {
	            return null;
	        }
	        
	        DetailMap map = this.itemMap.get(key.getItem());
	        
	        return this.itemMap.computeIfAbsent(key.getItem(), k -> new DetailMap(this.NBTSensitive)).merge(key, value, remappingFunction);
	    }
	    
	    public T computeIfAbsent(ItemStack key, Function<? super ItemStack, ? extends T> mappingFunction)
	    {
	        if (key == null || key.getItem() == null || mappingFunction == null)
	        {
	            return null;
	        }
	        
	        DetailMap map = this.itemMap.get(key.getItem());
	        
	        return this.itemMap.computeIfAbsent(key.getItem(), k -> new DetailMap(this.NBTSensitive)).computeIfAbsent(key, mappingFunction);
	    }
	    
	    public void clear()
	    {
	        this.itemMap.clear();
	        this.size = 0;
	    }
	    
	    public Set<Map.Entry<ItemStack, T>> entrySet()
	    {
	        return new SetView();
	    }

	    private static class StackMetaKey
	    {
	        public final int damage;
	        public final NBTTagCompound tag;

	        public StackMetaKey(int damage, NBTTagCompound tag)
	        {
	            this.damage = damage;
	            this.tag = tag;
	        }

	        public StackMetaKey(ItemStack key)
	        {
	            this(actualDamage(key), key.stackTagCompound);
	        }

	        public int hashCode()
	        {
	            return Objects.hashCode(this.damage, this.tag);
	        }

	        public boolean equals(Object o)
	        {
	            if (!(o instanceof StackMetaKey))
	            {
	                return false;
	            }
	            
	            StackMetaKey t = (StackMetaKey) o;
	            
	            return this.damage == t.damage && Objects.equal(this.tag, t.tag);
	        }
	    }

	    private static class DetailIter<T> implements Iterator<Map.Entry<ItemStack, T>> {

	        private final Item owner;
	        private final ItemStackMap<T>.DetailMap backing;

	        @Nullable
	        private final Iterator<Map.Entry<Integer, T>> damageIter;

	        @Nullable
	        private final Iterator<Map.Entry<NBTTagCompound, T>> tagIter;

	        @Nullable
	        private final Iterator<Map.Entry<StackMetaKey, T>> metaIter;

	        private DetailIterState state = DetailIterState.NOT_STARTED;
	        private boolean removed = false;

	        private DetailIter(Map.Entry<Item, ItemStackMap<T>.DetailMap> input) {
	            this.owner = input.getKey();
	            this.backing = input.getValue();
	            damageIter = backing.damageMap != null ? backing.damageMap.entrySet().iterator() : null;
	            tagIter = backing.tagMap != null ? backing.tagMap.entrySet().iterator() : null;
	            metaIter = backing.metaMap != null ? backing.metaMap.entrySet().iterator() : null;
	        }

	        private DetailIterState nextState() {
	            switch (state) {
	                case NOT_STARTED:
	                    if (backing.hasWildcard) return DetailIterState.WILDCARD;
	                case WILDCARD:
	                case DAMAGE:
	                    if (damageIter != null && damageIter.hasNext()) return DetailIterState.DAMAGE;
	                case TAG:
	                    if (tagIter != null && tagIter.hasNext()) return DetailIterState.TAG;
	                case META:
	                    if (metaIter != null && metaIter.hasNext()) return DetailIterState.META;
	                case DONE:
	                    return DetailIterState.DONE;
	                default:
	                    throw new IllegalStateException("Unexpected value: " + state);
	            }
	        }

	        @Override
	        public void remove() {
	            if (removed) throw new IllegalStateException("remove() called twice");
	            state.remove(this);
	            removed = true;
	        }

	        @Override
	        public boolean hasNext() {
	            return nextState() != DetailIterState.DONE;
	        }

	        @Override
	        public Map.Entry<ItemStack, T> next() {
	            DetailIterState nextState = nextState();
	            if (nextState == DetailIterState.DONE) throw new NoSuchElementException();
	            state = nextState;
	            removed = false;
	            return nextState.get(this);
	        }

	        private enum DetailIterState {

	            NOT_STARTED {
	                @Override
	                <T> Map.Entry<ItemStack, T> get(DetailIter<T> iter) {
	                    throw new AssertionError("Should not call get on NOT_STARTED");
	                }

	                @Override
	                <T> void remove(DetailIter<T> iter) {
	                    throw new IllegalStateException("next() never called");
	                }
	            },
	            WILDCARD {
	                @Override
	                <T> Map.Entry<ItemStack, T> get(DetailIter<T> iter) {
	                    return new Map.Entry<ItemStack, T>() {

	                        private final ItemStack key = wildcard(iter.owner, iter.backing.NBTSensitive);

	                        @Override
	                        public ItemStack getKey() {
	                            return key;
	                        }

	                        @Override
	                        public T getValue() {
	                            return iter.backing.wildcard;
	                        }

	                        @Override
	                        public T setValue(T value) {
	                            return iter.backing.wildcard = value;
	                        }
	                    };
	                }

	                @Override
	                <T> void remove(DetailIter<T> iter) {
	                    assert iter.backing.hasWildcard;
	                    iter.backing.wildcard = null;
	                    iter.backing.hasWildcard = false;
	                }
	            },
	            DAMAGE {
	                @Override
	                <T> Map.Entry<ItemStack, T> get(DetailIter<T> iter) {
	                    assert iter.damageIter != null;
	                    Map.Entry<Integer, T> entry = iter.damageIter.next();
	                    return new ItemStackEntry<>(
	                            newItemStack(
	                                    iter.owner,
	                                    1,
	                                    entry.getKey(),
	                                    iter.backing.NBTSensitive ? WILDCARD_TAG : null),
	                            entry);
	                }

	                @Override
	                <T> void remove(DetailIter<T> iter) {
	                    assert iter.damageIter != null;
	                    iter.damageIter.remove();
	                }
	            },
	            TAG {
	                @Override
	                <T> Map.Entry<ItemStack, T> get(DetailIter<T> iter) {
	                    assert iter.tagIter != null;
	                    Map.Entry<NBTTagCompound, T> entry = iter.tagIter.next();
	                    return new ItemStackEntry<>(newItemStack(iter.owner, 1, WILDCARD_VALUE, entry.getKey()), entry);
	                }

	                @Override
	                <T> void remove(DetailIter<T> iter) {
	                    assert iter.tagIter != null;
	                    iter.tagIter.remove();
	                }
	            },
	            META {
	                @Override
	                <T> Map.Entry<ItemStack, T> get(DetailIter<T> iter) {
	                    assert iter.metaIter != null;
	                    Map.Entry<StackMetaKey, T> entry = iter.metaIter.next();
	                    return new ItemStackEntry<>(
	                            newItemStack(iter.owner, 1, entry.getKey().damage, entry.getKey().tag),
	                            entry);
	                }

	                @Override
	                <T> void remove(DetailIter<T> iter) {
	                    assert iter.metaIter != null;
	                    iter.metaIter.remove();
	                }
	            },
	            DONE {
	                @Override
	                <T> Map.Entry<ItemStack, T> get(DetailIter<T> iter) {
	                    throw new AssertionError("Should not call get on DONE");
	                }

	                @Override
	                <T> void remove(DetailIter<T> iter) {
	                    throw new AssertionError("Should not call remove on DONE");
	                }
	            };

	            abstract <T> Map.Entry<ItemStack, T> get(DetailIter<T> iter);

	            abstract <T> void remove(DetailIter<T> iter);

	            private static class ItemStackEntry<T> implements Map.Entry<ItemStack, T> {

	                private final ItemStack key;
	                private final Map.Entry<?, T> entry;

	                public ItemStackEntry(ItemStack key, Map.Entry<?, T> entry) {
	                    this.key = key;
	                    this.entry = entry;
	                }

	                @Override
	                public ItemStack getKey() {
	                    return key;
	                }

	                @Override
	                public T getValue() {
	                    return entry.getValue();
	                }

	                @Override
	                public T setValue(T value) {
	                    return entry.setValue(value);
	                }
	            }
	        }
	    }

	    private class DetailMap {

	        private boolean hasWildcard;
	        private T wildcard;
	        private HashMap<Integer, T> damageMap;
	        private HashMap<NBTTagCompound, T> tagMap;
	        private HashMap<StackMetaKey, T> metaMap;
	        private int size;

	        private final boolean NBTSensitive;

	        public DetailMap(boolean NBTSensitive) {
	            this.NBTSensitive = NBTSensitive;
	        }

	        private KeyType getKeyType(int damage, NBTTagCompound tag) {
	            KeyType i = isWildcard(damage) ? KeyType.WildcardMeta : KeyType.NotWildcard;
	            if (!NBTSensitive || isWildcard(tag)) i = i.withWildcardTag();
	            return i;
	        }

	        public T get(ItemStack key) {
	            if (wildcard != null) return wildcard;

	            if (damageMap != null) {
	                final T ret = damageMap.get(actualDamage(key));
	                if (ret != null) return ret;
	            }
	            if (tagMap != null) {
	                final T ret = tagMap.get(key.stackTagCompound);
	                if (ret != null) return ret;
	            }
	            if (metaMap != null) return metaMap.get(new StackMetaKey(key));

	            return null;
	        }

	        public T put(ItemStack key, T value) {
	            try {
	                switch (getKeyType(actualDamage(key), key.stackTagCompound)) {
	                    case NotWildcard:
	                        if (metaMap == null) metaMap = new HashMap<>();
	                        return metaMap.put(new StackMetaKey(key), value);
	                    case WildcardMeta:
	                        if (tagMap == null) tagMap = new HashMap<>();
	                        return tagMap.put(key.stackTagCompound, value);
	                    case WildcardTag:
	                        if (damageMap == null) damageMap = new HashMap<>();
	                        return damageMap.put(actualDamage(key), value);
	                    case WildcardAll:
	                        T ret = wildcard;
	                        wildcard = value;
	                        hasWildcard = true;
	                        return ret;
	                }
	            } finally {
	                updateSize();
	            }
	            return null;
	        }

	        public T remove(ItemStack key) {
	            try {
	                switch (getKeyType(actualDamage(key), key.stackTagCompound)) {
	                    case NotWildcard:
	                        return metaMap != null ? metaMap.remove(new StackMetaKey(key)) : null;
	                    case WildcardMeta:
	                        return tagMap != null ? tagMap.remove(key.stackTagCompound) : null;
	                    case WildcardTag:
	                        return damageMap != null ? damageMap.remove(actualDamage(key)) : null;
	                    case WildcardAll:
	                        T ret = wildcard;
	                        wildcard = null;
	                        hasWildcard = false;
	                        return ret;
	                }
	            } finally {
	                updateSize();
	            }
	            return null;
	        }

	        private void updateSize()
	        {
	            int newSize = (this.hasWildcard ? 1 : 0) + (this.metaMap != null ? this.metaMap.size() : 0) + (this.tagMap != null ? this.tagMap.size() : 0) + (this.damageMap != null ? this.damageMap.size() : 0);

	            if (newSize != this.size)
	            {
	                ItemStackMap.this.size += newSize - this.size;
	                this.size = newSize;
	            }
	        }

	        public T merge(ItemStack key, T value, BiFunction<? super T, ? super T, ? extends T> remappingFunction)
	        {
	            try
	            {
	                switch (getKeyType(actualDamage(key), key.stackTagCompound))
	                {
	                    case NotWildcard:
	                        
	                        if (this.metaMap == null)
	                        {
	                            this.metaMap = new HashMap<>();
	                        }
	                        
	                        return this.metaMap.merge(new StackMetaKey(key), value, remappingFunction);
	                        
	                    case WildcardMeta:
	                        
	                        if (this.tagMap == null)
	                        {
	                            this.tagMap = new HashMap<>();
	                        }
	                        
	                        return this.tagMap.merge(key.stackTagCompound, value, remappingFunction);
	                        
	                    case WildcardTag:
	                        
	                        if (this.damageMap == null)
	                        {
	                            this.damageMap = new HashMap<>();
	                        }
	                        
	                        return this.damageMap.merge(actualDamage(key), value, remappingFunction);
	                        
	                    case WildcardAll:
	                        
	                        T newValue = this.wildcard == null ? value : remappingFunction.apply(this.wildcard, value);
	                        this.wildcard = newValue;
	                        this.hasWildcard = newValue != null;
	                        
	                        return newValue;
	                }
	            }
	            
	            finally
	            {
	                this.updateSize();
	            }
	            
	            return null;
	        }

	        public T computeIfAbsent(ItemStack key, Function<? super ItemStack, ? extends T> mappingFunction)
	        {
	            try
	            {
	                switch (getKeyType(actualDamage(key), key.stackTagCompound))
	                {
	                    case NotWildcard
	                    :
	                        if (this.metaMap == null)
	                        {
	                            this.metaMap = new HashMap<>();
	                        }
	                        
	                        return this.metaMap.computeIfAbsent(new StackMetaKey(key), x -> mappingFunction.apply(key));
	                        
	                    case WildcardMeta:
	                        
	                        if (this.tagMap == null)
	                        {
	                            this.tagMap = new HashMap<>();
	                        }
	                        
	                        return this.tagMap.computeIfAbsent(key.stackTagCompound, x -> mappingFunction.apply(key));
	                        
	                    case WildcardTag:
	                        
	                        if (this.damageMap == null)
	                        {
	                            this.damageMap = new HashMap<>();
	                        }
	                        
	                        return this.damageMap.computeIfAbsent(actualDamage(key), x -> mappingFunction.apply(key));
	                        
	                    case WildcardAll:
	                        
	                        T newValue = this.wildcard == null ? mappingFunction.apply(key) : this.wildcard;
	                        this.wildcard = newValue;
	                        this.hasWildcard = newValue != null;
	                        
	                        return newValue;
	                }
	            }
	            
	            finally
	            {
	                this.updateSize();
	            }
	            
	            return null;
	        }
	    }

	    private class SetView extends AbstractSet<Map.Entry<ItemStack, T>>
	    {
	        public boolean contains(Object o) 
	        {
	            if (!(o instanceof Map.Entry))
	            {
	                return false;
	            }
	            
	            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
	            
	            return java.util.Objects.equals(get(entry.getKey()), entry.getValue());
	        }
	        
	        public boolean add(Map.Entry<ItemStack, T> itemStackTEntry)
	        {
	            return itemStackTEntry.getKey() != null && itemStackTEntry.getValue() != null && put(itemStackTEntry.getKey(), itemStackTEntry.getValue()) == null;
	        }
	        
	        public boolean remove(Object o)
	        {
	            if (!(o instanceof Map.Entry))
	            {
	                return false;
	            }
	            
	            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
	            
	            return ItemStackMap.this.remove(entry.getKey(), entry.getValue());
	        }

	        @Nonnull
	        public Iterator<Map.Entry<ItemStack, T>> iterator()
	        {
	            return Iterators.concat(Iterators.transform(itemMap.entrySet().iterator(), DetailIter::new));
	        }
	        
	        public int size()
	        {
	            return size;
	        }
	    }

	    private enum KeyType {

	        NotWildcard,
	        WildcardMeta,
	        WildcardTag,
	        WildcardAll,;

	        private static final KeyType[] VALUES = values();

	        public KeyType withWildcardMeta() 
	        {
	            return VALUES[ordinal() | 1];
	        }

	        public KeyType withWildcardTag() 
	        {
	            return VALUES[ordinal() | 2];
	        }
	    }
	}
	
	//TODO ItemStackSet.java
	public static class ItemStackSet extends ItemStackMap<ItemStack>
	{
	    public ItemStackSet() {}
	    
	    public ItemStackSet with(ItemStack... items)
	    {
	        ItemStack[] arr$ = items;
	        int len$ = items.length;
	        
	        for (int i$ = 0; i$ < len$; i$++)
	        {
	            ItemStack item = arr$[i$];
	            this.add(item);
	        }
	        
	        return this;
	    }
	    
	    public ItemStackSet with(Item... items)
	    {
	        Item[] arr$ = items;
	        int len$ = items.length;

	        for (int i$ = 0; i$ < len$; ++i$) 
	        {
	            Item item = arr$[i$];
	            this.add(ItemStackMap.wildcard(item));
	        }

	        return this;
	    }

	    public ItemStackSet with(Block... blocks)
	    {
	        Block[] arr$ = blocks;
	        int len$ = blocks.length;

	        for (int i$ = 0; i$ < len$; ++i$) 
	        {
	            Block block = arr$[i$];
	            this.add(ItemStackMap.wildcard(Item.getItemFromBlock(block)));
	        }

	        return this;
	    }
	    
	    public void add(ItemStack itemStack)
	    {
	        this.put(itemStack, itemStack);
	    }
	    
	    public boolean contains(ItemStack itemStack)
	    {
	        return this.get(itemStack) != null;
	    }
	    
	    public boolean containsAll(Item item)
	    {
	        return this.get(ItemStackMap.wildcard(item)) != null;
	    }
	    
	    public boolean matches(ItemStack itemStack)
	    {
	        return this.contains(itemStack);
	    }
	    
	    public static ItemStackSet of(Block... blocks)
	    {
	        return (new ItemStackSet()).with(blocks);
	    }
	    
	    public static ItemStackSet of(Item... items)
	    {
	        return (new ItemStackSet()).with(items);
	    }
	    
	    public static ItemStackSet of(ItemStack... itemStacks)
	    {
	        return (new ItemStackSet()).with(itemStacks);
	    }
	}
    
    //TODO Combo.java
	@SideOnly(Side.CLIENT)
    public static class Combo
    {
        private Minecraft mc;
        
        public static final Map<Integer, Combo> TARGET = new HashMap<>();
        
        private int combo = 0;
        private long lastHit = 0L, startTime = 0L;
        private String message = "";
        private boolean showCombo = true;
        
        public void addCombo()
        {
            long now = System.currentTimeMillis();
            
            if (now - this.startTime < 100L)
            {
                return;
            }
            
            if (now - this.lastHit <= Math.max(500L, 2000L - (this.combo - 1) * 250L))
            {
                this.combo++;
            }
            
            else
            {
                this.combo = 1;
            }
            
            this.lastHit = now;
            this.startTime = now;
            
            switch (this.combo)
            {
                case 1:
                    this.message = I18n.format("combo.1");
                    break;
                    
                case 2:
                    this.message = I18n.format("combo.2");
                    break;
                    
                case 3:
                    this.message = I18n.format("combo.3");
                    break;
                    
                case 4:
                    this.message = I18n.format("combo.4");
                    break;
                    
                case 5:
                    this.message = I18n.format("combo.5");
                    break;
                    
                case 6:
                    this.message = I18n.format("combo.6");
                    break;
                    
                default:
                    this.message = I18n.format("combo.7");
                    break;
            }
        }
        
        public boolean render()
        {
            return this.showCombo && System.currentTimeMillis() - this.lastHit <= 2000L;
        }
        
        public int getCombo()
        {
            return this.combo;
        }
        
        public String getMessage()
        {
            return this.message;
        }
    }
    
    public static class RandomXoshiro256StarStar extends Random
    {
        private static final long serialVersionUID = -2837799889588687855L;
        private static final AtomicLong uniq = new AtomicLong(System.nanoTime());
        
        private static final long nextUniq()
        {
            return splitmix64_2(uniq.addAndGet(SPLITMIX1_MAGIC));
        }
        
        public RandomXoshiro256StarStar()
        {
            this(System.nanoTime() ^ nextUniq());
        }
        
        public RandomXoshiro256StarStar(long seed)
        {
            super(seed);
        }
        
        public RandomXoshiro256StarStar(long s1, long s2, long s3, long s4)
        {
            this.setState(s1, s2, s3, s4);
        }
        
        private static final long SPLITMIX1_MAGIC = 0x9E3779B97F4A7C15L;
        
        private static long splitmix64_1(long x)
        {
            return (x + SPLITMIX1_MAGIC);
        }
        
        private static long splitmix64_2(long z)
        {
            z = (z ^ (z >> 30)) * 0xBF58476D1CE4E5B9L;
            z = (z ^ (z >> 27)) * 0x94D049BB133111EBL;
            
            return z ^ (z >> 31);
        }
        
        public void setSeed(long seed)
        {
            super.setSeed(seed);
            
            long sms = splitmix64_1(seed);
            
            this.s0 = splitmix64_2(sms);
            sms = splitmix64_1(sms);
            this.s1 = splitmix64_2(sms);
            sms = splitmix64_1(sms);
            this.s2 = splitmix64_2(sms);
            sms = splitmix64_1(sms);
            this.s3 = splitmix64_2(sms);
        }
        
        public void setState(long s0, long s1, long s2, long s4)
        {
            if (s0 == 0 && s1 == 0 && s2 == 0 && s4 == 0)
            {
                throw new IllegalArgumentException("xoshiro256** state cannot be all zeroes");
            }
            
            this.s0 = s0;
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s4;
        }
        
        protected int next(int bits)
        {
            return (int) (this.nextLong() & ((1L << bits) - 1));
        }
        
        public int nextInt()
        {
            return (int) this.nextLong();
        }
        
        public int nextInt(int bound)
        {
            return (int) this.nextLong(bound);
        }
        
        public long nextLong(long bound)
        {
            if (bound <= 0)
            {
                throw new IllegalArgumentException("bound must be positive");
            }
            
            return (this.nextLong() & Long.MAX_VALUE) % bound;
        }
        
        public double nextDouble()
        {
            return (this.nextLong() >>> 11) * 0x1.0P-53;
        }
        
        public float nextFloat()
        {
            return (this.nextLong() >>> 40) * 0x1.0P-24F;
        }
        
        public boolean nextBoolean()
        {
            return (this.nextLong() & 1) != 0;
        }
        
        public void nextBytes(byte[] buf)
        {
            this.nextBytes(buf, 0, buf.length);
        }
        
        public void nextBytes(byte[] buf, int ofs, int len)
        {
            if (ofs < 0)
            {
                throw new ArrayIndexOutOfBoundsException("Offset " + ofs + " is negative");
            }
            
            if (ofs >= buf.length)
            {
                throw new ArrayIndexOutOfBoundsException("Offset " + ofs + " is greater than buffer length");
            }
            
            if (ofs + len > buf.length)
            {
                throw new ArrayIndexOutOfBoundsException("Length " + len + " with offset " + ofs + " is past end of buffer");
            }
            
            int j = 8;
            long l = 0;
            
            for (int i = ofs; i < ofs + len; i++)
            {
                if (j >= 8)
                {
                    l = this.nextLong();
                    j = 0;
                }
                
                buf[i] = (byte) (l&0xFF);
                l = l >>> 8L;
                j++;
            }
        }
        
        private static long rotl(long x, int k)
        {
            return (x << k) | (x >>> (64 - k));
        }
        
        private long s0, s1, s2, s3;
        
        public long nextLong()
        {
            long result_starstar = rotl(this.s1 * 5, 7) * 9;
            
            long t = this.s1 << 17;
            
            this.s2 ^= this.s0;
            this.s3 ^= this.s1;
            this.s1 ^= this.s2;
            this.s0 ^= this.s3;
            
            this.s2 ^= t;
            
            this.s3 = rotl(this.s3, 45);
            
            return result_starstar;
        }
    }
    
    //TODO ExtendedData.java
    public static class ExtendedData
    {
        private final String propName;
        private final Class<? extends Entity> entityType;
        private final List<ExtendedProperty> properties;
        private final Class<? extends ExtendedEntityProperties> extended;
        
        public ExtendedData(String propName, Class<? extends Entity> entityType, ExtendedProperty[] properties, Class<? extends ExtendedEntityProperties> extended)
        {
            this.propName = propName;
            this.entityType = entityType;
            this.properties = Arrays.asList(properties);
            this.extended = extended;
        }
        
        public String getPropName()
        {
            return this.propName;
        }
        
        public Class<? extends Entity> getEntityType()
        {
            return this.entityType;
        }
        
        public List<ExtendedProperty> getProperties()
        {
            return this.properties;
        }
        
        public Class<? extends ExtendedEntityProperties> getExtended()
        {
            return this.extended;
        }
    }
    
    //TODO ExtendedEntityProperties.java
    public static abstract class ExtendedEntityProperties implements IExtendedEntityProperties
    {
        private String propName;
        private transient Entity entity;
        
        public ExtendedEntityProperties() {}
        
        public void init(Entity entity, World world)
        {
            this.entity = entity;
        }
        
        public void sync()
        {
            if (!(this.entity instanceof EntityPlayerMP))
            {
                return;
            }
            
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) this.entity;
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            
            this.saveNBTData(nbtTagCompound);
            
            ExtendedData extendedData = ExtendedUtils.getExtended().get(this.propName);
            
            C07PacketExtendedEntityProperties packet = new C07PacketExtendedEntityProperties(this.entity.getUniqueID(), this.propName, nbtTagCompound);
            
            if (extendedData.getProperties().contains(ExtendedProperty.SYNCHRONIZED))
            {
                WelloZMod.proxy.getNetworkManager().sendToPlayer(packet, entityPlayerMP);
            }
            
            if (extendedData.getProperties().contains(ExtendedProperty.SYNCHRONIZED_TRACKER))
            {
                EntityTracker entityTracker = ((WorldServer) entityPlayerMP.worldObj).getEntityTracker();
                
                for (EntityPlayer entityPlayer : entityTracker.getTrackingPlayers(entityPlayerMP))
                {
                    WelloZMod.proxy.getNetworkManager().sendToPlayer(packet, (EntityPlayerMP) entityPlayer);
                }
            }
        }
        
        public void sync(EntityPlayerMP entityPlayerMP)
        {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();

            this.saveNBTData(nbtTagCompound);
            
            WelloZMod.proxy.getNetworkManager().sendToPlayer(new C07PacketExtendedEntityProperties(this.entity.getUniqueID(), this.propName, nbtTagCompound), entityPlayerMP);
        }
        
        public void setPropName(String propName)
        {
            this.propName = propName;
        }
        
        public String getPropName()
        {
            return this.propName;
        }
        
        public Entity getEntity()
        {
            return this.entity;
        }
    }
    
    //TODO ExtendedProperty.java
    public static enum ExtendedProperty
    {
        SELF_CONSTRUCT, PERSISTANT, SYNCHRONIZED, SYNCHRONIZED_TRACKER;
    }
    
    //TODO ExtendedUtils.java
    public static class ExtendedUtils
    {
        private static Map<String, ExtendedData> extended = new HashMap<>();
        
        public static void init(Class<? extends Entity> entity, Class<? extends ExtendedEntityProperties> extended, String propName, ExtendedProperty... properties)
        {
            ExtendedUtils.extended.put(propName, new ExtendedData(propName, entity, properties, extended));
        }
        
        public static Map<String, ExtendedData> getExtended()
        {
            return extended;
        }
    }
}