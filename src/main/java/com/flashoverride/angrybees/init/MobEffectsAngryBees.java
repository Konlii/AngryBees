package com.flashoverride.angrybees.init;

import javax.annotation.Nullable;

import com.flashoverride.angrybees.potion.PotionAngryBees;

import net.minecraft.init.Bootstrap;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;

public class MobEffectsAngryBees extends MobEffects {

    @Nullable
    private static PotionAngryBees getRegisteredMobEffect(String id)
    {
    	PotionAngryBees potion = (PotionAngryBees) PotionAngryBees.REGISTRY.getObject(new ResourceLocation(id));

        if (potion == null)
        {
            throw new IllegalStateException("Invalid MobEffect requested: " + id);
        }
        else
        {
            return potion;
        }
    }

    static
    {
        if (!Bootstrap.isRegistered())
        {
            throw new RuntimeException("Accessed MobEffects before Bootstrap!");
        }
        else
        {
        	PotionAngryBees.BEE_POTION = getRegisteredMobEffect("angrybeespotioneffect");
        }
    }
}
