package by.niruin.lwjgl_game.core.graphic.shader;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL20.*;

public class Uniform {
    public static void setMat4(int programId, String name, Matrix4f matrix) {
        int location = glGetUniformLocation(programId, name);
        try (var stack = MemoryStack.stackPush()) {
            var buffer = stack.mallocFloat(16);
            matrix.get(buffer);
            glUniformMatrix4fv(location, false, buffer);
        }
    }

    public static void setVec4(int programId, String name, float r, float g, float b, float a) {
        int location = glGetUniformLocation(programId, name);
        glUniform4f(location, r, g, b, a);
    }

    public static void setInt(int programId, String name, int value) {
        int location = glGetUniformLocation(programId, name);
        glUniform1i(location, value);
    }
}
