package plus.dragons.createdragonlib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import plus.dragons.createdragonlib.tag.TagGen;

public class DragonLib implements ModInitializer {

    public static final String MOD_ID = "create_dragon_lib";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info(
                "Create: Dragon Lib " +
                        FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getName() +
                        " has initialized, ready to support your Create add-ons!"
        );
    }

    public void datagen(final FabricDataGenerator datagen) {
        TagGen.genAll();
    }
}
