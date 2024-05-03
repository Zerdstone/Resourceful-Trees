package fr.zerdstone.resourcefultrees.registry.minecraft;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import fr.zerdstone.resourcefultrees.custom.items.WoodChiselItem;
import fr.zerdstone.resourcefultrees.custom.items.fuelBlockItem;
import fr.zerdstone.resourcefultrees.gui.CreativeTab;
import fr.zerdstone.resourcefultrees.utils.ModConstants;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	private ModItems() {
		throw new IllegalAccessError(ModConstants.UTILITY_CLASS);
	}

	public static final DeferredRegister<Item> ITEMS = createItemRegistry();
	public static final DeferredRegister<Item> WOOD_CHISEL_ITEMS = createItemRegistry();
	public static final DeferredRegister<Item> BARK_ITEMS = createItemRegistry();
	public static final DeferredRegister<Item> LOG_BLOCK_ITEMS = createItemRegistry();
	public static final DeferredRegister<Item> LEAVES_BLOCK_ITEMS = createItemRegistry();
	public static final DeferredRegister<Item> SAPLING_BLOCK_ITEMS = createItemRegistry();

	private static DeferredRegister<Item> createItemRegistry() {
		return DeferredRegister.create(ForgeRegistries.ITEMS, ResourcefulTrees.MOD_ID);
	}

	private static Item.Properties getItemProperties() {
		return new Item.Properties().tab(CreativeTab.RESOURCEFULTREES_TAB);
	}

	public static void initializeRegistries(IEventBus bus) {
		ITEMS.register(bus);
		WOOD_CHISEL_ITEMS.register(bus);
		BARK_ITEMS.register(bus);
		LOG_BLOCK_ITEMS.register(bus);
		LEAVES_BLOCK_ITEMS.register(bus);
		SAPLING_BLOCK_ITEMS.register(bus);
	}

	public static RegistryObject<Item> addBark(String name) {
		return BARK_ITEMS.register(name.replace("_tree", "_bark"), () -> new Item(getItemProperties()));
	}

	public static void addLogItem(String name, RegistryObject<Block> log, int burnTime) {
		if (burnTime == 0) {
			LOG_BLOCK_ITEMS.register(name, () -> new BlockItem(log.get(), getItemProperties()));
		} else {
			LOG_BLOCK_ITEMS.register(name, () -> new fuelBlockItem(log.get(), getItemProperties(), burnTime));
		}
	}

	public static void addLeavesItem(String name, RegistryObject<Block> leaves) {
		LEAVES_BLOCK_ITEMS.register(name, () -> new BlockItem(leaves.get(), getItemProperties()));
	}

	public static void addSaplingItem(String name, RegistryObject<Block> sapling, int burnTime) {
		if (burnTime == 0) {
			SAPLING_BLOCK_ITEMS.register(name, () -> new BlockItem(sapling.get(), getItemProperties()));
		} else {
			SAPLING_BLOCK_ITEMS.register(name, () -> new fuelBlockItem(sapling.get(), getItemProperties(), burnTime));
		}
	}

	public static final RegistryObject<Item> WOOD_CHISEL = WOOD_CHISEL_ITEMS.register("wood_chisel",
			() -> new WoodChiselItem(getItemProperties().durability(32)));
}
