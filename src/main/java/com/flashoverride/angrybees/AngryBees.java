package com.flashoverride.angrybees;


import com.flashoverride.angrybees.proxy.CommonProxy;

import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Reference.MODID, name=Reference.NAME, version=Reference.VERSION)

public class AngryBees {
	@Instance
	public static AngryBees instance;
	
	@SidedProxy(clientSide=Reference.CLIENT_PROXY, serverSide=Reference.SERVER_PROXY)

    public static CommonProxy proxy;

	
	public static DamageSource damageSourceBees = new DamageSource(Reference.MODID + ".bees");
	
	@EventHandler
	 public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}
	 
	 @EventHandler
	 public void init(FMLInitializationEvent event) {
		 proxy.init(event);
	 }
	 
	 @EventHandler
	 public void postInit(FMLPostInitializationEvent event) {
		 proxy.postInit(event);
	 }
	 
	 @EventHandler
	 public void modsLoaded(FMLPostInitializationEvent event)
	 {
		 if(Loader.isModLoaded("harvestcraft"))
		 {
			 proxy.registerHarvestCraft();
		 }
		 else
		 {
			 proxy.initCraftingHandler();
		 }
	 }
}
