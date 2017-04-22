package com.flashoverride.angrybees.item;

import javax.annotation.Nullable;

import com.flashoverride.angrybees.Reference;
import com.flashoverride.angrybees.handlers.ArmorHandler;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemBeeArmor extends ItemArmor {
	public ItemBeeArmor(String name, CreativeTabs tab, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		  super(materialIn, renderIndexIn, equipmentSlotIn);
		  setRegistryName(name);
		  setUnlocalizedName(this.getRegistryName().toString());
		  setCreativeTab(tab);
		  
		  BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
	 }
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        if (this.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER)
        {
        	ItemStack mat = new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE);
        	if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat,repair,false)) return true;
        }
        return super.getIsRepairable(toRepair, repair);
    }
	
    protected static final ResourceLocation BEEKEEPING_BLUR_TEX_PATH = new ResourceLocation(Reference.MODID,"textures/misc/beekeepingblur.png");
	
	@Override
    @SideOnly(Side.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, net.minecraft.client.gui.ScaledResolution resolution, float partialTicks){
		ItemStack helmet = player.inventory.armorItemInSlot(3);
		
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && helmet != null && helmet.isItemEqualIgnoreDurability(new ItemStack(ArmorHandler.beeHelmet)))
		{
			GlStateManager.enableBlend();
			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableAlpha();
			Minecraft.getMinecraft().getTextureManager().bindTexture(BEEKEEPING_BLUR_TEX_PATH);
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer vertexbuffer = tessellator.getBuffer();
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
			vertexbuffer.pos(0.0D, (double)resolution.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
			vertexbuffer.pos((double)resolution.getScaledWidth(), (double)resolution.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
			vertexbuffer.pos((double)resolution.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
			vertexbuffer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
			tessellator.draw();
			GlStateManager.depthMask(true);
			GlStateManager.enableDepth();
			GlStateManager.enableAlpha();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	@Override
    public boolean hasColor(ItemStack stack)
    {
		return false;
    }
    
	@Override
    public boolean hasOverlay(ItemStack stack)
    {
        return true;
    }
}