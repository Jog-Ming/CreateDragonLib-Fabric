package plus.dragons.createdragonlib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class TextureStitchPreEvent {

    public interface CallBack {
        Event<CallBack> EVENT = EventFactory.createArrayBacked(CallBack.class,
                (listeners) -> (itemGroup, items) -> {
                    for (CallBack listener : listeners) {
                        listener.interact(itemGroup, items);
                    }
                });

        void interact(ResourceLocation location, Set<ResourceLocation> sprites);
    }
}
