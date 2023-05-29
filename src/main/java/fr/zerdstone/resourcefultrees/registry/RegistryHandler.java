package fr.zerdstone.resourcefultrees.registry;

import fr.zerdstone.resourcefultrees.config.CommonConfig;
import fr.zerdstone.resourcefultrees.data.TreeData;
import fr.zerdstone.resourcefultrees.gui.CreativeTab;
import fr.zerdstone.resourcefultrees.registry.minecraft.ModBlocks;
import fr.zerdstone.resourcefultrees.registry.minecraft.ModItems;
import fr.zerdstone.resourcefultrees.registry.resourcefulTrees.TreeRegistry;
import fr.zerdstone.resourcefultrees.utils.ModConstants;
import fr.zerdstone.resourcefultrees.world.TreeGrower;
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
		ModItems.initializeRegistries(bus);
		ModBlocks.initializeRegistries(bus);
	}

	public static void registerDynamicTrees() {
		TreeRegistry.getRegistry().getTrees().forEach(RegistryHandler::registerTree);
		if(Boolean.TRUE.equals(CommonConfig.GENERATE_RGB_TREES.get())) {
			ModItems.ITEMS.register("rgb_sap", () -> new Item(new Item.Properties().tab(CreativeTab.RESOURCEFULTREES_TAB)));
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
		LOGS.put(name, ModBlocks.addLog(logName, treeData.log.useChiselInWorld, treeData.log.burnTime));
	}

	private static void registerLeaf(String name) {
		String leafName = name.replace("_tree", "_leaves");
		LEAVES.put(name, ModBlocks.addLeaves(leafName));
	}

	private static void registerSapling(String name, TreeData treeData) {
		String saplingName = name.replace("_tree", "_sapling");
		SAPLING.put(name, ModBlocks.addSapling(saplingName, treeData.sapling.burnTime, treeData.growOn));
	}
}
