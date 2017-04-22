package com.flashoverride.angrybees.entity.ai;

import javax.annotation.Nullable;

import com.flashoverride.angrybees.config.ConfigHandler;
import com.flashoverride.angrybees.init.SoundEventsAngryBees;
import com.flashoverride.angrybees.potion.PotionAngryBees;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIBeePanic extends EntityAIBase {

	private final EntityLiving theEntity;
    protected double speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;
    private boolean stingImmune = false;
    
    public EntityAIBeePanic(EntityLiving entityLivingIn, double speedIn)
    {
        this.theEntity = entityLivingIn;
        this.speed = speedIn;
        this.setMutexBits(5);
        
        for (String blacklistEntityName: ConfigHandler.entityBlacklist)
        {
        	if (EntityList.isMatchingName(entityLivingIn, new ResourceLocation(blacklistEntityName)))
        	{
        		stingImmune = true;
        	}
        }
    }


	@Override
	public boolean shouldExecute() {
		if (!theEntity.isPotionActive(PotionAngryBees.BEE_POTION) || stingImmune)
		{
			return false;
		}
        if (this.theEntity.getAITarget() == null && !theEntity.isPotionActive(PotionAngryBees.BEE_POTION))
        {
            return false;
        }
        else
        {
            if (theEntity.isPotionActive(PotionAngryBees.BEE_POTION))
            {
                BlockPos blockpos = this.getRandPos(this.theEntity.world, this.theEntity, 5, 4);

                if (blockpos != null)
                {
                    this.randPosX = (double)blockpos.getX();
                    this.randPosY = (double)blockpos.getY();
                    this.randPosZ = (double)blockpos.getZ();
                    return true;
                }
            }

            return this.findRandomPosition();
        }
    }

    private boolean findRandomPosition()
    {
        Vec3d vec3d = RandomEntityPositionGenerator.findRandomTarget(this.theEntity, 5, 4);

        if (vec3d == null)
        {
            return false;
        }
        else
        {
            this.randPosX = vec3d.xCoord;
            this.randPosY = vec3d.yCoord;
            this.randPosZ = vec3d.zCoord;
            return true;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	if (theEntity.isPotionActive(PotionAngryBees.BEE_POTION) && !stingImmune)
    	{
    		this.theEntity.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    		
     		theEntity.world.playSound(null, theEntity.getPosition(), SoundEventsAngryBees.BEE_BUZZ, SoundCategory.HOSTILE, 1.0f, 1.25f - (0.01f * (float) theEntity.world.rand.nextInt(50)));
    	}
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
    	if (theEntity.isPotionActive(PotionAngryBees.BEE_POTION) && !stingImmune)
    	{
    		return !this.theEntity.getNavigator().noPath();
    	}
    	else return false;
    }

    @Nullable
    private BlockPos getRandPos(World worldIn, Entity entityIn, int horizontalRange, int verticalRange)
    {
        BlockPos blockpos = new BlockPos(entityIn);
        int i = blockpos.getX();
        int j = blockpos.getY();
        int k = blockpos.getZ();
        float f = (float)(horizontalRange * horizontalRange * verticalRange * 2);
        BlockPos blockpos1 = null;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int l = i - horizontalRange; l <= i + horizontalRange; ++l)
        {
            for (int i1 = j - verticalRange; i1 <= j + verticalRange; ++i1)
            {
                for (int j1 = k - horizontalRange; j1 <= k + horizontalRange; ++j1)
                {
                    blockpos$mutableblockpos.setPos(l, i1, j1);
                    IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.getMaterial() == Material.WATER)
                    {
                        float f1 = (float)((l - i) * (l - i) + (i1 - j) * (i1 - j) + (j1 - k) * (j1 - k));

                        if (f1 < f)
                        {
                            f = f1;
                            blockpos1 = new BlockPos(blockpos$mutableblockpos);
                        }
                    }
                }
            }
        }

        return blockpos1;
    }
}