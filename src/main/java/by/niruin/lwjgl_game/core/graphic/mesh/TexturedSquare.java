package by.niruin.lwjgl_game.core.graphic.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class TexturedSquare extends BaseMesh {
    public TexturedSquare() {
        this.stride = 4;
    }

    @Override
    protected float[] createVertices() {
        return new float[]{
                -1f, 1f, 0f, 1f,
                1f, 1f, 1f, 1f,
                1f, -1f, 1f, 0f,
                -1f, -1f, 0f, 0f
        };
    }

    @Override
    protected int[] createIndices() {
        return new int[]{
                0, 1, 2,
                0, 2, 3
        };
    }

    @Override
    protected void setupAttributes() {
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, stride * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);
    }
}
