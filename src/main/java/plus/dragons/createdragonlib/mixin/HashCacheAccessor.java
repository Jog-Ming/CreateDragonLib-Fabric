package plus.dragons.createdragonlib.mixin;

import net.minecraft.data.HashCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.nio.file.Path;
import java.util.Map;

@Mixin(HashCache.class)
public interface HashCacheAccessor {
    @Accessor
    Map<Path, String> getOldCache();

    @Accessor
    Map<Path, String> getNewCache();
}
