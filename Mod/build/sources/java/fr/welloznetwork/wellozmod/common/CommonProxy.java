package fr.welloznetwork.wellozmod.common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.welloznetwork.venus.lib.proxy.AProxy;
import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.common.listener.ExtendedEntityPropertyListener;
import fr.welloznetwork.wellozmod.common.listener.ServerListener;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryBiomes;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCapes;
import fr.welloznetwork.wellozmod.init.RegistryCommands;
import fr.welloznetwork.wellozmod.init.RegistryCrafting;
import fr.welloznetwork.wellozmod.init.RegistryEnchantments;
import fr.welloznetwork.wellozmod.init.RegistryEntity;
import fr.welloznetwork.wellozmod.init.RegistryFluids;
import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryTileEntity;
import fr.welloznetwork.wellozmod.init.RegistryUI;
import fr.welloznetwork.wellozmod.item.crafting.ComposterRecipes;
import fr.welloznetwork.wellozmod.item.crafting.SmithingTableRecipes;
import fr.welloznetwork.wellozmod.network.NetworkManager;
import fr.welloznetwork.wellozmod.network.play.client.C02PacketEmote;
import fr.welloznetwork.wellozmod.world.WorldGenerator;
import fr.welloznetwork.wellozmod.world.WorldProviderTheAfter;
import fr.welloznetwork.wellozmod.world.gen.structure.MapGenCloud.Start;
import fr.welloznetwork.wellozmod.world.gen.structure.StructureComponentCloud;
import fr.welloznetwork.wellozmod.world.gen.structure.StructureOceanMonument;
import fr.welloznetwork.wellozmod.world.storage.loot.LootTableManager;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.DimensionManager;

public class CommonProxy extends AProxy
{
    private NetworkManager networkManager = new NetworkManager();
    private ServerListener serverEventRegister = new ServerListener();
    
	public void init(FMLInitializationEvent event)
	{
	    RegistryEntity.init();
		RegistryBlocks.init();
		RegistryItems.init();
		RegistryTileEntity.init();
		RegistryEnchantments.init();
		RegistryCrafting.init();
		RegistryFluids.init();
		RegistryBiomes.init();
		RegistryAchievements.init();
		SmithingTableRecipes.init();
		ComposterRecipes.init();
		RegistryGrades.init();
		
		if (Constants.V2)
        {
            RegistryCapes.init();
        }
		
		StructureOceanMonument.init();
		
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
        
        MapGenStructureIO.registerStructure(Start.class, "wellozmod:cloud_start");
        MapGenStructureIO.func_143031_a(StructureComponentCloud.class, "wellozmod:cloud_component");
        
        DimensionManager.registerProviderType(2, WorldProviderTheAfter.class, false);
        DimensionManager.registerDimension(2, 2);
		
        this.registerRenders();
        
        this.addListener(new Class[] {ServerListener.class});
		
		NetworkRegistry.INSTANCE.registerGuiHandler(WelloZMod.instance, new RegistryUI());
		
		this.networkManager.init();
	}
	
	public void serverStarting(FMLServerStartingEvent event)
	{
        RegistryCommands.serverStarting(event);
        
        this.addListener(new Class[] {ExtendedEntityPropertyListener.class});
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
	    LootTableManager.postInit();
	}
	
	public NetworkManager getNetworkManager()
	{
	    return this.networkManager;
	}
	
	public ServerListener getServerEventRegister()
	{
	    return this.serverEventRegister;
	}
	
	public void registerRenders() {}
	
	public void registerEmotePacket(C02PacketEmote packet) {}
}