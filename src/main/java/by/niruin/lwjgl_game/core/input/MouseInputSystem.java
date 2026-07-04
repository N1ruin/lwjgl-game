package by.niruin.lwjgl_game.core.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.lwjgl.glfw.GLFW.*;

@RequiredArgsConstructor
public class MouseInputSystem {
    private final long windowDescriptor;
    @Getter
    private float x;
    @Getter
    private float y;
    @Getter
    private float worldX;
    @Getter
    private float worldY;
    @Getter
    private boolean leftButtonPressed;
    @Getter
    private boolean leftButtonJustPressed;
    @Getter
    private boolean leftButtonWasPressed;

    public void update(int windowWidth, int windowHeight) {
        double[] posX = new double[1];
        double[] posY = new double[1];
        glfwGetCursorPos(windowDescriptor, posX, posY);

        x = (float) posX[0];
        y = (float) posY[0];

        float aspect = (float) windowWidth / windowHeight;
        worldX = (x / windowWidth * 2f - 1f) * aspect;
        worldY = 1f - y / windowHeight * 2f;

        leftButtonWasPressed = leftButtonPressed;
        leftButtonPressed = glfwGetMouseButton(windowDescriptor, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS;
        leftButtonJustPressed = leftButtonPressed && !leftButtonWasPressed;
    }
}
