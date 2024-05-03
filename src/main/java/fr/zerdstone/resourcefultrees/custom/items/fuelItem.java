package fr.zerdstone.resourcefultrees.custom.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class fuelItem extends Item {

	private int burnTime;

	public fuelItem(Properties pProperties, int burnTime) {
		super(pProperties);
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
		return this.burnTime;
	}

}
