package by.niruin.lwjgl_game.core.game_logic.world;

import by.niruin.lwjgl_game.core.game_logic.object.Enemy;
import by.niruin.lwjgl_game.core.game_logic.object.Player;
import by.niruin.lwjgl_game.core.game_logic.object.Projectile;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameWorld {
    private float boundX;
    private float boundY;
    private final Player player;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();
    private final List<Projectile> enemyProjectiles = new ArrayList<>();

    public GameWorld(Player player) {
        this.player = player;
    }

    public void updateBounds(int windowWidth, int windowHeight) {
        float aspect = (float) windowWidth / windowHeight;
        boundX = aspect;
        boundY = 1f;
    }

    public boolean isInside(float x, float y) {
        return x >= -boundX && x <= boundX && y >= -boundY && y <= boundY;
    }

    public boolean isOutside(float x, float y, float margin) {
        return x < -boundX - margin || x > boundX + margin ||
               y < -boundY - margin || y > boundY + margin;
    }

    public float clampX(float x) {
        return Math.max(-boundX, Math.min(boundX, x));
    }

    public float clampY(float y) {
        return Math.max(-boundY, Math.min(boundY, y));
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile);
    }

    public void addEnemyProjectile(Projectile projectile) {
        enemyProjectiles.add(projectile);
    }

    public void removeEnemyProjectile(Projectile projectile) {
        enemyProjectiles.remove(projectile);
    }
}
