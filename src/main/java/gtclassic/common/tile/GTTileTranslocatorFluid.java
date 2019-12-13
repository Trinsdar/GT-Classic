package gtclassic.common.tile;

import java.util.Collections;
import java.util.List;

import gtclassic.common.GTLang;
import gtclassic.common.container.GTContainerTranslocatorFluid;
import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.container.ContainerIC2;
import ic2.core.inventory.gui.GuiComponentContainer;
import ic2.core.platform.lang.components.base.LocaleComp;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GTTileTranslocatorFluid extends GTTileBaseBuffer implements IHasGui {

	public FluidStack filter;

	public GTTileTranslocatorFluid() {
		super(1);
		this.hasRedstone = false;
	}

	@Override
	public LocaleComp getBlockName() {
		return GTLang.TRANSLOCATOR_FLUID;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.getString("filter") != null) {
			this.filter = new FluidStack(FluidRegistry.getFluid(nbt.getString("filter")), 1000);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (this.filter != null) {
			nbt.setString("filter", this.filter.getFluid().getName());
		}
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Class<? extends GuiScreen> getGuiClass(EntityPlayer player) {
		return GuiComponentContainer.class;
	}

	@Override
	public ContainerIC2 getGuiContainer(EntityPlayer player) {
		return new GTContainerTranslocatorFluid(player.inventory, this);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return !this.isInvalid();
	}

	@Override
	public void onGuiClosed(EntityPlayer entityPlayer) {
		// needed for construction
	}

	@Override
	public boolean hasGui(EntityPlayer player) {
		return true;
	}

	@Override
	public void onBufferTick() {
		IFluidHandler start = FluidUtil.getFluidHandler(world, getImportTilePos(), getFacing());
		IFluidHandler end = FluidUtil.getFluidHandler(world, getExportTilePos(), getFacing().getOpposite());
		boolean canExport = start != null && end != null;
		if (canExport && filter == null) {
			FluidUtil.tryFluidTransfer(end, start, 500, true);
		}
		if (canExport && filter != null) {
			FluidStack fake = FluidUtil.tryFluidTransfer(end, start, 500, false);
			if (fake != null && (fake.getFluid().getName() == filter.getFluid().getName())) {
				FluidUtil.tryFluidTransfer(end, start, 500, true);
				tryBlacklistPipe(this, getFacing());
			}
		}
	}

	@Override
	public List<ItemStack> getDrops() {
		return Collections.emptyList();
	}

	@Override
	public boolean isInventoryFull() {
		return false;
	}
}