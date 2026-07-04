package by.niruin.lwjgl_game.core.graphic.shader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.lwjgl.opengl.GL20.*;

@RequiredArgsConstructor
public class Shader {
    private final String source;
    @Getter
    private int id;
    private final int shaderType;


    public void compile() {
        id = glCreateShader(shaderType);
        glShaderSource(id, source);
        glCompileShader(id);

        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            var log = glGetShaderInfoLog(id);
            glDeleteShader(id);

            throw new RuntimeException("Shader compilation failed:\n" + log);
        }
    }

    public void delete() {
        glDeleteShader(id);
    }
}
