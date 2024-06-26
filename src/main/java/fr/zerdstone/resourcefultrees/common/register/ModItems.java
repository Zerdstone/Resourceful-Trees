package fr.zerdstone.resourcefultrees.common.register;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import fr.zerdstone.resourcefultrees.common.inventory.CreativeTab;
import fr.zerdstone.resourcefultrees.common.item.WoodChiselItem;
import fr.zerdstone.resourcefultrees.common.item.fuelBlockItem;
import fr.zerdstone.resourcefultrees.common.item.fuelItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
																			   ResourcefulTrees.MOD_ID);
	public static final RegistryObject<Item> TINY_CHARCOAL = ITEMS.register("tiny_charcoal",
																			() -> new fuelItem(new Item.Properties().tab(CreativeTab.RESOURCEFULTREES_TAB), 400));
	public static final RegistryObject<Item> TINY_DIAMOND = ITEMS.register("tiny_diamond",
																		   () -> new Item(new Item.Properties().tab(CreativeTab.RESOURCEFULTREES_TAB)));
	public static final RegistryObject<Item> HALF_EMERALD = ITEMS.register("half_emerald",
																		   () -> new Item(new Item.Properties().tab(CreativeTab.RESOURCEFULTREES_TAB)));
	public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
																			() -> new Item(new Item.Properties().tab(CreativeTab.RESOURCEFULTREES_TAB)));
	public static final RegistryObject<Item> WOOD_CHISEL = ITEMS.register("wood_chisel",
																		  () -> new WoodChiselItem(getItemProperties().durability(32)));
	public static final RegistryObject<Item> SIMPLE_BARK_REFINERY = ITEMS.register("simple_bark_refinery",
																				   () -> new BlockItem(ModBlocks.SIMPLE_BARK_REFINERY.get(), getItemProperties()));

	private static Item.Properties getItemProperties() {
		return new Item.Properties().tab(CreativeTab.RESOURCEFULTREES_TAB);
	}

	public static RegistryObject<Item> addBark(String name) {
		return ITEMS.register(name.replace("_tree", "_bark"), () -> new Item(getItemProperties()));
	}

	public static void addLogItem(String name, RegistryObject<Block> log, int burnTime) {
		if (burnTime == 0) {
			ITEMS.register(name, () -> new BlockItem(log.get(), getItemProperties()));
		}
		else {
			ITEMS.register(name, () -> new fuelBlockItem(log.get(), getItemProperties(), burnTime));
		}
	}

	public static void addLeavesItem(String name, RegistryObject<Block> leaves) {
		ITEMS.register(name, () -> new BlockItem(leaves.get(), getItemProperties()));
	}

	public static void addSaplingItem(String name, RegistryObject<Block> sapling, int burnTime) {
		if (burnTime == 0) {
			ITEMS.register(name, () -> new BlockItem(sapling.get(), getItemProperties()));
		}
		else {
			ITEMS.register(name, () -> new fuelBlockItem(sapling.get(), getItemProperties(), burnTime));
		}
	}

	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}

}
