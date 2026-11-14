package fr.welloznetwork.venus.lib.loader;

import java.io.File;
import java.io.IOException;

public class Loader
{
    public static void loadComplete(ILoaderCallback loaderCallback)
    {
        try
        {
            File modsFolder;
            
            if (System.getProperty("os.name").toLowerCase().contains("win"))
            {
                modsFolder = new File(System.getProperty("user.home"), "AppData/Roaming/.welloznetwork/mods");
            }
            
            else
            {
                modsFolder = new File(System.getProperty("user.home"), ".welloznetwork/mods");
            }
            
            if (rightFile(modsFolder.getCanonicalFile()) == null)
            {
                loaderCallback.fail("Unable to find WelloZMod.jar in: " + modsFolder.getCanonicalFile());
                return;
            }
            
            System.out.println("WelloZMod loaded from: " + rightFile(modsFolder.getCanonicalFile()));
        }
        
        catch (IOException e)
        {
            loaderCallback.fail("IOException: " + e.getMessage());
        }
    }
    
    private static File rightFile(File file)
    {
        File[] files = file.listFiles((directory, fileName) -> fileName.toLowerCase().endsWith(".jar"));
        
        if (files == null)
        {
            return null;
        }
        
        for (File file1 : files)
        {
            if (file1.getName().toLowerCase().contains("wellozmod"))
            {
                return file1;
            }
        }
        
        return null;
    }
}