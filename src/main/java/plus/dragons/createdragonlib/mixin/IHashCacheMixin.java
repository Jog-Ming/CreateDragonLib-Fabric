package plus.dragons.createdragonlib.mixin;

import com.google.common.hash.HashCode;

import java.io.IOException;
import java.nio.file.Path;

public interface IHashCacheMixin {
    @SuppressWarnings("unused")
    default void writeIfNeeded(Path path, byte[] bs, HashCode hashCode) throws IOException {}
}
