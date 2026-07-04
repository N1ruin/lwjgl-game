package by.niruin.lwjgl_game.core.texture;

import java.util.HashMap;
import java.util.Map;

public class TextureRegistry {
    private final Map<String, Texture> textures = new HashMap<>();

    public void register(String name, Texture texture) {
        textures.put(name, texture);
    }

    public Texture get(String name) {
        Texture texture = textures.get(name);
        if (texture == null) {
            throw new RuntimeException("Texture '" + name + "' not found");
        }
        return texture;
    }

    public void deleteAll() {
        textures.values().forEach(Texture::delete);
        textures.clear();
    }
}
