package com.flashoverride.angrybees.handlers;

import com.flashoverride.angrybees.item.ItemBeeArmor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ArmorHandler {
	public static Item beeHelmet;
	public static Item beeChest;
	public static Item beeLegs;
	public static Item beeBoots;
	public static Item beeGlovesMain;
	public static Item beeGlovesOff;
	
	public static void init(){
		beeHelmet = new ItemBeeArmor("beekeeping_helmet", CreativeTabs.COMBAT, MaterialHandler.BEEKEEPING_ARMOR, 0, EntityEquipmentSlot.HEAD);
		beeChest = new ItemBeeArmor("beekeeping_chestplate", CreativeTabs.COMBAT, MaterialHandler.BEEKEEPING_ARMOR, 0, EntityEquipmentSlot.CHEST);
		beeLegs = new ItemBeeArmor("beekeeping_leggings", CreativeTabs.COMBAT, MaterialHandler.BEEKEEPING_ARMOR, 0, EntityEquipmentSlot.LEGS);
		beeBoots = new ItemBeeArmor("beekeeping_boots", CreativeTabs.COMBAT, MaterialHandler.BEEKEEPING_ARMOR, 0, EntityEquipmentSlot.FEET);
		beeGlovesMain = new ItemBeeArmor("beekeeping_gloves_mainhand", CreativeTabs.COMBAT, ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.MAINHAND).setFull3D();
		beeGlovesOff = new ItemBeeArmor("beekeeping_gloves_offhand", CreativeTabs.COMBAT, ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.OFFHAND).setFull3D();
	 }
	 
	 public static void register(){
		 GameRegistry.register(beeHelmet);
		 GameRegistry.register(beeChest);
		 GameRegistry.register(beeLegs);
		 GameRegistry.register(beeBoots);
		 GameRegistry.register(beeGlovesMain);
		 GameRegistry.register(beeGlovesOff);

	 }
	 
	 public static void registerRenders(){
			registerRender(beeHelmet);
			registerRender(beeChest);
			registerRender(beeLegs);
			registerRender(beeBoots);
			registerRender(beeGlovesMain);
			registerRender(beeGlovesOff);
	 }
	 
	 public static void registerRender(Item item){
		 Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	 }
}
