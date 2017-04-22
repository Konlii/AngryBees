package com.flashoverride.angrybees.proxy;

import com.flashoverride.angrybees.client.particle.ParticleBee;
import com.flashoverride.angrybees.handlers.ArmorHandler;
import com.flashoverride.angrybees.handlers.ItemHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
	
    @Override
	 public void init(FMLInitializationEvent e) {
    	super.init(e);
    	ArmorHandler.registerRenders();
    	if(Loader.isModLoaded("harvestcraft"))
    	{
    		ItemHandler.registerRenders();
    	}
    }
	 
	@Override
	public void generateBeeParticles(Entity theEntity)
	{
	    double motionX = theEntity.world.rand.nextGaussian() * 2d;
	    double motionY = theEntity.world.rand.nextGaussian() * 2d;
	    double motionZ = theEntity.world.rand.nextGaussian() * 2d;
	    Particle particleBee = new ParticleBee(Minecraft.getMinecraft().renderEngine, theEntity.world, theEntity.posX + theEntity.world.rand.nextFloat() * theEntity.width * 2.0F - theEntity.width, theEntity.posY + 0.5D + theEntity.world.rand.nextFloat() * theEntity.height, theEntity.posZ + theEntity.world.rand.nextFloat() * theEntity.width * 2.0F - theEntity.width, motionX, motionY, motionZ);
	    Minecraft.getMinecraft().effectRenderer.addEffect(particleBee);        
	}
	
	@Override
	public void generateBeeParticles(World world, BlockPos blockPos)
	{
	    double coordX = world.rand.nextGaussian() * 0.4d;
	    double coordY = world.rand.nextGaussian() * 0.4d;
	    double coordZ = world.rand.nextGaussian() * 0.4d;
	    Particle particleBee = new ParticleBee(Minecraft.getMinecraft().renderEngine, world, coordX + blockPos.getX() + 0.5d, coordY + blockPos.getY() + 0.5d, coordZ + blockPos.getZ() + 0.5d);
	    Minecraft.getMinecraft().effectRenderer.addEffect(particleBee);        
	}
}
