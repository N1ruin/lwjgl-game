package by.niruin.lwjgl_game.core.game_logic.spawner;

import by.niruin.lwjgl_game.core.game_logic.object.Enemy;
import by.niruin.lwjgl_game.core.game_logic.world.GameWorld;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnemySpawner {
    private final GameWorld world;
    private float timer;
    private final float interval;

    public void update(float deltaTime) {
        timer += deltaTime;
        if (timer >= interval) {
            timer -= interval;
            spawn();
        }
    }

    private void spawn() {
        float bx = world.getBoundX();
        float by = world.getBoundY();
        float side = (float) Math.random();
        float x, y;

        if (side < 0.25f) {
            x = (float) Math.random() * bx * 2 - bx;
            y = by + 0.3f;
        } else if (side < 0.5f) {
            x = (float) Math.random() * bx * 2 - bx;
            y = -by - 0.3f;
        } else if (side < 0.75f) {
            x = -bx - 0.3f;
            y = (float) Math.random() * by * 2 - by;
        } else {
            x = bx + 0.3f;
            y = (float) Math.random() * by * 2 - by;
        }

        world.addEnemy(new Enemy(x, y, world.getPlayer().getX(), world.getPlayer().getY(), 0.2f));
    }

    public void reset() {
        timer = 0;
    }
}
