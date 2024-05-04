package fr.zerdstone.resourcefultrees.registry.minecraft;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import fr.zerdstone.resourcefultrees.custom.blocks.entity.SimpleBarkRefineryBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITIES, ResourcefulTrees.MOD_ID);

	public static final RegistryObject<BlockEntityType<SimpleBarkRefineryBlockEntity>> SIMPLE_BARK_REFINERY_BLOCK_ENTITY = BLOCK_ENTITIES
			.register(
					"simple_bark_refinery_block_entity",
					() -> BlockEntityType.Builder.of(SimpleBarkRefineryBlockEntity::new, ModBlocks.SIMPLE_BARK_REFINERY.get())
							.build(null));

	public static void register(IEventBus bus) {
		BLOCK_ENTITIES.register(bus);
	}

}
