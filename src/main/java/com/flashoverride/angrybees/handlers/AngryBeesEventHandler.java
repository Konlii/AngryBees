package com.flashoverride.angrybees.handlers;

import com.flashoverride.angrybees.AngryBees;
import com.flashoverride.angrybees.config.ConfigHandler;
import com.flashoverride.angrybees.entity.ai.EntityAIBeePanic;
import com.flashoverride.angrybees.init.SoundEventsAngryBees;
import com.flashoverride.angrybees.potion.PotionAngryBees;
import com.flashoverride.angrybees.potion.PotionEffectAngryBees;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AngryBeesEventHandler {
	
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
		
		if (mainhand != null && mainhand.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeGlovesMain)))
		{
			mainhand.damageItem(1, theEntity);
			theEntity.setHeldItem(EnumHand.MAIN_HAND, mainhand);
		}
		if (offhand != null && offhand.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeGlovesOff)))
		{
			offhand.damageItem(1, theEntity);
			theEntity.setHeldItem(EnumHand.OFF_HAND, offhand);
		}


		if (event.getSource().equals(AngryBees.damageSourceBees))
		 {
			entityWorld.playSound(null, theEntity.getPosition(), SoundEventsAngryBees.BEE_STING, SoundCategory.HOSTILE, 1.0f, 1.1f - (0.01f * (float) entityWorld.rand.nextInt(20)));

			if (mainhand != null && offhand != null && head != null && chest != null && legs != null && feet != null &&
				mainhand.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeGlovesMain)) && offhand.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeGlovesOff)) &&
				head.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeHelmet)) && chest.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeChest)) &&
				legs.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeLegs)) && (feet.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeBoots)) ||
				feet.isItemEqualIgnoreDurability(new ItemStack(Items.LEATHER_BOOTS))))
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
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntityLiving)
		{
			EntityAIBase aiBeePanic;
			EntityLiving entityLiving = (EntityLiving) event.getEntity();
			
			if (!entityLiving.isAIDisabled())
			{
				if ((double)entityLiving.getAIMoveSpeed() > 0d)
				{
					aiBeePanic = new EntityAIBeePanic(entityLiving, (double)entityLiving.getAIMoveSpeed() * 1.4d);
				}
				else
				{
					aiBeePanic = new EntityAIBeePanic(entityLiving, 1.4d);
				}
				entityLiving.tasks.addTask(1, aiBeePanic);
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onPlayerOpenContainer(PlayerContainerEvent event)
	{
		EntityPlayer entityPlayer = event.getEntityPlayer();
		World theWorld = entityPlayer.getEntityWorld();
		
		if (event.getContainer().getInventory().toString().contains("item.queenbeeitem"))
		{
			theWorld.playSound(null, entityPlayer.getPosition(), SoundEventsAngryBees.BEE_BUZZ, SoundCategory.HOSTILE, 1.0f, 1.25f - (0.01f * (float) theWorld.rand.nextInt(50)));
			
			for(int i = 0; i < 5; i++)
			{
				AngryBees.proxy.generateBeeParticles(theWorld, entityPlayer.getPosition());
			}
			
	    	int stingChanceApiary;
	    	int swarmDurationApiary = ConfigHandler.swarmDuration;

	    	switch (ConfigHandler.stingChance) {
	        case "low":
	        	stingChanceApiary = 20;
	        	swarmDurationApiary = Math.round((float)ConfigHandler.swarmDuration * 0.1f);
	            break;
	        case "medium":
	        	stingChanceApiary = 10;
	        	swarmDurationApiary = Math.round((float)ConfigHandler.swarmDuration * 0.1f);
	            break;
	        case "high":
	        	stingChanceApiary = 5;
	        	swarmDurationApiary = Math.round((float)ConfigHandler.swarmDuration * 0.1f);
	            break;
	        case "africanized":
	        	stingChanceApiary = 1;
	        	swarmDurationApiary = ConfigHandler.swarmDuration;
	            break;
	        default:
	        	stingChanceApiary = 10;
	    	}

	    	if (ConfigHandler.stingChance.contentEquals("africanized")) {
	    		entityPlayer.addPotionEffect(new PotionEffectAngryBees(PotionAngryBees.BEE_POTION, swarmDurationApiary, 0, false, false));
	    	}
	    	else if (theWorld.rand.nextInt(stingChanceApiary) == 0 && !ConfigHandler.stingChance.contentEquals("none")) {
	    		entityPlayer.addPotionEffect(new PotionEffectAngryBees(PotionAngryBees.BEE_POTION, swarmDurationApiary, 0, false, false));
	    	}
		}
	}
}
