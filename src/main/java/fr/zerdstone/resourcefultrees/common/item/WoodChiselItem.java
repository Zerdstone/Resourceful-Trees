package fr.zerdstone.resourcefultrees.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WoodChiselItem extends Item {

	public WoodChiselItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext pContext) {
		Level level = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();
		Player player = pContext.getPlayer();
		BlockState blockstate = level.getBlockState(blockpos);
		Optional<BlockState> optional = Optional.ofNullable(blockstate.getToolModifiedState(level, blockpos, player, pContext.getItemInHand(), ToolActions.AXE_STRIP));
		ItemStack itemstack = pContext.getItemInHand();
		Optional<BlockState> optional2 = Optional.empty();
		if (optional.isPresent()) {
			level.playSound(player, blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
			optional2 = optional;
		}

		if (optional2.isPresent()) {
			if (player instanceof ServerPlayer) {
				CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
			}

			level.setBlock(blockpos, optional2.get(), 11);
			if (player != null) {
				itemstack.hurtAndBreak(1, player, (p_150686_) -> p_150686_.broadcastBreakEvent(pContext.getHand()));
			}

			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		else {
			return InteractionResult.PASS;
		}
	}
}
