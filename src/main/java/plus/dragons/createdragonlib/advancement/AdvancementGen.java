package plus.dragons.createdragonlib.advancement;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

@ApiStatus.Internal
class AdvancementGen implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final String name;
    private final String modid;
    DataGenerator generator;

    AdvancementGen(String name, String modid) {
        this.name = name;
        this.modid = modid;
    }

    @Override
    public void run(HashCache cache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = advancement -> {
            if (!set.add(advancement.getId()))
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            Path advancementPath = path.resolve("data/"
                    + advancement.getId().getNamespace() + "/advancements/"
                    + advancement.getId().getPath() + ".json"
            );
            try {
                DataProvider.save(GSON, cache, advancement.deconstruct().serializeToJson(), advancementPath);
            } catch (IOException ioexception) {
                LOGGER.error("Couldn't save advancement {}", advancementPath, ioexception);
            }
        };
        var advancements = AdvancementHolder.ENTRIES_MAP.get(modid);
        if (advancements != null)
            for (var advancement : advancements) {
                advancement.save(consumer);
            }
    }

    @Override
    public String getName() {
        return name + " Advancements";
    }

}
