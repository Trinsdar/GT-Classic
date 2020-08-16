package gtclassic.common.container;

import gtclassic.api.gui.GTGuiCompMachinePower;
import gtclassic.api.gui.GTSlotUpgrade;
import gtclassic.common.tile.GTTileCentrifuge;
import ic2.core.inventory.container.ContainerTileComponent;
import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.components.base.MachineProgressComp;
import ic2.core.inventory.slots.SlotCustom;
import ic2.core.inventory.slots.SlotDischarge;
import ic2.core.inventory.slots.SlotDisplay;
import ic2.core.inventory.slots.SlotOutput;
import ic2.core.util.math.Box2D;
import ic2.core.util.math.Vec2i;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GTContainerCentrifuge extends ContainerTileComponent<GTTileCentrifuge> {

	public static final Box2D machineProgressBox = new Box2D(69, 24, 20, 18);
	public static final Vec2i machineProgressPos = new Vec2i(176, 0);

	public GTContainerCentrifuge(InventoryPlayer player, GTTileCentrifuge tile) {
		super(tile);
		for (int x = 0; x < 2; ++x) {
			this.addSlotToContainer(new SlotCustom(tile, x, 26 + x * 18, 17, tile.filter));
		}

		for (int y = 0; y < 2; ++y) {
			for (int x = 0; x < 3; ++x) {
				this.addSlotToContainer(new SlotOutput(player.player, tile, 2 + x + y * 3, 98 + x * 18, 8 + y * 18));
			}
		}for (int y = 0; y < 2; ++y) {
			for (int x = 0; x < 3; ++x) {
				this.addSlotToContainer(new SlotDisplay(tile, 8 + x + y * 3, 98 + x * 18, 44 + y * 18));
			}
		}
		this.addSlotToContainer(new SlotDisplay(tile, 14, 35, 36));
		this.addSlotToContainer(new SlotDischarge(tile, Integer.MAX_VALUE, 15, 8, 62));
		for (int i = 0; i < 4; ++i) {
			this.addSlotToContainer(new GTSlotUpgrade(tile, 16 + i, 26 + (i * 18), 62));
		}
		this.addComponent(new GTGuiCompMachinePower(tile, 75, 40));
		this.addPlayerInventory(player);
		this.addComponent(new MachineProgressComp(tile, machineProgressBox, machineProgressPos));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onGuiLoaded(GuiIC2 gui) {
		gui.dissableInvName();
		gui.disableName();
	}

	@Override
	public ResourceLocation getTexture() {
		return this.getGuiHolder().getGuiTexture();
	}

	@Override
	public int guiInventorySize() {
		return this.getGuiHolder().slotCount;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.getGuiHolder().canInteractWith(playerIn);
	}
}
