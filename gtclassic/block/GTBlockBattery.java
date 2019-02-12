package gtclassic.block;

import gtclassic.item.GTBatteryBuilder;
import gtclassic.util.helpers.ICustomItemBlock;
import ic2.core.item.block.ItemBlockRare;

public class GTBlockBattery extends GTBlockTileCustom implements ICustomItemBlock
{
	public int max;
	public int trans;
	public int tier;
	
	public GTBlockBattery(String name, int width, int height, boolean light, int max, int trans, int tier)
	{
		super(name, width, height, light);
		this.max = max;
		this.trans = trans;
		this.tier = tier;
	}

	@Override
	public Class<? extends ItemBlockRare> getCustomItemBlock()
	{
		return GTBatteryBuilder.class;
	}
}
