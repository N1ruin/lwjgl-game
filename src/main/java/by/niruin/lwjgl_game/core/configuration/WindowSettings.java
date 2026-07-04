package by.niruin.lwjgl_game.core.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class WindowSettings {
    private int width;
    private int height;
    private String name;
    private boolean isFullscreen;
    private boolean isResizable;
}
