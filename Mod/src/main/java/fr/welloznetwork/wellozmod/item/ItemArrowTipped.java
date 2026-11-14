package fr.welloznetwork.wellozmod.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.dispenser.BehaviorTippedArrowDispense;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemArrowTipped extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon tipIcon;
    
    private static final Map field_77835_b = new LinkedHashMap();
    private final HashMap effectCache = new HashMap();
    
    public ItemArrowTipped()
    {
        setUnlocalizedName("tipped_arrow");
        setTextureName("wellozmod:tipped_arrow");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setHasSubtypes(true);
        
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new BehaviorTippedArrowDispense());
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        int j;
        
        if (field_77835_b.isEmpty())
        {
            for (int i = 1; i <= 15; i++)
            {
                int k;
                
                k = i | 8192;
                
                for (int l = 0; l <= 2; l++)
                {
                    int i1 = k;
                    
                    if (l != 0)
                    {
                        if (l == 1)
                        {
                            i1 = k | 32;
                        }
                        
                        else if (l == 2)
                        {
                            i1 = k | 64;
                        }
                    }
                    
                    List list1 = this.getPotionEffects(i1, false);
                    
                    if (list1 != null && !list1.isEmpty())
                    {
                        field_77835_b.put(list1, Integer.valueOf(i1));
                    }
                }
            }
        }
        
        Iterator iterator = field_77835_b.values().iterator();
        
        while (iterator.hasNext())
        {
            j = ((Integer) iterator.next()).intValue();
            list.add(new ItemStack(item, 1, j));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iiconRegister)
    {
        this.itemIcon = iiconRegister.registerIcon(this.getIconString() + "_base");
        this.tipIcon = iiconRegister.registerIcon(this.getIconString() + "_head");
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int pass)
    {
        if (pass == 0 && itemStack.getItemDamage() == 0 && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("Potion", 10))
        {
            NBTTagCompound tagCompound = itemStack.getTagCompound().getCompoundTag("Potion");
            PotionEffect potionEffect = PotionEffect.readCustomPotionEffectFromNBT(tagCompound);
            
            if (potionEffect != null && potionEffect.getPotionID() >= 0 && potionEffect.getPotionID() < Potion.potionTypes.length)
            {
                return Potion.potionTypes[potionEffect.getPotionID()].getLiquidColor();
            }
        }
        
        return Items.potionitem.getColorFromItemStack(itemStack, pass);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int pass)
    {
        return pass == 0 ? this.tipIcon : super.getIcon(itemStack, pass);
    }
    
    public String getUnlocalizedName(ItemStack itemStack)
    {
        String s = "item.tipped_arrow";
        List list = this.getEffects(itemStack);
        String s1;
        
        if (list != null && !list.isEmpty())
        {
            if (itemStack.getItemDamage() > 0 || (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("Potion", 10)))
            {
                s1 = ((PotionEffect) list.get(0)).getEffectName();
                return s + "." + s1;
            }
        }
        
        else if (itemStack.getItemDamage() == 0)
        {
            return "item.tipped_arrow.empty";
        }
        
        return s;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List tooltip, boolean advanced)
    {
        List list1 = this.getEffects(itemStack);
        HashMultimap hashMultimap = HashMultimap.create();
        Iterator iterator1;
        
        if (list1 != null && !list1.isEmpty())
        {
            iterator1 = list1.iterator();
            
            while (iterator1.hasNext())
            {
                PotionEffect potionEffect = (PotionEffect) iterator1.next();
                String s1 = I18n.format(potionEffect.getEffectName()).trim();
                Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                Map map = potion.func_111186_k();
                
                if (map != null && map.size() > 0)
                {
                    Iterator iterator = map.entrySet().iterator();
                    
                    while (iterator.hasNext())
                    {
                        Entry entry = (Entry) iterator.next();
                        AttributeModifier attributeModifier = (AttributeModifier) entry.getValue();
                        AttributeModifier attributeModifier1 = new AttributeModifier(attributeModifier.getName(), potion.func_111183_a(potionEffect.getAmplifier(), attributeModifier), attributeModifier.getOperation());
                        hashMultimap.put(((IAttribute) entry.getKey()).getAttributeUnlocalizedName(), attributeModifier1);
                    }
                }
                
                if (potionEffect.getAmplifier() > 0)
                {
                    s1 = s1 + " " + I18n.format("potion.potency." + potionEffect.getAmplifier()).trim();
                }
                
                if (potionEffect.getDuration() > 20)
                {
                    s1 = s1 + " (" + Potion.getDurationString(potionEffect) + ")";
                }
                
                if (potion.isBadEffect())
                {
                    tooltip.add(EnumChatFormatting.RED + s1);
                }
                
                else
                {
                    tooltip.add(EnumChatFormatting.GRAY + s1);
                }
            }
        }
        
        else
        {
            tooltip.add(EnumChatFormatting.GRAY + I18n.format("potion.empty").trim());
        }
        
        if (!hashMultimap.isEmpty())
        {
            tooltip.add("");
            tooltip.add(EnumChatFormatting.DARK_PURPLE + I18n.format("potion.effects.whenDrank"));
            iterator1 = hashMultimap.entries().iterator();
            
            while (iterator1.hasNext())
            {
                Entry entry = (Entry) iterator1.next();
                AttributeModifier attributeModifier = (AttributeModifier) entry.getValue();
                double d0 = attributeModifier.getAmount();
                double d1;
                
                if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2)
                {
                    d1 = attributeModifier.getAmount();
                }
                
                else
                {
                    d1 = attributeModifier.getAmount() * 100.0D;
                }
                
                if (d0 > 0.0D)
                {
                    tooltip.add(EnumChatFormatting.BLUE + I18n.format("attribute.modifier.plus." + attributeModifier.getOperation(), new Object[] {ItemStack.field_111284_a.format(d1), I18n.format("attribute.name." + entry.getKey())}));
                }
                
                else if (d0 < 0.0D)
                {
                    d1 *= -1.0D;
                    tooltip.add(EnumChatFormatting.RED + I18n.format("attribute.modifier.take." + attributeModifier.getOperation(), new Object[] {ItemStack.field_111284_a.format(d1), I18n.format("attribute.name." + entry.getKey())}));
                }
            }
        }
    }
    
    private List<PotionEffect> getPotionEffects(int metadata, boolean flag)
    {
        List<PotionEffect> list = PotionHelper.getPotionEffects(metadata, flag);
        
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                PotionEffect potionEffect = list.get(i);
                list.set(i, new PotionEffect(potionEffect.getPotionID(), potionEffect.getDuration() / 8, potionEffect.getAmplifier()));
            }
        }
        
        return list;
    }
    
    public List getEffects(ItemStack itemStack)
    {
        if (itemStack.hasTagCompound())
        {
            if (itemStack.getTagCompound().hasKey("Potion", 10))
            {
                NBTTagCompound tagCompound = itemStack.getTagCompound().getCompoundTag("Potion");
                PotionEffect potionEffect = PotionEffect.readCustomPotionEffectFromNBT(tagCompound);
                List list = new ArrayList();
                list.add(potionEffect);
                return list;
            }
        }
        
        else if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("CustomPotionEffects", 9))
        {
            ArrayList arrayList = new ArrayList();
            NBTTagList tagList = itemStack.getTagCompound().getTagList("CustomPotionEffects", 10);
            
            for (int i = 0; i < tagList.tagCount(); i++)
            {
                NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                PotionEffect potionEffect = PotionEffect.readCustomPotionEffectFromNBT(tagCompound);
                
                if (potionEffect != null)
                {
                    arrayList.add(potionEffect);
                }
            }
            
            return arrayList;
        }
        
        List list = (List) this.effectCache.get(Integer.valueOf(itemStack.getItemDamage()));
        
        if (list == null)
        {
            list = this.getPotionEffects(itemStack.getItemDamage(), false);
            this.effectCache.put(Integer.valueOf(itemStack.getItemDamage()), list);
        }
        
        return list;
    }
}