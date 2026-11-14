package fr.welloznetwork.wellozmod.modules.hud.client.listener;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Throwables;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.render.FontRenderer;
import fr.welloznetwork.venus.lib.time.TimeType;
import fr.welloznetwork.wellozmod.modules.hud.client.network.TimeChangerNetHandler;
import fr.welloznetwork.wellozmod.modules.hud.client.resources.ShaderResourcePack;
import fr.welloznetwork.wellozmod.modules.hud.client.ui.UICPS;
import fr.welloznetwork.wellozmod.modules.hud.client.ui.UIHUD;
import fr.welloznetwork.wellozmod.modules.hud.client.ui.UIKeystrokes;
import fr.welloznetwork.wellozmod.modules.hud.client.ui.UIReachDisplay;
import fr.welloznetwork.wellozmod.modules.hud.client.ui.UIToggleSprint;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import fr.welloznetwork.wellozmod.modules.hud.util.HUDUtil;
import fr.welloznetwork.wellozmod.modules.hud.util.HUDUtil.KeyMode;
import fr.welloznetwork.wellozmod.modules.hud.util.HUDUtil.Keys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.client.util.JsonException;
import net.minecraft.network.INetHandler;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

@SideOnly(Side.CLIENT)
public class HUDClientListener
{
    private Minecraft mc = Minecraft.getMinecraft();
    private GuiScreen parentScreen;
    public TimeType timeType = TimeType.VANILLA;
    private String rangeText = "Hasn't attacked";
    private long lastAttack, start;
    private final List<Long> clicks = new ArrayList<>();
    private Field listShaders;
    public int minXCPS, minYCPS, maxXCPS, maxYCPS, minXKeystrokes, minYKeystrokes, maxXKeystrokes, maxYKeystrokes, minXReachDisplay, minYReachDisplay, maxXReachDisplay, maxYReachDisplay, minXToggleSprint, minYToggleSprint, maxXToggleSprint, maxYToggleSprint;
    
    @Nonnull
    private ShaderResourcePack shaderResourcePack = new ShaderResourcePack();
    
