package by.niruin.lwjgl_game.core.graphic.mesh;

public interface Mesh {
    void init();

    void bind();

    void unbind();

    int getIndexCount();

    void delete();
}