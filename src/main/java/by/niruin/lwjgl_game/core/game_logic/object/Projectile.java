package by.niruin.lwjgl_game.core.game_logic.object;

public class Projectile extends GameObject {
    private final float velocityX;
    private final float velocityY;
    private final float maxDistance;
    private float traveled;

    public Projectile(float x, float y, float dirX, float dirY, float speed) {
        super(x, y, speed);

        float length = (float) Math.sqrt(dirX * dirX + dirY * dirY);
        velocityX = dirX / length;
        velocityY = dirY / length;
        maxDistance = 2f;
        traveled = 0;
    }

    @Override
    public void update(double deltaTime) {
        x += velocityX * speed * (float) deltaTime;
        y += velocityY * speed * (float) deltaTime;
        traveled += speed * (float) deltaTime;
    }

    public boolean isExpired() {
        return traveled >= maxDistance;
    }
}
