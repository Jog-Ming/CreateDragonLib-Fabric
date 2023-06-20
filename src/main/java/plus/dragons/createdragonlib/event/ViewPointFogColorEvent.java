package plus.dragons.createdragonlib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.Nullable;

public class ViewPointFogColorEvent {
    public interface CallBack {
        Event<CallBack> EVENT = EventFactory.createArrayBacked(CallBack.class,
                (listeners) -> (activeRenderInfo, partialTicks, level, renderDistanceChunks, bossColorModifier) -> {
                    for (CallBack listener : listeners) {
                        var result = listener.interact(activeRenderInfo, partialTicks, level, renderDistanceChunks, bossColorModifier);

                        if (result != null) {
                            return result;
                        }
                    }

                    return null;
                });

        @Nullable
        FogColor interact(Camera activeRenderInfo, float partialTicks, ClientLevel level, int renderDistanceChunks, float bossColorModifier);
    }

    public record FogColor(float red, float green, float blue) {
    }
}
