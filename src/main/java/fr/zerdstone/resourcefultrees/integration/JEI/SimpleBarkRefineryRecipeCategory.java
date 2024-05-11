package fr.zerdstone.resourcefultrees.integration.JEI;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import fr.zerdstone.resourcefultrees.common.recipe.SimpleBarkRefineryRecipe;
import fr.zerdstone.resourcefultrees.common.register.ModBlocks;
import fr.zerdstone.resourcefultrees.common.register.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SimpleBarkRefineryRecipeCategory implements IRecipeCategory<SimpleBarkRefineryRecipe> {

	public static final ResourceLocation UID = new ResourceLocation(ResourcefulTrees.MOD_ID, "simple_bark_refinery");
	public static final ResourceLocation TEXTURE = new ResourceLocation(ResourcefulTrees.MOD_ID, "textures/gui/simple_bark_refinery_gui.png");

	private static IDrawable background;
	private static IDrawable icon;

	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlame;
	private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

	public static final int xGuiOffset = 23;
	public static final int yGuiOffset = 12;

	public SimpleBarkRefineryRecipeCategory(IGuiHelper helper) {
		background = helper.createDrawable(TEXTURE, xGuiOffset, yGuiOffset, 157 - xGuiOffset, 68 - yGuiOffset);
		icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SIMPLE_BARK_REFINERY.get()));
		cachedArrows = CacheBuilder.newBuilder()
								   .maximumSize(25)
								   .build(new CacheLoader<>() {
									   @Override
									   public @NotNull IDrawableAnimated load(@NotNull Integer cookTime) {
										   return helper.drawableBuilder(TEXTURE, 176, 0, 44, 46)
														.buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
									   }
								   });
		staticFlame = helper.createDrawable(TEXTURE, 176, 46, 14, 14);
		animatedFlame = helper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
	}

	@Override
	public @NotNull Component getTitle() {
		return new TranslatableComponent("gui.resourcefultrees.simple_bark_refinery");
	}


	public @NotNull IDrawable getBackground() {
		return background;
	}

	@Override
	public @NotNull IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, SimpleBarkRefineryRecipe recipe, @NotNull IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 63 - xGuiOffset, 35 - yGuiOffset).addIngredients(recipe.getIngredients().get(0));

		builder.addSlot(RecipeIngredientRole.OUTPUT, 134 - xGuiOffset, 19 - yGuiOffset).addItemStack(recipe.getResultItem());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 134 - xGuiOffset, 49 - yGuiOffset).addItemStack(new ItemStack(ModItems.TINY_CHARCOAL.get(), 1));
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<? extends SimpleBarkRefineryRecipe> getRecipeClass() {
		return SimpleBarkRefineryRecipe.class;
	}

	@Override
	public void draw(@NotNull SimpleBarkRefineryRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack poseStack, double mouseX, double mouseY) {
		animatedFlame.draw(poseStack, 27 - xGuiOffset, 25 - yGuiOffset);

		IDrawableAnimated arrow = getArrow();
		arrow.draw(poseStack, 82 - xGuiOffset, 20 - yGuiOffset);

		drawCookTime(poseStack);
	}

	protected IDrawableAnimated getArrow() {
		return this.cachedArrows.getUnchecked(200);
	}


	protected void drawCookTime(PoseStack poseStack) {
		int cookTimeSeconds = 200 / 20;
		TranslatableComponent timeString = new TranslatableComponent("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
		Minecraft minecraft = Minecraft.getInstance();
		Font fontRenderer = minecraft.font;
		fontRenderer.draw(poseStack, timeString, 0, 0, 0xFF808080);

	}
}
