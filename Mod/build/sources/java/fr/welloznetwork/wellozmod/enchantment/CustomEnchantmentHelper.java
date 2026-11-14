package fr.welloznetwork.wellozmod.enchantment;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

@Custom(EnchantmentHelper.class)
public class CustomEnchantmentHelper
{
    private static final List PROTECTION_PARTS = Lists.newArrayList((Object[]) new Integer[] {Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(1)});
    private static final Set<String> ALLOWED_DAMAGE_SOURCE = (Set<String>) ImmutableSet.of("arrow", "player", "mob");
    private static final ItemStack GUNPOWDER = new ItemStack(Items.gunpowder);
    private static final EnchantmentLevel[] ENCHANTMENT_LEVELS = new EnchantmentLevel[] {null, new EnchantmentLevel(0.1D, 5.0D, 5.0F, 1.0F, false, 1), new EnchantmentLevel(0.75D, 7.5D, 10.0F, 2.0F, false, 2), new EnchantmentLevel(1.0D, 10.0D, 5.0F, 4.0F, true, 4)};
    
	public static void getImplantsModifier(EntityPlayer entityPlayer, Entity entity, LivingHurtEvent event)
	{
		int wither = countEnchantLevels(entityPlayer, RegistryEnchantments.WITHER_HUNTER.effectId);
		
		if ((wither > 0) && ((entity instanceof EntityWither))) 
		{
			event.ammount /= wither;
		}
		
		int implants = countEnchantLevels(entityPlayer, RegistryEnchantments.IMPLANTS.effectId);
		
		if (implants > 0)
		{
			Random rand = entityPlayer.worldObj.rand;
			
			if (rand.nextInt(150) >= 149 - implants)
			{
				if (event.ammount > 7.0F)
				{
				    entityPlayer.heal(2.0F);
				}
				
				event.ammount = 0.0F;
			}
		}
	}
	
