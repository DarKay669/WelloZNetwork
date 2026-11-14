package fr.welloznetwork.wellozmod.client.listener;

import java.awt.Color;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.dev.Debug;
import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.client.audio.CustomMusicTicker;
import fr.welloznetwork.wellozmod.client.audio.ElytraSound;
import fr.welloznetwork.wellozmod.client.notification.Notification;
import fr.welloznetwork.wellozmod.client.notification.NotificationManager;
import fr.welloznetwork.wellozmod.client.notification.NotificationType;
import fr.welloznetwork.wellozmod.client.ui.UICredits;
import fr.welloznetwork.wellozmod.client.ui.UIIngameMenu;
import fr.welloznetwork.wellozmod.client.ui.UIMainMenu;
import fr.welloznetwork.wellozmod.client.ui.UISpyglass;
import fr.welloznetwork.wellozmod.client.ui.inventory.UIInventory;
import fr.welloznetwork.wellozmod.client.ui.v2.UINewIngameMenu;
import fr.welloznetwork.wellozmod.command.CommandNightVision;
import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import fr.welloznetwork.wellozmod.init.RegistryEmotes;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryKeys;
import fr.welloznetwork.wellozmod.mixins.client.IMixinMinecraftAccessor;
import fr.welloznetwork.wellozmod.modules.jobs.client.ui.UIJobs;
import fr.welloznetwork.wellozmod.util.ModUtil.Combo;
import fr.welloznetwork.wellozmod.util.ModUtil.HealthBarRenderer;
import fr.welloznetwork.wellozmod.util.ModUtil.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiCreateFlatWorld;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

@SideOnly(Side.CLIENT)
public class ClientListener
{
    private Minecraft mc = Minecraft.getMinecraft();
    private GuiScreen parentScreen;
    private float time = 0.0F, partialTicks = 0.0F;
    private final CustomMusicTicker musicTicker = new CustomMusicTicker(this.mc);
    public static float delta = 0.0F;
    private float prevYOffset;
    public static boolean zoomMode = false, jobNotifications = true;
    
