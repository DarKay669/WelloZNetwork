package fr.welloznetwork.wellozmod.common.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.MapMaker;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.command.CommandDeathLocate;
import fr.welloznetwork.wellozmod.command.CommandSpawn;
import fr.welloznetwork.wellozmod.command.CommandSpawn.TeleportData;
import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.command.CommandTPA;
import fr.welloznetwork.wellozmod.command.CommandTPA.Teleport;
import fr.welloznetwork.wellozmod.command.CommandTPAccept;
import fr.welloznetwork.wellozmod.enchantment.CustomEnchantmentHelper;
import fr.welloznetwork.wellozmod.enchantment.CustomEnchantmentHelper.EnchantmentLevel;
import fr.welloznetwork.wellozmod.enchantment.CustomEnchantmentHelper.JumpInfo;
import fr.welloznetwork.wellozmod.entity.item.CustomEntityBoat;
import fr.welloznetwork.wellozmod.entity.item.CustomEntityBoat.BoatType;
import fr.welloznetwork.wellozmod.entity.monster.EntityEndermite;
import fr.welloznetwork.wellozmod.entity.monster.EntityStray;
import fr.welloznetwork.wellozmod.entity.passive.EntityRabbit;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeChicken;
import fr.welloznetwork.wellozmod.entity.passive.snow.EntitySnowChicken;
import fr.welloznetwork.wellozmod.entity.player.CustomEntityPlayer;
import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import fr.welloznetwork.wellozmod.entity.projectile.EntityArrowTipped;
import fr.welloznetwork.wellozmod.event.entity.player.PlayerDamageEvent;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryEnchantments;
import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryPotions;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import fr.welloznetwork.wellozmod.item.ItemArrowTipped;
import fr.welloznetwork.wellozmod.item.ItemElytra;
import fr.welloznetwork.wellozmod.item.armor.ItemArmorObsidian;
import fr.welloznetwork.wellozmod.network.play.client.C04PacketOpenGui;
import fr.welloznetwork.wellozmod.network.play.client.C05PacketScaffolding;
import fr.welloznetwork.wellozmod.network.play.server.S07PacketGrades;
import fr.welloznetwork.wellozmod.world.CustomTeleporter;
import fr.welloznetwork.wellozmod.world.TeleporterSpawn;
import fr.welloznetwork.wellozmod.world.gen.structure.MapGenMesaMineshaft;
import fr.welloznetwork.wellozmod.world.gen.structure.StructureMesaMineshaftPieces;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingPackSizeEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.event.entity.player.PlayerEvent.SaveToFile;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.ChunkEvent.Load;
import net.minecraftforge.event.world.ChunkEvent.Unload;

public class ServerListener
{
    private boolean dropAtLeastOne = true, chickensDropFeathers = true, hasFeather = false, clearLagWarning = false;
    private Random random = new Random();
    private List<String> playersWithStepUp = new ArrayList<String>();
    private int minimumLevel, afkTickCounter = 0, clearLagCounter = 0, clearLagInterval = 7200;
    private Map<Entity, JumpInfo> jumpBoost = (new MapMaker()).weakKeys().makeMap();
    private Set<Chunk> loadedChunks = Collections.newSetFromMap(new WeakHashMap<Chunk, Boolean>());
    public static HashSet<EntityPlayerMP> playersClosedContainers = new HashSet<>();
    private static final Map<UUID, Long> AFK_PLAYERS = new HashMap<>();
    public static Map<Block, Item> buckets = new HashMap<>();
    
    public ServerListener()
    {
        this.minimumLevel--;
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }
    
    private boolean shouldPlayerHaveStepUp(EntityPlayer entityPlayer)
    {
        PotionEffect jumpBoost = entityPlayer.getActivePotionEffect(Potion.jump);
        return (jumpBoost != null && jumpBoost.getAmplifier() >= this.minimumLevel);
    }
    
    private void replaceEntity(Entity oldEntity, Entity newEntity, World world, Chunk chunk)
    {
        newEntity.copyDataFrom(oldEntity, true);

        if (this.loadedChunks.contains(chunk))
        {
            world.spawnEntityInWorld(newEntity);
        }
        
        else
        {
            chunk.addEntity(newEntity);
        }
        
        oldEntity.setDead();
    }
    
    private void teleportToSpawn(EntityPlayerMP entityPlayerMP, int[] data)
    {
        MinecraftServer minecraftServer = MinecraftServer.getServer();
        
        int currentDimension = entityPlayerMP.worldObj.provider.dimensionId;
        int destinationDimension = data[4];
        
        ServerConfigurationManager serverConfigurationManager = minecraftServer.getConfigurationManager();
        WorldServer worldServer = minecraftServer.worldServerForDimension(destinationDimension);
        
        if (destinationDimension != currentDimension)
        {
            serverConfigurationManager.transferPlayerToDimension(entityPlayerMP, destinationDimension, new TeleporterSpawn(worldServer));
        }
        
        else
        {
            float posX = data[0];
            float posY = data[1];
            float posZ = data[2];
            float yaw = data[3];
            
            entityPlayerMP.setLocationAndAngles(posX + 0.5D, posY + 0.5D, posZ + 0.5D, yaw, 0.0F);
            entityPlayerMP.setPositionAndUpdate(posX + 0.5D, posY + 0.5D, posZ + 0.5D);
            entityPlayerMP.rotationYaw = yaw;
            entityPlayerMP.motionX = 0.0D;
            entityPlayerMP.motionY = 0.0D;
            entityPlayerMP.motionZ = 0.0D;
        }
    }
    
    private String colorText(String message)
    {
        if (message == null)
        {
            return null;
        }
        
        return message.replace('\u0026', '\u00A7');
    }
    
    public int clearLag()
    {
        int removedEntities = 0;
        
        for (WorldServer worldServer : MinecraftServer.getServer().worldServers)
        {
            if (worldServer != null)
            {
                for (Object o : worldServer.loadedEntityList)
                {
                    if (o instanceof EntityItem)
                    {
                        ((EntityItem) o).setDead();
                        removedEntities++;
                    }
                }
            }
        }
        
        return removedEntities;
    }
    
    public int getTimeBeforeClearLag()
    {
        return (this.clearLagInterval - this.clearLagCounter) / 20;
    }
    
    public void resetClearLagCounter()
    {
        this.clearLagCounter = 0;
        this.clearLagWarning = false;
    }
    
