package fr.welloznetwork.wellozmod.init;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;

public class RegistryGrades
{
    private static final Map<UUID, GradeType> GRADES = new HashMap<>();
    
    public static void init()
    {
        GRADES.put(UUID.fromString("29eada11-8cce-4ef3-86e8-6deab298850d"), GradeType.ADMINISTRATOR);
    }
    
    public static void setGrade(UUID uuid, GradeType gradeType)
    {
        GRADES.put(uuid, gradeType);
    }
    
    public static GradeType getGrade(UUID uuid)
    {
        return GRADES.getOrDefault(uuid, GradeType.PLAYER);
    }
    
    public static GradeType getGrade(EntityPlayerMP entityPlayerMP)
    {
        return getGrade(entityPlayerMP.getUniqueID());
    }
    
    public static enum GradeType
    {
        NOT_SET(-1, "", EnumChatFormatting.WHITE),
        PLAYER(0, "player", EnumChatFormatting.GRAY),
        DONATOR(1, "donator", EnumChatFormatting.GREEN),
        STAFF(2, "staff", EnumChatFormatting.AQUA),
        ADMINISTRATOR(3, "administrator", EnumChatFormatting.RED);
        
        private final int id;
        private final String name;
        private final EnumChatFormatting color;
        
        private GradeType(int id, String name, EnumChatFormatting color)
        {
            this.id = id;
            this.name = name;
            this.color = color;
        }
        
        public int getID()
        {
            return this.id;
        }
        
        public String getName()
        {
            return this.name;
        }
        
        public EnumChatFormatting getColor()
        {
            return this.color;
        }
        
        public String getPrefix()
        {
            return this.color + "[" + this.name.toUpperCase() + "] ";
        }
        
        public String getTranslationKey()
        {
            return "grade." + this.name;
        }
        
        public static GradeType getByID(int id)
        {
            return parseGradeTypeWithDefault(id, PLAYER);
        }
        
        private static GradeType parseGradeTypeWithDefault(int targetId, GradeType fallback)
        {
            for (GradeType gradeType : values())
            {
                if (gradeType.id == targetId)
                {
                    return gradeType;
                }
            }
            
            return fallback;
        }
    }
}