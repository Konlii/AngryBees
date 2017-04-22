package com.flashoverride.angrybees.handlers;

import com.pam.harvestcraft.item.ItemRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingHandlerHarvestCraft {
	public static void init() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeGlovesMain), new Object[] {
	            false,
	            "LLC",
	            'C', ItemRegistry.wovencottonItem,
	            'L', Items.LEATHER
	    }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeGlovesOff), new Object[] {
	            false,
	            "CLL",
	            'C', ItemRegistry.wovencottonItem,
	            'L', Items.LEATHER
	    }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeBoots), new Object[] {
	            "C.C",
	            "W.W",
	            "W.W",
	            'C', ItemRegistry.wovencottonItem,
	            'W', Blocks.WOOL
	    }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeChest), new Object[] {
	            "WCW",
	            "WWW",
	            "WWW",
	            'C', ItemRegistry.wovencottonItem,
	            'W', Blocks.WOOL
	    }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeHelmet), new Object[] {
	            "WWW",
	            "WCW",
	            "CCC",
	            'C', ItemRegistry.wovencottonItem,
	            'W', Blocks.WOOL
	    }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ArmorHandler.beeLegs), new Object[] {
	            "WWW",
	            "WCW",
	            "WCW",
	            'C', ItemRegistry.wovencottonItem,
	            'W', Blocks.WOOL
	    }));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.beeBottle), new Object[] {
				new ItemStack(ItemRegistry.queenbeeItem), new ItemStack(Items.GLASS_BOTTLE)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.queenbeeItem), new Object[] {
				new ItemStack(ItemHandler.beeBottle.setContainerItem(Items.GLASS_BOTTLE))
		}));
	}
}
