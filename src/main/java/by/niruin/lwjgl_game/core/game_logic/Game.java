package by.niruin.lwjgl_game.core.game_logic;

import by.niruin.lwjgl_game.core.collision.CollisionSystem;
import by.niruin.lwjgl_game.core.game_logic.object.Enemy;
import by.niruin.lwjgl_game.core.game_logic.object.Player;
import by.niruin.lwjgl_game.core.game_logic.object.Projectile;
import by.niruin.lwjgl_game.core.game_logic.spawner.EnemySpawner;
import by.niruin.lwjgl_game.core.game_logic.world.GameWorld;
import by.niruin.lwjgl_game.core.input.KeyboardInputSystem;
import by.niruin.lwjgl_game.core.input.MouseInputSystem;
import lombok.Getter;

@Getter
public class Game {
    private final KeyboardInputSystem input;
    private final GameWorld world;
    private final GameState state;
    private final CollisionSystem collisionSystem;
    private final EnemySpawner spawner;

    public Game(KeyboardInputSystem input) {
        this.input = input;
        Player player = new Player(0, 0, 0.5f, input);
        this.world = new GameWorld(player);
        this.state = new GameState();
        this.collisionSystem = new CollisionSystem();
        this.spawner = new EnemySpawner(world, 1.5f);
    }

    public void updateBounds(int windowWidth, int windowHeight) {
        world.updateBounds(windowWidth, windowHeight);
    }

    public void update(double deltaTime, float mouseWorldX, float mouseWorldY, MouseInputSystem mouseInput) {
        if (state.isGameOver()) {
            state.checkRestart(input);

            if (!state.isGameOver()) {
                restart();
            }
            return;
        }

        updatePlayer(deltaTime);
        spawner.update((float) deltaTime);
        updateEnemies((float) deltaTime);
        handleShooting(mouseWorldX, mouseWorldY, mouseInput);
        updateProjectiles((float) deltaTime);
        updateEnemyProjectiles((float) deltaTime);
        handleCollisions();
    }

    private void updatePlayer(double deltaTime) {
        var player = world.getPlayer();

        player.update(deltaTime);
        player.setX(world.clampX(player.getX()));
        player.setY(world.clampY(player.getY()));
    }

    private void updateEnemies(float deltaTime) {
        Player player = world.getPlayer();
        for (int i = world.getEnemies().size() - 1; i >= 0; i--) {
            Enemy enemy = world.getEnemies().get(i);
            enemy.update(deltaTime);
            if (world.isOutside(enemy.getX(), enemy.getY(), 0.3f)) {
                world.removeEnemy(enemy);
                continue;
            }
            if (enemy.isReadyToShoot()) {
                float dirX = player.getX() - enemy.getX();
                float dirY = player.getY() - enemy.getY();
                world.addEnemyProjectile(new Projectile(enemy.getX(), enemy.getY(), dirX, dirY, 0.3f));
            }
        }
    }

    private void handleShooting(float mouseWorldX, float mouseWorldY, MouseInputSystem mouseInput) {
        if (mouseInput.isLeftButtonJustPressed()) {
            var player = world.getPlayer();
            float dirX = mouseWorldX - player.getX();
            float dirY = mouseWorldY - player.getY();
            world.addProjectile(new Projectile(player.getX(), player.getY(), dirX, dirY, 0.8f));
        }
    }

    private void updateProjectiles(float deltaTime) {
        for (int i = world.getProjectiles().size() - 1; i >= 0; i--) {
            var projectile = world.getProjectiles().get(i);
            projectile.update(deltaTime);
            if (projectile.isExpired() || world.isOutside(projectile.getX(), projectile.getY(), 0.5f)) {
                world.removeProjectile(projectile);
            }
        }
    }

    private void updateEnemyProjectiles(float deltaTime) {
        for (int i = world.getEnemyProjectiles().size() - 1; i >= 0; i--) {
            var projectile = world.getEnemyProjectiles().get(i);
            projectile.update(deltaTime);
            if (projectile.isExpired() || world.isOutside(projectile.getX(), projectile.getY(), 0.5f)) {
                world.removeEnemyProjectile(projectile);
            }
        }
    }

    private void handleCollisions() {
        int killed = collisionSystem.handleCollisions(world);
        state.addScore(killed);

        if (!world.getPlayer().isAlive()) {
            state.setGameOver(true);
        }
    }

    public void restart() {
        world.getEnemies().clear();
        world.getProjectiles().clear();
        world.getEnemyProjectiles().clear();
        world.getPlayer().setX(0);
        world.getPlayer().setY(0);
        world.getPlayer().setAlive(true);
        state.reset();
        spawner.reset();
    }

    public String getWindowTitle() {
        return state.getWindowTitle(input);
    }

    public boolean isGameOver() {
        return state.isGameOver();
    }
}
