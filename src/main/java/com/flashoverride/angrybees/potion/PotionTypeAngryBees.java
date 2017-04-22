package com.flashoverride.angrybees.potion;

import javax.annotation.Nullable;

import com.flashoverride.angrybees.Reference;

import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;

public class PotionTypeAngryBees extends PotionType {

	public static PotionTypeAngryBees BEE_POTION_TYPE = (PotionTypeAngryBees) new PotionTypeAngryBees().setRegistryName(new ResourceLocation(Reference.getResID()+"angrybeespotiontype")); 
	
    @Nullable
    public static PotionTypeAngryBees getPotionTypeForName(String p_185168_0_)
    {
        return (PotionTypeAngryBees)REGISTRY.getObject(new ResourceLocation(p_185168_0_));
    }

    public PotionTypeAngryBees(PotionEffectAngryBees... p_i46739_1_)
    {
    	super(p_i46739_1_);
    }

    @Override
    public boolean hasInstantEffect()
    {
    	return PotionAngryBees.BEE_POTION.isInstant();
    }
}
