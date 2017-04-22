package com.flashoverride.angrybees.entity.ai;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RandomEntityPositionGenerator
{
    /**
     * used to store a driection when the user passes a point to move towards or away from. WARNING: NEVER THREAD SAFE.
     * MULTIPLE findTowards and findAway calls, will share this var
     */
    private static Vec3d staticVector = Vec3d.ZERO;

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks
     */
    @Nullable
    public static Vec3d findRandomTarget(EntityLiving entityIn, int xz, int y)
    {
        return findRandomTargetBlock(entityIn, xz, y, (Vec3d)null);
    }

    @Nullable
    public static Vec3d getLandPos(EntityLiving p_191377_0_, int p_191377_1_, int p_191377_2_)
    {
        return generateRandomPos(p_191377_0_, p_191377_1_, p_191377_2_, (Vec3d)null, false);
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the direction of the point par3
     */
    @Nullable
    public static Vec3d findRandomTargetBlockTowards(EntityLiving entityIn, int xz, int y, Vec3d targetVec3)
    {
        staticVector = targetVec3.subtract(entityIn.posX, entityIn.posY, entityIn.posZ);
        return findRandomTargetBlock(entityIn, xz, y, staticVector);
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the reverse direction of the point par3
     */
    @Nullable
    public static Vec3d findRandomTargetBlockAwayFrom(EntityLiving entityIn, int xz, int y, Vec3d targetVec3)
    {
        staticVector = (new Vec3d(entityIn.posX, entityIn.posY, entityIn.posZ)).subtract(targetVec3);
        return findRandomTargetBlock(entityIn, xz, y, staticVector);
    }

    /**
     * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction of
     * par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
     */
    @Nullable
    private static Vec3d findRandomTargetBlock(EntityLiving entityIn, int xz, int y, @Nullable Vec3d targetVec3)
    {
        return generateRandomPos(entityIn, xz, y, targetVec3, true);
    }

    @Nullable
    private static Vec3d generateRandomPos(EntityLiving entityLiving, int p_191379_1_, int p_191379_2_, @Nullable Vec3d p_191379_3_, boolean p_191379_4_)
    {
    	EntityCreature entityCreature = null;
        PathNavigate pathnavigate = entityLiving.getNavigator();
        Random random = entityLiving.getRNG();
        boolean flag = false;
        boolean creatureFlag = false;
        
        if (entityLiving instanceof EntityCreature)
        {
        	creatureFlag = true;
        	entityCreature = (EntityCreature) entityLiving;
        	
        	if (entityCreature.hasHome())
        	{
        		double d0 = entityCreature.getHomePosition().distanceSq((double)MathHelper.floor(entityLiving.posX), (double)MathHelper.floor(entityLiving.posY), (double)MathHelper.floor(entityLiving.posZ)) + 4.0D;
        		double d1 = (double)(entityCreature.getMaximumHomeDistance() + (float)p_191379_1_);
        		flag = d0 < d1 * d1;
        	}
        }

        boolean flag1 = false;
        float f = -99999.0F;
        int k1 = 0;
        int i = 0;
        int j = 0;

        for (int k = 0; k < 10; ++k)
        {
            int l = random.nextInt(2 * p_191379_1_ + 1) - p_191379_1_;
            int i1 = random.nextInt(2 * p_191379_2_ + 1) - p_191379_2_;
            int j1 = random.nextInt(2 * p_191379_1_ + 1) - p_191379_1_;

            if (p_191379_3_ == null || (double)l * p_191379_3_.xCoord + (double)j1 * p_191379_3_.zCoord >= 0.0D)
            {
                if (creatureFlag && flag && p_191379_1_ > 1)
                {
                    BlockPos blockpos = entityCreature.getHomePosition();

                    if (entityLiving.posX > (double)blockpos.getX())
                    {
                        l -= random.nextInt(p_191379_1_ / 2);
                    }
                    else
                    {
                        l += random.nextInt(p_191379_1_ / 2);
                    }

                    if (entityLiving.posZ > (double)blockpos.getZ())
                    {
                        j1 -= random.nextInt(p_191379_1_ / 2);
                    }
                    else
                    {
                        j1 += random.nextInt(p_191379_1_ / 2);
                    }
                }

                BlockPos blockpos1 = new BlockPos((double)l + entityLiving.posX, (double)i1 + entityLiving.posY, (double)j1 + entityLiving.posZ);

                if (creatureFlag)
                {
                	if ((!flag || entityCreature.isWithinHomeDistanceFromPosition(blockpos1)) && pathnavigate.canEntityStandOnPos(blockpos1))
                	{
                		if (!p_191379_4_)
                		{
                			blockpos1 = moveAboveSolid(blockpos1, entityLiving);

                			if (isWaterDestination(blockpos1, entityLiving))
                			{
                				continue;
                			}
                		}

                		float f1 = entityCreature.getBlockPathWeight(blockpos1);

                		if (f1 > f)
                		{
                			f = f1;
                			k1 = l;
                			i = i1;
                			j = j1;
                			flag1 = true;
                		}
                	}
                }
            }
        }

        if (flag1)
        {
            return new Vec3d((double)k1 + entityLiving.posX, (double)i + entityLiving.posY, (double)j + entityLiving.posZ);
        }
        else
        {
            return null;
        }
    }

    private static BlockPos moveAboveSolid(BlockPos p_191378_0_, EntityLiving entityLiving)
    {
        if (!entityLiving.world.getBlockState(p_191378_0_).getMaterial().isSolid())
        {
            return p_191378_0_;
        }
        else
        {
            BlockPos blockpos;

            for (blockpos = p_191378_0_.up(); blockpos.getY() < entityLiving.world.getHeight() && entityLiving.world.getBlockState(blockpos).getMaterial().isSolid(); blockpos = blockpos.up())
            {
                ;
            }

            return blockpos;
        }
    }

    private static boolean isWaterDestination(BlockPos p_191380_0_, EntityLiving entityLiving)
    {
        return entityLiving.world.getBlockState(p_191380_0_).getMaterial() == Material.WATER;
    }
}