package com.flashoverride.angrybees.handlers;

import com.flashoverride.angrybees.item.ItemBeeBottle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemHandler {
	public static ItemBeeBottle beeBottle;
	
	public static void init(){
		beeBottle = new ItemBeeBottle("bee_bottle", CreativeTabs.MISC);
	 }
	 
	 public static void register(){
		 GameRegistry.register(beeBottle);
	 }
	 
	 public static void registerRenders(){
		 registerRender(beeBottle);
	 }
	 
	 public static void registerRender(Item item){
		 Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	 }
}
