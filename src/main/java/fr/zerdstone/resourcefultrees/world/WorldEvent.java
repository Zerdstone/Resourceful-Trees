package fr.zerdstone.resourcefultrees.world;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ResourcefulTrees.MOD_ID)
public class WorldEvent {
	@SubscribeEvent
	public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
		TreeGeneration.generateTrees(event);
	}
}
