package gtclassic.tileentity;

import java.util.Random;

import gtclassic.util.GTBlocks;
import ic2.core.block.base.tile.TileEntityGeneratorBase;
import ic2.core.inventory.container.ContainerIC2;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.registry.Ic2States;
import ic2.core.util.math.Box2D;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GTTileEntityLightningRod extends TileEntityGeneratorBase {

	public GTTileEntityLightningRod() {
		super(0);
		this.maxStorage = 100000000;
		this.production = 8096;

	}

	public static boolean correctWeather(World world, BlockPos pos) {
		if (world.provider.hasSkyLight()) {
			if (!world.canBlockSeeSky(pos)) {
				return false;
			} else {
				return world.isRaining() /* && world.isThundering() */;
			}
		} else {
			return false;
		}
	}

	@Override
	public void update() {
		if (world.getTotalWorldTime() % 256 == 0 && world.rand.nextInt(10) == 0)
		{
			if (world.isThundering() && world.getPrecipitationHeight(pos) <= pos.getY() + 1 && checkStructure()) 
			{
				this.world.addWeatherEffect(new EntityLightningBolt(this.world, this.getPos().getX(), getPos().getY(), this.getPos().getZ(), false));
				if (this.storage < this.maxStorage) 
				{
					this.storage = Math.min(this.maxStorage, storage + 25000000);
					getNetwork().updateTileGuiField(this, "storage");
				}
			}
		}
		updateComparators();
	}

	@Override
	public ContainerIC2 getGuiContainer(EntityPlayer var1) {
		return null;
	}

	@Override
	public ResourceLocation getTexture() {
		return null;
	}

	@Override
	public Box2D getEnergyBox() {
		return null;
	}

	@Override
	public boolean gainFuel() {
		return false;
	}

	@Override
	public boolean hasGui(EntityPlayer player) {
		return false;
	}

	public boolean checkStructure() {
		// if (world.isRemote) return false; //Return false if on client side
		//world.isRemote is not a good idea, just use isRendering() instead which is in Ic2cs TileEntities a native function and is also not relying on a world.
		MutableBlockPos position = new MutableBlockPos(pos);
		int heighest = 0;
		for(int i = pos.getY() + 1;i<world.provider.getActualHeight();i++)
		{
			position.setY(i);
			if(!checkPos(position))
			{
				heighest = i-1;
				break;
			}
		}
		return heighest > 7;
	}

	public boolean checkPos(BlockPos pos) {
		return world.getBlockState(pos) == Ic2States.ironFence;
	}
}
