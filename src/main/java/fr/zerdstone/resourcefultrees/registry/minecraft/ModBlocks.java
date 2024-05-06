package fr.zerdstone.resourcefultrees.registry.minecraft;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import fr.zerdstone.resourcefultrees.custom.blocks.ModLog;
import fr.zerdstone.resourcefultrees.custom.blocks.ModSapling;
import fr.zerdstone.resourcefultrees.custom.blocks.SimpleBarkRefineryBlock;
import fr.zerdstone.resourcefultrees.registry.RegistryHandler;
import fr.zerdstone.resourcefultrees.utils.ModConstants;
import fr.zerdstone.resourcefultrees.world.TreeGrower;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = createBlockRegistry();
	public static final DeferredRegister<Block> LOGS = createBlockRegistry();
	public static final DeferredRegister<Block> LEAVES = createBlockRegistry();
	public static final DeferredRegister<Block> SAPLING = createBlockRegistry();
	public static final RegistryObject<Block> SIMPLE_BARK_REFINERY = BLOCKS.register("simple_bark_refinery",
																					 () -> new SimpleBarkRefineryBlock(
																							 BlockBehaviour.Properties
																									 .copy(Blocks.BLAST_FURNACE)
																									 .noOcclusion()
																									 .lightLevel((p_50763_) -> {
																										 return p_50763_.getValue(BlockStateProperties.LIT) ? 12 : 0;
																									 })
																					 )); // FOR BLOCKS.FURNACE, must implement lit
	private static final BlockBehaviour.Properties LOG_PROPERTIES = BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD);
	private static final BlockBehaviour.Properties LEAVES_PROPERTIES = BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).sound(SoundType.AZALEA_LEAVES);
	private static final BlockBehaviour.Properties
			SAPLING_PROPERTIES =
			BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS);

	private ModBlocks() {
		throw new IllegalAccessError(ModConstants.UTILITY_CLASS);
	}

	private static DeferredRegister<Block> createBlockRegistry() {
		return DeferredRegister.create(ForgeRegistries.BLOCKS, ResourcefulTrees.MOD_ID);
	}

	public static void initializeRegistries(IEventBus bus) {
		BLOCKS.register(bus);
		LOGS.register(bus);
		LEAVES.register(bus);
		SAPLING.register(bus);
	}

	public static RegistryObject<Block> addLog(String name, boolean useChiselInWold, int burnTime) {
		RegistryObject<Block> log = LOGS.register(name, () -> new ModLog(LOG_PROPERTIES, RegistryHandler.BARK.get(name.replace("_log", "_tree")), useChiselInWold));
		ModItems.addLogItem(name, log, burnTime);
		return log;
	}

	public static RegistryObject<Block> addLeaves(String name) {
		RegistryObject<Block> leaves = LEAVES.register(name, () -> new LeavesBlock(LEAVES_PROPERTIES));
		ModItems.addLeavesItem(name, leaves);
		return leaves;
	}

	public static RegistryObject<Block> addSapling(String name, int burnTime, String dirt) {
		Block otherDirt = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(dirt));
		RegistryObject<Block>
				sapling =
				SAPLING.register(name, () -> new ModSapling(new TreeGrower(name.replace("_sapling", "_tree"), () -> otherDirt), SAPLING_PROPERTIES, () -> otherDirt));
		ModItems.addSaplingItem(name, sapling, burnTime);
		return sapling;
	}
}
