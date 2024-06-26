package fr.zerdstone.resourcefultrees.common.blockentity;

import fr.zerdstone.resourcefultrees.common.inventory.SimpleBarkRefineryMenu;
import fr.zerdstone.resourcefultrees.common.recipe.SimpleBarkRefineryRecipe;
import fr.zerdstone.resourcefultrees.common.register.ModBlockEntities;
import fr.zerdstone.resourcefultrees.common.register.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SimpleBarkRefineryBlockEntity extends BlockEntity implements MenuProvider {

	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	private static final int FUEL_SLOT = 0;
	private static final int INPUT_SLOT = 1;
	private static final int RESULT_SLOT = 2;
	private static final int TINY_CHARCOAL_OUTPUT_SLOT = 3;
	protected final ContainerData data;
	private final ItemStackHandler itemHandler = new ItemStackHandler(4) { // NUMBER OF SLOT
		protected void onContentsChanged(int slot) {
			setChanged();
		}

	};
	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
	private int cookingTime = 0;
	private int cookingDuration = 200;
	private int burnTime;
	private int burnDuration;

	public SimpleBarkRefineryBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(ModBlockEntities.SIMPLE_BARK_REFINERY_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);

		this.data = new ContainerData() {
			public int get(int index) {
				return switch (index) {
					case 0 -> SimpleBarkRefineryBlockEntity.this.cookingTime;
					case 1 -> SimpleBarkRefineryBlockEntity.this.cookingDuration;
					case 2 -> SimpleBarkRefineryBlockEntity.this.burnTime;
					case 3 -> SimpleBarkRefineryBlockEntity.this.burnDuration;
					default -> 0;
				};
			}

			public void set(int index, int value) {
				switch (index) {
					case 0:
						SimpleBarkRefineryBlockEntity.this.cookingTime = value;
						break;

					case 1:
						SimpleBarkRefineryBlockEntity.this.cookingDuration = value;
						break;

					case 2:
						SimpleBarkRefineryBlockEntity.this.burnTime = value;
						break;

					case 3:
						SimpleBarkRefineryBlockEntity.this.burnDuration = value;
						break;
				}
			}

			public int getCount() {
				return 4;
			}
		};
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SimpleBarkRefineryBlockEntity pBlockEntity) {

		boolean isBurning = pBlockEntity.burnTime > 0;
		ItemStackHandler items = pBlockEntity.itemHandler;

		Level level = pBlockEntity.level;
		SimpleContainer inventory = new SimpleContainer(items.getSlots());
		for (int i = 0; i < items.getSlots(); i++) {
			inventory.setItem(i, items.getStackInSlot(i));
		}

		assert level != null;
		Optional<SimpleBarkRefineryRecipe> recipe = level.getRecipeManager().getRecipeFor(SimpleBarkRefineryRecipe.Type.INSTANCE, inventory, level);

		if (pBlockEntity.burnTime > 0) {
			--pBlockEntity.burnTime;
		}

		if (pBlockEntity.burnTime == 0) {
			pLevel.setBlock(pPos, pState.setValue(LIT, Boolean.FALSE), 3);
		}

		if (recipe.isEmpty() && pBlockEntity.cookingTime > 0) {
			pBlockEntity.cookingTime = 0;
		}

		if (pBlockEntity.burnTime == 0 && pBlockEntity.cookingTime > 0) {
			pBlockEntity.cookingTime = 0;
			pLevel.setBlock(pPos, pState.setValue(LIT, Boolean.FALSE), 3);
		}

		if (pBlockEntity.burnTime == 0 && recipe.isPresent() && !items.getStackInSlot(FUEL_SLOT).isEmpty()) {
			pBlockEntity.burnTime = ForgeHooks.getBurnTime(items.getStackInSlot(FUEL_SLOT), null);
			pBlockEntity.burnDuration = pBlockEntity.burnTime;
			items.getStackInSlot(FUEL_SLOT).shrink(1);
			pLevel.setBlock(pPos, pState.setValue(LIT, Boolean.TRUE), 3);
		}

		if (recipe.isPresent() && (!items.getStackInSlot(FUEL_SLOT).isEmpty() || pBlockEntity.burnTime > 0)) {

			if (items.getStackInSlot(TINY_CHARCOAL_OUTPUT_SLOT).getCount() < items.getStackInSlot(TINY_CHARCOAL_OUTPUT_SLOT).getMaxStackSize()
					&& ((items.getStackInSlot(RESULT_SLOT).getCount() < items.getStackInSlot(RESULT_SLOT).getMaxStackSize()
								 && items.getStackInSlot(RESULT_SLOT).is(recipe.get().getResultItem().getItem())) || items.getStackInSlot(RESULT_SLOT).isEmpty())) {
				++pBlockEntity.cookingTime;

				if (pBlockEntity.cookingTime == pBlockEntity.cookingDuration) {
					items.getStackInSlot(INPUT_SLOT).shrink(1);
					items.setStackInSlot(RESULT_SLOT,
										 new ItemStack(recipe.get().getResultItem().getItem(),
													   items.getStackInSlot(RESULT_SLOT).getCount() + recipe.get().getResultItem().getCount()));

					items.setStackInSlot(TINY_CHARCOAL_OUTPUT_SLOT, new ItemStack(ModItems.TINY_CHARCOAL.get(), items.getStackInSlot(TINY_CHARCOAL_OUTPUT_SLOT).getCount() + 1));
					pBlockEntity.cookingTime = 0;
				}
			}
		}

		if (isBurning != pBlockEntity.burnTime > 0) {
			setChanged(pLevel, pPos, pState);
		}
	}

	@Override
	public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
		return new SimpleBarkRefineryMenu(pContainerId, pInventory, this, this.data);
	}

	@Override
	public @NotNull Component getDisplayName() {
		return new TextComponent("Simple Bark Refinery");
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return lazyItemHandler.cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		lazyItemHandler = LazyOptional.of(() -> itemHandler);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		lazyItemHandler.invalidate();
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		pTag.put("inventory", itemHandler.serializeNBT());
		pTag.putInt("simple_bark_refinery.cookingTime", cookingTime);
		pTag.putInt("simple_bark_refinery.burnTime", burnTime);
		pTag.putInt("simple_bark_refinery.burnDuration", burnDuration);
		super.saveAdditional(pTag);
	}

	@Override
	public void load(@NotNull CompoundTag pTag) {
		super.load(pTag);
		itemHandler.deserializeNBT(pTag.getCompound("inventory"));
		cookingTime = pTag.getInt("simple_bark_refinery.cookingTime");
		burnTime = pTag.getInt("simple_bark_refinery.burnTime");
		burnDuration = pTag.getInt("simple_bark_refinery.burnDuration");
	}

	public void drops() {
		SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			inventory.setItem(i, itemHandler.getStackInSlot(i));
		}

		assert this.level != null;
		Containers.dropContents(this.level, this.worldPosition, inventory);
	}

	public NonNullList<ItemStack> getItems() {
		NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
		items.add(itemHandler.getStackInSlot(INPUT_SLOT));
		items.add(itemHandler.getStackInSlot(RESULT_SLOT));
		return items;
	}
}
