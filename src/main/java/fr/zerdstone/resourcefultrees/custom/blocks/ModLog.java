package fr.zerdstone.resourcefultrees.custom.blocks;

import fr.zerdstone.resourcefultrees.custom.items.WoodChiselItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class ModLog extends RotatedPillarBlock {
	private final RegistryObject<Item> bark;
	private final boolean useChiselInWorld;

	public ModLog(Properties properties, RegistryObject<Item> bark, boolean useChiselInWorld) {
		super(properties);
		this.bark = bark;
		this.useChiselInWorld = useChiselInWorld;
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return true;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 5;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 5;
	}

	@Nullable
	@Override
	public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
		if(stack.getItem() instanceof AxeItem) {
			if(state.getBlock() instanceof ModLog) {
				return Blocks.STRIPPED_OAK_LOG.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
		}

		if(stack.getItem() instanceof WoodChiselItem && this.useChiselInWorld) {
			if(state.getBlock() instanceof ModLog) {
				world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.bark.get())));
				return Blocks.STRIPPED_OAK_LOG.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
		}

		return super.getToolModifiedState(state, world, pos, player, stack, toolAction);
	}
}
