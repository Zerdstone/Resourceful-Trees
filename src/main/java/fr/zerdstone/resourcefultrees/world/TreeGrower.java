package fr.zerdstone.resourcefultrees.world;

import fr.zerdstone.resourcefultrees.registry.RegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class TreeGrower extends AbstractTreeGrower {

	public final String name;
	private final Holder<? extends ConfiguredFeature<?, ?>> TREE;

	public TreeGrower(String name, Supplier<Block> otherDirt) {
		this.name = name;
		this.TREE = FeatureUtils.register(name,
										  Feature.TREE,
										  new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(RegistryHandler.LOGS.get(name).get()),
																						 new StraightTrunkPlacer(5, 2, 0),
																						 BlockStateProvider.simple(RegistryHandler.LEAVES.get(name).get()),
																						 new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
																						 new TwoLayersFeatureSize(1,
																												  0,
																												  1)).dirt(BlockStateProvider.simple(otherDirt.get()))
																													 .forceDirt()
																													 .build());

		RegistryHandler.GROWER.put(name, this);
	}

	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull Random p_204307_, boolean p_204308_) {
		return this.TREE;
	}

	private Holder<PlacedFeature> getTreeChecked() {
		return PlacementUtils.register(name.replace("_tree", "_checked"), TREE, PlacementUtils.filteredByBlockSurvival(RegistryHandler.SAPLING.get(name).get()));
	}

	private Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> getTreeSpawn() {
		return FeatureUtils.register(name.replace("_tree", "_spawn"),
									 Feature.RANDOM_SELECTOR,
									 new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(getTreeChecked(), 0.5F)), getTreeChecked()));
	}

	public Holder<PlacedFeature> getTreePlaced() {
		return PlacementUtils.register(name.replace("_tree", "_placed"), getTreeSpawn(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(200)));
	}
}