	public static void getDeathBringerModifier(EntityPlayer entityPlayer, EntityLivingBase entityLivingBase, ItemStack itemStack, LivingHurtEvent event)
	{
		if (itemStack != null)
		{
			if (EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.DEATH_BRINGER.effectId, itemStack) > 0)
			{
				int enchLevel = EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.DEATH_BRINGER.effectId, itemStack);
				Random rand = entityPlayer.worldObj.rand;
				
				if (rand.nextInt(15) >= 14 - enchLevel)
				{
					event.ammount *= 2.0F;
				}
			}
		}
	}
	
	private static int countEnchantLevels(EntityPlayer entityPlayer, int enchantmentLevel)
	{
		if (entityPlayer != null)
		{
			int count = 0;
			
			for (ItemStack stack : entityPlayer.inventory.armorInventory)
			{
				if (stack != null)
				{
					Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
					Integer enchantment = (Integer) enchantments.get(Integer.valueOf(enchantmentLevel));
					
					if (enchantment != null)
					{
						count += enchantment.intValue();
					}
				}
			}
			
			return count;
		}
		
		return 0;
	}
	
	public static int getLastStandModifier(EntityPlayer entityPlayer)
    {
        if (entityPlayer != null)
        {
            int count = 0;
            
            for (ItemStack itemStack : entityPlayer.inventory.armorInventory)
            {
                if (itemStack != null)
                {
                    Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
                    Integer enchantment = enchantments.get(Integer.valueOf(RegistryEnchantments.LAST_STAND.effectId));
                    
                    if (enchantment != null)
                    {
                        count += enchantment.intValue();
                    }
                }
            }
            
            return count;
        }
        
        return 0;
    }
	
	public static void getFrostWalkerModifier(EntityLivingBase entityLivingBase, World world, int x, int y, int z)
	{
	    int i = EnchantmentHelper.getMaxEnchantmentLevel(RegistryEnchantments.FROST_WALKER.effectId, entityLivingBase.getLastActiveItems());
	    
	    if (i > 0)
	    {
	        EnchantmentFrostWalker.freezeNearby(entityLivingBase, world, x, y, z, i);
	    }
	}
	
	public static int getDepthStriderModifier(EntityLivingBase entityLivingBase)
	{
	    return EnchantmentHelper.getMaxEnchantmentLevel(RegistryEnchantments.DEPTH_STRIDER.effectId, entityLivingBase.getLastActiveItems());
	}
	
	public static int getSoulSpeedModifier(EntityLivingBase entityLivingBase)
	{
	    return EnchantmentHelper.getMaxEnchantmentLevel(RegistryEnchantments.SOUL_SPEED.effectId, entityLivingBase.getLastActiveItems());
	}
	
	public static void getSweepingEdgeModifier(EntityPlayer entityPlayer, EntityLivingBase entityLivingBase)
	{
	    ItemStack itemStack = entityPlayer.getHeldItem();
	    
	    if (itemStack != null && itemStack.getItem() instanceof ItemSword)
	    {
	        int level = EnchantmentHelper.getEnchantmentLevel(RegistryEnchantments.SWEEPING_EDGE.effectId, itemStack);
	        
	        if (level > 0)
	        {
	            float ratio = EnchantmentSweepingEdge.getSweepingDamageRatio(level);
	            
	            List<EntityLivingBase> entityLivingBases = entityPlayer.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, entityLivingBase.boundingBox.expand(1.0D, 0.25D, 1.0D));
	            
	            for (EntityLivingBase entityLivingBase1 : entityLivingBases)
	            {
	                if (entityLivingBase1 != entityPlayer && entityLivingBase1 != entityLivingBase)
	                {
	                    entityLivingBase1.attackEntityFrom(DamageSource.causePlayerDamage(entityPlayer), (float) (ratio * entityPlayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue()));
	                }
	            }
	            
	            entityPlayer.worldObj.playSoundAtEntity(entityPlayer, "random.sweep", 1.0F, 1.0F);
	        }
	    }
	}
	
	private static void useItems(EntityPlayer entityPlayer, int gunpowderSlot, int armorSlot, int gunpowderAmount)
    {
        if (entityPlayer.capabilities.isCreativeMode)
        {
            return;
        }
        
        InventoryPlayer inventoryPlayer = entityPlayer.inventory;
        ItemStack itemStack = inventoryPlayer.armorItemInSlot(armorSlot);
        
        if (itemStack.stackSize <= 0)
        {
            inventoryPlayer.armorInventory[armorSlot] = null;
        }
            
        ItemStack itemStack1 = inventoryPlayer.mainInventory[gunpowderSlot];
        itemStack1.stackSize -= gunpowderAmount;
        
        if (itemStack1.stackSize <= 0)
        {
            inventoryPlayer.mainInventory[gunpowderSlot] = null;
        }
    }
    
    public static EnchantmentLevel tryUseEnchantment(EntityPlayer entityPlayer, int armorSlot)
    {
        InventoryPlayer inventoryPlayer = entityPlayer.inventory;
        ItemStack itemStack = inventoryPlayer.armorInventory[armorSlot];
        
        if (itemStack == null || !(itemStack.getItem() instanceof ItemArmor))
        {
            return null;
        }
            
        Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
        Integer enchantment = enchantments.get(Integer.valueOf(RegistryEnchantments.EXPLOSIVE.effectId));
        
        if (enchantment == null || enchantment.intValue() >= ENCHANTMENT_LEVELS.length)
        {
            return null;
        }
            
        EnchantmentLevel enchantmentLevel = ENCHANTMENT_LEVELS[enchantment.intValue()];
        
        if (enchantmentLevel == null)
        {
            return null;
        }
            
        for (int i = 0; i < inventoryPlayer.mainInventory.length; i++)
        {
            ItemStack itemStack1 = inventoryPlayer.mainInventory[i];
            
            if (itemStack1 != null && GUNPOWDER.isItemEqual(itemStack1) && itemStack1.stackSize >= enchantmentLevel.gunpowderNeeded)
            {
                useItems(entityPlayer, i, armorSlot, enchantmentLevel.gunpowderNeeded);
                return enchantmentLevel;
            }
        }
        
        return null;
    }
    
    public static EnchantmentLevel tryUseUpperArmor(EntityPlayer entityPlayer)
    {
        Collections.shuffle(PROTECTION_PARTS);
        
        for (Iterator<Integer> iterator = PROTECTION_PARTS.iterator(); iterator.hasNext(); ) {int armorPart = ((Integer) iterator.next()).intValue();
        
            EnchantmentLevel enchantmentLevel = tryUseEnchantment(entityPlayer, armorPart);
            
            if (enchantmentLevel != null)
            {
                return enchantmentLevel;
            }
        }
    
        return null;
    }
	
	private static void createExplosionForEntity(Entity entity, float power, boolean isDestructive)
    {
        if (!entity.worldObj.isRemote)
        {
            entity.worldObj.createExplosion(entity, entity.posX, entity.boundingBox.minY, entity.posZ, power, isDestructive);
        }
    }
	
	public static boolean checkSource(DamageSource damageSource)
    {
        return (damageSource instanceof EntityDamageSource && ALLOWED_DAMAGE_SOURCE.contains(damageSource.damageType));
    }
	
	public static int getPlayerXP(EntityPlayer entityPlayer)
    {
        return (int) (getExperienceForLevel(entityPlayer.experienceLevel) + entityPlayer.experience * entityPlayer.xpBarCap());
    }
    
    public static void addPlayerXP(EntityPlayer entityPlayer, int amount)
    {
        int experience = getPlayerXP(entityPlayer) + amount, experienceForLevel = getExperienceForLevel(entityPlayer.experienceLevel);
        entityPlayer.experienceTotal = experience;
        entityPlayer.experienceLevel = getLevelForExperience(experience);
        entityPlayer.experience = (experience - experienceForLevel) / entityPlayer.xpBarCap();
    }
    
    private static int getExperienceForLevel(int level)
    {
        if (level == 0)
        {
            return 0;
        }
        
        if (level > 0 && level < 16)
        {
            return level * 17;
        }
            
        if (level > 15 && level < 31)
        {
            return (int) (1.5D * Math.pow(level, 2.0D) - 29.5D * level + 360.0D);
        }
        
        return (int) (3.5D * Math.pow(level, 2.0D) - 151.5D * level + 2220.0D);
    }
    
    private static int getLevelForExperience(int experience)
    {
        int i = 0;
        
        while (getExperienceForLevel(i) <= experience)
        {
            i++;
        }
        
        return i - 1;
    }
    
    public static void addAllBooks(Enchantment enchantment, List<ItemStack> list)
    {
        for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++)
        {
            list.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i)));
        }
    }
	
	public static final class EnchantmentLevel
    {
        public final double multiplier, maxHeightBonus;
        public final float jumpExplosionPower, armorExplosionPower;
        public final boolean isDestructive;
        public final int gunpowderNeeded;
        
        private EnchantmentLevel(double multiplier, double maxHeightBonus, float jumpExplosionPower, float armorExplosionPower, boolean isDestructive, int gunpowderNeeded)
        {
            this.multiplier = multiplier;
            this.maxHeightBonus = maxHeightBonus;
            this.jumpExplosionPower = jumpExplosionPower;
            this.armorExplosionPower = armorExplosionPower;
            this.isDestructive = isDestructive;
            this.gunpowderNeeded = gunpowderNeeded;
        }
        
        protected double calculateHeightBonus(float height)
        {
            return Math.min(Math.sqrt(height), this.maxHeightBonus);
        }
        
        public double calculateMultiplier(float height)
        {
            return this.multiplier + calculateHeightBonus(height);
        }
        
        public void createJumpExplosion(Entity entity)
        {
            createExplosionForEntity(entity, this.jumpExplosionPower, this.isDestructive);
        }
        
        public void createArmorExplosion(Entity entity)
        {
            createExplosionForEntity(entity, this.armorExplosionPower, this.isDestructive);
        }
    }
	
	public static class JumpInfo
    {
        public final EnchantmentLevel enchantmentLevel;
        public final float height;
        
        public JumpInfo(EnchantmentLevel enchantmentLevel, float height)
        {
            this.enchantmentLevel = enchantmentLevel;
            this.height = height;
        }
        
        public void modifyVelocity(Entity entity)
        {
            double multiplier = this.enchantmentLevel.calculateMultiplier(this.height);
            
            entity.motionX *= multiplier * 5.0D;
            entity.motionY *= multiplier;
            entity.motionZ *= multiplier * 5.0D;
        }
    }
}