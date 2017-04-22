package com.flashoverride.angrybees.proxy;

import com.flashoverride.angrybees.config.ConfigHandler;
import com.flashoverride.angrybees.handlers.AngryBeesEventHandler;
import com.flashoverride.angrybees.handlers.ArmorHandler;
import com.flashoverride.angrybees.handlers.CraftingHandler;
import com.flashoverride.angrybees.handlers.CraftingHandlerHarvestCraft;
import com.flashoverride.angrybees.handlers.HarvestCraftEventHandler;
import com.flashoverride.angrybees.handlers.ItemHandler;
import com.flashoverride.angrybees.potion.PotionAngryBees;
import com.flashoverride.angrybees.potion.PotionTypeAngryBees;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CommonProxy{
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		ArmorHandler.init();
		ArmorHandler.register();
		ForgeRegistries.POTION_TYPES.register(PotionTypeAngryBees.BEE_POTION_TYPE);
		ForgeRegistries.POTIONS.register(PotionAngryBees.BEE_POTION);
    	if(Loader.isModLoaded("harvestcraft"))
    	{
    		ItemHandler.init();
    		ItemHandler.register();
    	}
    	else
    	{
    		System.out.println("AngryBees: Pam's HarvestCraft not loaded");
    		System.out.println("AngryBees: HarvestCraft items will not be usesd");
    	}
    }
	
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new AngryBeesEventHandler());
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public void initCraftingHandler() {
		CraftingHandler.init();
	}

	
	public void registerHarvestCraft() {
		MinecraftForge.EVENT_BUS.register(new HarvestCraftEventHandler());
		CraftingHandlerHarvestCraft.init();
	}
	
	public void generateBeeParticles(Entity theEntity) {}
	public void generateBeeParticles(World world, BlockPos blockPos) {}
}
