package fr.welloznetwork.wellozmod.client;

import static cpw.mods.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer;
import static cpw.mods.fml.client.registry.RenderingRegistry.registerBlockHandler;
import static cpw.mods.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler;
import static net.minecraftforge.client.MinecraftForgeClient.registerItemRenderer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.dev.Debug;
import fr.welloznetwork.venus.lib.dev.DeveloperHelper;
import fr.welloznetwork.venus.lib.loader.Loader;
import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.client.listener.ClientListener;
import fr.welloznetwork.wellozmod.client.model.ModelRabbit;
import fr.welloznetwork.wellozmod.client.renderer.block.BlockBambooRenderer;
import fr.welloznetwork.wellozmod.client.renderer.block.BlockBarrelRenderer;
import fr.welloznetwork.wellozmod.client.renderer.block.BlockChorusFlowerRenderer;
import fr.welloznetwork.wellozmod.client.renderer.block.BlockChorusPlantRenderer;
import fr.welloznetwork.wellozmod.client.renderer.block.BlockComposterRenderer;
import fr.welloznetwork.wellozmod.client.renderer.block.BlockEndRodRenderer;
import fr.welloznetwork.wellozmod.client.renderer.block.BlockGlazedTerracottaRenderer;
import fr.welloznetwork.wellozmod.client.renderer.block.BlockObserverRenderer;
import fr.welloznetwork.wellozmod.client.renderer.block.BlockTNTPresentRenderer;
import fr.welloznetwork.wellozmod.client.renderer.entity.CustomRenderBoat;
import fr.welloznetwork.wellozmod.client.renderer.entity.RenderRabbit;
import fr.welloznetwork.wellozmod.client.renderer.entity.RenderTechnobladeCrown;
import fr.welloznetwork.wellozmod.client.renderer.entity.RenderWhiter;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.RenderAquaCreeper;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.RenderEndermite;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.RenderHerobrine;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.RenderStray;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.haunted.RenderGhost;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.huge.RenderHugeCreeper;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.huge.RenderHugeSkeleton;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow.RenderSnowCreeper;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow.RenderSnowEnderman;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow.RenderSnowSlime;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow.RenderSnowSpider;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow.RenderSnowWitch;
import fr.welloznetwork.wellozmod.client.renderer.entity.monster.snow.RenderSnowZombie;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.RenderFox;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.RenderTravelingVillager;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.haunted.RenderSuspiciousGirl;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge.RenderHugeBat;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge.RenderHugeChicken;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge.RenderHugeCow;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge.RenderHugeHorse;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge.RenderHugeMooshroom;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge.RenderHugeOcelot;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge.RenderHugePig;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge.RenderHugeRabbit;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge.RenderHugeSheep;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge.RenderHugeWolf;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderElf;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderPenguin;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderSnowBat;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderSnowChicken;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderSnowCow;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderSnowHorse;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderSnowOcelot;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderSnowPig;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderSnowRabbit;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderSnowVillager;
import fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow.RenderSnowWolf;
import fr.welloznetwork.wellozmod.client.renderer.entity.projectile.RenderEntitySplashPotion;
import fr.welloznetwork.wellozmod.client.renderer.item.ItemEmeraldChestRenderer;
import fr.welloznetwork.wellozmod.client.renderer.item.ItemSpyglassRenderer;
import fr.welloznetwork.wellozmod.client.renderer.tileentity.TileEntityEmeraldChestRenderer;
import fr.welloznetwork.wellozmod.client.renderer.tileentity.TileEntityEndRodRenderer;
import fr.welloznetwork.wellozmod.client.renderer.tileentity.TileEntitySnowGlobeRenderer;
import fr.welloznetwork.wellozmod.common.CommonProxy;
import fr.welloznetwork.wellozmod.entity.block.EntityTNTPresent;
import fr.welloznetwork.wellozmod.entity.boss.EntityWhiter;
import fr.welloznetwork.wellozmod.entity.item.CustomEntityBoat;
import fr.welloznetwork.wellozmod.entity.monster.EntityAquaCreeper;
import fr.welloznetwork.wellozmod.entity.monster.EntityEndermite;
import fr.welloznetwork.wellozmod.entity.monster.EntityHerobrine;
import fr.welloznetwork.wellozmod.entity.monster.EntityStray;
import fr.welloznetwork.wellozmod.entity.monster.haunted.EntityGhost;
import fr.welloznetwork.wellozmod.entity.monster.huge.EntityHugeCreeper;
import fr.welloznetwork.wellozmod.entity.monster.huge.EntityHugeSkeleton;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowCreeper;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowEnderman;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowSlime;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowSpider;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowWitch;
import fr.welloznetwork.wellozmod.entity.monster.snow.EntitySnowZombie;
import fr.welloznetwork.wellozmod.entity.passive.EntityElf;
import fr.welloznetwork.wellozmod.entity.passive.EntityFox;
import fr.welloznetwork.wellozmod.entity.passive.EntityRabbit;
import fr.welloznetwork.wellozmod.entity.passive.EntityTravelingVillager;
import fr.welloznetwork.wellozmod.entity.passive.haunted.EntitySuspiciousGirl;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeBat;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeChicken;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeCow;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeHorse;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeMooshroom;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeOcelot;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugePig;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeRabbit;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeSheep;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeWolf;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntityPenguin;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowBat;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowChicken;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowCow;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowHorse;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowOcelot;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowPig;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowRabbit;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowVillager;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowWolf;
import fr.welloznetwork.wellozmod.entity.projectile.EntitySplashPotion;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryEmotes;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryKeys;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import fr.welloznetwork.wellozmod.network.play.client.C02PacketEmote;
import fr.welloznetwork.wellozmod.tileentity.TileEntityEmeraldChest;
import fr.welloznetwork.wellozmod.tileentity.TileEntityEndRod;
import fr.welloznetwork.wellozmod.tileentity.TileEntitySnowGlobe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBat;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    public static final ClientListener EVENT_REGISTER = new ClientListener();
    
	public ClientProxy()
	{
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@DeveloperHelper("Un-comment when compiled")
    public void preInit(FMLPreInitializationEvent event)
    {
        /*if (Debug.isDebugMode())
        {
            Dongle.preInit();
        }*/
	    
	    RegistrySettings.preInit(event);
	    
        super.preInit(event);
    }
	
    public void init(FMLInitializationEvent event)
    {
        RegistryKeys.init();
        
        this.addListener(new Class[] {ClientListener.class});
        
        super.init(event);
    }
    
    @DeveloperHelper("Un-comment when compiled")
    public void postInit(FMLPostInitializationEvent event)
    {
        /*if (Debug.isDebugMode())
        {
            Dongle.postInit();
        }*/
        
        super.postInit(event);
    }
    
    public void loadComplete(FMLLoadCompleteEvent event)
    {
        if (!Debug.isDebugMode())
        {
            Loader.loadComplete(reason -> 
            {
                if (Minecraft.getMinecraft() != null)
                {
                    Minecraft.getMinecraft().shutdown();
                }
                
                FMLCommonHandler.instance().exitJava(-1, true);
            });
        }
        
        super.loadComplete(event);
    }
    
    public void registerRenders()
    {
        registerItemRenderer(Item.getItemFromBlock(RegistryBlocks.emerald_chest), new ItemEmeraldChestRenderer());
        registerItemRenderer(RegistryItems.spyglass, new ItemSpyglassRenderer());
        
        registerBlockHandler(new BlockEndRodRenderer());
        registerBlockHandler(new BlockChorusFlowerRenderer());
        registerBlockHandler(new BlockChorusPlantRenderer());
        registerBlockHandler(new BlockBambooRenderer());
        registerBlockHandler(new BlockComposterRenderer());
        registerBlockHandler(new BlockObserverRenderer());
        registerBlockHandler(new BlockBarrelRenderer());
        registerBlockHandler(new BlockGlazedTerracottaRenderer());
        bindTileEntitySpecialRenderer(TileEntityEmeraldChest.class, new TileEntityEmeraldChestRenderer());
        bindTileEntitySpecialRenderer(TileEntityEndRod.class, new TileEntityEndRodRenderer());
        bindTileEntitySpecialRenderer(TileEntitySnowGlobe.class, new TileEntitySnowGlobeRenderer());
        
        registerEntityRenderingHandler(EntityHerobrine.class, new RenderHerobrine());
        registerEntityRenderingHandler(EntityPenguin.class, new RenderPenguin());
        registerEntityRenderingHandler(EntitySplashPotion.class, new RenderEntitySplashPotion());
        registerEntityRenderingHandler(EntitySnowChicken.class, new RenderSnowChicken());
        registerEntityRenderingHandler(EntitySnowCow.class, new RenderSnowCow());
        registerEntityRenderingHandler(EntitySnowCreeper.class, new RenderSnowCreeper());
        registerEntityRenderingHandler(EntitySnowHorse.class, new RenderSnowHorse());
        registerEntityRenderingHandler(EntitySnowOcelot.class, new RenderSnowOcelot());
        registerEntityRenderingHandler(EntitySnowPig.class, new RenderSnowPig());
        registerEntityRenderingHandler(EntitySnowWolf.class, new RenderSnowWolf());
        registerEntityRenderingHandler(EntitySnowZombie.class, new RenderSnowZombie());
        registerEntityRenderingHandler(EntitySnowRabbit.class, new RenderSnowRabbit());
        registerEntityRenderingHandler(EntityElf.class, new RenderElf());
        registerEntityRenderingHandler(EntityHugeBat.class, new RenderHugeBat(new ModelBat(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugeChicken.class, new RenderHugeChicken(new ModelChicken(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugeCow.class, new RenderHugeCow(new ModelCow(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugeHorse.class, new RenderHugeHorse(new ModelHorse(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugeMooshroom.class, new RenderHugeMooshroom(new ModelCow(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugeOcelot.class, new RenderHugeOcelot(new ModelOcelot(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugePig.class, new RenderHugePig(new ModelPig(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugeSheep.class, new RenderHugeSheep(new ModelSheep2(), new ModelSheep1(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugeWolf.class, new RenderHugeWolf(new ModelWolf(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugeCreeper.class, new RenderHugeCreeper(new ModelCreeper(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugeSkeleton.class, new RenderHugeSkeleton(new ModelSkeleton(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntityHugeRabbit.class, new RenderHugeRabbit(new ModelRabbit(), 0.5F, 6.0F));
        registerEntityRenderingHandler(EntitySnowVillager.class, new RenderSnowVillager());
        registerEntityRenderingHandler(EntitySuspiciousGirl.class, new RenderSuspiciousGirl());
        registerEntityRenderingHandler(EntityGhost.class, new RenderGhost());
        registerEntityRenderingHandler(EntityRabbit.class, new RenderRabbit());
        registerEntityRenderingHandler(CustomEntityBoat.class, new CustomRenderBoat());
        registerEntityRenderingHandler(EntityPig.class, new RenderTechnobladeCrown());
        registerEntityRenderingHandler(EntityEndermite.class, new RenderEndermite());
        registerEntityRenderingHandler(EntityStray.class, new RenderStray());
        registerEntityRenderingHandler(EntitySnowEnderman.class, new RenderSnowEnderman());
        registerEntityRenderingHandler(EntitySnowSlime.class, new RenderSnowSlime());
        registerEntityRenderingHandler(EntitySnowSpider.class, new RenderSnowSpider());
        registerEntityRenderingHandler(EntitySnowWitch.class, new RenderSnowWitch());
        registerEntityRenderingHandler(EntitySnowBat.class, new RenderSnowBat());
        registerEntityRenderingHandler(EntityTravelingVillager.class, new RenderTravelingVillager());
        registerEntityRenderingHandler(EntityTNTPresent.class, new BlockTNTPresentRenderer());
        
        if (Constants.V2)
        {
            registerEntityRenderingHandler(EntityAquaCreeper.class, new RenderAquaCreeper());
            registerEntityRenderingHandler(EntityWhiter.class, new RenderWhiter());
            registerEntityRenderingHandler(EntityFox.class, new RenderFox());
            
            RegistryEmotes.init();
        }
        
        super.registerRenders();
    }
    
    public void registerEmotePacket(C02PacketEmote packet)
    {
        if (Minecraft.getMinecraft().theWorld == null)
        {
            return;
        }
        
        for (Object o : Minecraft.getMinecraft().theWorld.playerEntities)
        {
            if (o instanceof EntityPlayer)
            {
                EntityPlayer entityPlayer = (EntityPlayer) o;
                
                if (entityPlayer.getCommandSenderName().equals(packet.playerName))
                {
                    RegistryEmotes.registerEmote(entityPlayer, packet.emoteName);
                    break;
                }
            }
        }
        
        EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;
        
        if (entityPlayer != null && entityPlayer.getCommandSenderName().equals(packet.playerName))
        {
            RegistryEmotes.registerEmote(entityPlayer, packet.emoteName);
        }
        
        super.registerEmotePacket(packet);
    }
}