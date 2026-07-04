package by.niruin.lwjgl_game.core.graphic.shader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class ShaderLoader {
    public ShaderProgram loadProgram(String vertexPath, String fragmentPath) {
        var vertex = createShader(vertexPath, GL_VERTEX_SHADER);
        vertex.compile();

        var fragment = createShader(fragmentPath, GL_FRAGMENT_SHADER);
        fragment.compile();

        var program = new ShaderProgram(vertex, fragment);
        program.link();

        vertex.delete();
        fragment.delete();

        return program;
    }

    private Shader createShader(String resourcePath, int shaderType) {
        String source = loadResource(resourcePath);
        return new Shader(source, shaderType);
    }

    private String loadResource(String resourcePath) {
        try {
            var url = getClass().getClassLoader().getResource(resourcePath);
            if (url == null) {
                throw new RuntimeException("Shader not found: " + resourcePath);
            }
            return Files.readString(Path.of(url.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to load shader: " + resourcePath, e);
        }
    }
}
