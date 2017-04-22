package com.flashoverride.angrybees.handlers;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MaterialHandler {
	public static final ArmorMaterial BEEKEEPING_ARMOR = EnumHelper.addArmorMaterial("beekeeping_armor", "angrybees:beekeeping", 4, new int[]{1, 1, 1, 1}, 5, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0F);

}
