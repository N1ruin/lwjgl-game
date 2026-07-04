package by.niruin.lwjgl_game.loader;

import by.niruin.lwjgl_game.core.configuration.OpenGLContextSettings;
import by.niruin.lwjgl_game.core.configuration.WindowSettings;
import by.niruin.lwjgl_game.core.input.Action;
import by.niruin.lwjgl_game.core.input.KeyBindings;

import java.io.IOException;
import java.util.Properties;

import static org.lwjgl.glfw.GLFW.*;

public class SettingsLoader {
    private final Properties properties;

    public SettingsLoader() {
        properties = new Properties();

        try (var inputStream = getClass().getClassLoader().getResourceAsStream("settings.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.err.println("settings.properties not found, using defaults");
            }
        } catch (IOException e) {
            System.err.println("Error loading settings: " + e.getMessage());
        }
    }

    public OpenGLContextSettings loadOpenGlContext() {
        return OpenGLContextSettings.builder()
                .isVsyncEnabled(Boolean.parseBoolean(properties.getProperty("opengl.vsync", "true")))
                .targetFPS(Integer.parseInt(properties.getProperty("opengl.targetFPS", "60")))
                .build();
    }

    public WindowSettings loadWindowSettings() {
        return WindowSettings.builder()
                .width(Integer.parseInt(properties.getProperty("window.width", "800")))
                .height(Integer.parseInt(properties.getProperty("window.height", "600")))
                .name(properties.getProperty("window.name", "Game"))
                .isFullscreen(Boolean.parseBoolean(properties.getProperty("window.fullscreen", "false")))
                .isResizable(Boolean.parseBoolean(properties.getProperty("window.resizable", "true")))
                .build();
    }

    public KeyBindings loadKeyBindings() {
        KeyBindings bindings = new KeyBindings();

        bindings.putBind(Action.MOVE_UP, parseKey(properties.getProperty("keybind.move_up")));
        bindings.putBind(Action.MOVE_DOWN, parseKey(properties.getProperty("keybind.move_down")));
        bindings.putBind(Action.MOVE_LEFT, parseKey(properties.getProperty("keybind.move_left")));
        bindings.putBind(Action.MOVE_RIGHT, parseKey(properties.getProperty("keybind.move_right")));
        bindings.putBind(Action.SHOOT, parseKey(properties.getProperty("keybind.shoot")));
        bindings.putBind(Action.PAUSE, parseKey(properties.getProperty("keybind.pause")));
        bindings.putBind(Action.RESTART, parseKey(properties.getProperty("keybind.restart")));

        return bindings;
    }

    private int parseKey(String keyName) {
        if (keyName == null) return -1;

        return switch (keyName.toUpperCase()) {
            case "W" -> GLFW_KEY_W;
            case "A" -> GLFW_KEY_A;
            case "S" -> GLFW_KEY_S;
            case "D" -> GLFW_KEY_D;
            case "SPACE" -> GLFW_KEY_SPACE;
            case "ESCAPE" -> GLFW_KEY_ESCAPE;
            case "ENTER" -> GLFW_KEY_ENTER;
            case "LEFT_SHIFT" -> GLFW_KEY_LEFT_SHIFT;
            case "TAB" -> GLFW_KEY_TAB;
            case "R" -> GLFW_KEY_R;
            default -> throw new RuntimeException("GLFW key parse error");
        };
    }
}
