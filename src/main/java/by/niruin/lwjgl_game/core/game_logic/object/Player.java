package by.niruin.lwjgl_game.core.game_logic.object;

import by.niruin.lwjgl_game.core.input.Action;
import by.niruin.lwjgl_game.core.input.KeyboardInputSystem;
import lombok.Getter;
import lombok.Setter;

public class Player extends GameObject {
    private final KeyboardInputSystem input;
    @Setter
    @Getter
    private boolean alive = true;

    public Player(float x, float y, float speed, KeyboardInputSystem input) {
        super(x, y, speed);
        this.input = input;
    }

    @Override
    public void update(double deltaTime) {
        float velocityX = 0;
        float velocityY = 0;

        if (input.isActionActive(Action.MOVE_UP))    velocityY = 1;
        if (input.isActionActive(Action.MOVE_DOWN))  velocityY = -1;
        if (input.isActionActive(Action.MOVE_LEFT))  velocityX = -1;
        if (input.isActionActive(Action.MOVE_RIGHT)) velocityX = 1;

        float length = (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (length > 0) {
            velocityX = velocityX / length;
            velocityY = velocityY / length;
        }

        x += velocityX * speed * (float) deltaTime;
        y += velocityY * speed * (float) deltaTime;
    }

    private void clampPosition() {
        float aspect = 1f;
        if (x < -aspect) x = -aspect;
        if (x > aspect) x = aspect;
        if (y < -1f) y = -1f;
        if (y > 1f) y = 1f;
    }
}
