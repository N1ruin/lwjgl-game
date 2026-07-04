package by.niruin.lwjgl_game.core.graphic.render;

import by.niruin.lwjgl_game.core.game_logic.world.GameWorld;
import by.niruin.lwjgl_game.core.game_logic.object.Enemy;
import by.niruin.lwjgl_game.core.game_logic.object.Projectile;
import by.niruin.lwjgl_game.core.graphic.mesh.Mesh;
import by.niruin.lwjgl_game.core.graphic.mesh.Round;
import by.niruin.lwjgl_game.core.graphic.mesh.Square;
import by.niruin.lwjgl_game.core.graphic.mesh.TexturedSquare;
import by.niruin.lwjgl_game.core.graphic.render.matrix.Camera;
import by.niruin.lwjgl_game.core.graphic.shader.ShaderProgram;
import by.niruin.lwjgl_game.core.graphic.shader.ShaderRegistry;
import by.niruin.lwjgl_game.core.graphic.shader.Uniform;
import by.niruin.lwjgl_game.core.texture.TextureRegistry;
import org.joml.Matrix4f;

import static by.niruin.lwjgl_game.constant.ShaderRegistryKeys.BACKGROUND_SHADER;
import static by.niruin.lwjgl_game.constant.ShaderRegistryKeys.DEFAULT_SHADER;
import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private final ShaderRegistry shaderRegistry;
    private final TextureRegistry textureRegistry;
    private final Camera camera;
    private Mesh playerMesh;
    private Mesh enemyMesh;
    private Mesh backgroundMesh;
    private Mesh projectileMesh;
    private final Matrix4f backgroundProjection;

    public Renderer(ShaderRegistry shaderRegistry, TextureRegistry textureRegistry) {
        this.shaderRegistry = shaderRegistry;
        this.textureRegistry = textureRegistry;
        this.camera = new Camera();
        this.backgroundProjection = new Matrix4f().ortho(-1f, 1f, -1f, 1f, -1f, 1f);
    }

    public void init() {
        playerMesh = new Round(0.05f, 32);
        playerMesh.init();

        enemyMesh = new Round(0.04f, 16);
        enemyMesh.init();

        projectileMesh = new Square(0.02f, 0.02f);
        projectileMesh.init();

        backgroundMesh = new TexturedSquare();
        backgroundMesh.init();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void updateProjection(int width, int height) {
        float aspect = (float) width / height;
        camera.updateProjection(width, height);
        backgroundProjection.identity().ortho(-aspect, aspect, -1f, 1f, -1f, 1f);
    }

    public void render(GameWorld world, boolean gameOver) {
        if (gameOver) {
            glClearColor(0.3f, 0.05f, 0.05f, 1.0f);
        } else {
            glClearColor(0.1f, 0.1f, 0.15f, 1.0f);
        }
        glClear(GL_COLOR_BUFFER_BIT);

        if (gameOver) {
            return;
        }

        renderBackground();
        renderGameObjects(world);
    }

    private void renderBackground() {
        ShaderProgram program = shaderRegistry.getProgram(BACKGROUND_SHADER);
        program.use();

        float aspect = camera.getProjectionMatrix().get(0, 0);
        Matrix4f model = new Matrix4f().scale(aspect, 1f, 1f);
        Matrix4f projection = new Matrix4f().ortho(-aspect, aspect, -1f, 1f, -1f, 1f);

        Uniform.setMat4(program.getProgramId(), "uProjection", projection);
        Uniform.setMat4(program.getProgramId(), "uModel", model);

        textureRegistry.get("background").bind();
        backgroundMesh.bind();
        glDrawElements(GL_TRIANGLES, backgroundMesh.getIndexCount(), GL_UNSIGNED_INT, 0);
        backgroundMesh.unbind();
    }

    private void renderGameObjects(GameWorld world) {
        camera.updateView(0, 0);

        ShaderProgram program = shaderRegistry.getProgram(DEFAULT_SHADER);
        program.use();
        Uniform.setMat4(program.getProgramId(), "uProjectionView", camera.getProjectionViewMatrix());

        Uniform.setVec4(program.getProgramId(), "uColor", 0.2f, 0.6f, 1.0f, 1.0f);
        drawObject(playerMesh, world.getPlayer().getX(), world.getPlayer().getY(), program);

        Uniform.setVec4(program.getProgramId(), "uColor", 1.0f, 0.2f, 0.2f, 1.0f);
        for (Enemy enemy : world.getEnemies()) {
            drawObject(enemyMesh, enemy.getX(), enemy.getY(), program);
        }

        Uniform.setVec4(program.getProgramId(), "uColor", 1.0f, 1.0f, 0.2f, 1.0f);
        for (Projectile projectile : world.getProjectiles()) {
            drawObject(projectileMesh, projectile.getX(), projectile.getY(), program);
        }

        Uniform.setVec4(program.getProgramId(), "uColor", 1.0f, 0.5f, 0.0f, 1.0f);
        for (Projectile projectile : world.getEnemyProjectiles()) {
            drawObject(projectileMesh, projectile.getX(), projectile.getY(), program);
        }
    }

    private void drawObject(Mesh mesh, float x, float y, ShaderProgram program) {
        Matrix4f model = new Matrix4f().translate(x, y, 0);
        Uniform.setMat4(program.getProgramId(), "uModel", model);

        mesh.bind();
        glDrawElements(GL_TRIANGLES, mesh.getIndexCount(), GL_UNSIGNED_INT, 0);
        mesh.unbind();
    }

    public void delete() {
        playerMesh.delete();
        enemyMesh.delete();
        projectileMesh.delete();
        backgroundMesh.delete();
    }
}