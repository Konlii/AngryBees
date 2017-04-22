package com.flashoverride.angrybees.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	public static Configuration config;
	
	public static boolean enableGravity;
	public static boolean enableGravityDefault = true;
	public static int swarmDuration;
	public static int swarmDurationDefault = 800;
	public static int swarmRadius;
	public static int swarmRadiusDefault = 6;
	public static String stingChance;
	public static String stingChanceDefault = "medium";
	public static String[] stingChanceValues = {"none", "low", "medium", "high", "africanized"};
	public static String[] entityBlacklist;
	public static String[] entityBlacklistDefault = {"minecraft:polar_bear", "tfc2:bear"};
	public static boolean enableAmbientBees;
	public static boolean enableAmbientBeesDefault = true;
	
	public static void init(File configFile) {
		config = new Configuration(configFile);

		config.load();
		
//		enableGravity = config.getBoolean("enableGravity", Configuration.CATEGORY_GENERAL, enableGravityDefault, "Unsupported beehives are affected by gravity" + config.NEW_LINE + "[true, false]" + config.NEW_LINE);
		
		swarmDuration = config.getInt("swarmDuration", Configuration.CATEGORY_GENERAL, swarmDurationDefault, 1, 2147483647, "The duration of an angry bee swarm (in number of ticks)" + config.NEW_LINE);
		
		swarmRadius = config.getInt("swarmRadius", Configuration.CATEGORY_GENERAL, swarmRadiusDefault, 0, 15, "The distance an angry bee swarm will travel to attack an entity" + config.NEW_LINE);

		stingChance = config.getString("stingChance", Configuration.CATEGORY_GENERAL, stingChanceDefault, "Chance of being stung while swarmed by angry bees" + config.NEW_LINE + "[none, low, medium, high, africanized]" + config.NEW_LINE, stingChanceValues);
		
		entityBlacklist = config.getStringList("entityBlacklist", Configuration.CATEGORY_GENERAL, entityBlacklistDefault, "List of entities that are immune to bee stings, one per line" + config.NEW_LINE);
		
//		enableAmbientBees = config.getBoolean("enableAmbientBees", Configuration.CATEGORY_GENERAL, enableAmbientBeesDefault, "Spawn ambient bees around flowers and beehives" + config.NEW_LINE + "[true, false]" + config.NEW_LINE);
		
		config.save();
	}
}
