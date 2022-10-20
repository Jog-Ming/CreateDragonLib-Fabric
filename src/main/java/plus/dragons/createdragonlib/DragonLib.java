package plus.dragons.createdragonlib;

import net.fabricmc.api.ModInitializer;
import plus.dragons.createdragonlib.api.event.FluidLavaInteractionRegisterEvent;
import plus.dragons.createdragonlib.foundation.data.advancement.ModTriggerFactory;


public class DragonLib implements ModInitializer {
    public static final String MOD_ID = "create_dragon_lib";

    @Override
    public void onInitialize() {

        ModTriggerFactory.register();
        new FluidLavaInteractionRegisterEvent();

    }
}