    public ClientListener()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void debugFeedbackTranslated(String untranslatedTemplate)
    {
        this.mc.ingameGUI.getChatGUI().printChatMessage((new ChatComponentText("")).appendSibling((new ChatComponentTranslation("[Debug]:", new Object[0])).setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.YELLOW).setBold(Boolean.valueOf(true)))).appendText(" ").appendSibling(new ChatComponentText(untranslatedTemplate)));
    }
    
    private void renderDebugInfoLeft()
    {
        List<String> list = this.getDebugInfoLeft();
        
        for (int i = 0; i < list.size(); i++)
        {
            String s = (String) list.get(i);
            
            if (!Strings.isNullOrEmpty(s))
            {
                int j = this.mc.fontRenderer.FONT_HEIGHT;
                int k = this.mc.fontRenderer.getStringWidth(s);
                int l = 2;
                int i1 = 2 + j * i;
                Gui.drawRect(1, i1 - 1, 2 + k + 1, i1 + j - 1, -1873784752);
                this.mc.fontRenderer.drawString(s, 2, i1, 14737632);
            }
        }
    }
    
    private void renderDebugInfoRight(ScaledResolution scaledResolution) 
    {
        List<String> list = this.getDebugInfoRight();
        
        for (int i = 0; i < list.size(); i++)
        {
            String s = (String) list.get(i);
            
            if (!Strings.isNullOrEmpty(s))
            {
                int j = this.mc.fontRenderer.FONT_HEIGHT;
                int k = this.mc.fontRenderer.getStringWidth(s);
                int l = scaledResolution.getScaledWidth() - 2 - k;
                int i1 = 2 + j * i;
                Gui.drawRect(l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
                this.mc.fontRenderer.drawString(s, l, i1, 14737632);
            }
        }
    }
    
    private List<String> getDebugInfoLeft()
    {
        String[] directions = {"SOUTH", "WEST", "NORTH", "EAST"};
        BiomeGenBase biomeGenBase = this.mc.theWorld.getBiomeGenForCoords((int) this.mc.thePlayer.posX, (int) this.mc.thePlayer.posZ);
        
        List<String> list = Lists.newArrayList(new String[] {"WelloZNetwork (release)", this.mc.debug, "", "XYZ: " + (int) this.mc.renderViewEntity.posX + " / " + (int) this.mc.renderViewEntity.boundingBox.minY + " / " + (int) this.mc.renderViewEntity.posZ, "Direction: " + directions[(MathHelper.floor_double(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3)], "", "Biome: " + biomeGenBase.biomeName + " (" + biomeGenBase.biomeID + ")"});
        
        return list;
    }
    
    private List<String> getDebugInfoRight()
    {
        Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(this.mc.thePlayer.chunkCoordX, this.mc.thePlayer.chunkCoordZ);
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalUsedMemory = totalMemory - freeMemory;
        
        List<String> list = Lists.newArrayList(new String[] {String.format("Java: %s", new Object[] {System.getProperty("java.version")}), "Mem: " + (totalUsedMemory * 100L / maxMemory) + "% " + (totalUsedMemory / 1024L / 1024L) + "/" + (maxMemory / 1024L / 1024L) + "MB", "Allocated: " + (totalMemory * 100L / maxMemory) + "% " + (totalMemory / 1024L / 1024L) + "MB", GL11.glGetString(GL11.GL_RENDERER), "", "Block: (" + (int) this.mc.renderViewEntity.posX + " " + (int) this.mc.renderViewEntity.boundingBox.minY + " " + (int) this.mc.renderViewEntity.posZ + ")", "Light: " + EnumChatFormatting.RED + this.mc.theWorld.getSavedLightValue(EnumSkyBlock.Block, (int) this.mc.renderViewEntity.posX, (int) this.mc.renderViewEntity.boundingBox.minY, (int) this.mc.renderViewEntity.posZ)});
        
        return list;
    }
    
    private float getItemInUsePercentage(EntityPlayer entityPlayer, float speed)
    {
        float maxUse = entityPlayer.getItemInUse().getItem().getMaxItemUseDuration(entityPlayer.getItemInUse());
        float currentUse = entityPlayer.getItemInUseCount();
        float percentage = 0.0F;
        
        percentage = maxUse - currentUse;
        percentage *= speed * ((percentage > 100.0F) ? 1.0F : (percentage / 100.0F));
        percentage /= maxUse;
        percentage *= 1.0F + percentage;
        
        return (percentage > 1.0F) ? 1.0F : percentage;
    }
    
    private void setModelBipedVisible(ModelBiped modelBiped, boolean visible)
    {
        modelBiped.bipedHead.showModel = visible;
        modelBiped.bipedHeadwear.showModel = visible;
        modelBiped.bipedBody.showModel = visible;
        modelBiped.bipedRightArm.showModel = visible;
        modelBiped.bipedLeftArm.showModel = visible;
        modelBiped.bipedRightLeg.showModel = visible;
        modelBiped.bipedLeftLeg.showModel = visible;
    }
    
    @SubscribeEvent
    public void onKeyInputEvent(KeyInputEvent event)
    {
        if (Debug.isDebugMode() && RegistryKeys.KEY_BIND_TIMER.isPressed())
        {
            ((IMixinMinecraftAccessor) this.mc).getTimer().timerSpeed = 20.0F;
        }
        
        else
        {
            ((IMixinMinecraftAccessor) this.mc).getTimer().timerSpeed = 1.0F;
        }
        
        if (RegistryKeys.KEY_BIND_ADVANCEMENTS.isPressed())
        {
            if (this.mc.currentScreen instanceof GuiAchievements)
            {
                this.mc.displayGuiScreen(null);
            }
            
            else
            {
                this.mc.displayGuiScreen(new GuiAchievements(this.parentScreen, this.mc.thePlayer.getStatFileWriter()));
            }
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_F3))
        {
            if (Keyboard.isKeyDown(Keyboard.KEY_A))
            {
                NotificationManager.addNotification(new Notification(NotificationType.WARNING, "Reloading all chunks", 2));
            }
            
            else if (Keyboard.isKeyDown(Keyboard.KEY_B))
            {
                NotificationManager.addNotification(new Notification(NotificationType.WARNING, "Hitboxes: " + RenderManager.debugBoundingBox, 2));
            }
            
            else if (Keyboard.isKeyDown(Keyboard.KEY_D))
            {
                if (this.mc.ingameGUI != null)
                {
                    this.mc.ingameGUI.getChatGUI().clearChatMessages();
                }
            }
            
            else if (Keyboard.isKeyDown(Keyboard.KEY_F))
            {
                NotificationManager.addNotification(new Notification(NotificationType.WARNING, "Render distance: " + this.mc.gameSettings.renderDistanceChunks, 2));
            }
            
            else if (Keyboard.isKeyDown(Keyboard.KEY_H))
            {
                NotificationManager.addNotification(new Notification(NotificationType.WARNING, "Advanced tooltips: " + this.mc.gameSettings.advancedItemTooltips, 2));
            }
            
            else if (Keyboard.isKeyDown(Keyboard.KEY_N))
            {
                if (!this.mc.thePlayer.canCommandSenderUseCommand(2, ""))
                {
                    NotificationManager.addNotification(new Notification(NotificationType.ERROR, "Unable to switch gamemode, no permission", 2));
                }
                
                else if (this.mc.playerController.isInCreativeMode())
                {
                    this.mc.thePlayer.sendChatMessage("/spectator");
                }
                
                else if (CommandSpectator.isSpectator())
                {
                    this.mc.thePlayer.sendChatMessage("/gamemode creative");
                }
            }
            
            else if (Keyboard.isKeyDown(Keyboard.KEY_P))
            {
                NotificationManager.addNotification(new Notification(NotificationType.WARNING, "Pause on lost focus: " + this.mc.gameSettings.pauseOnLostFocus, 2));
            }
            
            else if (Keyboard.isKeyDown(Keyboard.KEY_Q))
            {
                this.debugFeedbackTranslated("Debug shortcuts");
                
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("F3+A = Reload chunks"));
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("F3+B = Show hitboxes"));
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("F3+D = Clear chat"));
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("F3+F = Cycle renderdistance (Shift to inverse)"));
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("F3+H = Advanced tooltips"));
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("F3+N = Cycle creative <-> spectator"));
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("F3+P = Pause on lost focus"));
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("F3+Q = Show F3 shortcuts list"));
                this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("F3+T = Reload resources"));
            }
            
            else if (Keyboard.isKeyDown(Keyboard.KEY_T))
            {
                NotificationManager.addNotification(new Notification(NotificationType.WARNING, "Reloaded resources", 2));
            }
        }
        
        if (Constants.V2)
        {
            if (RegistryKeys.KEY_BIND_JOB_OPEN_UI.isPressed())
            {
                if (this.mc.currentScreen instanceof UIJobs)
                {
                    this.mc.displayGuiScreen(null);
                }
                
                else
                {
                    this.mc.displayGuiScreen(new UIJobs(this.parentScreen));
                }
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPreRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event)
    {
        if (Debug.isDebugMode())
        {
            if (event.type == ElementType.TEXT)
            {
                this.mc.fontRenderer.drawStringWithShadow("Minecraft 1.7.10", 2, 2, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow(this.mc.debug, 2, 12, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("NTSC Debug Code : " + Debug.NTSC_DEBUG_CODE, 2, 22, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("PLAYER INFO", 2, 42, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("name: " + this.mc.getSession().getUsername(), 2, 52, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("x: " + (int) this.mc.thePlayer.posX, 2, 62, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("y: " + (int) this.mc.thePlayer.posY, 2, 72, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("z: " + (int) this.mc.thePlayer.posZ, 2, 82, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("sprint: " + this.mc.thePlayer.isSprinting(), 2, 92, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("fly: " + this.mc.thePlayer.capabilities.isFlying, 2, 102, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("reach distance: " + this.mc.playerController.getBlockReachDistance() + " blocks", 2, 112, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("creative mode: " + this.mc.thePlayer.capabilities.isCreativeMode, 2, 122, Color.WHITE.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("spectator mode: " + CommandSpectator.isSpectator(), 2, 132, Color.WHITE.getRGB());
                
                this.mc.fontRenderer.drawStringWithShadow("LEVEL INFO", 2, 152, Color.WHITE.getRGB());
                
                if (this.mc.isSingleplayer())
                {
                    this.mc.fontRenderer.drawStringWithShadow("name: " + MinecraftServer.getServer().getWorldName(), 2, 162, Color.WHITE.getRGB());
                }
                
                this.mc.fontRenderer.drawStringWithShadow("dimension: " + this.mc.thePlayer.dimension, 2, 172, Color.white.getRGB());
                this.mc.fontRenderer.drawStringWithShadow("biome: " + this.mc.theWorld.getBiomeGenForCoords((int) this.mc.thePlayer.posX, (int) this.mc.thePlayer.posZ).biomeName + " (" + this.mc.theWorld.getBiomeGenForCoords((int) this.mc.thePlayer.posX, (int) this.mc.thePlayer.posZ).biomeID + ")", 2, 182, Color.WHITE.getRGB());
                
                this.mc.fontRenderer.drawStringWithShadow(String.format("Display: %dx%d (%s %s)", Display.getWidth(), Display.getHeight(), GL11.glGetString(GL11.GL_RENDERER), GL11.glGetString(GL11.GL_VERSION)), 2, 202, Color.WHITE.getRGB());
            }
            
            else if (event.type == ElementType.DEBUG)
            {
                event.setCanceled(true);
            }
        }
        
        else if (event.type == ElementType.DEBUG)
        {
            this.mc.mcProfiler.startSection("debug");
            
            GL11.glPushMatrix();
            
            this.renderDebugInfoLeft();
            this.renderDebugInfoRight(new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight));
            
            GL11.glPopMatrix();
            
            this.mc.mcProfiler.endSection();
            
            event.setCanceled(true);
        }
        
        if (this.mc.thePlayer.getItemInUse() != null && this.mc.thePlayer.getItemInUse().getItem() == RegistryItems.spyglass) 
        {
            if (UISpyglass.INSTANCE != null && event.type == ElementType.ALL)
            {
                UISpyglass.INSTANCE.draw();
            }
        }
    }
    
    @SubscribeEvent
    public void onGuiOpenEvent(GuiOpenEvent event)
    {
        if ((event.gui != null) && ((event.gui.getClass() == GuiMainMenu.class) || (event.gui.getClass().getName().equalsIgnoreCase("net.minecraft.client.gui.GuiMainMenu"))))
        {
            event.gui = new UIMainMenu();
        }
        
        if ((event.gui != null) && ((event.gui.getClass() == GuiIngameMenu.class) || (event.gui.getClass().getName().equalsIgnoreCase("net.minecraft.client.gui.GuiIngameMenu"))))
        {
            event.gui = new UIIngameMenu();
        }
        
        if ((event.gui != null) && ((event.gui.getClass() == GuiInventory.class) || (event.gui.getClass().getName().equalsIgnoreCase("net.minecraft.client.gui.inventory.GuiInventory"))))
        {
            event.gui = new UIInventory(this.mc.thePlayer);
        }
        
        if ((event.gui != null) && ((event.gui.getClass() == GuiWinGame.class) || (event.gui.getClass().getName().equalsIgnoreCase("net.minecraft.client.gui.GuiWinGame"))))
        {
            event.gui = new UICredits();
        }
        
        if (Constants.V2)
        {
            if ((event.gui != null) && ((event.gui.getClass() == UIIngameMenu.class) || (event.gui.getClass().getName().equalsIgnoreCase("fr.welloznetwork.wellozmod.client.ui.UIIngameMenu"))))
            {
                event.gui = new UINewIngameMenu();
            }
        }
        
        if (!Debug.isDebugMode())
        {
            if ((event.gui != null) && ((event.gui.getClass() == GuiSelectWorld.class) || ((event.gui.getClass() == GuiMultiplayer.class) || ((event.gui.getClass() == GuiMainMenu.class)) || ((event.gui.getClass() == GuiCreateFlatWorld.class)) || ((event.gui.getClass() == GuiCreateWorld.class)))))
            {
                FMLCommonHandler.instance().exitJava(-1, true);
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onAttackEntityEvent(AttackEntityEvent event)
    {
        Combo.TARGET.computeIfAbsent(event.target.getEntityId(), entityId -> new Combo()).addCombo();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event)
    {
        if (CommandSpectator.isSpectator())
        {
            if (event.type == ElementType.CROSSHAIRS || event.type == ElementType.EXPERIENCE || event.type == ElementType.FOOD || event.type == ElementType.HEALTH || event.type == ElementType.HOTBAR)
            {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onFogDensity(FogDensity event)
    {
        if (event.entity instanceof EntityPlayer)
        {
            if (CommandSpectator.isSpectator())
            {
                if (event.block.getMaterial().isLiquid())
                {
                    event.setCanceled(true);
                    event.density = 0.0F;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onFOVUpdateEvent(FOVUpdateEvent event)
    {
        if (!event.entity.isUsingItem())
        {
            return;
        }
        
        if (event.entity.getItemInUse().getItem() == RegistryItems.spyglass) 
        {
            event.newfov = event.fov / (event.fov + 6.5F * this.getItemInUsePercentage(event.entity, 1500.0F));
        }
    }
    
    @SubscribeEvent
    public void onRenderBlockOverlayEvent(RenderBlockOverlayEvent event)
    {
        if (CommandSpectator.isSpectator())
        {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onSetArmorModel(SetArmorModel event)
    {
        if (CommandSpectator.isSpectator())
        {
            event.result = 0;
        }
    }
    
    @SubscribeEvent
    public void onRenderPlayerEventSpecials(Specials.Pre event)
    {
        if (CommandSpectator.isSpectator())
        {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent event)
    {
        if (event.type == ElementType.EXPERIENCE || event.type == ElementType.FOOD || event.type == ElementType.JUMPBAR)
        {
            return;
        }
        
        if (!event.isCancelable())
        {
            return;
        }
        
        if (this.mc.thePlayer.ridingEntity != null)
        {
            GuiIngameForge.renderFood = true;
        }
        
        if (GuiIngameForge.renderExperiance)
        {
            if (this.mc.thePlayer.ridingEntity instanceof EntityHorse)
            {
                GuiIngameForge.renderJumpBar = this.mc.gameSettings.keyBindJump.isPressed();
            }
        }
    }
    
    @SubscribeEvent()
    public void onRenderWorldLastEvent(RenderWorldLastEvent event)
    {
        if (!this.mc.isGuiEnabled())
        {
            return;
        }
        
        EntityLivingBase entityLivingBase = this.mc.renderViewEntity;
        Vec3 vec3 = entityLivingBase.getPosition(event.partialTicks);
        Frustrum frustrum = new Frustrum();
        
        double viewX = entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * event.partialTicks;
        double viewY = entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * event.partialTicks;
        double viewZ = entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * event.partialTicks;
        
        frustrum.setPosition(viewX, viewY, viewZ);
        
        WorldClient worldClient = this.mc.theWorld;
        Set<Entity> entities = (Set<Entity>) ReflectionHelper.getPrivateValue(WorldClient.class, worldClient, new String[] {"entityList", "field_73032_d", "J"});
        
        for (Entity entity : entities)
        {
            if (entity != null && entity instanceof EntityLivingBase && entity.isInRangeToRender3d(vec3.xCoord, vec3.yCoord, vec3.zCoord) && (entity.ignoreFrustumCheck || frustrum.isBoundingBoxInFrustum(entity.boundingBox)) && entity.isEntityAlive() && !entity.isInvisible())
            {
                if (entity == mc.thePlayer)
                {
                    continue;
                }
                
                HealthBarRenderer.renderHealthBar((EntityLivingBase) entity, event.partialTicks, (Entity) entityLivingBase);
            }
        }
        
        if (CommandSpectator.isSpectator())
        {
            for (Object o : this.mc.theWorld.loadedTileEntityList)
            {
                if (o.getClass().getName().toLowerCase().contains("chest") || o.getClass().getName().toLowerCase().contains("barrel") || o.getClass().getName().toLowerCase().contains("spawner"))
                {
                    TileEntity tileEntity = (TileEntity) o;
                    
                    GL11.glDisable(3553);
                    GL11.glDisable(2929);
                    GL11.glDisable(2884);
                    GL11.glDepthMask(false);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glLineWidth(3.0F);
                    
                    RenderUtils.drawOutlinedBlock(event.partialTicks, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, Color.WHITE.getRGB());
                    
                    GL11.glDepthMask(true);
                    GL11.glDisable(3042);
                    GL11.glEnable(3553);
                    GL11.glEnable(2929);
                    GL11.glEnable(2884);
                }
            }
        }
        
        this.partialTicks = event.partialTicks;
    }
    
    @SubscribeEvent
    public void onClientTickEvent(ClientTickEvent event)
    {
        if (event.phase == Phase.START)
        {
            this.time = (float) Math.ceil(this.time);
        }
        
        EntityClientPlayerMP entityClientPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
        
        if (entityClientPlayerMP != null && CommandSpectator.isSpectator())
        {
            if (!entityClientPlayerMP.capabilities.isFlying)
            {
                entityClientPlayerMP.capabilities.isFlying = true;
                entityClientPlayerMP.sendPlayerAbilities();
            }
        }
                
        if (event.phase == Phase.END)
        {
            if (event.type.equals(TickEvent.Type.CLIENT))
            {
                if (!this.mc.isGamePaused())
                {
                    if (!this.musicTicker.playingRecord())
                    {
                        this.musicTicker.update();
                    }
                }
            }
        }
        
        if (!(this.mc.getSoundHandler().isSoundPlaying(this.musicTicker.getRecord())))
        {
            this.musicTicker.trackRecord(null);
        }
        
        else
        {
            this.musicTicker.stopMenuMusic();
        }
    }
    
    @SubscribeEvent
    public void onRenderPlayerEvent(RenderPlayerEvent.Pre event)
    {
        if (CommandSpectator.isSpectator())
        {
            this.setModelBipedVisible(event.renderer.modelBipedMain, false);
            
            event.renderer.modelBipedMain.bipedHead.showModel = true;
            event.renderer.modelBipedMain.bipedHeadwear.showModel = true;
        }
        
        else
        {
            this.setModelBipedVisible(event.renderer.modelBipedMain, true);
        }
    }
    
    @SubscribeEvent
    public void onRenderLivingEvent(RenderLivingEvent.Pre event)
    {
        if (event.entity != null && (event.entity instanceof EntityLivingBase))
        {
            EntityLivingBase entityLivingBase = (EntityLivingBase) event.entity;
            
            boolean ambientRendering = false, aggressiveRendering = false, passiveRendering = false, ambientRendering1 = true, aggressiveRendering1 = true, passiveRendering1 = true;
            
            double x = Math.abs(entityLivingBase.posX - this.mc.thePlayer.posX);
            double y = Math.abs(entityLivingBase.posY - this.mc.thePlayer.posY);
            double z = Math.abs(entityLivingBase.posZ - this.mc.thePlayer.posZ);
            
            if (entityLivingBase instanceof EntityAmbientCreature)
            {
                if (x > 32 || y > 24 || z > 32)
                {
                    ambientRendering = !ambientRendering1;
                }
            }
            
            else if (entityLivingBase instanceof EntityMob)
            {
                if (x > 64 || y > 24 || z > 64)
                {
                    aggressiveRendering = !aggressiveRendering1;
                }
            }
            
            else if (entityLivingBase instanceof EntityAnimal)
            {
                if (x > 48 || y > 24 || z > 48)
                {
                    passiveRendering = !passiveRendering1;
                }
            }
            
            if (ambientRendering && entityLivingBase instanceof EntityAmbientCreature || aggressiveRendering && entityLivingBase instanceof EntityMob || passiveRendering && entityLivingBase instanceof EntityAnimal)
            {
                event.setCanceled(true);
                
                EntityLivingBase entityLivingBase1 = (EntityLivingBase) entityLivingBase;
                entityLivingBase1.setInvisible(true);
            }
            
            else if (entityLivingBase.isInvisible())
            {
                EntityLivingBase entityLivingBase1 = (EntityLivingBase) entityLivingBase;
                entityLivingBase1.setInvisible(false);
            }
        }
    }
    
    @SubscribeEvent
    public void onPlaySoundEvent17(PlaySoundEvent17 event)
    {
        if (event.result == null)
        {
            return;
        }
        
        if (event.result.getPositionedSoundLocation().toString().equals("minecraft:music.menu"))
        {
            this.musicTicker.trackMinecraftMusic(event.result);
            
            if (this.mc.theWorld == null && !(this.mc.currentScreen instanceof GuiScreenWorking))
            {
                event.result = null;
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderTickEvent(RenderTickEvent event)
    {
        if (event.phase == Phase.START)
        {
            float time1 = (float) Math.floor(this.time) + event.renderTickTime;
            
            delta = (time1 - this.time) * 50.0F;
            this.time = time1;
            
            RegistryEmotes.clearPlayerList();
        }
        
        EntityPlayer entityPlayer = this.mc.thePlayer;
        
        if (!(entityPlayer instanceof IEntityPlayer))
        {
            return;
        }
        
        if (((IEntityPlayer) entityPlayer).isElytraFlying())
        {
            if (event.phase == Phase.START)
            {
                this.prevYOffset = entityPlayer.yOffset;
                
                if (this.mc.gameSettings.thirdPersonView == 0)
                {
                    entityPlayer.yOffset = 3.02F;
                }
            }
            
            else
            {
                entityPlayer.yOffset = this.prevYOffset;
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent event)
    {
        /*if (event.isCanceled())
        {
            return;
        }*/
        
        if (event.entityLiving instanceof EntityPlayer)
        {
            if (CommandSpectator.isSpectator() || CommandNightVision.isNightVision())
            {
                this.mc.gameSettings.gammaSetting = 100.0F;
            }
            
            else
            {
                this.mc.gameSettings.gammaSetting = 1.0F;
            }
        }
        
        if (event.entityLiving instanceof EntityPlayer)
        {
            IEntityPlayer iEntityPlayer = (IEntityPlayer) event.entityLiving;
            
            if (iEntityPlayer.isElytraFlying() && !iEntityPlayer.lastElytraFlying())
            {
                this.mc.getSoundHandler().playSound(new ElytraSound((EntityPlayer) event.entityLiving));
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderLivingEvent(RenderLivingEvent.Specials.Pre event)
    {
        if (!(event.entity instanceof EntityPlayer))
        {
            EntityLivingBase entityLivingBase = this.mc.renderViewEntity;
            Frustrum frustrum = new Frustrum();
            Vec3 vec3 = entityLivingBase.getPosition(this.partialTicks);
            
            frustrum.setPosition(entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * this.partialTicks, entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * this.partialTicks, entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * this.partialTicks);
            
            if (event.entity.isInRangeToRender3d(vec3.xCoord, vec3.yCoord, vec3.zCoord) && (event.entity.ignoreFrustumCheck || frustrum.isBoundingBoxInFrustum(event.entity.boundingBox)) && event.entity.isEntityAlive() && !event.entity.canEntityBeSeen(entityLivingBase) && event.entity instanceof EntityLiving && ((EntityLiving) event.entity).hasCustomNameTag())
            {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event)
    {
        if (event.type == ElementType.ALL)
        {
            NotificationManager.renderNotification();
        }
    }
}