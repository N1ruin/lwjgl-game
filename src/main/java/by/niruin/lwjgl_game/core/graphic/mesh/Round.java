package by.niruin.lwjgl_game.core.graphic.mesh;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class Round extends BaseMesh {
    private final float radius;
    private final int segments;

    public Round(float radius, int segments) {
        this.radius = radius;
        this.segments = segments;
        this.stride = 2;
    }

    @Override
    protected float[] createVertices() {
        float[] vertices = new float[(segments + 1) * 2];
        vertices[0] = 0f;
        vertices[1] = 0f;

        double angleStep = 2.0 * Math.PI / segments;
        for (int i = 0; i < segments; i++) {
            double angle = i * angleStep;
            int idx = (i + 1) * 2;
            vertices[idx] = (float) (radius * Math.cos(angle));
            vertices[idx + 1] = (float) (radius * Math.sin(angle));
        }

        return vertices;
    }

    @Override
    protected int[] createIndices() {
        int[] indices = new int[segments * 3];
        for (int i = 0; i < segments; i++) {
            int idx = i * 3;
            indices[idx] = 0;
            indices[idx + 1] = i + 1;
            indices[idx + 2] = (i + 1) % segments + 1;
        }
        return indices;
    }

    @Override
    protected void setupAttributes() {
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
    }
}
