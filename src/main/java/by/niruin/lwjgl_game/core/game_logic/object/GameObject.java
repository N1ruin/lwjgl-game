package by.niruin.lwjgl_game.core.game_logic.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class GameObject {
    protected float x;
    protected float y;
    protected float speed;

    public abstract void update(double deltaTime);
}