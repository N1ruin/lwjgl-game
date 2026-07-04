package by.niruin.lwjgl_game.core.configuration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OpenGLContextSettings {
    private final int glMinorVersion = 3;
    private final int glMajorVersion = 3;
    @Builder.Default
    private boolean isVsyncEnabled = true;
    @Builder.Default
    private int targetFPS = 60;
}
