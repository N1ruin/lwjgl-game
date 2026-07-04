package by.niruin.lwjgl_game.core.graphic.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class Square extends BaseMesh {
    private final float width;
    private final float height;

    public Square(float width, float height) {
        this.width = width;
        this.height = height;
        this.stride = 2;
    }

    @Override
    protected float[] createVertices() {
        float hw = width / 2f;
        float hh = height / 2f;

        return new float[]{
                -hw, hh,
                hw, hh,
                hw, -hh,
                -hw, -hh
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
    }
}
