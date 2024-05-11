package fr.zerdstone.resourcefultrees.common.inventory;

import fr.zerdstone.resourcefultrees.common.register.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CreativeTab {
	public static final CreativeModeTab RESOURCEFULTREES_TAB = new CreativeModeTab("resourcefultreestab") {
		@Override
		public @NotNull ItemStack makeIcon() {
			return new ItemStack(ModItems.WOOD_CHISEL.get());
		}
	};
}
