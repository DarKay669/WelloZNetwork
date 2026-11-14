package fr.welloznetwork.venus.lib.dev;

@NTSC_DEBUG
public class Debug
{
    private static boolean NTSC_DEBUG = false;
    private static boolean NEWER_VERSION = false;
    public static final String NTSC_DEBUG_CODE = "4W86-70XA-WTN2Y-MWC5-2BBB-FXB7E";
    
    static
    {
        String command = System.getProperty("sun.java.command");
        
        if (command != null && !command.isEmpty())
        {
            String[] args = command.split(" ");
            
            if (args.length >= 1 && args[0].contains("Gradle"))
            {
                NTSC_DEBUG = true;
                NEWER_VERSION = true;
            }
        }
    }
    
    public static boolean isDebugMode()
    {
        return NTSC_DEBUG;
    }
    
    public static boolean isNewerVersion()
    {
        return NEWER_VERSION;
    }
}