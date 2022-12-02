package plus.dragons.createdragonlib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonLib implements ModInitializer {

  public static final String MOD_ID = "create_dragon_lib";
  private static final Logger LOGGER = LogManager.getLogger();

  @Override
  public void onInitialize() {
//    ModTriggerFactory.register();
//    new FluidLavaInteractionRegisterEvent();
    LOGGER.info(
      "Create: Dragon Lib " +
      FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getName() +
      " has initialized, ready to support your Create add-ons!"
    );
  }
}
