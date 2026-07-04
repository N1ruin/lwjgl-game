package by.niruin.lwjgl_game.core.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class KeyboardInputSystem {
    @Getter
    private final KeyBindings keyBindings;
    private final long windowDescriptor;
    private final Map<Integer, Boolean> currentKeys = new HashMap<>();
    private final Map<Integer, Boolean> previousKeys = new HashMap<>();

    public void processInput() {
        previousKeys.clear();
        previousKeys.putAll(currentKeys);

        currentKeys.clear();

        for (Action action : Action.values()) {
            var keyCode = keyBindings.getBind(action);
            if (keyCode != -1) {
                var isDown = GLFW.glfwGetKey(windowDescriptor, keyCode) == GLFW.GLFW_PRESS;
                currentKeys.put(keyCode, isDown);
            }
        }
    }

    public boolean isActionActive(Action action) {
        var keyCode = keyBindings.getBind(action);
        if (keyCode == -1) return false;
        return currentKeys.getOrDefault(keyCode, false);
    }

    public boolean isActionTriggered(Action action) {
        var keyCode = keyBindings.getBind(action);
        if (keyCode == -1) return false;

        var isDown = currentKeys.getOrDefault(keyCode, false);
        var wasDown = previousKeys.getOrDefault(keyCode, false);

        return isDown && !wasDown;
    }

    public boolean isActionReleased(Action action) {
        var keyCode = keyBindings.getBind(action);
        if (keyCode == -1) return false;

        var isDown = currentKeys.getOrDefault(keyCode, false);
        var wasDown = previousKeys.getOrDefault(keyCode, false);

        return !isDown && wasDown;
    }
}
