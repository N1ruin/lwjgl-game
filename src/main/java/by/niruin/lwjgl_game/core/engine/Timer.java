package by.niruin.lwjgl_game.core.engine;

import lombok.Getter;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {
    @Getter
    private double lastTime;

    public Timer() {
        lastTime = glfwGetTime();
    }

    public double getDeltaTime() {
        var currentTime = glfwGetTime();
        var deltaTime = currentTime - lastTime;
        lastTime = currentTime;

        return deltaTime;
    }

    public double getElapsedSinceLastDelta() {
        return glfwGetTime() - lastTime;
    }
}
