package by.niruin.lwjgl_game.core.engine;

import by.niruin.lwjgl_game.core.graphic.render.Renderer;
import by.niruin.lwjgl_game.core.graphic.window.Window;
import by.niruin.lwjgl_game.core.input.KeyboardInputSystem;
import by.niruin.lwjgl_game.core.game_logic.Game;
import by.niruin.lwjgl_game.core.input.MouseInputSystem;
import lombok.RequiredArgsConstructor;

import static java.lang.Thread.sleep;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;

@RequiredArgsConstructor
public class GameEngine {
    private final Window window;
    private final Game game;
    private final Renderer renderer;
    private final KeyboardInputSystem keyboardInputSystem;
    private final MouseInputSystem mouseInputSystem;
    private final Timer timer;

    public void start() {
        renderer.init();

        double targetDelta = 0.0;
        boolean isVsyncEnabled = window.getContextSettings().isVsyncEnabled();

        if (!isVsyncEnabled) {
            int targetFps = window.getContextSettings().getTargetFPS();
            if (targetFps > 0) {
                targetDelta = 1.0 / targetFps;
            }
        }

        while (!window.shouldClose()) {
            double deltaTime = timer.getDeltaTime();

            int w = window.getWidth();
            int h = window.getHeight();

            renderer.updateProjection(w, h);
            mouseInputSystem.update(w, h);
            game.updateBounds(w, h);

            keyboardInputSystem.processInput();
            game.update(deltaTime, mouseInputSystem.getWorldX(), mouseInputSystem.getWorldY(), mouseInputSystem);
            renderer.render(game.getWorld(), game.isGameOver());
            glfwSetWindowTitle(window.getDescriptor(), game.getWindowTitle());
            window.update();

            if (!isVsyncEnabled && targetDelta > 0) {
                double elapsed = timer.getElapsedSinceLastDelta();
                double sleepTime = targetDelta - elapsed;
                if (sleepTime > 0) {
                    sleepSeconds(sleepTime);
                }
            }
        }
    }

    private void sleepSeconds(double seconds) {
        try {
            long millis = (long) (seconds * 1000);
            int nanos = (int) ((seconds * 1_000_000_000) % 1_000_000_000);
            sleep(millis, nanos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void destroy() {
        renderer.delete();
        window.destroy();
    }
}
