package by.niruin.lwjgl_game.core.graphic.shader;

import lombok.RequiredArgsConstructor;

import static by.niruin.lwjgl_game.constant.ShaderRegistryKeys.BACKGROUND_SHADER;
import static by.niruin.lwjgl_game.constant.ShaderRegistryKeys.DEFAULT_SHADER;

@RequiredArgsConstructor
public class ShaderInitializer {
    private final ShaderRegistry registry;
    private final ShaderLoader loader;

    public void initialize() {
        var defaultProgram = loader.loadProgram(
                "shaders/default_vertex.vert",
                "shaders/default_frag.frag");

        var textureProgram = loader.loadProgram(
                "shaders/background.vert",
                "shaders/background.frag");
        registry.register(BACKGROUND_SHADER, textureProgram);

        registry.register(DEFAULT_SHADER, defaultProgram);
    }
}