    public HUDClientListener()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    public void init()
    {
        ((List<ShaderResourcePack>) ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), new String[] {"field_110449_ao", "defaultResourcePacks"})).add(this.shaderResourcePack);
        ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this.shaderResourcePack);
    }
    
    private int getClicks()
    {
        Iterator<Long> iterator = this.clicks.iterator();
        
        while (iterator.hasNext())
        {
            if (iterator.next() < System.currentTimeMillis() - 1000L)
            {
                iterator.remove();
            }
        }
        
        return this.clicks.size();
    }
    
    public int getBackgroundColor()
    {
        int color = 75000000;
        int a = (color >>> 24);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        
        float progress = this.getProgress();
        
        a = (int) (a * progress);
        r = (int) (r * progress);
        g = (int) (g * progress);
        b = (int) (b * progress);
        
        return a << 24 | r << 16 | g << 8 | b;
    }
    
    private float getProgress()
    {
        return Math.min((float) (System.currentTimeMillis() - this.start) / 200, 1.0F);
    }
    
    @SubscribeEvent
    public void onInitGuiEvent(InitGuiEvent.Post event)
    {
        if (event.gui != null && event.gui.getClass().getName().equalsIgnoreCase("fr.welloznetwork.wellozmod.client.gui.CustomGuiIngameMenu"))
        {
            event.buttonList.add(new GuiButton(1000, event.gui.width / 2 + 2, event.gui.height / 4 + 96 + (byte) -16, 98, 20, "HUD"));
        }
    }
    
    @SubscribeEvent
    public void onActionPerformedEvent(ActionPerformedEvent.Post event)
    {
        if (event.button.id == 1000)
        {
            this.mc.displayGuiScreen(new UIHUD(this.parentScreen));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onAttackEntityEvent(AttackEntityEvent event)
    {
        if (RegistrySettings.reachDisplayEnabled)
        {
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY && this.mc.objectMouseOver.entityHit.getEntityId() == event.target.getEntityId())
            {
                this.rangeText = (new DecimalFormat(".##")).format(this.mc.objectMouseOver.hitVec.distanceTo(this.mc.renderViewEntity.getPosition(1.0F))) + " blocks";
            }
            
            else
            {
                this.rangeText = "Not on target?";
            }
        }
        
        this.lastAttack = System.currentTimeMillis();
    }
    
    @SubscribeEvent
    public void onClientTickEvent(ClientTickEvent event)
    {
        if (this.mc.theWorld != null)
        {
            INetHandler iNetHandler = this.mc.thePlayer.sendQueue.getNetworkManager().getNetHandler();
            
            if (!(iNetHandler instanceof TimeChangerNetHandler))
            {
                this.mc.thePlayer.sendQueue.getNetworkManager().setNetHandler((INetHandler) new TimeChangerNetHandler((NetHandlerPlayClient) iNetHandler));
            }
            
            if (this.timeType == TimeType.FAST)
            {
                this.mc.theWorld.setWorldTime((long) (System.currentTimeMillis() * 1.0D % 24000.0D));
            }
        }
    }
    
    @SubscribeEvent
    public void onMouseEvent(MouseEvent event)
    {
        if (event.button == 0 && event.buttonstate)
        {
            this.clicks.add(System.currentTimeMillis());
        }
    }
    
    @SubscribeEvent
    public void onRenderTickEventCPS(RenderTickEvent event)
    {
        if (this.mc.gameSettings.showDebugInfo)
        {
            return;
        }
        
        if ((this.mc.currentScreen == null || this.mc.currentScreen instanceof UICPS) && this.mc.theWorld != null && RegistrySettings.cpsEnabled)
        {
            int cpsWidth;
            String cpsText = this.getClicks() + " CPS";
            
            int minX = RegistrySettings.cpsX;
            int minY = RegistrySettings.cpsY;
            int maxX = RegistrySettings.cpsX + 4 + this.mc.fontRenderer.getStringWidth(cpsText);
            int maxY = RegistrySettings.cpsY + 4 + this.mc.fontRenderer.FONT_HEIGHT;
            
            Gui.drawRect(minX, minY, RegistrySettings.cpsX + 58, RegistrySettings.cpsY + 13, 2130706432);
            
            int cpsColor = Integer.MAX_VALUE;
            
            if (UICPS.color == 0)
            {
                cpsColor = 2130706432;
            }
            
            else if (UICPS.color == 1)
            {
                cpsColor = 2130706602;
            }
    
            else if (UICPS.color == 2)
            {
                cpsColor = 2130749952;
            }
            
            else if (UICPS.color == 3)
            {
                cpsColor = 2130750122;
            }
            
            else if (UICPS.color == 4)
            {
                cpsColor = 2141847552;
            }
            
            else if (UICPS.color == 5)
            {
                cpsColor = 2141847722;
            }
            
            else if (UICPS.color == 6)
            {
                cpsColor = 2147461632;
            }
            
            else if (UICPS.color == 7)
            {
                cpsColor = 2141891242;
            }
            
            else if (UICPS.color == 8)
            {
                cpsColor = 2136298837;
            }
            
            else if (UICPS.color == 9)
            {
                cpsColor = 2136299007;
            }
            
            else if (UICPS.color == 10)
            {
                cpsColor = 2136342357;
            }
            
            else if (UICPS.color == 11)
            {
                cpsColor = 2136342527;
            }
            
            else if (UICPS.color == 12)
            {
                cpsColor = 2147439957;
            }
            
            else if (UICPS.color == 13)
            {
                cpsColor = 2147440127;
            }
            
            else if (UICPS.color == 14)
            {
                cpsColor = 2147483477;
            }
            
            else if (UICPS.color == 15)
            {
                cpsColor = Integer.MAX_VALUE;
            }
            
            else if (UICPS.color == 16)
            {
                cpsColor = FontRenderer.rainbowEffect(0 + (float) 0L * 2000.0F, 1.0F).getRGB();
            }
            
            if (this.getClicks() > 9)
            {
                cpsWidth = 12;
            }
            
            else
            {
                cpsWidth = 15;
            }
                        
            if (UICPS.color == 17)
            {
                HUDUtil.drawStringWithHueRainbowEffect(cpsText, minX + cpsWidth, minY + 2, 0.6F, true);
            }
            
            else
            {
                this.mc.fontRenderer.drawString(cpsText, minX + cpsWidth, minY + 2, cpsColor);
            }
            
            this.minXCPS = minX;
            this.minYCPS = minY;
            this.maxXCPS = maxX;
            this.maxYCPS = maxY;
            
            if (this.mc.currentScreen != null)
            {
                this.mc.currentScreen.handleInput();
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderTickEventKeystrokes(RenderTickEvent event)
    {
        if (this.mc.gameSettings.showDebugInfo)
        {
            return;
        }
        
        if ((this.mc.currentScreen == null || this.mc.currentScreen instanceof UIKeystrokes) && this.mc.theWorld != null && RegistrySettings.keystrokesEnabled)
        {
            KeyMode mode = KeyMode.values()[RegistrySettings.keystrokesMode];
            
            int minX = RegistrySettings.keystrokesX;
            int minY = RegistrySettings.keystrokesY;
            int maxX = RegistrySettings.keystrokesX + 4 + this.mc.fontRenderer.getStringWidth("");
            int maxY = RegistrySettings.keystrokesY + 4 + this.mc.fontRenderer.FONT_HEIGHT;
            
            int keyColor = Integer.MAX_VALUE;
            
            if (UIKeystrokes.color == 0)
            {
                keyColor = 2130706602;
            }
            
            else if (UIKeystrokes.color == 1)
            {
                keyColor = 2130706602;
            }
            
            else if (UIKeystrokes.color == 2) 
            {
                keyColor = 2130749952;
            }
            
            else if (UIKeystrokes.color == 3) 
            {
                keyColor = 2130750122;
            }
            
            else if (UIKeystrokes.color == 4) 
            {
                keyColor = 2141847552;
            }
            
            else if (UIKeystrokes.color == 5)
            {
                keyColor = 2141847722;
            }
            
            else if (UIKeystrokes.color == 6)
            {
                keyColor = 2147461632;
            }
            
            else if (UIKeystrokes.color == 7) 
            {
                keyColor = 2141891242;
            }
            
            else if (UIKeystrokes.color == 8)
            {
                keyColor = 2136298837;
            }
            
            else if (UIKeystrokes.color == 9) 
            {
                keyColor = 2136299007;
            }
            
            else if (UIKeystrokes.color == 10)
            {
                keyColor = 2136342357;
            }
            
            else if (UIKeystrokes.color == 11) 
            {
                keyColor = 2136342527;
            }
            
            else if (UIKeystrokes.color == 12) 
            {
                keyColor = 2147439957;
            }
            
            else if (UIKeystrokes.color == 13) 
            {
                keyColor = 2147440127;
            }
            
            else if (UIKeystrokes.color == 14) 
            {
                keyColor = 2147483477;
            }
            
            else if (UIKeystrokes.color == 15) 
            {
                keyColor = Integer.MAX_VALUE;
            }
            
            else if (UIKeystrokes.color == 16)
            {
                keyColor = FontRenderer.rainbowEffect(0 + (float) 0L * 2000.0F, 1.0F).getRGB();
            }
            
            int keyColorPressed = 2130706432;
            
            if (UIKeystrokes.colorPressed == 0)
            {
                keyColorPressed = 2130706432;
            }
            
            else if (UIKeystrokes.colorPressed == 1) 
            {
                keyColorPressed = 2130706602;
            }
            
            else if (UIKeystrokes.colorPressed == 2) 
            {
                keyColorPressed = 2130749952;
            }
            
            else if (UIKeystrokes.colorPressed == 3)
            {
                keyColorPressed = 2130750122;
            }
            
            else if (UIKeystrokes.colorPressed == 4)
            {
                keyColorPressed = 2141847552;
            }
            
            else if (UIKeystrokes.colorPressed == 5) 
            {
                keyColorPressed = 2141847722;
            }
            
            else if (UIKeystrokes.colorPressed == 6)
            {
                keyColorPressed = 2147461632;
            }
            
            else if (UIKeystrokes.colorPressed == 7)
            {
                keyColorPressed = 2141891242;
            }
            
            else if (UIKeystrokes.colorPressed == 8)
            {
                keyColorPressed = 2136298837;
            }
            
            else if (UIKeystrokes.colorPressed == 9)
            {
                keyColorPressed = 2136299007;
            }
            
            else if (UIKeystrokes.colorPressed == 10)
            {
                keyColorPressed = 2136342527;
            }
            
            else if (UIKeystrokes.colorPressed == 11) 
            {
                keyColorPressed = 2136342357;
            }
            
            else if (UIKeystrokes.colorPressed == 12) 
            {
                keyColorPressed = 2147439957;
            }
            
            else if (UIKeystrokes.colorPressed == 13) 
            {
                keyColorPressed = 2147440127;
            }
            
            else if (UIKeystrokes.colorPressed == 14)
            {
                keyColorPressed = 2147483477;
            }
            
            else if (UIKeystrokes.colorPressed == 15) 
            {
                keyColorPressed = Integer.MAX_VALUE;
            }
            
            else if (UIKeystrokes.colorPressed == 16)
            {
                keyColorPressed = FontRenderer.rainbowEffect(0 + (float) 0L * 2000.0F, 1.0F).getRGB();
            }
            
            for (Keys key : mode.getKeys())
            {
                int textWidth = this.mc.fontRenderer.getStringWidth(key.getKeyName());
                Gui.drawRect(minX + key.getX(), minY + key.getY(), minX + key.getX() + key.getWidth(), minY + key.getY() + key.getHeight(), key.getIsKeyPressed() ? Integer.MAX_VALUE : 2130706432);
                this.mc.fontRenderer.drawString(key.getKeyName(), minX + key.getX() + key.getWidth() / 2 - textWidth / 2, minY + key.getY() + key.getHeight() / 2 - 4, key.getIsKeyPressed() ? keyColorPressed : keyColor);
            }
            
            this.minXKeystrokes = minX;
            this.minYKeystrokes = minY;
            this.maxXKeystrokes = maxX;
            this.maxYKeystrokes = maxY;
            
            if (this.mc.currentScreen != null)
            {
                this.mc.currentScreen.handleInput();
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayEventReachDisplay(RenderTickEvent event)
    {
        if (this.mc.gameSettings.showDebugInfo)
        {
            return;
        }
        
        if ((this.mc.currentScreen == null || this.mc.currentScreen instanceof UIReachDisplay) && this.mc.theWorld != null && RegistrySettings.reachDisplayEnabled)
        {
            if (System.currentTimeMillis() - this.lastAttack > 2000L)
            {
                this.rangeText = "Hasn't attacked";
            }
            
            int minX = RegistrySettings.reachDisplayX;
            int minY = RegistrySettings.reachDisplayY;
            int maxX = RegistrySettings.reachDisplayX + 4 + this.mc.fontRenderer.getStringWidth(this.rangeText);
            int maxY = RegistrySettings.reachDisplayY + 4 + this.mc.fontRenderer.FONT_HEIGHT;
            
            Gui.drawRect(minX, minY, maxX, maxY, 2130706432);
            
            int reachDisplayColor = Integer.MAX_VALUE;
            
            if (UIReachDisplay.color == 0)
            {
                reachDisplayColor = 2130706432;
            }
            
            else if (UIReachDisplay.color == 1)
            {
                reachDisplayColor = 2130706602;
            }
    
            else if (UIReachDisplay.color == 2)
            {
                reachDisplayColor = 2130749952;
            }
            
            else if (UIReachDisplay.color == 3)
            {
                reachDisplayColor = 2130750122;
            }
            
            else if (UIReachDisplay.color == 4)
            {
                reachDisplayColor = 2141847552;
            }
            
            else if (UIReachDisplay.color == 5)
            {
                reachDisplayColor = 2141847722;
            }
            
            else if (UIReachDisplay.color == 6)
            {
                reachDisplayColor = 2147461632;
            }
            
            else if (UIReachDisplay.color == 7)
            {
                reachDisplayColor = 2141891242;
            }
            
            else if (UIReachDisplay.color == 8)
            {
                reachDisplayColor = 2136298837;
            }
            
            else if (UIReachDisplay.color == 9)
            {
                reachDisplayColor = 2136299007;
            }
            
            else if (UIReachDisplay.color == 10)
            {
                reachDisplayColor = 2136342357;
            }
            
            else if (UIReachDisplay.color == 11)
            {
                reachDisplayColor = 2136342527;
            }
            
            else if (UIReachDisplay.color == 12)
            {
                reachDisplayColor = 2147439957;
            }
            
            else if (UIReachDisplay.color == 13)
            {
                reachDisplayColor = 2147440127;
            }
            
            else if (UIReachDisplay.color == 14)
            {
                reachDisplayColor = 2147483477;
            }
            
            else if (UIReachDisplay.color == 15)
            {
                reachDisplayColor = Integer.MAX_VALUE;
            }
            
            else if (UIReachDisplay.color == 16)
            {
                reachDisplayColor = FontRenderer.rainbowEffect(0 + (float) 0L * 2000.0F, 1.0F).getRGB();
            }
            
            if (UIReachDisplay.color == 17)
            {
                HUDUtil.drawStringWithHueRainbowEffect(this.rangeText, minX + 2, minY + 2, 0.6F, true);
            }
            
            else
            {
                this.mc.fontRenderer.drawString(this.rangeText, minX + 2, minY + 2, reachDisplayColor);
            }
            
            this.minXReachDisplay = minX;
            this.minYReachDisplay = minY;
            this.maxXReachDisplay = maxX;
            this.maxYReachDisplay = maxY;
            
            if (this.mc.currentScreen != null)
            {
                this.mc.currentScreen.handleInput();
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderTickEventToggleSprint(RenderTickEvent event)
    {
        if (this.mc.gameSettings.showDebugInfo)
        {
            return;
        }
        
        if ((this.mc.currentScreen == null || this.mc.currentScreen instanceof UIToggleSprint) && this.mc.theWorld != null && RegistrySettings.toggleSprintEnabled)
        {
            String toggleSprintText = "[Sprinting (Toggled)]";
            
            int minX = RegistrySettings.toggleSprintX;
            int minY = RegistrySettings.toggleSprintY;
            int maxX = RegistrySettings.toggleSprintX + 4 + this.mc.fontRenderer.getStringWidth(toggleSprintText);
            int maxY = RegistrySettings.toggleSprintY + 4 + this.mc.fontRenderer.FONT_HEIGHT;
            
            Gui.drawRect(minX, minY, maxX, maxY, 2130706432);
            
            int toggleSprintColor = Integer.MAX_VALUE;
            
            if (UIToggleSprint.color == 0)
            {
                toggleSprintColor = 2130706432;
            }
            
            else if (UIToggleSprint.color == 1)
            {
                toggleSprintColor = 2130706602;
            }
    
            else if (UIToggleSprint.color == 2)
            {
                toggleSprintColor = 2130749952;
            }
            
            else if (UIToggleSprint.color == 3)
            {
                toggleSprintColor = 2130750122;
            }
            
            else if (UIToggleSprint.color == 4)
            {
                toggleSprintColor = 2141847552;
            }
            
            else if (UIToggleSprint.color == 5)
            {
                toggleSprintColor = 2141847722;
            }
            
            else if (UIToggleSprint.color == 6)
            {
                toggleSprintColor = 2147461632;
            }
            
            else if (UIToggleSprint.color == 7)
            {
                toggleSprintColor = 2141891242;
            }
            
            else if (UIToggleSprint.color == 8)
            {
                toggleSprintColor = 2136298837;
            }
            
            else if (UIToggleSprint.color == 9)
            {
                toggleSprintColor = 2136299007;
            }
            
            else if (UIToggleSprint.color == 10)
            {
                toggleSprintColor = 2136342357;
            }
            
            else if (UIToggleSprint.color == 11)
            {
                toggleSprintColor = 2136342527;
            }
            
            else if (UIToggleSprint.color == 12)
            {
                toggleSprintColor = 2147439957;
            }
            
            else if (UIToggleSprint.color == 13)
            {
                toggleSprintColor = 2147440127;
            }
            
            else if (UIToggleSprint.color == 14)
            {
                toggleSprintColor = 2147483477;
            }
            
            else if (UIToggleSprint.color == 15)
            {
                toggleSprintColor = Integer.MAX_VALUE;
            }
            
            else if (UIToggleSprint.color == 16)
            {
                toggleSprintColor = FontRenderer.rainbowEffect(0 + (float) 0L * 2000.0F, 1.0F).getRGB();
            }
            
            if (UIToggleSprint.color == 17)
            {
                HUDUtil.drawStringWithHueRainbowEffect(toggleSprintText, minX + 2, minY + 2, 0.6F, true);
            }
            
            else
            {
                this.mc.fontRenderer.drawString(toggleSprintText, minX + 2, minY + 2, toggleSprintColor);
            }
            
            this.minXToggleSprint = minX;
            this.minYToggleSprint = minY;
            this.maxXToggleSprint = maxX;
            this.maxYToggleSprint = maxY;
            
            if (this.mc.currentScreen != null)
            {
                this.mc.currentScreen.handleInput();
            }
        }
    }
    
    @SubscribeEvent
    public void onGuiOpenEventBlur(GuiOpenEvent event) throws JsonException
    {
        if (this.listShaders == null)
        {
            this.listShaders = ReflectionHelper.findField(ShaderGroup.class, new String[] {"field_148031_d", "listShaders"});
        }
        
        if (this.mc.theWorld != null && ShaderLinkHelper.getStaticShaderLinkHelper() != null)
        {
            EntityRenderer entityRenderer = this.mc.entityRenderer;
            
            if (RegistrySettings.blurEnabled && !entityRenderer.isShaderActive() && event.gui != null && !ArrayUtils.contains(new String[] {GuiChat.class.getName()}, event.gui.getClass().getName()))
            {
                entityRenderer.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), new ResourceLocation("shaders/post/fade_in_blur.json"));
                entityRenderer.updateShaderGroupSize(this.mc.displayWidth, this.mc.displayHeight);
                
                start = System.currentTimeMillis();
            }
            
            else if (entityRenderer.isShaderActive() && event.gui == null)
            {
                entityRenderer.deactivateShader();
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderTickEventBlur(RenderTickEvent event)
    {
        if (event.phase == Phase.END && this.mc.currentScreen != null && this.mc.entityRenderer.isShaderActive())
        {            
            try
            {
                List<Shader> shaders = (List<Shader>) this.listShaders.get(this.mc.entityRenderer.getShaderGroup());
                
                for (Shader shader : shaders)
                {
                    ShaderUniform shaderUniform = shader.getShaderManager().func_147991_a("Progress");
                    
                    if (shaderUniform != null)
                    {
                        shaderUniform.func_148090_a(this.getProgress());
                    }
                }
            }
            
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                Throwables.propagate(e);
            }
        }
    }
}