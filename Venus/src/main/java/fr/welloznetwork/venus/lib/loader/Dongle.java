package fr.welloznetwork.venus.lib.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.welloznetwork.venus.lib.dev.Debug;
import fr.welloznetwork.venus.lib.dev.NTSC_DEBUG;
import fr.welloznetwork.venus.lib.os.EnumOS;
import fr.welloznetwork.venus.lib.os.OS;

@NTSC_DEBUG
public class Dongle
{
    private static Set<String> previousRoots = new HashSet<>();
    
    public static void preInit()
    {
        if (Debug.isDebugMode())
        {
            while (!NTSC_DEBUG())
            {
                try
                {
                    Thread.sleep(1000L);
                    System.out.println("Please insert the Startup Dongle");
                }
                
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void postInit()
    {
        if (Debug.isDebugMode())
        {
            new Thread(() ->
            {
                while (true)
                {
                    Set<String> currentRoots = getRoots();
                    
                    if (!currentRoots.equals(previousRoots))
                    {
                        if (!NTSC_DEBUG())
                        {
                            System.exit(-1);
                            break;
                        }
                    }
                    
                    previousRoots = currentRoots;
                }
            }).start();
        }
    }
    
    @SuppressWarnings("resource")
    public static boolean NTSC_DEBUG()
    {
        List<String> serialsNumber = getSerialNumber();
        
        if (OS.getOSType() == EnumOS.WINDOWS)
        {
            for (FileStore fileStore : FileSystems.getDefault().getFileStores())
            {
                if (fileStore.name() != null && fileStore.name().equalsIgnoreCase("NTSC_DEBUG"))
                {   
                    if (serialsNumber.contains("0101c18ef40a2531552f"))
                    {
                        return true;
                    }
                }
            }
        }
        
        else if (OS.getOSType() == EnumOS.OSX)
        {
            File fileVolumes = new File("/Volumes");
            File[] files = fileVolumes.listFiles();
            
            for (FileStore fileStore : FileSystems.getDefault().getFileStores())
            {
                if (fileVolumes != null)
                {
                    for (File file : files)
                    {
                        if (file.getName() != null && file.getName().equalsIgnoreCase("NTSC_DEBUG"))
                        {
                            if (serialsNumber.contains("0101c18ef40a2531552f"))
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    private static List<String> getSerialNumber()
    {
        List<String> serialsNumber = new ArrayList<String>();
        
        try 
        {
            String lineWindows, lineOSX, lineLinux;
            
            if (OS.getOSType() == EnumOS.WINDOWS)
            {
                Process processWindows = Runtime.getRuntime().exec("wmic diskdrive where MediaType='Removable Media' get SerialNumber");
                BufferedReader bufferedReaderWindows = new BufferedReader(new InputStreamReader(processWindows.getInputStream()));
                
                while ((lineWindows = bufferedReaderWindows.readLine()) != null)
                {
                    lineWindows = lineWindows.trim();
                    
                    if (!lineWindows.isEmpty() && !lineWindows.startsWith("SerialNumber"))
                    {
                        serialsNumber.add(lineWindows);
                    }
                }
            }
            
            else if (OS.getOSType() == EnumOS.OSX)
            {
                Process processOSX = new ProcessBuilder("system_profiler", "SPUSBDataType").start();
                BufferedReader bufferedReaderOSX = new BufferedReader(new InputStreamReader(processOSX.getInputStream()));
                
                while ((lineOSX = bufferedReaderOSX.readLine()) != null)
                {
                    lineOSX = lineOSX.trim();
                    boolean b = false;
                    
                    if (lineOSX.startsWith(":"))
                    {
                        b = lineOSX.toLowerCase().contains("usb");
                    }
                    
                    if (b && lineOSX.startsWith("Serial Number:"))
                    {
                        String serialNumber = lineOSX.split(":")[1].trim();
                    }
                }
            }
            
            else if (OS.getOSType() == EnumOS.LINUX)
            {
                Process processLinux = Runtime.getRuntime().exec("lsblk -o NAME,TYPE | grep disk");
                BufferedReader bufferedReaderLinux = new BufferedReader(new InputStreamReader(processLinux.getInputStream()));
                
                while ((lineLinux = bufferedReaderLinux.readLine()) != null)
                {
                    if (lineLinux.contains("usb"))
                    {
                        serialsNumber.add(lineLinux);
                    }
                }
            }
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return serialsNumber;
    }
    
    private static Set<String> getRoots()
    {
        Set<String> roots = new HashSet<>();
        
        for (File root : File.listRoots())
        {
            roots.add(root.getAbsolutePath());
        }
        
        return roots;
    }
}