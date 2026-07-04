package by.niruin.lwjgl_game.core.collision;

import by.niruin.lwjgl_game.core.game_logic.object.Enemy;
import by.niruin.lwjgl_game.core.game_logic.object.Player;
import by.niruin.lwjgl_game.core.game_logic.object.Projectile;
import by.niruin.lwjgl_game.core.game_logic.world.GameWorld;

public class CollisionSystem {
    public boolean checkPlayerEnemyCollision(Player player, Enemy enemy) {
        float dx = player.getX() - enemy.getX();
        float dy = player.getY() - enemy.getY();
        float radiusSum = 0.05f + 0.04f;
        return dx * dx + dy * dy < radiusSum * radiusSum;
    }

    public boolean checkPlayerEnemyProjectileCollision(Player player, Projectile projectile) {
        float dx = player.getX() - projectile.getX();
        float dy = player.getY() - projectile.getY();
        float radiusSum = 0.05f + 0.02f;
        return dx * dx + dy * dy < radiusSum * radiusSum;
    }

    public boolean checkProjectileEnemyCollision(Projectile projectile, Enemy enemy) {
        float dx = projectile.getX() - enemy.getX();
        float dy = projectile.getY() - enemy.getY();
        float radiusSum = 0.04f + 0.02f;
        return dx * dx + dy * dy < radiusSum * radiusSum;
    }

    public int handleCollisions(GameWorld world) {
        Player player = world.getPlayer();
        int killed = 0;

        for (int i = world.getEnemies().size() - 1; i >= 0; i--) {
            Enemy enemy = world.getEnemies().get(i);

            if (checkPlayerEnemyCollision(player, enemy)) {
                player.setAlive(false);
                return killed;
            }

            for (int j = world.getProjectiles().size() - 1; j >= 0; j--) {
                Projectile projectile = world.getProjectiles().get(j);
                if (checkProjectileEnemyCollision(projectile, enemy)) {
                    world.removeEnemy(enemy);
                    world.removeProjectile(projectile);
                    killed++;
                    break;
                }
            }
        }

        for (int i = world.getEnemyProjectiles().size() - 1; i >= 0; i--) {
            Projectile projectile = world.getEnemyProjectiles().get(i);
            if (checkPlayerEnemyProjectileCollision(player, projectile)) {
                player.setAlive(false);
                return killed;
            }
        }

        return killed;
    }
}