    private ItemStack fillBucket(World world, MovingObjectPosition movingObjectPosition, EntityPlayer entityPlayer)
    {
        Block block = world.getBlock(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
        
        if (!entityPlayer.canHarvestBlock(block))
        {
            return null;
        }
        
        Item item = buckets.get(block);
        
        if (item != null && world.getBlockMetadata(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ) == 0)
        {
            world.setBlockToAir(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
            return new ItemStack(item);
        }
        
        return null;
    }
    
    /*private Entity getEntityClosestToStartPos(EntityPlayer entityPlayer, World world, Vec3 start, Vec3 end)
    {
        Entity closest = null;
        
        double minDistance = Double.MAX_VALUE;
        
        AxisAlignedBB axisAlignedBB = AxisAlignedBB.getBoundingBox(Math.min(start.xCoord, end.xCoord), Math.min(start.yCoord, end.yCoord), Math.min(start.zCoord, end.zCoord), Math.max(start.xCoord, end.xCoord), Math.max(start.yCoord, end.yCoord), Math.max(start.zCoord, end.zCoord)).expand(1.0D, 1.0D, 1.0D);
        
        List list = world.getEntitiesWithinAABBExcludingEntity(entityPlayer, axisAlignedBB);
        
        for (Object object : list)
        {
            Entity entity = (Entity) object;
            
            if (!entity.canBeCollidedWith())
            {
                continue;
            }
            
            AxisAlignedBB axisAlignedBB1 = entity.boundingBox.expand(entity.getCollisionBorderSize(), entity.getCollisionBorderSize(), entity.getCollisionBorderSize());
            MovingObjectPosition movingObjectPosition = axisAlignedBB1.calculateIntercept(start, end);
            
            if (movingObjectPosition != null)
            {
                double distance = start.distanceTo(movingObjectPosition.hitVec);
                
                if (distance < minDistance)
                {
                    closest = entity;
                    minDistance = distance;
                }
            }
        }
        
        return closest;
    }*/
    
    @SubscribeEvent
    public void onLivingDropsEvent(LivingDropsEvent event)
    {
        if (event.entity instanceof EntityWaterMob)
        {
            EntityItem catFish = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(RegistryItems.cat_fish, this.random.nextInt(3)));
            EntityItem shark = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(RegistryItems.shark, this.random.nextInt(3)));
        }
        
        if (!this.dropAtLeastOne || event.entity.worldObj.isRemote || !(event.entity instanceof EntityChicken) || !((EntityChicken) event.entity).isChild() && !this.chickensDropFeathers)
        {
            return;
        }
        
        EntityChicken entityChicken = (EntityChicken) event.entity;
        
        for (EntityItem entityItem : event.drops)
        {
            if (!(entityItem.getEntityItem().getItem() == null) && entityItem.getEntityItem().getItem().equals(Items.feather))
            {
                this.hasFeather = true;
                break;
            }
        }
        
        if (!this.hasFeather)
        {
            event.drops.add(new EntityItem(event.entity.worldObj, entityChicken.posX, entityChicken.posY, entityChicken.posZ, new ItemStack(Items.feather, 1)));
        }
    }
    
    @SubscribeEvent
    public void onUseHoeEvent(UseHoeEvent event)
    {
        if (event.world.getBlock(event.x, event.y, event.z) == RegistryBlocks.fertilized_dirt)
        {
            event.world.setBlock(event.x, event.y, event.z, RegistryBlocks.fertilized_dirt_tilled);
            event.world.playSoundEffect((event.x + 0.5F), (event.y + 0.5F), (event.z + 0.5F), RegistryBlocks.fertilized_dirt_tilled.stepSound.getStepResourcePath(), (RegistryBlocks.fertilized_dirt_tilled.stepSound.getVolume() + 1.0F) / 2.0F, RegistryBlocks.fertilized_dirt_tilled.stepSound.getPitch() * 0.8F);
            event.setResult(Result.ALLOW);
        }
        
        if (event.world.getBlock(event.x, event.y, event.z) == RegistryBlocks.coarse_dirt)
        {
            event.world.setBlock(event.x, event.y, event.z, Blocks.dirt);
            event.world.playSoundEffect(event.x + 0.5F, event.y + 0.5F, event.z + 0.5F, Block.soundTypeGravel.getStepResourcePath(), 1.0F, 0.8F);
            event.setResult(Result.ALLOW);
        }
    }
    
    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        EntityPlayer entityPlayer = (EntityPlayer) event.entityPlayer;
        
