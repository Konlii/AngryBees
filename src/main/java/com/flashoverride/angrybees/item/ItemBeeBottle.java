package com.flashoverride.angrybees.item;

import com.flashoverride.angrybees.AngryBees;
import com.flashoverride.angrybees.config.ConfigHandler;
import com.flashoverride.angrybees.init.SoundEventsAngryBees;
import com.flashoverride.angrybees.potion.PotionAngryBees;
import com.flashoverride.angrybees.potion.PotionEffectAngryBees;
import com.pam.harvestcraft.item.ItemRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBeeBottle extends Item {
	public ItemBeeBottle(String name, CreativeTabs tab){
		setRegistryName(name);
		setUnlocalizedName(this.getRegistryName().toString());
		setCreativeTab(tab);
	}

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
    	if (attacker.getEntityWorld().rand.nextInt(10) == 0)
    	{
    		breakBeeBottle(stack, attacker);
    	}
    	else
    	{
    		attacker.getEntityWorld().playSound(null, attacker.getPosition(), SoundEvents.BLOCK_GLASS_HIT, SoundCategory.PLAYERS, 1.0f, 1.1f - (0.01f * (float) attacker.getEntityWorld().rand.nextInt(20)));
    	}
        return false;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
    	if (entityLiving.getEntityWorld().rand.nextInt(10) == 0)
    	{
    		breakBeeBottle(stack, entityLiving);
    	}
    	else
    	{
    		worldIn.playSound(null, entityLiving.getPosition(), SoundEvents.BLOCK_GLASS_HIT, SoundCategory.PLAYERS, 1.0f, 1.1f - (0.01f * (float) worldIn.rand.nextInt(20)));
    	}
        return false;
    }

    public void breakBeeBottle(ItemStack stack, EntityLivingBase entityLiving)
    {
    	if (!(entityLiving instanceof EntityPlayer) || !((EntityPlayer)entityLiving).capabilities.isCreativeMode)
    	{
    		EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
    		World theWorld = entityPlayer.getEntityWorld();

    		entityPlayer.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.AIR));
    		theWorld.playSound(null, entityLiving.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.0f, 1.1f - (0.01f * (float) theWorld.rand.nextInt(20)));
    		entityLiving.dropItem(ItemRegistry.queenbeeItem, 1);
    		entityLiving.renderBrokenItemStack(stack);
    		
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
    	else
    	{
    		entityLiving.getEntityWorld().playSound(null, entityLiving.getPosition(), SoundEvents.BLOCK_GLASS_HIT, SoundCategory.PLAYERS, 1.0f, 1.1f - (0.01f * (float) entityLiving.getEntityWorld().rand.nextInt(20)));
    	}
    }
}
