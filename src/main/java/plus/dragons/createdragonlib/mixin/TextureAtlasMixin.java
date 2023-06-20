package plus.dragons.createdragonlib.mixin;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import plus.dragons.createdragonlib.event.TextureStitchPreEvent;

import java.util.Set;

@Mixin(TextureAtlas.class)
public abstract class TextureAtlasMixin extends AbstractTexture implements Tickable {
    @ModifyVariable(method = "prepareToStitch", at = @At("STORE"), ordinal = 0)
    private Set<ResourceLocation> textureStitch(Set<ResourceLocation> set) {
        TextureStitchPreEvent
                .CallBack
                .EVENT
                .invoker()
                .interact(((TextureAtlas) (Object) this).location(), set);
        return set;
    }
}
