package gtclassic.api.material;

import gtclassic.GTMod;
import gtclassic.api.interfaces.IGTColorItem;
import ic2.core.platform.registry.Ic2Lang;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.obj.ILayeredItemModel;
import ic2.core.platform.textures.obj.IStaticTexturedItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class GTMaterialItem extends Item implements IStaticTexturedItem, IGTColorItem, ILayeredItemModel {

	private GTMaterial material;
	private GTMaterialFlag flag;

	public GTMaterialItem(GTMaterial material, GTMaterialFlag flag) {
		this.material = material;
		this.flag = flag;
		setRegistryName(this.material.getName() + this.flag.getSuffix());
		setUnlocalizedName(GTMod.MODID + "." + this.flag.getPrefix() + this.material.getDisplayName());
		setCreativeTab(GTMod.creativeTabGT);
	}

	@Nonnull
	@Override
	public String getItemStackDisplayName(@Nonnull ItemStack stack) {
		if (flag.getComp() != Ic2Lang.nullKey){
			return flag.getComp().getLocalizedFormatted(I18n.translateToLocal("material." + material.getDisplayName() + ".name").trim());
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public List<Integer> getValidVariants() {
		return Arrays.asList(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getTexture(int i) {
		return Ic2Icons.getTextures(flag.getTexture())[flag.getTextureID()];
	}

	@Override
	public Color getColor(ItemStack stack, int index) {
		if (index == 0) {
			return this.material.getColor();
		} else {
			return Color.white;
		}
	}

	@Override
	public boolean isLayered(ItemStack var1) {
		return flag.isLayered();
	}

	@Override
	public int getLayers(ItemStack var1) {
		return 2;
	}

	@Override
	public TextureAtlasSprite getTexture(int index, ItemStack var2) {
		return Ic2Icons.getTextures(flag.getTexture())[flag.getTextureID() + index];
	}

	@Override
	public int getItemBurnTime(ItemStack itemstack) {
		if (this.material.equals(GTMaterial.Lithium) && this.flag.equals(GTMaterialFlag.DUST)) {
			return 2000;
		}
		if (this.material.equals(GTMaterial.Carbon) && this.flag.equals(GTMaterialFlag.DUST)) {
			return 1000;
		}
		if (this.material.equals(GTMaterial.Sulfur) && this.flag.equals(GTMaterialFlag.DUST)) {
			return 1600;
		}
		return 0;
	}

	public GTMaterial getMaterial() {
		return this.material;
	}

	public GTMaterialFlag getFlag() {
		return this.flag;
	}
}
