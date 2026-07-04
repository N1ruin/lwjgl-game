package by.niruin.lwjgl_game.core.graphic.mesh;

import lombok.Getter;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

@Getter
public abstract class BaseMesh implements Mesh{
    protected int vao;
    protected int vbo;
    protected int ebo;
    protected int indexCount;
    protected int stride;

    @Override
    public void init() {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        ebo = glGenBuffers();

        float[] vertices = createVertices();
        int[] indices = createIndices();
        indexCount = indices.length;

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        setupAttributes();

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    protected abstract float[] createVertices();
    protected abstract int[] createIndices();
    protected abstract void setupAttributes();

    @Override
    public void bind() {
        glBindVertexArray(vao);
    }

    @Override
    public void unbind() {
        glBindVertexArray(0);
    }

    @Override
    public int getIndexCount() {
        return indexCount;
    }

    @Override
    public void delete() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);
    }
}
