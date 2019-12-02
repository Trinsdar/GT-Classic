package gtclassic.common.item;

import java.util.Arrays;
import java.util.List;

import gtclassic.GTMod;
import ic2.api.classic.item.IEUReader;
import ic2.api.item.ElectricItem;
import ic2.core.item.base.BasicElectricItem;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.obj.IStaticTexturedItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GTItemSurvivalScanner extends BasicElectricItem implements IStaticTexturedItem, IEUReader {

	double energyCost = 100;

	public GTItemSurvivalScanner() {
		this.maxStackSize = 1;
		this.setHasSubtypes(false);
		this.setMaxDamage(0);
		this.maxStackSize = 1;
		setRegistryName("portable_scanner");
		setUnlocalizedName(GTMod.MODID + ".portable_scanner");
		setCreativeTab(GTMod.creativeTabGT);
		this.maxCharge = 100000;
		this.transferLimit = 128;
		this.tier = 1;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		GTItemCreativeScanner.genToolTip(stack, worldIn, tooltip, flagIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getTexture(int meta) {
		return Ic2Icons.getTextures(GTMod.MODID + "_items")[29];
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ, EnumHand hand) {
		if (!canScan(player.getHeldItemMainhand())) {
			return EnumActionResult.PASS;
		} else {
			ElectricItem.manager.use(player.getHeldItemMainhand(), energyCost, (EntityLivingBase) null);
			return GTItemCreativeScanner.scanBlock(player, world, pos, side, hitX, hitY, hitZ, hand);
		}
	}

	public boolean canScan(ItemStack stack) {
		return ElectricItem.manager.canUse(stack, energyCost);
	}

	@Override
	public List<Integer> getValidVariants() {
		return Arrays.asList(0);
	}

	@Override
	public boolean isEUReader(ItemStack var1) {
		return true;
	}

	@Override
	public int getTextureEntry(int var1) {
		return 0;
	}
}
