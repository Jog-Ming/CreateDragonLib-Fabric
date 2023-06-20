package plus.dragons.createdragonlib.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import plus.dragons.createdragonlib.event.ViewPointFogColorEvent;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
    @ModifyArgs(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V"))
    private static void inkRender(Args args, Camera activeRenderInfo, float partialTicks, ClientLevel level, int renderDistanceChunks, float bossColorModifier) {
        if (activeRenderInfo.getFluidInCamera() == FogType.POWDER_SNOW)
            return;
        var result = ViewPointFogColorEvent
                .CallBack
                .EVENT
                .invoker()
                .interact(activeRenderInfo, partialTicks, level, renderDistanceChunks, bossColorModifier);
        if (result != null) {
            args.set(0, result.red());
            args.set(1, result.blue());
            args.set(2, result.green());
        }
    }
}
