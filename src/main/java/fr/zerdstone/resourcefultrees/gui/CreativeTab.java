package fr.zerdstone.resourcefultrees.gui;

import fr.zerdstone.resourcefultrees.registry.minecraft.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTab {
	public static final CreativeModeTab RESOURCEFULTREES_TAB = new CreativeModeTab("resourcefultreestab") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModItems.WOOD_CHISEL.get());
		}
	};
}
