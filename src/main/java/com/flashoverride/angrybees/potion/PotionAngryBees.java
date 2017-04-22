package com.flashoverride.angrybees.potion;

import java.util.List;

import com.flashoverride.angrybees.AngryBees;
import com.flashoverride.angrybees.Reference;
import com.flashoverride.angrybees.config.ConfigHandler;
import com.flashoverride.angrybees.init.SoundEventsAngryBees;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionAngryBees extends Potion 
{
	private static final ResourceLocation beeIcon = new ResourceLocation(Reference.MODID,"textures/bee.png");
	public static PotionAngryBees BEE_POTION = (PotionAngryBees) new PotionAngryBees(true, 0xffff00, "Angry Bees").setIconIndex(0, 0).setRegistryName(new ResourceLocation(Reference.getResID()+"angrybeespotion"));
	
	public PotionAngryBees(boolean isBadEffectIn, int liquidColorIn, String name) 
	{
		super(isBadEffectIn, liquidColorIn);
		this.setPotionName(name);
	}
	
    public boolean isInstant()
    {
        return true;
    }
    public boolean isReady(int duration, int amplifier)
    {
        return duration >= 1;
    }
    
    public PotionAngryBees setIconIndex(int x, int y) {
    	super.setIconIndex(x, y);
    	return this;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon() {
        Minecraft.getMinecraft().renderEngine.bindTexture(beeIcon);
        return true;
    }
    
    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_)
    {
    	int stingChanceBeehive;

    	switch (ConfigHandler.stingChance) {
        case "low":
        	stingChanceBeehive = 80;
            break;
        case "medium":
        	stingChanceBeehive = 40;
            break;
        case "high":
        	stingChanceBeehive = 20;
            break;
        case "africanized":
        	stingChanceBeehive = 3;
            break;
        default:
        	stingChanceBeehive = 40;
    	}
    	
    	AngryBees.proxy.generateBeeParticles(entityLivingBaseIn);
    	
    	if (entityLivingBaseIn.world.rand.nextInt(20) == 0) {
   			entityLivingBaseIn.world.playSound(null, entityLivingBaseIn.getPosition(), SoundEventsAngryBees.BEE_BUZZ, SoundCategory.NEUTRAL, 1.0f, 1.25f - (0.01f * (float) entityLivingBaseIn.world.rand.nextInt(50)));
    	}
    	
    	if (!entityLivingBaseIn.world.isRemote)
    	{
    		AxisAlignedBB axisalignedbb = (new AxisAlignedBB(entityLivingBaseIn.getPosition()).expandXyz((double)ConfigHandler.swarmRadius));
    		List<EntityLivingBase> list = entityLivingBaseIn.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

    		int swarmDurationCurrent = entityLivingBaseIn.getActivePotionEffect(BEE_POTION).getDuration();
        
    		if (swarmDurationCurrent > 4)
    		{
    			for (EntityLivingBase entityLivingNew : list)
    			{
    				if (!entityLivingBaseIn.isEntityEqual(entityLivingNew) && !entityLivingNew.isPotionActive(BEE_POTION) && !entityLivingBaseIn.isInLava() && !entityLivingBaseIn.isInWater())
    				{
    					entityLivingNew.addPotionEffect(new PotionEffectAngryBees(PotionAngryBees.BEE_POTION, Math.round(swarmDurationCurrent * 0.25f), 0, false, false));
    					entityLivingBaseIn.removePotionEffect(BEE_POTION);
    					entityLivingBaseIn.addPotionEffect(new PotionEffectAngryBees(PotionAngryBees.BEE_POTION, Math.round(swarmDurationCurrent * 0.75f), 0, false, false));
    				}
    			}
    		}
    	}
    	
    	if (entityLivingBaseIn.isInLava() || entityLivingBaseIn.isInWater()) {
    		entityLivingBaseIn.removePotionEffect(BEE_POTION);
    	}
    	else if (entityLivingBaseIn.world.rand.nextInt(stingChanceBeehive) == 0 && !ConfigHandler.stingChance.contentEquals("none")) {
    		boolean stingImmune = false;
            for (String blacklistEntityName: ConfigHandler.entityBlacklist)
            {
            	if (EntityList.isMatchingName(entityLivingBaseIn, new ResourceLocation(blacklistEntityName)))
            	{
            		stingImmune = true;
            	}
            }
            
            if (!stingImmune)
            {
            	entityLivingBaseIn.attackEntityFrom(AngryBees.damageSourceBees, 1.0f);
            }
		}
    }
}