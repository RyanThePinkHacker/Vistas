package net.ludocrypt.vistas.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.vistas.Vistas.Panorama;
import net.ludocrypt.vistas.config.PanoramaConfig;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.TitleScreen;

@Environment(EnvType.CLIENT)
@Mixin(TitleScreen.class)
public abstract class TitleScreenBackgroundMixin {

	@Shadow
	private RotatingCubeMapRenderer backgroundRenderer;

	@Inject(method = "init", at = @At("TAIL"))
	private void VISTAS_initPanoramaChange(CallbackInfo ci) {
		if (PanoramaConfig.INSTANCE().randomPerScreen) {
			Panorama.setRandomPanorama();
		}
		updateScreen();
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void VISTAS_tickPanoramaChange(CallbackInfo ci) {

		if (PanoramaConfig.INSTANCE().hectic) {
			Panorama.setRandomPanorama();

			updateScreen();
		}
	}

	private void updateScreen() {
		if (Panorama.getPanorama() != null) {
			this.backgroundRenderer = new RotatingCubeMapRenderer(new CubeMapRenderer(Panorama.getPanorama().getId()));
		} else {
			this.backgroundRenderer = new RotatingCubeMapRenderer(TitleScreen.PANORAMA_CUBE_MAP);
		}
	}

}