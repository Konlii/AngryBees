package com.flashoverride.angrybees.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftingHandler {
	public static void init(){
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeGlovesMain), new Object[] {
	            false,
	            "LLS",
	            'S', Items.STRING,
	            'L', Items.LEATHER
	    }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeGlovesOff), new Object[] {
	            false,
	            "SLL",
	            'S', Items.STRING,
	            'L', Items.LEATHER
	    }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeBoots), new Object[] {
	            "S.S",
	            "W.W",
	            "W.W",
	            'S', Items.STRING,
	            'W', Blocks.WOOL
	    }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeChest), new Object[] {
	            "WSW",
	            "WWW",
	            "WWW",
	            'S', Items.STRING,
	            'W', Blocks.WOOL
	    }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeHelmet), new Object[] {
	            "WWW",
	            "WSW",
	            "SSS",
	            'S', Items.STRING,
	            'W', Blocks.WOOL
	    }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeLegs), new Object[] {
	            "WWW",
	            "WSW",
	            "WSW",
	            'S', Items.STRING,
	            'W', Blocks.WOOL
	    }));
	}
}
