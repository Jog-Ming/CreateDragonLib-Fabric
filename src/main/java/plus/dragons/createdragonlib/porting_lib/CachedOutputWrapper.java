package plus.dragons.createdragonlib.porting_lib;

import com.google.common.hash.HashCode;
import net.minecraft.data.HashCache;
import plus.dragons.createdragonlib.mixin.HashCacheAccessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class CachedOutputWrapper {
    private final HashCache self;

    public CachedOutputWrapper(HashCache self) {
        this.self = self;
    }

    private boolean shouldWrite(Path path, HashCode hashCode) {
        return !Objects.equals(((HashCacheAccessor) self).getOldCache().get(path), String.valueOf(hashCode)) || !Files.exists(path);
    }

    public void writeIfNeeded(Path path, byte[] bs, HashCode hashCode) throws IOException {
        if (this.shouldWrite(path, hashCode)) {
            Files.createDirectories(path.getParent());
            Files.write(path, bs);
        }

        ((HashCacheAccessor) self).getNewCache().put(path, String.valueOf(hashCode));
    }
}
