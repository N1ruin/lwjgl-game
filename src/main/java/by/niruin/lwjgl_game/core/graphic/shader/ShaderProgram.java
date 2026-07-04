package by.niruin.lwjgl_game.core.graphic.shader;

import lombok.Getter;

import static org.lwjgl.opengl.GL20.*;

@Getter
public class ShaderProgram {
    private final Shader vertexShader;
    private final Shader fragmentShader;
    private int programId;

    public ShaderProgram(Shader vertexShader, Shader fragmentShader) {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }

    public void link() {
        programId = glCreateProgram();
        glAttachShader(programId, vertexShader.getId());
        glAttachShader(programId, fragmentShader.getId());
        glLinkProgram(programId);

        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            String log = glGetProgramInfoLog(programId);
            glDeleteProgram(programId);
            throw new RuntimeException("Shader program linking failed:\n" + log);
        }
    }

    public void use() {
        glUseProgram(programId);
    }

    public void delete() {
        glDeleteProgram(programId);
    }
}
