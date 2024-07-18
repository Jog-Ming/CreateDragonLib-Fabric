package plus.dragons.createdragonlib.mixin;

import com.google.common.hash.HashCode;
import net.minecraft.data.HashCache;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Mixin(HashCache.class)
public class HashCacheMixin implements IHashCacheMixin {
    @Unique
    int writes;

    @Shadow @Final private Map<Path, String> newCache;

    @Shadow @Final private Map<Path, String> oldCache;

    @Unique
    private boolean shouldWrite(Path path, HashCode hashCode) {
        return !Objects.equals(this.oldCache.get(path), String.valueOf(hashCode)) || !Files.exists(path);
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void writeIfNeeded(Path path, byte[] bs, HashCode hashCode) throws IOException {
        if (this.shouldWrite(path, hashCode)) {
            ++this.writes;
            Files.createDirectories(path.getParent());
            Files.write(path, bs);
        }

        this.newCache.put(path, String.valueOf(hashCode));
    }
}
