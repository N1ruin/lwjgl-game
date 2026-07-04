package by.niruin.lwjgl_game.core.graphic.shader;

import java.util.HashMap;
import java.util.Map;

public class ShaderRegistry {
    private final Map<String, ShaderProgram> shaders = new HashMap<>();

    public void register(String shaderType, ShaderProgram shaderProgram) {
        shaders.put(shaderType, shaderProgram);
    }

    public ShaderProgram getProgram(String shaderType) {
        var shaderProgramId = shaders.get(shaderType);
        if (shaderProgramId == null) {
            throw new RuntimeException("Shader by type %s not found".formatted(shaderType));
        }

        return shaders.get(shaderType);
    }

    public boolean contains(String name) {
        return shaders.containsKey(name);
    }

    public void deleteAll() {
        shaders.values().forEach(ShaderProgram::delete);
        shaders.clear();
    }
}
