package by.niruin.lwjgl_game.core.texture;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TextureInitializer {
    private final TextureRegistry registry;

    public void initialize() {
        var background = new Texture("textures/background.png");
        background.load();
        registry.register("background", background);
    }
}
