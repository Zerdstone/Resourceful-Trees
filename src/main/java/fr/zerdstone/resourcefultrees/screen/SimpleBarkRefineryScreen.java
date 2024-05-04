package fr.zerdstone.resourcefultrees.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SimpleBarkRefineryScreen extends AbstractContainerScreen<SimpleBarkRefineryMenu> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(ResourcefulTrees.MOD_ID,
			"textures/gui/simple_bark_refinery_gui.png");

	public SimpleBarkRefineryScreen(SimpleBarkRefineryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}

	@Override
	protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;

		this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

		if (menu.isCrafting()) {
			this.blit(pPoseStack, x + 102, y + 41, 176, 0, 8, menu.getScaledProgress());
		}
	}

	@Override
	public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
		renderBackground(pPoseStack);
		super.render(pPoseStack, mouseX, mouseY, delta);
		renderTooltip(pPoseStack, mouseX, mouseY);
	}

}
