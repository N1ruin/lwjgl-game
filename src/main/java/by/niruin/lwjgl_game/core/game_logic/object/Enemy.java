package by.niruin.lwjgl_game.core.game_logic.object;

public class Enemy extends GameObject {
    private final float velocityX;
    private final float velocityY;
    private float shootTimer;
    private final float shootInterval;
    private boolean canShoot;

    public Enemy(float x, float y, float targetX, float targetY, float speed) {
        super(x, y, speed);

        float dx = targetX - x;
        float dy = targetY - y;
        float length = (float) Math.sqrt(dx * dx + dy * dy);

        velocityX = dx / length;
        velocityY = dy / length;
        shootInterval = 1.5f + (float) Math.random() * 1.5f;
        shootTimer = (float) Math.random() * shootInterval;
        canShoot = true;
    }

    @Override
    public void update(double deltaTime) {
        x += velocityX * speed * (float) deltaTime;
        y += velocityY * speed * (float) deltaTime;

        if (canShoot) {
            shootTimer -= (float) deltaTime;
        }
    }

    public boolean isReadyToShoot() {
        if (canShoot && shootTimer <= 0) {
            shootTimer = shootInterval;
            return true;
        }
        return false;
    }

    public void disableShooting() {
        canShoot = false;
    }
}