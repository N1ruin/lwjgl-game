package by.niruin.lwjgl_game.core.graphic.render.matrix;

import lombok.Getter;
import org.joml.Matrix4f;

@Getter
public class Camera {
    private final Matrix4f projectionMatrix = new Matrix4f();
    private final Matrix4f viewMatrix = new Matrix4f();
    private final Matrix4f projectionViewMatrix = new Matrix4f();

    public void updateProjection(int width, int height) {
        float aspect = (float) width / height;
        projectionMatrix.identity().ortho(-aspect, aspect, -1f, 1f, -1f, 1f);
        updateCombined();
    }

    public void updateView(float x, float y) {
        viewMatrix.identity().translate(-x, -y, 0);
        updateCombined();
    }

    private void updateCombined() {
        projectionViewMatrix.set(projectionMatrix).mul(viewMatrix);
    }
}
