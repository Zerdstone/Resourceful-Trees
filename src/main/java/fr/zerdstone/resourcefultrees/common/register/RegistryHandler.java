package fr.zerdstone.resourcefultrees.common.register;

import fr.zerdstone.resourcefultrees.common.config.CommonConfig;
import fr.zerdstone.resourcefultrees.common.inventory.CreativeTab;
import fr.zerdstone.resourcefultrees.common.inventory.ModMenuTypes;
import fr.zerdstone.resourcefultrees.common.world.TreeGrower;
import fr.zerdstone.resourcefultrees.datagen.TreeData;
import fr.zerdstone.resourcefultrees.utils.ModConstants;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class RegistryHandler {
	private RegistryHandler() {
		throw new IllegalAccessError(ModConstants.UTILITY_CLASS);
	}

	public static Map<String, RegistryObject<Block>> LOGS = new HashMap<>();
	public static Map<String, RegistryObject<Block>> LEAVES = new HashMap<>();
	public static Map<String, RegistryObject<Block>> SAPLING = new HashMap<>();
	public static Map<String, RegistryObject<Item>> BARK = new HashMap<>();
	public static Map<String, TreeGrower> GROWER = new HashMap<>();

	public static void init() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModItems.register(bus);

		ModBlocks.initializeRegistries(bus);

		ModBlockEntities.register(bus);
		ModMenuTypes.register(bus);

		ModRecipe.register(bus);
	}

	public static void registerDynamicTrees() {
		ModTrees.getRegistry().getTrees().forEach(RegistryHandler::registerTree);
		if (CommonConfig.GENERATE_RAINBOW_TREES.get()) {
			ModItems.ITEMS.register("rainbow_sap",
									() -> new Item(new Item.Properties().tab(CreativeTab.RESOURCEFULTREES_TAB)));
		}
		if (CommonConfig.GENERATE_ENDER_TREES.get()) {
			ModItems.ITEMS.register("ender_pearl_part",
									() -> new Item(new Item.Properties().tab(CreativeTab.RESOURCEFULTREES_TAB)));
		}
		if (CommonConfig.GENERATE_WITHER_TREES.get()) {
			ModItems.ITEMS.register("nether_star_part",
									() -> new Item(new Item.Properties().tab(CreativeTab.RESOURCEFULTREES_TAB)));
		}
	}

	private static void registerTree(String name, TreeData treeData) {
		registerBark(name);
		registerLog(name, treeData);
		registerLeaf(name);
		registerSapling(name, treeData);
	}

	private static void registerBark(String name) {
		String barkName = name.replace("_tree", "_bark");
		BARK.put(name, ModItems.addBark(barkName));
	}

	private static void registerLog(String name, TreeData treeData) {
		String logName = name.replace("_tree", "_log");
		LOGS.put(name, ModBlocks.addLog(logName, treeData.useChiselOnLog, treeData.logBurnTime));
	}

	private static void registerLeaf(String name) {
		String leafName = name.replace("_tree", "_leaves");
		LEAVES.put(name, ModBlocks.addLeaves(leafName));
	}

	private static void registerSapling(String name, TreeData treeData) {
		String saplingName = name.replace("_tree", "_sapling");
		SAPLING.put(name, ModBlocks.addSapling(saplingName, treeData.saplingBurnTime, treeData.growOn));
	}
}
