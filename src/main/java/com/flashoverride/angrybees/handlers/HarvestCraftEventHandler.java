package com.flashoverride.angrybees.handlers;

import java.util.List;

import com.flashoverride.angrybees.AngryBees;
import com.flashoverride.angrybees.config.ConfigHandler;
import com.flashoverride.angrybees.init.SoundEventsAngryBees;
import com.flashoverride.angrybees.potion.PotionAngryBees;
import com.flashoverride.angrybees.potion.PotionEffectAngryBees;
import com.pam.harvestcraft.blocks.BlockRegistry;
import com.pam.harvestcraft.item.ItemRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HarvestCraftEventHandler {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onBlockBreakEvent(BreakEvent event)
	{
		EntityPlayer entityPlayer = event.getPlayer();
		World theWorld = event.getWorld();
		
		if (event.getState().getBlock().equals(BlockRegistry.beehive) && !BiomeDictionary.hasType(event.getWorld().getBiome(event.getPos()), Type.COLD))
		{
			theWorld.playSound(null, event.getPos(), SoundEventsAngryBees.BEE_BUZZ, SoundCategory.HOSTILE, 1.0f, 1.25f - (0.01f * (float) theWorld.rand.nextInt(50)));
			
			for(int i = 0; i < 25; i++)
			{
				AngryBees.proxy.generateBeeParticles(theWorld, event.getPos());
			}

            AxisAlignedBB axisalignedbb = (new AxisAlignedBB(event.getPos()).expandXyz((double)ConfigHandler.swarmRadius));
            List<EntityLivingBase> list = theWorld.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

            for (EntityLivingBase entityLivingBase : list)
            {
                entityLivingBase.addPotionEffect(new PotionEffectAngryBees(PotionAngryBees.BEE_POTION, ConfigHandler.swarmDuration, 0, false, false));
            }
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onLivingHurt(LivingHurtEvent event)
	{
		EntityLivingBase theEntity = event.getEntityLiving();
		World entityWorld = theEntity.getEntityWorld();
		
		ItemStack mainhand = theEntity.getHeldItemMainhand();
		ItemStack offhand = theEntity.getHeldItemOffhand();
		ItemStack head = theEntity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		ItemStack chest = theEntity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack legs = theEntity.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		ItemStack feet = theEntity.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		
		if (event.getSource().equals(AngryBees.damageSourceBees))
		 {
			if (mainhand != null && offhand != null && head != null && chest != null && legs != null && feet != null &&
					mainhand.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeGlovesMain)) && offhand.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeGlovesOff)) &&
					head.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeHelmet)) && chest.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeChest)) &&
					legs.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeLegs)) && feet.isItemEqualIgnoreDurability(new ItemStack(ItemRegistry.hardenedleatherbootsItem)))
			{
				head.damageItem(1, theEntity);
				event.getEntityLiving().setItemStackToSlot(EntityEquipmentSlot.HEAD, head);
				chest.damageItem(1, theEntity);
				event.getEntityLiving().setItemStackToSlot(EntityEquipmentSlot.CHEST, chest);
				legs.damageItem(1, theEntity);
				event.getEntityLiving().setItemStackToSlot(EntityEquipmentSlot.LEGS, legs);
				feet.damageItem(1, theEntity);
				event.getEntityLiving().setItemStackToSlot(EntityEquipmentSlot.FEET, feet);

				event.setCanceled(true);
			}
		}
	}
}