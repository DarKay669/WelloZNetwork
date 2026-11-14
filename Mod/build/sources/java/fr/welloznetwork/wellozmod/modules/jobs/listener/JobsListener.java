package fr.welloznetwork.wellozmod.modules.jobs.listener;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import fr.welloznetwork.venus.lib.time.TimeUtils;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.client.ClientProxy;
import fr.welloznetwork.wellozmod.entity.monster.EntityAquaCreeper;
import fr.welloznetwork.wellozmod.entity.monster.EntityEndermite;
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
import fr.welloznetwork.wellozmod.entity.passive.EntityFox;
import fr.welloznetwork.wellozmod.entity.passive.EntityRabbit;
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
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowWolf;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.modules.jobs.eep.JobsEEP;
import fr.welloznetwork.wellozmod.modules.jobs.util.JobData;
import fr.welloznetwork.wellozmod.modules.jobs.util.JobType;
import fr.welloznetwork.wellozmod.network.play.client.C08PacketJobXpNotification;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class JobsListener
{
    private static final Map<Class<? extends EntityLivingBase>, Integer> HUNTER_ENTITIES = new HashMap<>();
    private static final Map<Block, Integer> HUNTER_BLOCKS = new HashMap<>();
    private static final Map<Block, Integer> MINER_BLOCKS = new HashMap<>();
    private static final Map<Block, Integer> LUMBERJACK_BLOCKS = new HashMap<>();
    private boolean doubleXp = false;
    
    static
    {
        //Passive
        HUNTER_ENTITIES.put(EntityBat.class, 3);
        HUNTER_ENTITIES.put(EntityChicken.class, 3);
        HUNTER_ENTITIES.put(EntityCow.class, 5);
        HUNTER_ENTITIES.put(EntityHorse.class, 3);
        HUNTER_ENTITIES.put(EntityMooshroom.class, 3);
        HUNTER_ENTITIES.put(EntityOcelot.class, 3);
        HUNTER_ENTITIES.put(EntityPig.class, 3);
        HUNTER_ENTITIES.put(EntitySheep.class, 3);
        HUNTER_ENTITIES.put(EntitySquid.class, 3);
        HUNTER_ENTITIES.put(EntityWolf.class, 3);
        HUNTER_ENTITIES.put(EntityHugeBat.class, 4);
        HUNTER_ENTITIES.put(EntityHugeChicken.class, 4);
        HUNTER_ENTITIES.put(EntityHugeCow.class, 6);
        HUNTER_ENTITIES.put(EntityHugeHorse.class, 4);
        HUNTER_ENTITIES.put(EntityHugeMooshroom.class, 4);
        HUNTER_ENTITIES.put(EntityHugeOcelot.class, 4);
        HUNTER_ENTITIES.put(EntityHugePig.class, 4);
        HUNTER_ENTITIES.put(EntityHugeRabbit.class, 4);
        HUNTER_ENTITIES.put(EntityHugeSheep.class, 4);
        HUNTER_ENTITIES.put(EntityHugeWolf.class, 4);
        HUNTER_ENTITIES.put(EntityPenguin.class, 3);
        HUNTER_ENTITIES.put(EntitySnowBat.class, 3);
        HUNTER_ENTITIES.put(EntitySnowChicken.class, 3);
        HUNTER_ENTITIES.put(EntitySnowCow.class, 5);
        HUNTER_ENTITIES.put(EntitySnowHorse.class, 3);
        HUNTER_ENTITIES.put(EntitySnowOcelot.class, 3);
        HUNTER_ENTITIES.put(EntitySnowPig.class, 3);
        HUNTER_ENTITIES.put(EntitySnowRabbit.class, 3);
        HUNTER_ENTITIES.put(EntitySnowWolf.class, 3);
        HUNTER_ENTITIES.put(EntityFox.class, 4);
        HUNTER_ENTITIES.put(EntityRabbit.class, 4);
        
        //Monster
        HUNTER_ENTITIES.put(EntityBlaze.class, 5);
        HUNTER_ENTITIES.put(EntityCaveSpider.class, 4);
        HUNTER_ENTITIES.put(EntityEnderman.class, 6);
        HUNTER_ENTITIES.put(EntityGhast.class, 6);
        HUNTER_ENTITIES.put(EntityIronGolem.class, 5);
        HUNTER_ENTITIES.put(EntityMagmaCube.class, 5);
        HUNTER_ENTITIES.put(EntityPigZombie.class, 4);
        HUNTER_ENTITIES.put(EntitySilverfish.class, 3);
        HUNTER_ENTITIES.put(EntitySkeleton.class, 5);
        HUNTER_ENTITIES.put(EntitySlime.class, 4);
        HUNTER_ENTITIES.put(EntitySnowman.class, 4);
        HUNTER_ENTITIES.put(EntitySpider.class, 5);
        HUNTER_ENTITIES.put(EntityWitch.class, 5);
        HUNTER_ENTITIES.put(EntityZombie.class, 5);
        HUNTER_ENTITIES.put(EntityGhost.class, 5);
        HUNTER_ENTITIES.put(EntityHugeCreeper.class, 6);
        HUNTER_ENTITIES.put(EntityHugeSkeleton.class, 6);
        HUNTER_ENTITIES.put(EntitySnowCreeper.class, 6);
        HUNTER_ENTITIES.put(EntitySnowEnderman.class, 5);
        HUNTER_ENTITIES.put(EntitySnowSlime.class, 5);
        HUNTER_ENTITIES.put(EntitySnowSpider.class, 5);
        HUNTER_ENTITIES.put(EntitySnowWitch.class, 5);
        HUNTER_ENTITIES.put(EntitySnowZombie.class, 6);
        HUNTER_ENTITIES.put(EntityAquaCreeper.class, 6);
        HUNTER_ENTITIES.put(EntityEndermite.class, 5);
        HUNTER_ENTITIES.put(EntityStray.class, 5);
        
        HUNTER_BLOCKS.put(Blocks.brown_mushroom, 3);
        HUNTER_BLOCKS.put(Blocks.red_mushroom, 3);
        HUNTER_BLOCKS.put(Blocks.pumpkin, 5);
        HUNTER_BLOCKS.put(RegistryBlocks.pumpkin_face_1_off, 5);
        HUNTER_BLOCKS.put(RegistryBlocks.pumpkin_face_2_off, 5);
        HUNTER_BLOCKS.put(RegistryBlocks.pumpkin_face_3_off, 5);
        HUNTER_BLOCKS.put(RegistryBlocks.pumpkin_face_4_off, 5);
        HUNTER_BLOCKS.put(RegistryBlocks.pumpkin_face_5_off, 5);
        
        MINER_BLOCKS.put(Blocks.stone, 1);
        MINER_BLOCKS.put(Blocks.coal_ore, 2);
        MINER_BLOCKS.put(Blocks.iron_ore, 3);
        MINER_BLOCKS.put(Blocks.gold_ore, 3);
        MINER_BLOCKS.put(Blocks.diamond_ore, 1);
        MINER_BLOCKS.put(Blocks.quartz_block, 6);
        
        LUMBERJACK_BLOCKS.put(Blocks.log, 3);
        LUMBERJACK_BLOCKS.put(Blocks.log2, 3);
        LUMBERJACK_BLOCKS.put(RegistryBlocks.cherry_log, 4);
        LUMBERJACK_BLOCKS.put(RegistryBlocks.mangrove_log, 4);
    }
    
    private void addStat(EntityPlayerMP entityPlayerMP, JobType jobType, int xp)
    {
        JobsEEP jobsEEP = JobsEEP.get(entityPlayerMP);
        
        if (entityPlayerMP == null || jobsEEP == null)
        {
            return;
        }
        
        JobData jobData = jobsEEP.getJob(jobType);
        
        if (jobData == null)
        {
            return;
        }
        
        if (jobData.getLevel() >= 20)
        {
            return;
        }
        
        if (!jobsEEP.addXp(jobType, xp))
        {
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("event.maxXpReached");
            entityPlayerMP.addChatMessage(chatComponentTranslation);
            return;
        }
        
        jobsEEP.sync();
        
        if (ClientProxy.EVENT_REGISTER.jobNotifications && !(jobData.getLevel() >= 20))
        {
            if (this.isDoubleXp())
            {
                WelloZMod.proxy.getNetworkManager().sendToPlayer(new C08PacketJobXpNotification(jobType, xp * 2), entityPlayerMP);
            }
            
            else
            {
                WelloZMod.proxy.getNetworkManager().sendToPlayer(new C08PacketJobXpNotification(jobType, xp), entityPlayerMP);
            }
        }
    }
    
    private boolean isDoubleXp()
    {
        if (TimeUtils.nowZoned().getDayOfWeek() == DayOfWeek.FRIDAY || TimeUtils.nowZoned().getDayOfWeek() == DayOfWeek.SATURDAY || TimeUtils.nowZoned().getDayOfWeek() == DayOfWeek.SUNDAY)
        {
            if (TimeUtils.nowZoned().getHour() >= 18 && TimeUtils.nowZoned().getHour() < 21)
            {
                return true;
            }
        }
        
        return false;
    }
    
    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        if (event.entity.worldObj.isRemote)
        {
            return;
        }
        
        if (!(event.source.getEntity() instanceof EntityPlayerMP))
        {
            return;
        }
        
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.source.getEntity();
        
        if (entityPlayerMP == null)
        {
            return;
        }
        
        JobsEEP jobsEEP = JobsEEP.get(entityPlayerMP);
        
        if (jobsEEP == null)
        {
            return;
        }
        
        Integer xp = HUNTER_ENTITIES.get(event.entity.getClass());
        
        if (xp != null)
        {
            JobData jobData = jobsEEP.getJob(JobType.HUNTER);
            
            if (jobData != null)
            {
                if (jobData.getLevel() >= 10)
                {
                    if (event.entity instanceof EntitySheep || event.entity instanceof EntityPenguin || event.entity instanceof EntitySnowBat || event.entity instanceof EntitySnowChicken || event.entity instanceof EntitySnowHorse || event.entity instanceof EntitySnowOcelot || event.entity instanceof EntitySnowPig || event.entity instanceof EntitySnowRabbit || event.entity instanceof EntitySnowWolf || event.entity instanceof EntityBat || event.entity instanceof EntityChicken || event.entity instanceof EntityHorse || event.entity instanceof EntityMooshroom || event.entity instanceof EntityOcelot || event.entity instanceof EntityPig || event.entity instanceof EntitySquid || event.entity instanceof EntityWolf)
                    {
                        xp = 2;
                    }
                    
                    if (event.entity instanceof EntityCow || event.entity instanceof EntityHugeBat || event.entity instanceof EntityHugeChicken || event.entity instanceof EntityHugeHorse || event.entity instanceof EntityHugeMooshroom || event.entity instanceof EntityHugeOcelot || event.entity instanceof EntityHugePig || event.entity instanceof EntityHugeRabbit || event.entity instanceof EntityHugeSheep || event.entity instanceof EntityHugeWolf || event.entity instanceof EntitySnowCow || event.entity instanceof EntityFox || event.entity instanceof EntityRabbit)
                    {
                        xp = 3;
                    }
                    
                    if (event.entity instanceof EntityHugeCow)
                    {
                        xp = 4;
                    }
                }
                
                if (jobData.getLevel() >= 15)
                {
                    if (event.entity instanceof EntitySilverfish)
                    {
                        xp = 1;
                    }
                    
                    if (event.entity instanceof EntityCaveSpider || event.entity instanceof EntityPigZombie || event.entity instanceof EntitySlime || event.entity instanceof EntitySnowman)
                    {
                        xp = 2;
                    }
                    
                    if (event.entity instanceof EntitySkeleton || event.entity instanceof EntityZombie || event.entity instanceof EntityGhost || event.entity instanceof EntitySnowEnderman || event.entity instanceof EntitySnowSlime || event.entity instanceof EntitySnowSpider || event.entity instanceof EntitySnowWitch || event.entity instanceof EntityEndermite || event.entity instanceof EntityStray || event.entity instanceof EntityBlaze || event.entity instanceof EntityIronGolem || event.entity instanceof EntityMagmaCube || event.entity instanceof EntitySpider || event.entity instanceof EntityWitch)
                    {
                        xp = 3;
                    }
                    
                    if (event.entity instanceof EntityHugeCreeper || event.entity instanceof EntityHugeSkeleton || event.entity instanceof EntitySnowCreeper || event.entity instanceof EntitySnowZombie || event.entity instanceof EntityAquaCreeper || event.entity instanceof EntityEnderman || event.entity instanceof EntityGhast)
                    {
                        xp = 4;
                    }
                }
            }
            
            this.addStat((EntityPlayerMP) event.source.getEntity(), JobType.HUNTER, xp);
        }
    }
    
    @SubscribeEvent
    public void onBreakEvent(BreakEvent event)
    {
        if (event.world.isRemote)
        {
            return;
        }
        
        if (!(event.getPlayer() instanceof EntityPlayerMP))
        {
            return;
        }
        
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.getPlayer();
        
        if (entityPlayerMP == null)
        {
            return;
        }
        
        JobsEEP jobsEEP = JobsEEP.get(entityPlayerMP);
        
        if (jobsEEP == null)
        {
            return;
        }
        
        Integer xpHunter = HUNTER_BLOCKS.get(event.block), xpMiner = MINER_BLOCKS.get(event.block), xpLumberjack = LUMBERJACK_BLOCKS.get(event.block);
        
        if (xpHunter != null)
        {
            JobData jobData = jobsEEP.getJob(JobType.HUNTER);
            
            if (jobData != null)
            {
                if (jobData.getLevel() >= 10)
                {
                    if (event.block == Blocks.brown_mushroom || event.block == Blocks.red_mushroom)
                    {
                        xpHunter = 2;
                    }
                    
                    if (event.block == Blocks.pumpkin)
                    {
                        xpHunter = 3;
                    }
                    
                    if (event.block == RegistryBlocks.pumpkin_face_1_off || event.block == RegistryBlocks.pumpkin_face_2_off || event.block == RegistryBlocks.pumpkin_face_3_off || event.block == RegistryBlocks.pumpkin_face_4_off || event.block == RegistryBlocks.pumpkin_face_5_off)
                    {
                        xpHunter = 3;
                    }
                }
                
                if (jobData.getLevel() >= 15)
                {
                    if (event.block == Blocks.pumpkin)
                    {
                        xpHunter = 2;
                    }
                    
                    if (event.block == RegistryBlocks.pumpkin_face_1_off || event.block == RegistryBlocks.pumpkin_face_2_off || event.block == RegistryBlocks.pumpkin_face_3_off || event.block == RegistryBlocks.pumpkin_face_4_off || event.block == RegistryBlocks.pumpkin_face_5_off)
                    {
                        xpHunter = 2;
                    }
                }
            }
            
            this.addStat(entityPlayerMP, JobType.HUNTER, xpHunter);
        }
        
        if (xpMiner != null)
        {
            JobData jobData = jobsEEP.getJob(JobType.MINER);
            
            if (jobData != null)
            {
                if (jobData.getLevel() >= 5)
                {
                    if (event.block == RegistryBlocks.deepslate_coal_ore)
                    {
                        xpMiner = 3;
                    }
                    
                    if (event.block == RegistryBlocks.deepslate_iron_ore || event.block == RegistryBlocks.deepslate_gold_ore)
                    {
                        xpMiner = 4;
                    }
                    
                    if (event.block == RegistryBlocks.deepslate_diamond_ore)
                    {
                        xpMiner = 6;
                    }
                }
                
                if (jobData.getLevel() >= 10)
                {
                    if (event.block == Blocks.iron_ore)
                    {
                        xpMiner = 8;
                    }
                    
                    if (event.block == Blocks.diamond_ore)
                    {
                        xpMiner = 25;
                    }
                    
                    if (event.block == Blocks.coal_ore)
                    {
                        xpMiner = 1;
                    }
                    
                    if (event.block == Blocks.iron_ore || event.block == Blocks.gold_ore)
                    {
                        xpMiner = 2;
                    }
                    
                    if (event.block == Blocks.diamond_ore)
                    {
                        xpMiner = 3;
                    }
                    
                    if (event.block == RegistryBlocks.ruby_ore || event.block == RegistryBlocks.xp_ore)
                    {
                        xpMiner = 5;
                    }
                }
                
                if (jobData.getLevel() >= 15)
                {
                    if (event.block == RegistryBlocks.deepslate_coal_ore)
                    {
                        xpMiner = 2;
                    }
                    
                    if (event.block == RegistryBlocks.deepslate_iron_ore || event.block == RegistryBlocks.deepslate_gold_ore)
                    {
                        xpMiner = 3;
                    }
                    
                    if (event.block == RegistryBlocks.deepslate_diamond_ore)
                    {
                        xpMiner = 5;
                    }
                }
            }
            
            this.addStat(entityPlayerMP, JobType.MINER, xpMiner);
        }
        
        if (xpLumberjack != null)
        {
            JobData jobData = jobsEEP.getJob(JobType.LUMBERJACK);
            
            if (jobData != null)
            {
                if (jobData.getLevel() >= 10)
                {
                    if (event.block == Blocks.log || event.block == Blocks.log2)
                    {
                        xpLumberjack = 2;
                    }
                    
                    if (event.block == RegistryBlocks.cherry_log || event.block == RegistryBlocks.mangrove_log)
                    {
                        xpLumberjack = 3;
                    }
                }
            }
            
            this.addStat(entityPlayerMP, JobType.LUMBERJACK, xpLumberjack);
        }
    }
    
    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerLoggedInEvent event)
    {
        if (!(event.player instanceof EntityPlayerMP))
        {
            return;
        }
        
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.player;
        JobsEEP jobsEEP = JobsEEP.get(entityPlayerMP);
        
        if (jobsEEP == null)
        {
            return;
        }
        
        if (this.isDoubleXp())
        {
            for (JobType jobType : JobType.values())
            {
                jobsEEP.getJob(jobType).setDoubleXp(true);
            }
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("event.doubleXp");
            
            entityPlayerMP.addChatMessage(chatComponentTranslation);
        }
    }
    
    @SubscribeEvent
    public void onServerTickEvent(ServerTickEvent event)
    {
        if (event.phase == Phase.END)
        {
            boolean doubleXp = this.isDoubleXp();
            
            if (doubleXp != this.doubleXp)
            {
                for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
                {
                    if (o instanceof EntityPlayerMP)
                    {
                        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) o;
                        JobsEEP jobsEEP = JobsEEP.get(entityPlayerMP);
                        
                        if (jobsEEP != null)
                        {
                            for (JobType jobType : JobType.values())
                            {
                                jobsEEP.getJob(jobType).setDoubleXp(doubleXp);
                            }
                        }
                        
                        if (doubleXp)
                        {
                            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("event.doubleXp");
                            
                            entityPlayerMP.addChatMessage(chatComponentTranslation);
                        }
                    }
                }
                
                this.doubleXp = doubleXp;
            }
            
            else
            {
                for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
                {
                    if (o instanceof EntityPlayerMP)
                    {
                        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) o;
                        JobsEEP jobsEEP = JobsEEP.get(entityPlayerMP);
                        
                        if (jobsEEP != null)
                        {
                            for (JobType jobType : JobType.values())
                            {
                                jobsEEP.getJob(jobType).setDoubleXp(doubleXp);
                            }
                        }
                    }
                }
            }
        }
    }
}