package fr.welloznetwork.mercury.init;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.welloznetwork.mercury.item.ItemDummy;
import fr.welloznetwork.mercury.item.ItemMap;

public class RegistryItems
{
    public static ItemMap map;
    public static ItemDummy faction, minage;
    
    public static void init()
    {
        map = new ItemMap();
        faction = new ItemDummy("faction", "faction", 0);
        minage = new ItemDummy("minage", "minage", 1);
        
        GameRegistry.registerItem(map, "map");
        GameRegistry.registerItem(faction, "faction");
        GameRegistry.registerItem(minage, "minage");
    }
}