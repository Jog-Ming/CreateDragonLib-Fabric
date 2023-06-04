# Depend on it
```
repositories {
    maven {
        // Maven of dragons.plus
        url "https://maven.dragons.plus/releases"
    }
}

dependencies {
    implementation fg.deobf("plus.dragons.createdragonlib:create_dragon_lib-fabric-${minecraft_version}:${create_dragon_lib_version}")
}
```
