package by.niruin.lwjgl_game;

import by.niruin.lwjgl_game.core.engine.GameEngine;
import by.niruin.lwjgl_game.core.engine.Timer;
import by.niruin.lwjgl_game.core.graphic.render.Renderer;
import by.niruin.lwjgl_game.core.graphic.shader.ShaderInitializer;
import by.niruin.lwjgl_game.core.graphic.shader.ShaderLoader;
import by.niruin.lwjgl_game.core.graphic.shader.ShaderRegistry;
import by.niruin.lwjgl_game.core.graphic.window.Window;
import by.niruin.lwjgl_game.core.input.KeyboardInputSystem;
import by.niruin.lwjgl_game.core.game_logic.Game;
import by.niruin.lwjgl_game.core.input.MouseInputSystem;
import by.niruin.lwjgl_game.core.texture.TextureInitializer;
import by.niruin.lwjgl_game.core.texture.TextureRegistry;
import by.niruin.lwjgl_game.loader.SettingsLoader;

public class Main {
    public static void main(String[] args) {
        var settingsLoader = new SettingsLoader();

        var windowSettings = settingsLoader.loadWindowSettings();
        var glContextSettings = settingsLoader.loadOpenGlContext();

        var window = new Window(glContextSettings, windowSettings);
        window.init();


        var keyBinding = settingsLoader.loadKeyBindings();
        var keyboardInputSystem = new KeyboardInputSystem(keyBinding, window.getDescriptor());
        var mouseInputSystem = new MouseInputSystem(window.getDescriptor());
        var timer = new Timer();

        var shaderRegistry = new ShaderRegistry();
        var shaderLoader = new ShaderLoader();
        var shaderInitializer = new ShaderInitializer(shaderRegistry, shaderLoader);
        shaderInitializer.initialize();

        var textureRegistry = new TextureRegistry();
        var textureInitializer = new TextureInitializer(textureRegistry);
        textureInitializer.initialize();

        var renderer = new Renderer(shaderRegistry, textureRegistry);
        renderer.init();

        var game = new Game(keyboardInputSystem);
        var gameEngine = new GameEngine(window, game, renderer, keyboardInputSystem, mouseInputSystem, timer);

        gameEngine.start();
        gameEngine.destroy();
    }
}
