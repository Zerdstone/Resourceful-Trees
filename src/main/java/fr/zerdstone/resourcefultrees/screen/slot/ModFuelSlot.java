package fr.zerdstone.resourcefultrees.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModFuelSlot extends SlotItemHandler {

	public ModFuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return ForgeHooks.getBurnTime(stack, null) > 0;
	}
}
