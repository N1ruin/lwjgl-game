package by.niruin.lwjgl_game.core.game_logic;

import by.niruin.lwjgl_game.core.input.Action;
import by.niruin.lwjgl_game.core.input.KeyboardInputSystem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameState {
    private int score;
    private boolean isGameOver;

    public void addScore(int points) {
        score += points;
    }

    public void checkRestart(KeyboardInputSystem input) {
        if (isGameOver && input.isActionTriggered(Action.RESTART)) {
            reset();
        }
    }

    public void reset() {
        score = 0;
        isGameOver = false;
    }

    public String getWindowTitle(KeyboardInputSystem input) {
        if (isGameOver) {
            int restartKey = input.getKeyBindings().getBind(Action.RESTART);
            String keyName = getKeyName(restartKey);
            return "Game Over. Press " + keyName + " to restart";
        }
        return "2D Shooter | Score: " + score;
    }

    private String getKeyName(int keyCode) {
        return switch (keyCode) {
            case org.lwjgl.glfw.GLFW.GLFW_KEY_R -> "R";
            case org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER -> "ENTER";
            case org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE -> "SPACE";
            default -> "KEY_" + keyCode;
        };
    }
}
