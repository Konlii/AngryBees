package com.flashoverride.angrybees.init;

import com.flashoverride.angrybees.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@SuppressWarnings("WeakerAccess")
@ObjectHolder(Reference.MODID)
public class SoundEventsAngryBees {

	@ObjectHolder("bee.buzz")
	public static final SoundEvent BEE_BUZZ = createSoundEvent("bee.buzz");

	@ObjectHolder("bee.sting")
	public static final SoundEvent BEE_STING = createSoundEvent("bee.sting");

	private static SoundEvent createSoundEvent(String soundName) {
		final ResourceLocation soundID = new ResourceLocation(Reference.MODID, soundName);
		return new SoundEvent(soundID).setRegistryName(soundID);
	}

	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		@SubscribeEvent(priority = EventPriority.NORMAL)
		public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
			event.getRegistry().registerAll(
					BEE_BUZZ,
					BEE_STING
			);
		}
	}
}