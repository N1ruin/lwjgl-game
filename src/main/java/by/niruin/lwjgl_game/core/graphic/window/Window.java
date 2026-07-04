package by.niruin.lwjgl_game.core.graphic.window;

import by.niruin.lwjgl_game.core.configuration.OpenGLContextSettings;
import by.niruin.lwjgl_game.core.configuration.WindowSettings;
import by.niruin.lwjgl_game.exception.GLFWException;
import lombok.Getter;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

@Getter
public class Window {
    private final OpenGLContextSettings contextSettings;
    private final WindowSettings windowSettings;
    private long descriptor;
    private int width;
    private int height;

    public Window(OpenGLContextSettings contextSettings, WindowSettings windowSettings) {
        this.contextSettings = contextSettings;
        this.windowSettings = windowSettings;
        this.width = windowSettings.getWidth();
        this.height = windowSettings.getHeight();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new GLFWException("GLFW initialization error");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        var isResizable = windowSettings.isResizable() ? GLFW_TRUE : GLFW_FALSE;
        glfwWindowHint(GLFW_RESIZABLE, isResizable);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, contextSettings.getGlMajorVersion());
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, contextSettings.getGlMinorVersion());
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        long monitor = windowSettings.isFullscreen() ? glfwGetPrimaryMonitor() : MemoryUtil.NULL;

        descriptor = glfwCreateWindow(
                windowSettings.getWidth(),
                windowSettings.getHeight(),
                windowSettings.getName(),
                monitor,
                MemoryUtil.NULL);

        if (descriptor == MemoryUtil.NULL) {
            throw new GLFWException("Failed to create a window");
        }

        if (!windowSettings.isFullscreen()) {
            var vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    descriptor,
                    (vidMode.width() - windowSettings.getWidth()) / 2,
                    (vidMode.height() - windowSettings.getHeight()) / 2);
        }

        glfwMakeContextCurrent(descriptor);
        GL.createCapabilities();

        glfwSwapInterval(contextSettings.isVsyncEnabled() ? GLFW_TRUE : GLFW_FALSE);
        glfwShowWindow(descriptor);

        glfwSetFramebufferSizeCallback(descriptor, (window, w, h) -> {
            this.width = w;
            this.height = h;
            glViewport(0, 0, w, h);
        });

        glfwSetKeyCallback(descriptor, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(descriptor);
    }

    public void update() {
        glfwSwapBuffers(descriptor);
        glfwPollEvents();
    }

    public void destroy() {
        glfwDestroyWindow(descriptor);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
