package gtclassic.common.container;

import gtclassic.GTMod;
import gtclassic.common.gui.GTGuiCompComputerCube;
import gtclassic.common.tile.datanet.GTTileComputerCube;
import ic2.core.inventory.container.ContainerTileComponent;
import ic2.core.inventory.gui.GuiIC2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GTContainerComputerCube extends ContainerTileComponent<GTTileComputerCube> {

	public static ResourceLocation TEXTURE = new ResourceLocation(GTMod.MODID, "textures/gui/computercube.png");

	public GTContainerComputerCube(InventoryPlayer player, GTTileComputerCube tile) {
		super(tile);
		this.addPlayerInventory(player, 0, 0);
		this.addComponent(new GTGuiCompComputerCube(tile));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onGuiLoaded(GuiIC2 gui) {
		gui.dissableInvName();
	}

	@Override
	public ResourceLocation getTexture() {
		return TEXTURE;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return getGuiHolder().canInteractWith(player);
	}

	@Override
	public int guiInventorySize() {
		return 0;
	}
}
