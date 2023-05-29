package fr.zerdstone.resourcefultrees.world;

import fr.zerdstone.resourcefultrees.registry.RegistryHandler;
import fr.zerdstone.resourcefultrees.registry.resourcefulTrees.TreeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;
import java.util.Objects;

public class TreeGeneration {
	public static void generateTrees(final BiomeLoadingEvent event) {
		ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, Objects.requireNonNull(event.getName()));

		TreeRegistry.getRegistry().getTrees().forEach((name, treeData) -> {
			if(treeData.worldGeneration.generateInWorld == Boolean.TRUE) {
				for(String biome : treeData.worldGeneration.biomeList) {
					if(key.location().toString().equals(biome)) {
						List<Holder<PlacedFeature>> base = event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION);
						base.add(RegistryHandler.GROWER.get(name).getTreePlaced());
					}
				}
			}
		});
	}
}