        if (entityPlayer != null)
        {
            World world = entityPlayer.worldObj;
            
            if (event.action == Action.RIGHT_CLICK_BLOCK)
            {
                if (world.getBlock(event.x, event.y, event.z) == Blocks.grass)
                {
                    ItemStack itemStack = entityPlayer.getCurrentEquippedItem();
                    
                    if (itemStack != null && (itemStack.getItem() instanceof ItemSpade))
                    {
                        world.setBlock(event.x, event.y, event.z, RegistryBlocks.grass_path);
                        entityPlayer.swingItem();
                        itemStack.damageItem(1, entityPlayer);
                        world.playSoundEffect(event.x + 0.5F, event.y + 0.5F, event.z + 0.5F, Block.soundTypeGravel.getStepResourcePath(), 1.0F, 0.8F);
                    }
                }
            }
            
            if (event.action == Action.RIGHT_CLICK_BLOCK)
            {
                Block block = entityPlayer.worldObj.getBlock(event.x, event.y, event.z);
                
                if (block == RegistryBlocks.cherry_trapdoor) 
                {
                    event.setCanceled(true);
                    entityPlayer.worldObj.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.soundTypeCherryWoodTrapdoor[event.world.rand.nextInt(RegistrySounds.soundTypeCherryWoodTrapdoor.length)], 1.0F, 1.0F);
                    
                    int metadata = entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z);
                    entityPlayer.worldObj.setBlockMetadataWithNotify(event.x, event.y, event.z, metadata ^ 4, 2);
                }
            }
        }
        
        AFK_PLAYERS.put(event.entityPlayer.getUniqueID(), System.currentTimeMillis());
        
        /*if (event.action != Action.LEFT_CLICK_BLOCK)
        {
            return;
        }
        
        World world = event.world;
        
        int x = event.x, y = event.y, z = event.z;
        
        Block block = world.getBlock(x, y, z);
        
        if (block.getCollisionBoundingBoxFromPool(world, x, y, z) != null)
        {
            return;
        }
                
        Vec3 start = Vec3.createVectorHelper(entityPlayer.posX, entityPlayer.posY + entityPlayer.getEyeHeight(), entityPlayer.posZ);
        Vec3 look = entityPlayer.getLook(1.0F);
        Vec3 end = start.addVector(look.xCoord * 4.5F, look.yCoord * 4.5F, look.zCoord * 4.5F);
        
        Entity entity = this.getEntityClosestToStartPos(entityPlayer, world, start, end);
        
        if (entity != null)
        {
            if (!world.isRemote)
            {
                entityPlayer.attackTargetEntityWithCurrentItem(entity);
            }
            
            event.setCanceled(true);
        }*/
    }
    
    @SubscribeEvent
    public void onLivingAttackEvent(LivingAttackEvent event)
    {
        if (event.source != null)
        {
            Entity entity = event.source.getEntity();
            
            if (entity != null && entity.ridingEntity == event.entity)
            {
                event.setCanceled(true);
            }
        }
        
        if (!event.isCanceled() && event.source instanceof EntityDamageSource && !(event.source instanceof EntityDamageSourceIndirect))
        {
            EntityDamageSource entityDamageSource = (EntityDamageSource) event.source;
            
            if (entityDamageSource.getEntity() != null && entityDamageSource.getEntity() instanceof EntityLivingBase)
            {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entityDamageSource.getEntity();
                
                if (entityLivingBase.isPotionActive(RegistryPotions.WITHER) && !entityLivingBase.isPotionActive(RegistryPotions.POISON))
                {
                    event.entityLiving.addPotionEffect(new PotionEffect(Potion.wither.id, 100, 1));
                }
                
                else if (entityLivingBase.isPotionActive(RegistryPotions.POISON) && !entityLivingBase.isPotionActive(RegistryPotions.WITHER))
                {
                    event.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, 100, 1));
                }
                
                else if (entityLivingBase.isPotionActive(RegistryPotions.FIRE))
                {
                    event.entityLiving.setFire(20);
                }
                
                else if (entityLivingBase.isPotionActive(RegistryPotions.LEVITATION))
                {
                    event.entityLiving.addPotionEffect(new PotionEffect(RegistryPotions.LEVITATION.id, 100, 1));
                }
                
                else if (entityLivingBase.isPotionActive(RegistryPotions.GLOWING))
                {
                    event.entityLiving.addPotionEffect(new PotionEffect(RegistryPotions.GLOWING.id, 100, 1));
                }
            }
        }
        
        Entity entity = event.entity;
        
        if (entity instanceof EntityPlayerMP && CustomEnchantmentHelper.checkSource(event.source))
        {
            EnchantmentLevel enchantmentLevel = CustomEnchantmentHelper.tryUseUpperArmor((EntityPlayer) entity);
            
            if (enchantmentLevel != null)
            {
                enchantmentLevel.createArmorExplosion(entity);
            }
        }
        
        if (event.source instanceof EntityDamageSourceIndirect)
        {
            EntityDamageSourceIndirect entityDamageSourceIndirect = (EntityDamageSourceIndirect) event.source;
            
            if (entityDamageSourceIndirect.getSourceOfDamage() instanceof EntityArrowTipped)
            {
                EntityArrowTipped entityArrowTipped = (EntityArrowTipped) entityDamageSourceIndirect.getSourceOfDamage();
                
                if (!entityArrowTipped.worldObj.isRemote && entityDamageSourceIndirect.getEntity() instanceof EntityLivingBase)
                {
                    List list = ((ItemArrowTipped) RegistryItems.tipped_arrow).getEffects(entityArrowTipped.getArrow());
                    Iterator iterator = list.iterator();
                    
                    while (iterator.hasNext())
                    {
                        PotionEffect potionEffect = (PotionEffect) iterator.next();
                        int i = potionEffect.getPotionID();
                        
                        if (Potion.potionTypes[i].isInstant())
                        {
                            Potion.potionTypes[i].affectEntity((EntityLivingBase) entityDamageSourceIndirect.getEntity(), event.entityLiving, potionEffect.getAmplifier(), 1.0D);
                        }
                        
                        else
                        {
                            event.entityLiving.addPotionEffect(new PotionEffect(i, potionEffect.getDuration(), potionEffect.getAmplifier()));
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent event)
    {
        if (event.isCanceled())
        {
            return;
        }
        
        if (!(event.entityLiving instanceof EntityLivingBase))
        {
            return;
        }
        
        if (event.entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) event.entityLiving;
            String string = entityPlayer.getGameProfile().getName() + ":" + entityPlayer.worldObj.isRemote;
            
            if (entityPlayer.isPotionActive(RegistryPotions.STEP))
            {
                entityPlayer.stepHeight = 3.0F;
            }
            
            else
            {
                entityPlayer.stepHeight = 0.6F;
            }
            
            if (entityPlayer.isPotionActive(RegistryPotions.WATER_JUMP))
            {
                if (entityPlayer.isInWater())
                {
                    entityPlayer.motionX *= 1.2D;
                    entityPlayer.motionY = 1.0D;
                    entityPlayer.motionZ *= 1.2D;
                    
                    if (entityPlayer.isCollidedHorizontally)
                    {
                        entityPlayer.onGround = true;
                    }
                }
            }
            
            int blockX = MathHelper.floor_double(entityPlayer.posX);
            int blockY = MathHelper.floor_double(entityPlayer.posY - 0.2D - (double) entityPlayer.yOffset);
            int blockZ = MathHelper.floor_double(entityPlayer.posZ);
            
            if (entityPlayer.worldObj.getBlock(blockX, blockY, blockZ) == RegistryBlocks.scaffolding)
            {
                if (entityPlayer.isSneaking())
                {
                    for (int i = 1; i < 24; i++)
                    {
                        if (entityPlayer.worldObj.getBlock(blockX, blockY - i, blockZ) == RegistryBlocks.scaffolding)
                        {
                            if (entityPlayer.worldObj.getBlock(blockX, blockY - i + 1, blockZ) == Blocks.air && entityPlayer.worldObj.getBlock(blockX, blockY - i + 2, blockZ) == Blocks.air)
                            {
                                entityPlayer.setPositionAndUpdate(blockX, blockY - i + 1, blockZ);
                            }
                        }
                    }
                }
            }
            
            if (entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() == RegistryItems.shield || entityPlayer.getCurrentArmor(2) != null && entityPlayer.getCurrentArmor(2).getItem() instanceof ItemArmorObsidian)
            {
                if (entityPlayer.hurtResistantTime > 0 && (entityPlayer.hurtTime > 0))
                {
                    entityPlayer.hurtResistantTime = 0;
                    entityPlayer.hurtTime = 0;
                    entityPlayer.motionX = 0.0D;
                    entityPlayer.motionY /= 10.0D;
                    entityPlayer.motionZ = 0.0D;
                }
            }
            
            if (entityPlayer.isEntityAlive())
            {
                CustomEnchantmentHelper.getFrostWalkerModifier(entityPlayer, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
            }
            
            if (this.playersWithStepUp.contains(string))
            {
                if (this.shouldPlayerHaveStepUp(entityPlayer))
                {
                    if (entityPlayer.isSneaking())
                    {
                        entityPlayer.stepHeight = 0.50001F;
                    }
                    
                    else
                    {
                        entityPlayer.stepHeight = 1.0F;
                    }
                }
                
                else
                {
                    entityPlayer.stepHeight = 0.5F;
                    this.playersWithStepUp.remove(string);
                }
            }
            
            else if (this.shouldPlayerHaveStepUp(entityPlayer))
            {
                this.playersWithStepUp.add(string);
                entityPlayer.stepHeight = 1.0F;
            }
        }
        
        if (event.entity.worldObj.isRemote || !(event.entity instanceof EntityChicken) || !(event.entity instanceof EntitySnowChicken) || !(event.entity instanceof EntityHugeChicken))
        {
            return;
        }
        
        EntityChicken entityChicken = (EntityChicken) event.entity;
        
        if ((this.chickensDropFeathers || !entityChicken.isChild()) && entityChicken.worldObj.rand.nextInt(28000) == 0)
        {
            entityChicken.dropItem(Items.feather, 1);
        }
        
        if (event.entityLiving instanceof IEntityPlayer)
        {
            ((IEntityPlayer) event.entityLiving).tickElytra();
        }
    }
    
    @SubscribeEvent
    public void onHarvestDropsEvent(HarvestDropsEvent event)
    {
        if (event.block == Blocks.deadbush)
        {
            boolean isShears = event.harvester != null && event.harvester.getCurrentEquippedItem() != null && event.harvester.getCurrentEquippedItem().getItem() == Items.shears;
            
            if (event.harvester == null || event.harvester.getCurrentEquippedItem() == null || !isShears)
            {
                for (int i = 0; i < event.world.rand.nextInt(3); i++)
                {
                    event.drops.add(new ItemStack(Items.stick));
                }
            }
        }
        
        else if (event.block == Blocks.web && event.harvester != null)
        {
            ItemStack itemStack = event.harvester.getCurrentEquippedItem();
            
            if (itemStack != null && itemStack.getItem() == Items.shears)
            {
                event.drops.clear();
                event.drops.add(new ItemStack(Blocks.web));
            }
        }
        
        else if (event.block == Blocks.iron_ore)
        {
            event.drops.clear();
            event.drops.add(new ItemStack(RegistryItems.raw_iron));
        }
        
        else if (event.block == Blocks.gold_ore)
        {
            event.drops.clear();
            event.drops.add(new ItemStack(RegistryItems.raw_gold));
        }
        
        else if (event.block == RegistryBlocks.zarkonium_ore)
        {
            event.harvester.triggerAchievement(RegistryAchievements.NEW_ORE);
        }
        
        else if (event.block == RegistryBlocks.ancient_debris)
        {
            event.harvester.triggerAchievement(RegistryAchievements.HIDE_IN_THE_DEPTH);
        }
    }
    
    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerLoggedInEvent event)
    {
        event.player.addStat(RegistryAchievements.NEW_ADVENTURE, 1);        
        
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.player;
        GradeType gradeType = RegistryGrades.getGrade(entityPlayerMP.getUniqueID());
        
        WelloZMod.proxy.getNetworkManager().sendToPlayer(new S07PacketGrades(entityPlayerMP.getUniqueID(), gradeType), entityPlayerMP);
        
        for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
        {
            if (o instanceof EntityPlayerMP)
            {
                EntityPlayerMP entityPlayerMP1 = (EntityPlayerMP) o;
                
                if (!entityPlayerMP1.getUniqueID().equals(entityPlayerMP.getUniqueID()))
                {
                    GradeType gradeType1 = RegistryGrades.getGrade(entityPlayerMP1.getUniqueID());
                    
                    WelloZMod.proxy.getNetworkManager().sendToPlayer(new S07PacketGrades(entityPlayerMP1.getUniqueID(), gradeType1), entityPlayerMP);
                    WelloZMod.proxy.getNetworkManager().sendToPlayer(new S07PacketGrades(entityPlayerMP.getUniqueID(), gradeType), entityPlayerMP1);
                }
            }
        }
        
        /*UUID uuid = event.player.getUniqueID();
        GradeType gradeType = RegistryGrades.getGrade(uuid);
        
        WelloZMod.proxy.getNetworkManager().sendToPlayer(new S07PacketGrades(uuid, gradeType), (EntityPlayerMP) event.player);*/
    }
    
    @SubscribeEvent
    public void onPlayerLoggedOutEvent(PlayerLoggedOutEvent event)
    {
        String username = event.player.getGameProfile().getName();
        this.playersWithStepUp.remove(username + ":false");
        this.playersWithStepUp.remove(username + ":true");
    }
    
    @SubscribeEvent
    public void onEntityInteractEvent(EntityInteractEvent event)
    {
        Entity target = event.target;
        EntityPlayer entityPlayer = (EntityPlayer) event.entityPlayer;
        
        if (target instanceof EntityChicken && !target.isDead && ((EntityChicken) target).hurtTime == 0)
        {
            ItemStack itemStack = entityPlayer.getHeldItem();
            
            if (itemStack == null || !(itemStack.getItem() == Items.shears))
            {
                itemStack = entityPlayer.getHeldItem();
            }
            
            if (!(itemStack == null) && itemStack.getItem() == Items.shears)
            {
                EntityItem entityItem = new EntityItem(event.entity.worldObj, target.posX, target.posY, target.posZ, new ItemStack(Items.feather, 1));
                entityPlayer.worldObj.spawnEntityInWorld(entityItem);
                
                target.attackEntityFrom(DamageSource.generic, 1);
                target.getEntityData().setBoolean("wellozmod:sheared", true);
                itemStack.damageItem(1, entityPlayer);
                entityPlayer.swingItem();
            }
        }
        
        ItemStack itemStack = entityPlayer.getCurrentEquippedItem();
        
        if (itemStack == null)
        {
            return;
        }
        
        if (!(target instanceof EntityAnimal))
        {
            return;
        }
        
        EntityAnimal entityAnimal = (EntityAnimal) event.target;
        EntityLiving entityLiving = (EntityLiving) event.target;
        
        if (!entityAnimal.isChild())
        {
            if (entityAnimal instanceof EntityPig)
            {
                if (itemStack.getItem() == RegistryItems.beetroot)
                {
                    if (!entityAnimal.isInLove())
                    {
                        entityAnimal.func_146082_f(entityPlayer);
                        
                        if (!entityPlayer.capabilities.isCreativeMode)
                        {
                            if (itemStack.stackSize-- <= 0)
                            {
                                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, itemStack);
                            }
                        }
                    }
                }
            }
            
            else if (entityAnimal instanceof EntityChicken)
            {
                if (itemStack.getItem() == RegistryItems.beetroot_seeds)
                {
                    if (!entityAnimal.isInLove())
                    {
                        entityAnimal.func_146082_f(entityPlayer);
                        
                        if (!entityPlayer.capabilities.isCreativeMode)
                        {
                            if (itemStack.stackSize-- <= 0)
                            {
                                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, itemStack);
                            }
                        }
                    }
                }
            }
        }
        
        if (event.entityPlayer != null && event.entityPlayer.getCurrentArmor(2) != null)
        {
            ItemStack itemStack1 = event.entityPlayer.getCurrentArmor(2);
            
            if (itemStack1 != null && itemStack1.getItem() instanceof ItemArmorObsidian)
            {
                ItemArmorObsidian itemArmorObsidian = (ItemArmorObsidian) itemStack1.getItem();
                
                if (itemArmorObsidian.armorType == 1)
                {
                    if (event.target instanceof EntityAnimal || event.target instanceof EntityHorse || event.target instanceof EntityPig)
                    {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
        if ((event.entityLiving != null) && (!event.entityLiving.worldObj.isRemote) && (event.source != null) && (event.source.getSourceOfDamage() != null))
        {
            Entity entity = (Entity) event.source.getSourceOfDamage();
            EntityLivingBase entityLivingBase = (EntityLivingBase) event.entityLiving;
             
            if ((entity instanceof EntityPlayer))
            {
                ItemStack itemStack = ((EntityPlayer) entity).getCurrentEquippedItem();
                CustomEnchantmentHelper.getDeathBringerModifier((EntityPlayer) entity, entityLivingBase, itemStack, event);
            }
             
            if ((entityLivingBase instanceof EntityPlayer))
            {
                CustomEnchantmentHelper.getImplantsModifier((EntityPlayer) entityLivingBase, entity, event);
            }
        }
         
        Entity targetEntity = (Entity) event.entity;
        EntityLivingBase entityLivingBase = (EntityLivingBase) targetEntity;
         
        if (entityLivingBase.getHealth() - event.ammount > 0.0F)
        {
            return;
        }
         
        if (entityLivingBase.getHeldItem() == null || entityLivingBase.getHeldItem().getItem() != RegistryItems.totem_of_undying)
        {
            return;
        }
         
        if (entityLivingBase instanceof EntityLiving || entityLivingBase instanceof EntityPlayer)
        {
            ((EntityPlayer) entityLivingBase).triggerAchievement(RegistryAchievements.INVINCIBLE);
            
            event.ammount = 0.0F;
            entityLivingBase.setHealth(1.0F);
            entityLivingBase.clearActivePotions();
            entityLivingBase.addPotionEffect(new PotionEffect(Potion.regeneration.id, 900, 1));
            entityLivingBase.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 100, 1));
            
            if (entityLivingBase instanceof EntityLiving)
            {
                entityLivingBase.setCurrentItemOrArmor(0, null);
            }
             
            if (entityLivingBase instanceof EntityPlayer)
            {
                ((EntityPlayer) entityLivingBase).destroyCurrentEquippedItem();
            }
             
            entityLivingBase.worldObj.playSoundEffect(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, RegistrySounds.SOUND_TYPE_TOTEM, 1.0F, 0.8F);
         }
    }

    @SubscribeEvent
    public void onLivingJumpEvent(LivingJumpEvent event)
    {
        Entity entity = event.entity;
        JumpInfo jumpInfo = this.jumpBoost.remove(entity);
        
        if (event.entity instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) event.entity;
            
            if (!entityPlayer.worldObj.isRemote && entityPlayer instanceof EntityPlayerMP)
            {
                int blockX = MathHelper.floor_double(entityPlayer.posX);
                int blockY = MathHelper.floor_double(entityPlayer.posY - 0.2D - (double) entityPlayer.yOffset);
                int blockZ = MathHelper.floor_double(entityPlayer.posZ);
                            
                if (entityPlayer.worldObj.getBlock(blockX, blockY, blockZ) == RegistryBlocks.scaffolding)
                {
                    for (int i = 1; i < 24; i++)
                    {
                        if (entityPlayer.worldObj.getBlock(blockX, blockY + i, blockZ) == RegistryBlocks.scaffolding)
                        {
                            if (entityPlayer.worldObj.getBlock(blockX, blockY + i + 1, blockZ) == Blocks.air && entityPlayer.worldObj.getBlock(blockX, blockY + i + 2, blockZ) == Blocks.air)
                            {
                                WelloZMod.proxy.getNetworkManager().sendToPlayer(new C05PacketScaffolding(blockX, blockY + i + 1, blockZ), (EntityPlayerMP) entityPlayer);
                            }
                            
                            break;
                        }
                    }
                }
                
                if (jumpInfo != null)
                {
                    jumpInfo.modifyVelocity(entity);
                }
            }
            
            if (entityPlayer.worldObj.isRemote)
            {
                if (jumpInfo != null)
                {                    
                    if (entity instanceof EntityPlayer)
                    {
                        jumpInfo.modifyVelocity(entity);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerPickupXpEvent(PlayerPickupXpEvent event)
    {
        if (CommandSpectator.isSpectator())
        {
            event.setCanceled(true);
        }
         
        EntityPlayer entityPlayer = (EntityPlayer) event.entityPlayer;
        EntityXPOrb entityXPOrb = (EntityXPOrb) event.orb;
            
        if (entityPlayer.worldObj.isRemote)
        {
            return;
        }
            
        ItemStack[] itemStacks = new ItemStack[5];
        itemStacks[0] = entityPlayer.getCurrentEquippedItem();
        itemStacks[1] = entityPlayer.getEquipmentInSlot(1);
        itemStacks[2] = entityPlayer.getEquipmentInSlot(2);
        itemStacks[3] = entityPlayer.getEquipmentInSlot(3);
        itemStacks[4] = entityPlayer.getEquipmentInSlot(4);
        
        for (ItemStack itemStack : itemStacks)
        {
            if (itemStack != null && itemStack.getItemDamage() > 0 && EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.MENDING.effectId, itemStack) > 0)
            {
                int xp = entityXPOrb.xpValue;
                
                while (xp > 0 && itemStack.getItemDamage() > 0)
                {
                    itemStack.setItemDamage(itemStack.getItemDamage() - 2);
                    xp--;
                }
                
                if (xp <= 0)
                {
                    entityXPOrb.setDead();
                    event.setCanceled(true);
                    return;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingFallEvent(LivingFallEvent event)
    {
        EntityLivingBase entityLivingBase = (EntityLivingBase) event.entityLiving;
        
        if (event.distance > 4.0F && !entityLivingBase.isSneaking() && entityLivingBase instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) entityLivingBase;
            EnchantmentLevel enchantmentLevel = CustomEnchantmentHelper.tryUseEnchantment(entityPlayer, 0);
            
            if (enchantmentLevel == null)
            {
                return;
            }
            
            JumpInfo jumpInfo = new JumpInfo(enchantmentLevel, event.distance);
            enchantmentLevel.createJumpExplosion((Entity) entityPlayer);
            
            if (entityPlayer instanceof EntityPlayer)
            {
                this.jumpBoost.put(entityPlayer, jumpInfo);
            }
            
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPlayerDamageEvent(PlayerDamageEvent event)
    {
        if (CommandSpectator.isSpectator())
        {
            event.setCanceled(true);
        }
        
        EntityPlayer entityPlayer = (EntityPlayer) event.entityPlayer;
        int enchantmentLevels = CustomEnchantmentHelper.getLastStandModifier(entityPlayer);
        
        if (enchantmentLevels > 0)
        {
            float playerHealth = entityPlayer.getHealth();
            float avaibleHealth = playerHealth - event.amount;
            
            if (avaibleHealth < 1.0F)
            {
                int avaibleXp = CustomEnchantmentHelper.getPlayerXP(entityPlayer);
                float requiredXp = 0.0F;
                
                if (avaibleXp >= requiredXp)
                {
                    entityPlayer.setHealth(1.0F);
                    CustomEnchantmentHelper.addPlayerXP(entityPlayer, -((int) requiredXp));
                    event.amount = 0.0F;
                    event.setCanceled(true);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinWorldEvent(EntityJoinWorldEvent event)
    {
        Chunk chunk = event.world.getChunkFromChunkCoords(MathHelper.floor_double(event.entity.posX) >> 4, MathHelper.floor_double(event.entity.posZ) >> 4);
        
        if (event.entity.getClass() == EntityBoat.class)
        {
            CustomEntityBoat customEntityBoat = new CustomEntityBoat(event.world);
            event.entity.rotationYaw += 90.0F;
            this.replaceEntity(event.entity, customEntityBoat, event.world, chunk);
            customEntityBoat.setBoatType(BoatType.OAK);
            event.setCanceled(true);
            return;
        }
        
        if (event.entity instanceof EntityPig)
        {
            EntityPig entityPig = (EntityPig) event.entity;
            entityPig.tasks.addTask(4, new EntityAITempt(entityPig, 1.2D, RegistryItems.beetroot, false));
        }
        
        else if (event.entity instanceof EntityChicken)
        {
            EntityChicken entityChicken = (EntityChicken) event.entity;
            entityChicken.tasks.addTask(3, new EntityAITempt(entityChicken, 1.0D, RegistryItems.beetroot_seeds, false));
        }
        
        else if (event.entity instanceof EntityWolf)
        {
            EntityWolf entityWolf = (EntityWolf) event.entity;
            entityWolf.targetTasks.addTask(4, new EntityAITargetNonTamed(entityWolf, EntityRabbit.class, 200, false));
        }
        
        else if (event.entity instanceof EntityVillager)
        {
            EntityVillager entityVillager = (EntityVillager) event.entity;
            
            for (Object obj : entityVillager.tasks.taskEntries)
            {
                EntityAITaskEntry entityAITaskEntry = (EntityAITaskEntry) obj;
                
                if (entityAITaskEntry.action instanceof EntityAIOpenDoor)
                {
                    entityVillager.tasks.removeTask(entityAITaskEntry.action);
                    entityVillager.tasks.addTask(entityAITaskEntry.priority, new EntityAIOpenDoor(entityVillager, true));
                    break;
                }
            }
        }
        
        else if (event.entity instanceof EntityEnderman)
        {
            EntityEnderman entityEnderman = (EntityEnderman) event.entity;
            entityEnderman.targetTasks.addTask(3, new EntityAINearestAttackableTarget(entityEnderman, EntityEndermite.class, 100, false));
        }
        
        if (!event.world.isRemote && event.entity instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) event.entity;
            NBTTagCompound nbtTagCompound = entityPlayer.getEntityData();
            
            if (nbtTagCompound.hasKey(CustomEntityPlayer.ID))
            {
                CustomEntityPlayer.get(entityPlayer).loadNBTData(nbtTagCompound.getCompoundTag(CustomEntityPlayer.ID));
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityStruckByLightningEvent(EntityStruckByLightningEvent event)
    {
        if (event.entity instanceof EntityVillager)
        {
            EntityVillager entityVillager = (EntityVillager) event.entity;
            
            EntityWitch entityWitch = new EntityWitch(entityVillager.worldObj);
            entityWitch.copyLocationAndAnglesFrom(entityVillager);
            entityWitch.onSpawnWithEgg(null);
            entityVillager.worldObj.spawnEntityInWorld(entityWitch);
            entityVillager.setDead();
        }
    }
    
    @SubscribeEvent
    public void onEntityItemPickupEvent(EntityItemPickupEvent event)
    {
        if (CommandSpectator.isSpectator())
        {
            event.setCanceled(true);
        }
                
        if (event.item.getEntityItem().getItem() == RegistryItems.tomato)
        {
            event.entityPlayer.triggerAchievement(RegistryAchievements.TOMATO);
        }
    }
    
    @SubscribeEvent
    public void onFillBucketEvent(FillBucketEvent event)
    {
        ItemStack itemStack = this.fillBucket(event.world, event.target, event.entityPlayer);
        
        if (itemStack == null)
        {
            return;
        }
        
        event.result = itemStack;
        event.setResult(Result.ALLOW);
    }
    
    @SubscribeEvent
    public void onInitMapGenEvent(InitMapGenEvent event)
    {
        if (event.type == EventType.MINESHAFT)
        {
            StructureMesaMineshaftPieces.registerStructurePieces();
            
            event.newGen = new MapGenMesaMineshaft();
        }
    }
    
    @SubscribeEvent
    public void onChunkEventLoad(Load event)
    {
        this.loadedChunks.add(event.getChunk());
    }
    
    @SubscribeEvent
    public void onChunkEventUnload(Unload event)
    {
        this.loadedChunks.remove(event.getChunk());
    }
    
    @SubscribeEvent
    public void onPlayerDropsEvent(PlayerDropsEvent event)
    {
        if (CommandSpectator.isSpectator())
        {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event)
    {
        if (!(event.entityPlayer instanceof EntityPlayerMP))
        {
            return;
        }
        
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.entityPlayer;
        
        if (entityPlayerMP.dimension != 0)
        {
            return;
        }
        
        if (entityPlayerMP.worldObj.isDaytime())
        {
            return;
        }
        
        if (entityPlayerMP.worldObj.rand.nextInt(100) != 0)
        {
            return;
        }
                
        if (MinecraftServer.getServer() == null)
        {
            return;
        }
        
        if (MinecraftServer.getServer().worldServerForDimension(2) == null)
        {
            return;
        }
        
        WelloZMod.proxy.getNetworkManager().sendToPlayer(new C04PacketOpenGui(), entityPlayerMP);
    }
    
    @SubscribeEvent
    public void onEntityConstructingEvent(EntityConstructing event)
    {
        if (event.entity instanceof EntityPlayer && CustomEntityPlayer.get((EntityPlayer) event.entity) == null)
        {
            event.entity.registerExtendedProperties(CustomEntityPlayer.ID, new CustomEntityPlayer());
        }
    }
    
    @SubscribeEvent
    public void onEnderTeleportEvent(EnderTeleportEvent event)
    {
        EntityLivingBase entityLivingBase = event.entityLiving;
        
        if (entityLivingBase instanceof EntityPlayerMP)
        {
            if (entityLivingBase.getRNG().nextFloat() < 0.05F && entityLivingBase.worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning"))
            {
                EntityEndermite entityEndermite = new EntityEndermite(entityLivingBase.worldObj);
                entityEndermite.setLocationAndAngles(event.targetX, event.targetY, event.targetZ, entityLivingBase.rotationYaw, entityLivingBase.rotationPitch);
                entityLivingBase.worldObj.spawnEntityInWorld(entityEndermite);
                entityEndermite.setSpawnedByPlayer(true);
                entityEndermite.aggroEndermen(64);
            }
        }
    }
    
    @SubscribeEvent
    public void onSpecialSpawn(SpecialSpawn event)
    {
        if (EntityList.getEntityID(event.entity) == 51 && event.world.rand.nextFloat() < 0.80F && event.world.canBlockSeeTheSky((int) event.x, (int) (event.y) + 1, (int) event.z))
        {
            Type[] biomeTags = BiomeDictionary.getTypesForBiome(event.world.getBiomeGenForCoords((int) event.x, (int) event.z));
            
            if (ArrayUtils.contains(biomeTags, Type.SNOWY))
            {
                EntityStray entityStray = new EntityStray(event.world);
                this.replaceEntity(event.entity, entityStray, event.world, event.world.getChunkFromBlockCoords((int) event.x, (int) event.z));
                entityStray.onSpawnWithEgg(null);
                event.setCanceled(true);
                event.setResult(Result.DENY);
            }
        }
    }
    
    @SubscribeEvent
    public void onArrowNockEvent(ArrowNockEvent event)
    {
        if (event.result == null)
        {
            return;
        }
        
        IInventory iinventory = event.entityPlayer.inventory;
        
        for (int i = 0; i < iinventory.getSizeInventory(); i++)
        {
            ItemStack itemStack = iinventory.getStackInSlot(i);
            
            if (itemStack == null || itemStack.stackSize <= 0)
            {
                continue;
            }
            
            if (itemStack.getItem() == Items.arrow)
            {
                return;
            }
            
            if (itemStack.getItem() == RegistryItems.tipped_arrow)
            {
                event.setCanceled(true);
                event.entityPlayer.setItemInUse(event.result, event.result.getItem().getMaxItemUseDuration(event.result));
                return;
            }
        }
    }
    
    @SubscribeEvent
    public void onArrowLooseEvent(ArrowLooseEvent event)
    {
        if (event.bow == null)
        {
            return;
        }
        
        IInventory iinventory = event.entityPlayer.inventory;
        EntityPlayer entityPlayer = (EntityPlayer) event.entityPlayer;
        
        for (int i = 0; i < iinventory.getSizeInventory(); i++)
        {
            ItemStack itemStack = iinventory.getStackInSlot(i);
            
            if (itemStack != null && itemStack.stackSize > 0 && itemStack.getItem() == RegistryItems.tipped_arrow)
            {
                float charge = event.charge / 20.0F;
                charge = (charge * charge + charge * 2.0F) / 3.0F;
                
                if (charge < 0.1D)
                {
                    return;
                }
                
                if (charge > 1.0F)
                {
                    charge = 1.0F;
                }
                
                EntityArrowTipped entityArrowTipped = new EntityArrowTipped(entityPlayer.worldObj, entityPlayer, charge * 2.0F);
                entityArrowTipped.setArrow(itemStack);
                
                if (charge == 1.0F)
                {
                    entityArrowTipped.setIsCritical(true);
                }
                
                int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, event.bow);
                
                if (power > 0)
                {
                    entityArrowTipped.setDamage(entityArrowTipped.getDamage() + power * 0.5D + 0.5D);
                }
                
                int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, event.bow);
                
                if (punch > 0)
                {
                    entityArrowTipped.setKnockbackStrength(punch);
                }
                
                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, event.bow) > 0)
                {
                    entityArrowTipped.setFire(100);
                }
                
                event.bow.damageItem(1, entityPlayer);
                entityPlayer.worldObj.playSoundAtEntity(entityPlayer, "random.bow", 1.0F, 1.0F / (entityPlayer.worldObj.rand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
                
                if (!entityPlayer.capabilities.isCreativeMode && --itemStack.stackSize <= 0)
                {
                    entityPlayer.inventory.setInventorySlotContents(i, null);
                }

                entityPlayer.worldObj.spawnEntityInWorld(entityArrowTipped);
                
                event.setCanceled(true);
                return;
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerTickEvent(PlayerTickEvent event)
    {
        if (event.phase == Phase.START)
        {            
            if (!CommandSpectator.isSpectator() && event.player.noClip)
            {
                event.player.setInvisible(false);
            }
            
            event.player.noClip = CommandSpectator.isSpectator();
            
            if (CommandSpectator.isSpectator())
            {
                event.player.onGround = false;
                event.player.setInvisible(true);
            }
        }
        
        EntityPlayer entityPlayer = (EntityPlayer) event.player;
        
        if (entityPlayer.inventory.hasItem(Items.record_13) && entityPlayer.inventory.hasItem(Items.record_cat) && entityPlayer.inventory.hasItem(Items.record_blocks) && entityPlayer.inventory.hasItem(Items.record_chirp) && entityPlayer.inventory.hasItem(Items.record_far) && entityPlayer.inventory.hasItem(Items.record_mall) && entityPlayer.inventory.hasItem(Items.record_mellohi) && entityPlayer.inventory.hasItem(Items.record_stal) && entityPlayer.inventory.hasItem(Items.record_strad) && entityPlayer.inventory.hasItem(Items.record_ward) && entityPlayer.inventory.hasItem(Items.record_11) && entityPlayer.inventory.hasItem(Items.record_wait) && entityPlayer.inventory.hasItem(RegistryItems.music_disc_5) && entityPlayer.inventory.hasItem(RegistryItems.music_disc_calmos) && entityPlayer.inventory.hasItem(RegistryItems.music_disc_jungle_belle) && entityPlayer.inventory.hasItem(RegistryItems.music_disc_lava_ground) && entityPlayer.inventory.hasItem(RegistryItems.music_disc_mid_day) && entityPlayer.inventory.hasItem(RegistryItems.music_disc_otherside) && entityPlayer.inventory.hasItem(RegistryItems.music_disc_pigstep) && entityPlayer.inventory.hasItem(RegistryItems.music_disc_relic))
        {
            entityPlayer.triggerAchievement(RegistryAchievements.HERE_A_DJ);
        }
        
        if (event.phase == Phase.END)
        {
            if (!entityPlayer.onGround && entityPlayer.motionY < 0.0D && !entityPlayer.capabilities.isFlying)
            {
                ItemStack itemStack = entityPlayer.getEquipmentInSlot(3);
                
                if (itemStack != null && itemStack.getItem() == RegistryItems.elytra && ItemElytra.isBroken(itemStack))
                {
                    entityPlayer.triggerAchievement(RegistryAchievements.FLYING_PETER_PAN);
                    
                    double horizontalSpeed, verticalSpeed;
                    
                    if (entityPlayer.isSneaking())
                    {
                        horizontalSpeed = 0.1D;
                        verticalSpeed = 0.9D;
                    }
                    
                    else
                    {
                        horizontalSpeed = 0.03D;
                        verticalSpeed = 0.7D;
                    }
                    
                    entityPlayer.motionY *= verticalSpeed;
                    double x = Math.cos(Math.toRadians(entityPlayer.rotationYawHead + 90.0F)) * horizontalSpeed;
                    double z = Math.sin(Math.toRadians(entityPlayer.rotationYawHead + 90.0F)) * horizontalSpeed;
                    entityPlayer.motionX += x;
                    entityPlayer.motionZ += z;
                    entityPlayer.fallDistance = 0.0F;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerCloneEvent(Clone event)
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        
        event.original.getExtendedProperties(CustomEntityPlayer.ID).saveNBTData(nbtTagCompound);
        event.entityPlayer.getExtendedProperties(CustomEntityPlayer.ID).loadNBTData(nbtTagCompound);
    }
    
    @SubscribeEvent
    public void onPlayerSaveToFileEvent(SaveToFile event)
    {
        EntityPlayer entityPlayer = event.entityPlayer;
        NBTTagCompound nbtTagCompound = entityPlayer.getEntityData();
        NBTTagCompound nbtTagCompound1 = new NBTTagCompound();
        
        CustomEntityPlayer.get(entityPlayer).saveNBTData(nbtTagCompound1);
        nbtTagCompound.setTag(CustomEntityPlayer.ID, nbtTagCompound1);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onCheckSpawnEvent(CheckSpawn event)
    {
        if (event.entityLiving.getClass() == EntitySquid.class)
        {
            if (event.world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(event.x, event.y, event.z, event.x, event.y, event.z).expand(64.0D, 64.0D, 64.0D)).size() == 0)
            {
                event.setResult(Result.DENY);
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingPackSizeEvent(LivingPackSizeEvent event)
    {
        if (event.entityLiving.getClass() == EntitySquid.class)
        {
            event.maxPackSize = 2;
        }
    }
    
    @SubscribeEvent
    public void onItemTossEvent(ItemTossEvent event)
    {
        if (event.player instanceof EntityPlayerMP && playersClosedContainers.contains(event.player))
        {
            if (event.player.inventory.addItemStackToInventory(event.entityItem.getEntityItem()))
            {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onServerTickEvent(ServerTickEvent event)
    {
        if (event.phase == Phase.END)
        {
            playersClosedContainers.clear();
            
            this.afkTickCounter++;
            
            if (this.afkTickCounter >= 20)
            {
                for (Object o : FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList)
                {
                    if (o instanceof EntityPlayerMP)
                    {
                        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) o;
                        
                        if (System.currentTimeMillis() - AFK_PLAYERS.getOrDefault(entityPlayerMP, System.currentTimeMillis()) >= 600000L)
                        {
                            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.afk.kick");
                            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                            
                            entityPlayerMP.addChatMessage(chatComponentTranslation);
                            
                            FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(entityPlayerMP, "lobby");
                            AFK_PLAYERS.put(entityPlayerMP.getUniqueID(), System.currentTimeMillis());
                        }
                    }
                }
                
                this.afkTickCounter = 0;
            }
            
            this.clearLagCounter++;
            
            if (!this.clearLagWarning && this.clearLagCounter >= this.clearLagInterval - 600)
            {
                this.clearLagWarning = true;
                
                for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
                {
                    if (o instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP) o).addChatMessage(new ChatComponentTranslation("commands.clearlag.removeEntities"));
                    }
                }
            }
            
            if (this.clearLagCounter >= this.clearLagInterval)
            {
                this.clearLag();
                
                for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
                {
                    if (o instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP) o).addChatMessage(new ChatComponentTranslation("commands.clearlag.removedEntities"));
                    }
                }
                
                this.clearLagCounter = 0;
                this.clearLagWarning = false;
            }
        }
        
        Iterator<Entry<EntityPlayerMP, TeleportData>> iteratorSpawn = CommandSpawn.COOLDOWN_MAP.entrySet().iterator();
        
        while (iteratorSpawn.hasNext())
        {
            Entry<EntityPlayerMP, TeleportData> entry = iteratorSpawn.next();
            
            EntityPlayerMP entityPlayerMP = entry.getKey();
            TeleportData teleportData = entry.getValue();
            
            if (entityPlayerMP.posX != teleportData.x || entityPlayerMP.posY != teleportData.y || entityPlayerMP.posZ != teleportData.z)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.spawn.move");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                entityPlayerMP.addChatMessage(chatComponentTranslation);
                
                iteratorSpawn.remove();
                continue;
            }
            
            teleportData.cooldown--;
            
            if (teleportData.cooldown <= 0)
            {
                this.teleportToSpawn(entityPlayerMP, teleportData.data);
                iteratorSpawn.remove();
            }
        }
        
        Iterator<Entry<EntityPlayerMP, TeleportData>> iteratorTPAccept = CommandTPAccept.COOLDOWN_MAP.entrySet().iterator();
        
        while (iteratorTPAccept.hasNext())
        {
            Entry<EntityPlayerMP, TeleportData> entry = iteratorTPAccept.next();
            
            EntityPlayerMP entityPlayerMP = entry.getKey();
            TeleportData teleportData = entry.getValue();
            
            teleportData.cooldown--;
            
            if (teleportData.cooldown <= 0)
            {
                int[] data = teleportData.data;
                
                int posX = data[0];
                int posY = data[1];
                int posZ = data[2];
                int yaw = data[3];
                int dimension = data[4];
                
                if (entityPlayerMP.dimension != dimension)
                {
                    MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityPlayerMP, dimension, new CustomTeleporter(MinecraftServer.getServer().worldServerForDimension(dimension)));
                }
                
                entityPlayerMP.setLocationAndAngles(posX + 0.5D, posY, posZ + 0.5D, yaw, 0.0F);
                entityPlayerMP.setPositionAndUpdate(posX + 0.5D, posY, posZ + 0.5D);
                entityPlayerMP.motionX = entityPlayerMP.motionY = entityPlayerMP.motionZ = 0.0D;
                
                iteratorTPAccept.remove();
            }
        }
        
        HashSet<Teleport> toRemove = new HashSet<>();
        
        for (Teleport teleport : CommandTPA.PLAYERS_MAP.keySet())
        {
            int time = CommandTPA.PLAYERS_MAP.get(teleport).intValue();
            
            if (time > 0)
            {
                time--;
                CommandTPA.PLAYERS_MAP.put(teleport, Integer.valueOf(time));
                continue;
            }
            
            if (time <= 0)
            {
                toRemove.add(teleport);
                
                EntityPlayerMP entityPlayerMP = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152612_a(teleport.getSender());
                EntityPlayerMP entityPlayerMP1 = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152612_a(teleport.getReceiver());
                
                if (entityPlayerMP != null)
                {
                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.tpa.teleportTimeOut", teleport.getSender());
                    
                    entityPlayerMP.addChatMessage(chatComponentTranslation);
                }
                
                if (entityPlayerMP1 != null)
                {
                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.tpa.timeOut", teleport.getReceiver());
                    
                    entityPlayerMP1.addChatMessage(chatComponentTranslation);
                }
            }
        }
        
        for (Teleport teleport : toRemove)
        {
            CommandTPA.PLAYERS_MAP.remove(teleport);
        }
    }
    
    @SubscribeEvent
    public void onLivingHurtEvent1(LivingHurtEvent event)
    {
        if (!(event.entityLiving instanceof EntityPlayer))
        {
            return;
        }

        EntityPlayer entityPlayer = (EntityPlayer) event.entityLiving;
        
        if (entityPlayer.getCurrentArmor(2) == null || entityPlayer.getCurrentArmor(2).getItem() != RegistryItems.slime_chestplate)
        {
            return;
        }

        if (!(event.source.getEntity() instanceof EntityLivingBase))
        {
            return;
        }

        EntityLivingBase entityLivingBase1 = (EntityLivingBase) event.source.getEntity();

        double x = entityLivingBase1.posX - entityPlayer.posX;
        double z = entityLivingBase1.posZ - entityPlayer.posZ;

        if (MathHelper.sqrt_double(x * x + z * z) > 0.0D)
        {
            entityLivingBase1.motionX += (x / MathHelper.sqrt_double(x * x + z * z)) * 0.4D;
            entityLivingBase1.motionZ += (z / MathHelper.sqrt_double(x * x + z * z)) * 0.4D;
            entityLivingBase1.velocityChanged = true;
        }
    }
    
    @SubscribeEvent
    public void onItemCraftedEvent(ItemCraftedEvent event)
    {
        if (event.crafting.getItem() == RegistryItems.shield)
        {
            event.player.triggerAchievement(RegistryAchievements.SHIELD);
        }
    }
    
    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event)
    {
        GradeType gradeType = RegistryGrades.getGrade(event.player.getUniqueID());    
        
        ChatComponentText prefix = new ChatComponentText("");
        
        if (gradeType != null && gradeType != GradeType.NOT_SET)
        {
            prefix.getChatStyle().setColor(gradeType.getColor());
            prefix.appendText("[");
            prefix.appendSibling(new ChatComponentTranslation(gradeType.getTranslationKey()));
            prefix.appendText("] ");
        }
        
        if (Constants.V2)
        {
            event.component = new ChatComponentTranslation("%s%s: %s", prefix, new ChatComponentText(event.player.getCommandSenderName()), new ChatComponentText(this.colorText(event.message)));
        }
        
        else
        {
            event.component = new ChatComponentTranslation("%s%s: %s", prefix, new ChatComponentText(event.player.getCommandSenderName()), new ChatComponentText(event.message));
        }
        
        AFK_PLAYERS.put(event.player.getUniqueID(), System.currentTimeMillis());
    }
    
    @SubscribeEvent
    public void onNameFormatEvent(NameFormat event)
    {
        GradeType gradeType = RegistryGrades.getGrade(event.entityPlayer.getUniqueID());
        
        if (gradeType != null && gradeType != GradeType.NOT_SET)
        {
            event.displayname = gradeType.getColor() + event.displayname;
        }
    }
    
    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        if (event.entity instanceof EntityPlayer)
        {
            CommandDeathLocate.onLivingDeathEvent((EntityPlayer) event.entity);
        }
    }
    
    @SubscribeEvent
    public void onPlayerEvent(PlayerEvent event)
    {
        if (event.entity instanceof EntityPlayerMP)
        {
            AFK_PLAYERS.put(event.entity.getUniqueID(), System.currentTimeMillis());
        }
    }
}