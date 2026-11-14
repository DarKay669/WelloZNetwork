package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.entity.player.CustomEntityPlayer;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.network.play.server.S02PacketBuy;
import fr.welloznetwork.wellozmod.util.ModUtil.RenderUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UIShop extends GuiScreen
{
    private static final String ASSETS_ITEMS_MOD_PATH = "wellozmod:textures/items/";
    private static final String ASSETS_ITEMS_MINECRAFT_PATH = "textures/items/";
    private static final List<Item> LIST = Arrays.asList(new Item(I18n.format("item.appleGold.name"), 100, 5, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "apple_golden.png"), new ItemStack(Items.golden_apple)), new Item(I18n.format("item.carrotGolden.name"), 100, 5, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "carrot_golden.png"), new ItemStack(Items.golden_carrot)), new Item(I18n.format("item.bootsChain.name"), 1000, 3, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "chainmail_boots.png"), new ItemStack(Items.chainmail_boots)), new Item(I18n.format("item.chestplateChain.name"), 1000, 3, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "chainmail_chestplate.png"), new ItemStack(Items.chainmail_chestplate)), new Item(I18n.format("item.helmetChain.name"), 1000, 3, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "chainmail_helmet.png"), new ItemStack(Items.chainmail_helmet)), new Item(I18n.format("item.leggingsChain.name"), 1000, 3, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "chainmail_leggings.png"), new ItemStack(Items.chainmail_leggings)), new Item(I18n.format("item.flintAndSteel.name"), 500, 10, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "flint_and_steel.png"), new ItemStack(Items.flint_and_steel)), new Item(I18n.format("item.melon.name"), 300, 20, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "melon.png"), new ItemStack(Items.melon)), new Item(I18n.format("item.nameTag.name"), 500, 10, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "name_tag.png"), new ItemStack(Items.name_tag)), new Item(I18n.format("item.saddle.name"), 100, 10, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "saddle.png"), new ItemStack(Items.saddle)), new Item(I18n.format("item.shears.name"), 200, 5, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "shears.png"), new ItemStack(Items.shears)), new Item(I18n.format("item.sign.name"), 150, 20, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "sign.png"), new ItemStack(Items.sign)), new Item("item.skull.creeper.name", 100000, 1, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "skull_creeper.png"), new ItemStack(Items.skull, 1, 4)), new Item(I18n.format("item.skull.skeleton.name"), 100000, 1, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "skull_skeleton.png"), new ItemStack(Items.skull, 1, 0)), new Item(I18n.format("item.skull.char.name"), 100000, 1, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "skull_steve.png"), new ItemStack(Items.skull, 1, 3)), new Item(I18n.format("item.skull.zombie.name"), 100000, 1, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "skull_zombie.png"), new ItemStack(Items.skull, 1, 2)), new Item(I18n.format("item.slimeball.name"), 500, 10, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "slimeball.png"), new ItemStack(Items.slime_ball)), new Item(I18n.format("item.spiderEye.name"), 600, 10, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "spider_eye.png"), new ItemStack(Items.spider_eye)), new Item(I18n.format("item.sugar.name"), 300, 15, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "sugar.png"), new ItemStack(Items.sugar)), new Item(I18n.format("item.wheat.name"), 1000, 10, new ResourceLocation(ASSETS_ITEMS_MINECRAFT_PATH + "wheat.png"), new ItemStack(Items.wheat)), new Item(I18n.format("item.elytra.name"), 1000000, 1, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "elytra.png"), new ItemStack(RegistryItems.elytra)), new Item(I18n.format("item.cat_paw_boots.name"), 100000, 1, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "cat_paw_boots.png"), new ItemStack(RegistryItems.cat_paw_boots)), new Item(I18n.format("item.darkness_splash_potion.name"), 100000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "darkness_splash_potion.png"), new ItemStack(RegistryItems.sickness_splash_potion, 1, 3)), new Item(I18n.format("item.disc_fragment_5.name"), 10000, 1, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "disc_fragment_5.png"), new ItemStack(RegistryItems.disc_fragment_5)), new Item(I18n.format("item.explosive_splash_potion.name"), 100000, 1, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "explosive_splash_potion.png"), new ItemStack(RegistryItems.sickness_splash_potion, 1, 2)), new Item(I18n.format("item.glow_berries.name"), 1000, 10, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "glow_berries.png"), new ItemStack(RegistryItems.glow_berries)), new Item(I18n.format("item.glow_ink_sac.name"), 1000, 10, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "glow_ink_sac.png"), new ItemStack(RegistryItems.glow_ink_sac)), new Item(I18n.format("item.heart_of_the_sea.name"), 10000, 2, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "heart_of_the_sea.png"), new ItemStack(RegistryItems.heart_of_the_sea)), new Item(I18n.format("item.max_repel.name"), 100000, 5, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "max_repel.png"), new ItemStack(RegistryItems.max_repel)), new Item(I18n.format("item.phantom_membrane.name"), 1000, 5, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "phantom_membrane.png"), new ItemStack(RegistryItems.phantom_membrane)), new Item(I18n.format("item.pizza.name"), 100, 20, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "pizza.png"), new ItemStack(RegistryItems.pizza)), new Item(I18n.format("item.potion_fire.name"), 100000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "potion_fire.png"), new ItemStack(RegistryItems.potionFire)), new Item(I18n.format("item.potion_glowing.name"), 100000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "potion_glowing.png"), new ItemStack(RegistryItems.potionGlowing)), new Item(I18n.format("item.potion_levitation.name"), 100000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "potion_levitation.png"), new ItemStack(RegistryItems.potionLevitation)), new Item(I18n.format("item.potion_poison.name"), 100000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "potion_poison.png"), new ItemStack(RegistryItems.potionPoison)), new Item(I18n.format("item.potion_step.name"), 100000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "potion_step.png"), new ItemStack(RegistryItems.potionStep)), new Item(I18n.format("item.potion_waterjump.name"), 100000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "potion_waterjump.png"), new ItemStack(RegistryItems.potionWaterJump)), new Item(I18n.format("item.potion_wither.name"), 100000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "potion_wither.png"), new ItemStack(RegistryItems.potionWither)), new Item(I18n.format("item.repel.name"), 1500, 20, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "repel.png"), new ItemStack(RegistryItems.repel)), new Item(I18n.format("item.ruby.name"), 100000, 5, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "ruby.png"), new ItemStack(RegistryItems.ruby)), new Item(I18n.format("item.shark.name"), 100, 10, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "shark.png"), new ItemStack(RegistryItems.shark)), new Item(I18n.format("item.sickness_splash_potion.name"), 100000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "sickness_splash_potion.png"), new ItemStack(RegistryItems.sickness_splash_potion, 1, 0)), new Item(I18n.format("item.spyglass.name"), 1000, 2, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "spyglass.png"), new ItemStack(RegistryItems.spyglass)), new Item(I18n.format("item.super_repel.name"), 5000, 15, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "super_repel.png"), new ItemStack(RegistryItems.super_repel)), new Item(I18n.format("item.sweet_berries.name"), 1000, 10, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "sweet_berries.png"), new ItemStack(RegistryItems.sweet_berries)), new Item(I18n.format("item.totem_of_undying.name"), 1000000, 1, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "totem_of_undying.png"), new ItemStack(RegistryItems.totem_of_undying)), new Item(I18n.format("item.trident.name"), 1000000, 1, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "trident.png"), new ItemStack(RegistryItems.trident)), new Item(I18n.format("item.warped_fungus_on_a_stick.name"), 10000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "warped_fungus_on_a_stick.png"), new ItemStack(RegistryItems.warped_fungus_on_a_stick)), new Item(I18n.format("item.web_splash_potion.name"), 100000, 3, new ResourceLocation(ASSETS_ITEMS_MOD_PATH + "web_splash_potion.png"), new ItemStack(RegistryItems.sickness_splash_potion, 1, 1)));
    private final List<Item> items = new ArrayList<>();
    private int selectedItem = 0, money = 0;
    private String title = "Shop";
    private boolean init = false;
    
    public UIShop()
    {
        this.init = false;
    }
    
    public void initGui()
    {
        this.title = I18n.format("shop.title");
        
        if (!this.init)
        {
            List<Item> list = new ArrayList<>(LIST);
            Collections.shuffle(list);
            
            this.items.clear();
            this.items.addAll(list.subList(0, 5));
            
            this.init = true;
        }
        
        this.selectedItem = 0;
        
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 60, this.height - 30, 50, 20, I18n.format("shop.buy")));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 10, this.height - 30, 50, 20, I18n.format("shop.back")));
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, Color.WHITE.getRGB());
        this.drawCenteredString(this.fontRendererObj, I18n.format("shop.money") + " " + EnumChatFormatting.GREEN + this.money + "$", this.width / 2, 35, Color.WHITE.getRGB());
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        int x = this.width / 2 - (this.items.size() * 44), y = 100;
        
        for (int i = 0; i < this.items.size(); i++)
        {
            Item item = this.items.get(i);
            
            int x1 = x + i * 100;
            
            boolean isHovered = (mouseX >= x1 && mouseX <= x1 + 32 && mouseY >= y + 2 && mouseY <= y + 2 + 32);
            
            if (i == this.selectedItem)
            {                
                this.drawHorizontalLine(x1 + 1, x1 + 1 + 32 + 1, y + 1, Color.WHITE.getRGB());
                this.drawHorizontalLine(x1 + 1, x1 + 1 + 32, y + 1 + 32 + 1, Color.WHITE.getRGB());
                this.drawVerticalLine(x1 + 1, y + 1, y + 1 + 32 + 1, Color.WHITE.getRGB());
                this.drawVerticalLine(x1 + 1 + 32 + 1, y + 1, y + 1 + 32 + 2, Color.WHITE.getRGB());
            }
            
            else if (isHovered)
            {
                this.selectedItem = i;
                
                this.drawHorizontalLine(x1 + 1, x1 + 1 + 32 + 1, y + 1, Color.WHITE.getRGB());
                this.drawHorizontalLine(x1 + 1, x1 + 1 + 32, y + 1 + 32 + 1, Color.WHITE.getRGB());
                this.drawVerticalLine(x1 + 1, y + 1, y + 1 + 32 + 1, Color.WHITE.getRGB());
                this.drawVerticalLine(x1 + 1 + 32 + 1, y + 1, y + 1 + 32 + 2, Color.WHITE.getRGB());
            }
            
            this.mc.getTextureManager().bindTexture(item.resourceLocation);
            RenderUtils.drawSizedTexture(x1 + 2, y + 2, 0.0F, 0.0F, 32, 32, 32, 32);
            
            this.drawCenteredString(this.fontRendererObj, String.valueOf(item.price) + "$", x1 + 19, y + 45, Color.WHITE.getRGB());
            
            if (item.purchasesLeft <= 1)
            {
                this.drawCenteredString(this.fontRendererObj, "" + item.purchasesLeft, x1 + 19, y + 55, Color.RED.getRGB());
            }
            
            else if (item.purchasesLeft >= 2)
            {
                this.drawCenteredString(this.fontRendererObj, "" + item.purchasesLeft, x1 + 19, y + 55, Color.LIGHT_GRAY.getRGB());
            }
            
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
        
        Item item = this.items.get(this.selectedItem);
        
        this.drawCenteredString(this.fontRendererObj, item.name, this.width / 2, y + 70, Color.WHITE.getRGB());
        this.drawCenteredString(this.fontRendererObj, item.description, this.width / 2, y + 85, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        Item item = this.items.get(this.selectedItem);
        
        if (guiButton.id == 0)
        {
            if (item.purchasesLeft > 0)
            {
                if (CustomEntityPlayer.get(this.mc.thePlayer).getMoney() >= item.price)
                {
                    item.purchasesLeft--;
                    
                    WelloZMod.proxy.getNetworkManager().sendToServer(new S02PacketBuy(item.itemStack, item.price));
                    
                    this.mc.thePlayer.addChatMessage(new ChatComponentText(I18n.format("shop.buying") + " " + EnumChatFormatting.GOLD + item.name));
                }
                
                else
                {
                    this.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18n.format("shop.notEnoughMoney") + " " + EnumChatFormatting.GOLD + item.name));
                }
            }
            
            else
            {
                this.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18n.format("shop.noLeftItem") + " " + EnumChatFormatting.GOLD + item.name + " " + EnumChatFormatting.RED + I18n.format("shop.noLeftItem1")));
            }
        }
        
        else if (guiButton.id == 1)
        {
            this.mc.displayGuiScreen(null);
        }
    }
    
    protected void keyTyped(char typedChar, int keyCode)
    {
        Item item = this.items.get(this.selectedItem);
        
        if (keyCode == Keyboard.KEY_LEFT)
        {
            this.selectedItem = (this.selectedItem - 1 + this.items.size()) % this.items.size();
        }
        
        else if (keyCode == Keyboard.KEY_RIGHT)
        {
            this.selectedItem = (this.selectedItem + 1) % this.items.size();
        }
        
        else if (keyCode == Keyboard.KEY_RETURN)
        {
            if (item.purchasesLeft > 0)
            {
                if (CustomEntityPlayer.get(this.mc.thePlayer).getMoney() >= item.price)
                {
                    item.purchasesLeft--;
                    
                    WelloZMod.proxy.getNetworkManager().sendToServer(new S02PacketBuy(item.itemStack, item.price));
                    
                    this.mc.thePlayer.addChatMessage(new ChatComponentText(I18n.format("shop.buying") + " " + EnumChatFormatting.GOLD + item.name));
                }
                
                else
                {
                    this.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18n.format("shop.notEnoughMoney") + " " + EnumChatFormatting.GOLD + item.name));
                }
            }
            
            else
            {
                this.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + I18n.format("shop.noLeftItem") + " " + EnumChatFormatting.GOLD + item.name + " " + EnumChatFormatting.RED + I18n.format("shop.noLeftItem1")));
            }
        }
        
        else
        {
            super.keyTyped(typedChar, keyCode);
        }
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    public void setMoney(int money)
    {
        this.money = money;
    }
    
    private static class Item
    {
        public final String name;
        private String description;
        private final int price, maxPurchases;
        public int purchasesLeft;
        private final ResourceLocation resourceLocation;
        public final ItemStack itemStack;
        
        private Item(String name, String description, int price, int maxPurchases, int purchasesLeft, ResourceLocation resourceLocation, ItemStack itemStack)
        {
            this.name = name;
            this.description = description;
            this.price = price;
            this.resourceLocation = resourceLocation;
            this.maxPurchases = maxPurchases;
            this.purchasesLeft = maxPurchases;
            this.itemStack = itemStack;;
        }
        
        private Item(String name, int price, int maxPurchases, ResourceLocation resourceLocation, ItemStack itemStack)
        {
            this(name, "", price, maxPurchases, maxPurchases, resourceLocation, itemStack);
        }
        
        private Item(String name, int price, ResourceLocation resourceLocation)
        {
            this(name, "", price, 0, 0, resourceLocation, null);
        }
    }
}