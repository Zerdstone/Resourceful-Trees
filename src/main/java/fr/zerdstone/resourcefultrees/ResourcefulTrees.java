package fr.zerdstone.resourcefultrees;

import fr.zerdstone.resourcefultrees.client.gui.SimpleBarkRefineryScreen;
import fr.zerdstone.resourcefultrees.common.config.CommonConfig;
import fr.zerdstone.resourcefultrees.common.config.ConfigLoader;
import fr.zerdstone.resourcefultrees.common.init.ModSetup;
import fr.zerdstone.resourcefultrees.common.init.TreeSetup;
import fr.zerdstone.resourcefultrees.common.inventory.ModMenuTypes;
import fr.zerdstone.resourcefultrees.common.register.ModBlocks;
import fr.zerdstone.resourcefultrees.common.register.RegistryHandler;
import fr.zerdstone.resourcefultrees.datagen.DataGen;
import fr.zerdstone.resourcefultrees.datagen.dataPack.AssetsPackLoader;
import fr.zerdstone.resourcefultrees.datagen.dataPack.DataPackLoader;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ResourcefulTrees.MOD_ID)
public class ResourcefulTrees {
	public static final String MOD_ID = "resourcefultrees";
	public static final Logger LOGGER = LogManager.getLogger();

	public ResourcefulTrees() {
		IEventBus eventbus = FMLJavaModLoadingContext.get().getModEventBus();

		ModSetup.initialize();
		RegistryHandler.init();

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG, "resourcefulTrees/common.toml");
		ConfigLoader.load(CommonConfig.COMMON_CONFIG, "resourcefulTrees/common.toml");

		TreeSetup.setupTrees();
		RegistryHandler.registerDynamicTrees();
		DataGen.generateCommonData();

		eventbus.addListener(this::onPackFinders);
		eventbus.addListener(this::clientSetup);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		RegistryHandler.SAPLING.forEach((name, sapling) -> ItemBlockRenderTypes.setRenderLayer(sapling.get(), RenderType.cutout()));

		RegistryHandler.LEAVES.forEach((name, leaf) -> ItemBlockRenderTypes.setRenderLayer(leaf.get(), RenderType.cutout()));

		ItemBlockRenderTypes.setRenderLayer(ModBlocks.SIMPLE_BARK_REFINERY.get(), RenderType.cutout());

		MenuScreens.register(ModMenuTypes.SIMPLE_BARK_REFINERY_MENU.get(), SimpleBarkRefineryScreen::new);
	}

	@SubscribeEvent
	public void onPackFinders(AddPackFindersEvent event) {
		switch (event.getPackType()) {
			case SERVER_DATA -> event.addRepositorySource(DataPackLoader.INSTANCE);
			case CLIENT_RESOURCES -> event.addRepositorySource(AssetsPackLoader.INSTANCE);
		}
	}
}
