package fr.zerdstone.resourcefultrees.screen;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS,
			ResourcefulTrees.MOD_ID);

	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(
			IContainerFactory<T> factory, String name) {
		return MENUS.register(name, () -> IForgeMenuType.create(factory));
	}

	public static final RegistryObject<MenuType<SimpleBarkRefineryMenu>> SIMPLE_BARK_REFINERY_MENU = registerMenuType(
			SimpleBarkRefineryMenu::new,
			"simple_bark_refinery_menu");

	public static void register(IEventBus bus) {
		MENUS.register(bus);
	}
}
