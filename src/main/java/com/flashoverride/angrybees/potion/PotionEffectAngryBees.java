package com.flashoverride.angrybees.potion;

import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PotionEffectAngryBees extends PotionEffect{

    private java.util.List<net.minecraft.item.ItemStack> curativeItems;
	public static PotionEffectAngryBees BEE_POTION_EFFECT = (PotionEffectAngryBees) new PotionEffectAngryBees(PotionAngryBees.BEE_POTION, 20, 0, false, false);

	public PotionEffectAngryBees(PotionAngryBees potionIn, int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn) {
		super(potionIn, durationIn, amplifierIn, ambientIn, showParticlesIn);
	}
	
    public PotionEffectAngryBees(PotionAngryBees potionIn)
    {
        super(potionIn);
    }

    public PotionEffectAngryBees(PotionAngryBees potionIn, int durationIn)
    {
        super(potionIn, durationIn);
    }

    public PotionEffectAngryBees(PotionAngryBees potionIn, int durationIn, int amplifierIn)
    {
        super(potionIn, durationIn, amplifierIn);
    }

    public PotionEffectAngryBees(PotionEffectAngryBees other)
    {
        super(other);
    }
    
    
	@Override
    public java.util.List<net.minecraft.item.ItemStack> getCurativeItems()
    {
        if (this.curativeItems == null) //Lazy load this so that we don't create a circular dep on Items.
        {
            this.curativeItems = new java.util.ArrayList<net.minecraft.item.ItemStack>();
        }
        return this.curativeItems;
    }

}
